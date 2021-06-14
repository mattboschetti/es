package com.mattboschetti.sandbox.es.inventory.command;

import com.mattboschetti.sandbox.es.domain.Command;

import java.math.BigDecimal;
import java.util.UUID;

public class RepriceInventoryItem extends Command {
    public final UUID inventoryItemId;
    public final BigDecimal unitPrice;
    public final int originalVersion;

    public RepriceInventoryItem(UUID inventoryItemId, BigDecimal unitPrice, int originalVersion) {
        this.inventoryItemId = inventoryItemId;
        this.unitPrice = unitPrice;
        this.originalVersion = originalVersion;
    }
}
