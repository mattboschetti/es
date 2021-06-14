package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.eventstore.Event;

import java.util.UUID;

public class ItemsCheckedInToInventory extends Event {
    public final UUID id;
    public final int count;

    public ItemsCheckedInToInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }
}
