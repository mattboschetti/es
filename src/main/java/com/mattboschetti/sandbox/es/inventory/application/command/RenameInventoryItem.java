package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.common.domain.Command;

import java.beans.ConstructorProperties;
import java.util.UUID;

public class RenameInventoryItem implements Command {
    public final UUID inventoryItemId;
    public final String newName;
    public final int originalVersion;

    @ConstructorProperties({"inventoryItemId", "newName", "originalVersion"})
    public RenameInventoryItem(UUID inventoryItemId, String newName, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.newName = newName;
        this.originalVersion = originalVersion;
    }
}
