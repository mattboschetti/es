package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.eventstore.DomainEvent;

import java.util.UUID;

public class ItemsCheckedInToInventory implements DomainEvent {
    public final UUID id;
    public final int count;

    public ItemsCheckedInToInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }

    @Override
    public int version() {
        return 0;
    }
}
