package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.eventstore.Event;

import java.util.UUID;

public class ItemsRemovedFromInventory extends Event {
    public final UUID id;
    public final int count;

    public ItemsRemovedFromInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }
}
