package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.domain.Command;

import java.util.UUID;

public class RenameInventoryItem extends Command {
    public final UUID inventoryItemId;
    public final String newName;
    public final int originalVersion;

    public RenameInventoryItem(UUID inventoryItemId, String newName, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.newName = newName;
        this.originalVersion = originalVersion;
    }
}