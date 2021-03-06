package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.common.domain.Command;

import java.beans.ConstructorProperties;
import java.util.UUID;

public class CheckInItemsToInventory implements Command {
    public final UUID inventoryItemId;
    public final int count;
    public final int originalVersion;

    @ConstructorProperties({"inventoryItemId", "count", "originalVersion"})
    public CheckInItemsToInventory(UUID inventoryItemId, int count, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.count = count;
        this.originalVersion = originalVersion;
    }
}
