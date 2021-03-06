package com.mattboschetti.sandbox.es.common.eventstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
public class EventStoreDao implements EventStore {

    private static final Logger LOG = LoggerFactory.getLogger(EventStoreDao.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventStoreDao(DataSource dataSource, ObjectMapper objectMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public EventStream getAll() {
        return namedParameterJdbcTemplate.query("select * from event_store", this::rowMapper);
    }

    @Override
    public EventStream getEventsById(List<UUID> ids) {
        return namedParameterJdbcTemplate.query("select * from event_store where id in (:ids)", Map.of("ids", ids), this::rowMapper);
    }

    @Override
    public EventStream getEventsForAggregate(UUID aggregateId) {
        return namedParameterJdbcTemplate.query("select * from event_store where stream like :stream", Map.of("stream", aggregateId.toString()), this::rowMapper);
    }

    @Override
    @Transactional
    public void saveEvents(EventStreamId eventStreamId, List<DomainEvent> events) {
        var currentVersion = new AtomicInteger(eventStreamId.version());
        var parameters = events.stream()
                .map(e -> parameterMapper(e, eventStreamId.id(), currentVersion.incrementAndGet()))
                .toArray(MapSqlParameterSource[]::new);

        try {
            namedParameterJdbcTemplate.batchUpdate("insert into event_store (id, stream, type, event, version, created_at) values (:id, :stream, :type, :event::json, :version, :created_at)", parameters);
        } catch (DataAccessException e) {
            if (e.getCause().getMessage().contains("event_store_stream_version_uindex")) {
                throw new ConcurrencyException();
            }
            throw new RuntimeException("Something went wrong persisting events");
        }

        Stream.of(parameters)
                .map(p -> (UUID) p.getValue("id"))
                .map(EventPersisted::new)
                .forEach(applicationEventPublisher::publishEvent);
    }

    private MapSqlParameterSource parameterMapper(DomainEvent e, UUID stream, int version) {
        var params = new MapSqlParameterSource();
        params.addValue("id", UUID.randomUUID());
        params.addValue("stream", stream);
        params.addValue("type", e.getClass().getName());
        try {
            params.addValue("event", objectMapper.writeValueAsString(e));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error parsing event to json", ex);
        }
        params.addValue("version", version);
        params.addValue("created_at", LocalDateTime.now());
        return params;
    }

    private EventStream rowMapper(ResultSet resultSet) {
        try {
            var result = new ArrayList<DomainEvent>();
            var version = 0;
            var stream = "";
            while (resultSet.next()) {
                version = resultSet.getInt("version");
                stream = resultSet.getString("stream");
                var type = Class.forName(resultSet.getString("type"));
                var json = resultSet.getString("event");
                result.add((DomainEvent) objectMapper.readValue(json, type));
            }
            if (result.isEmpty()) {
                throw new AggregateNotFoundException();
            }
            return new EventStream(new EventStreamId(UUID.fromString(stream), version), result);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Not sure what to do, could not parse stored json", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Not sure what to do, found a stored event for which there's not available type in the classpath", e);
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong with the database connection", e);
        }
    }
}
