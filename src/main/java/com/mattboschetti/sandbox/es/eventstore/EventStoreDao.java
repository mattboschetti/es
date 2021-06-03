package com.mattboschetti.sandbox.es.eventstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattboschetti.sandbox.es.event.Event;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class EventStoreDao implements EventStore {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ObjectMapper objectMapper;

    public EventStoreDao(DataSource dataSource, ObjectMapper objectMapper) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Event> getEventsForAggregate(UUID aggregateId) {
        return namedParameterJdbcTemplate.query("select * from sourced_events where stream like :stream", Map.of("stream", aggregateId.toString()), this::rowMapper);
    }

    @Override
    public void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion) {
        var parameters = events.stream()
                .map(e -> parameterMapper(e, aggregateId))
                .toArray(MapSqlParameterSource[]::new);

        try {
            namedParameterJdbcTemplate.batchUpdate("insert into sourced_events (id, stream, type, event, version, created_at) values (:id, :stream, :type, :event::json, :version, :created_at)", parameters);
        } catch (DataAccessException e) {
            throw new ConcurrencyException();
        }

        // publish current event to the bus for further processing by subscribers
        //_publisher.Publish(event);
    }

    private MapSqlParameterSource parameterMapper(Event e, UUID aggregateId) {
        var params = new MapSqlParameterSource();
        params.addValue("id", UUID.randomUUID());
        params.addValue("stream", aggregateId);
        params.addValue("type", e.getClass().getName());
        try {
            params.addValue("event", objectMapper.writeValueAsString(e));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error parsing event to json", ex);
        }
        params.addValue("version", e.version);
        params.addValue("created_at", LocalDateTime.now());
        return params;
    }

    private ArrayList<Event> rowMapper(ResultSet resultSet) {
        try {
            var result = new ArrayList<Event>();
            while (resultSet.next()) {
                var type = Class.forName(resultSet.getString("type"));
                var json = resultSet.getString("event");
                result.add((Event) objectMapper.readValue(json, type));
            }
            if (result.isEmpty()) {
                throw new AggregateNotFoundException();
            }
            return result;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Not sure what to do, could not parse stored json", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Not sure what to do, found a stored event for which there's not available type in the classpath", e);
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong with the database connection", e);
        }
    }
}