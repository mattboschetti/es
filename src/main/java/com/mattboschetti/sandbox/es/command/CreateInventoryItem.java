package com.mattboschetti.sandbox.es.command;

import java.util.UUID;

public class CreateInventoryItem extends Command {
    public final UUID inventoryItemId;
    public final String name;

    public CreateInventoryItem(UUID inventoryItemId, String name) {
        this.inventoryItemId = inventoryItemId;
        this.name = name;
    }
}
