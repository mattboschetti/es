package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.domain.Command;

import java.util.UUID;

public class DeactivateInventoryItem implements Command {
    public final UUID inventoryItemId;
    public final int originalVersion;

    public DeactivateInventoryItem(UUID inventoryItemId, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.originalVersion = originalVersion;
    }
}
