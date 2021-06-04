package com.mattboschetti.sandbox.es.outbox;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class OutboxDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OutboxDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(UUID eventId) {
        var params = Map.of("id", eventId, "created_at", LocalDateTime.now());
        jdbcTemplate.update("insert into outbox (id, created_at) values (:id, :created_at)", params);
    }

    public void delete(List<UUID> eventIds) {
        var params = Map.of("id", eventIds);
        jdbcTemplate.update("delete from outbox where id in (:id)", params);
    }

    public List<UUID> getAll() {
        return jdbcTemplate.query("select id from outbox order by created_at asc", rm -> {
            var ids = new ArrayList<UUID>();
            while(rm.next()) {
                ids.add(UUID.fromString(rm.getString(1)));
            }
            return ids;
        });
    }
}
