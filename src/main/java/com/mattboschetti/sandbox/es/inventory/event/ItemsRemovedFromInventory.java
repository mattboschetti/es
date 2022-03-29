package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.common.eventstore.DomainEvent;

import java.util.UUID;

public class ItemsRemovedFromInventory implements DomainEvent {
    public final UUID id;
    public final int count;

    public ItemsRemovedFromInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }

    @Override
    public int version() {
        return 0;
    }
}
