package com.mattboschetti.sandbox.es.eventstore;

import java.util.List;

public record EventStream(int version, List<DomainEvent> events) { }


