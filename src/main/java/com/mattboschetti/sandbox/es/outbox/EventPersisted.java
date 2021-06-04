package com.mattboschetti.sandbox.es.outbox;

import java.util.UUID;

public record EventPersisted(UUID eventId) {}
