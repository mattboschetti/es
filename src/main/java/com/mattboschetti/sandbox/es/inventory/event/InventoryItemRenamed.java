package com.mattboschetti.sandbox.es.inventory.event;

import java.util.UUID;

import com.mattboschetti.sandbox.es.eventstore.DomainEvent;

public class InventoryItemRenamed implements DomainEvent {
    public final UUID id;
    public final String newName;

    public InventoryItemRenamed(UUID id, String newName) {
        this.id = id;
        this.newName = newName;
    }

    @Override
    public int version() {
        return 0;
    }
}
