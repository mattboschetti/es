package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.common.domain.Command;

import java.beans.ConstructorProperties;
import java.util.UUID;

public class DeactivateInventoryItem implements Command {
    public final UUID inventoryItemId;
    public final int originalVersion;

    @ConstructorProperties({"inventoryItemId", "originalVersion"})
    public DeactivateInventoryItem(UUID inventoryItemId, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.originalVersion = originalVersion;
    }
}
