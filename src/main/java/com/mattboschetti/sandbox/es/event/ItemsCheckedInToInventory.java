package com.mattboschetti.sandbox.es.event;

import java.util.UUID;

public class ItemsCheckedInToInventory extends Event {
    public final UUID id;
    public final int count;

    public ItemsCheckedInToInventory(UUID id, int count) {
        this.id = id;
        this.count = count;
    }
}
