package com.mattboschetti.sandbox.es.common.eventstore;

public record DispatchableDomainEvent(EventStreamId eventStreamId, DomainEvent event) {
}
