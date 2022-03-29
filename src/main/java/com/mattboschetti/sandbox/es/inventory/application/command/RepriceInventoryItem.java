package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.common.domain.Command;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.UUID;

public class RepriceInventoryItem implements Command {
    public final UUID inventoryItemId;
    public final BigDecimal unitPrice;
    public final int originalVersion;

    @ConstructorProperties({"inventoryItemId", "unitPrice", "originalVersion"})
    public RepriceInventoryItem(UUID inventoryItemId, BigDecimal unitPrice, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.unitPrice = unitPrice;
        this.originalVersion = originalVersion;
    }
}
