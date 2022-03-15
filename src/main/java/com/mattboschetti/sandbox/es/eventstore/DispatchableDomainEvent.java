package com.mattboschetti.sandbox.es.eventstore;

public record DispatchableDomainEvent(EventStreamId eventStreamId, DomainEvent event) {
}
