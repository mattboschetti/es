package com.mattboschetti.sandbox.es.event;

import java.util.UUID;

public class InventoryItemCreated extends Event {
    public final UUID id;
    public final String name;

    public InventoryItemCreated(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
