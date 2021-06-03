package com.mattboschetti.sandbox.es.command;

import java.util.UUID;

public class DeactivateInventoryItem extends Command {
    public final UUID inventoryItemId;
    public final int originalVersion;

    public DeactivateInventoryItem(UUID inventoryItemId, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.originalVersion = originalVersion;
    }
}
