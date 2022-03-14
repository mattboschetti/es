package com.mattboschetti.sandbox.es.eventstore;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, List<Event> events);

    List<Event> getEventsById(List<UUID> ids);

    List<Event> getEventsForAggregate(UUID aggregateId);

    List<Event> getAll();

    class AggregateNotFoundException extends RuntimeException {}

    class ConcurrencyException extends RuntimeException {}
}
