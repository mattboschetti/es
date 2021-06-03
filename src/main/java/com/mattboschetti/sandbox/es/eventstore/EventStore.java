package com.mattboschetti.sandbox.es.eventstore;

import com.mattboschetti.sandbox.es.event.Event;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion);
    List<Event> getEventsForAggregate(UUID aggregateId);

    public static class AggregateNotFoundException extends RuntimeException {}

    public static class ConcurrencyException extends RuntimeException {}
}
