package com.mattboschetti.sandbox.es.common.eventstore;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(EventStreamId eventStreamId, List<DomainEvent> events);

    EventStream getEventsById(List<UUID> ids);

    EventStream getEventsForAggregate(UUID aggregateId);

    EventStream getAll();

    class AggregateNotFoundException extends RuntimeException {}

    class ConcurrencyException extends RuntimeException {}
}
