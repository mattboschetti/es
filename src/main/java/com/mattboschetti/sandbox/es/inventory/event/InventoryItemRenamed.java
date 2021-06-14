package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.eventstore.Event;

import java.util.UUID;

public class InventoryItemRenamed extends Event {
    public final UUID id;
    public final String newName;

    public InventoryItemRenamed(UUID id, String newName) {
        this.id = id;
        this.newName = newName;
    }
}
