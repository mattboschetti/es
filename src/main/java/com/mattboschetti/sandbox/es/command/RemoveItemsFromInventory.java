package com.mattboschetti.sandbox.es.command;

import java.util.UUID;

public class RemoveItemsFromInventory extends Command {
    public final UUID inventoryItemId;
    public final int count;
    public final int originalVersion;

    public RemoveItemsFromInventory(UUID inventoryItemId, int count, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.count = count;
        this.originalVersion = originalVersion;
    }
}
