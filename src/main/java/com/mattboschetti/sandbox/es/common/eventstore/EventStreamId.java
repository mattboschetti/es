package com.mattboschetti.sandbox.es.common.eventstore;

import java.util.UUID;

public record EventStreamId(UUID id, int version) {}
