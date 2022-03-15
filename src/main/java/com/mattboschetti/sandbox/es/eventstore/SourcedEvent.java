package com.mattboschetti.sandbox.es.eventstore;

import java.time.LocalDateTime;
import java.util.UUID;

public record SourcedEvent(UUID uuid,
                           EventStreamId stream,
                           String type,
                           DomainEvent event,
                           int version,
                           LocalDateTime createdAt) {
}
