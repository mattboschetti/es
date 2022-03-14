package com.mattboschetti.sandbox.es.inventory.application.command;

import com.mattboschetti.sandbox.es.domain.Command;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateInventoryItem extends Command {
    public final UUID inventoryItemId;
    public final String name;
    public final BigDecimal unitPrice;

    public CreateInventoryItem(UUID inventoryItemId, String name, BigDecimal unitPrice) {
        this.inventoryItemId = inventoryItemId;
        this.name = name;
        this.unitPrice = unitPrice;
    }
}
