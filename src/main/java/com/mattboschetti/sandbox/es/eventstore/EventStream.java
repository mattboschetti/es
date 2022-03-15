package com.mattboschetti.sandbox.es.eventstore;

import java.util.List;

public record EventStream(EventStreamId id, List<DomainEvent> events) { }


