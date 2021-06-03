package com.mattboschetti.sandbox.es.event;

import java.util.UUID;

public class ItemsRemovedFromInventory extends Event {
    public final UUID id;
    public final int count;

    public ItemsRemovedFromInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }
}
