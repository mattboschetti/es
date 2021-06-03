package com.mattboschetti.sandbox.es.eventstore;

import java.time.LocalDateTime;
import java.util.UUID;

public class SourcedEvent {

    public final UUID uuid;
    public final String stream;
    public final String type;
    public final String json;
    public final int version;
    public final LocalDateTime createdAt;

    public SourcedEvent(UUID uuid, String stream, String type, String json, int version, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.stream = stream;
        this.type = type;
        this.json = json;
        this.version = version;
        this.createdAt = createdAt;
    }
}
