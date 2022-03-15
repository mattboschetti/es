package com.mattboschetti.sandbox.es.eventstore;

import java.util.UUID;

public record EventStreamId(UUID id, int version) {}
