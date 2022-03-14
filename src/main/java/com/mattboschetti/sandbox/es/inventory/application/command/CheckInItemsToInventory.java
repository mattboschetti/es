package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.domain.Command;

import java.util.UUID;

public class CheckInItemsToInventory extends Command {
    public final UUID inventoryItemId;
    public final int count;
    public final int originalVersion;

    public CheckInItemsToInventory(UUID inventoryItemId, int count, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.count = count;
        this.originalVersion = originalVersion;
    }
}
