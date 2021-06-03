package com.mattboschetti.sandbox.es.event;

import java.util.UUID;

public class InventoryItemDeactivated extends Event {
    public final UUID id;

    public InventoryItemDeactivated(UUID id)
    {
        this.id = id;
    }
}
