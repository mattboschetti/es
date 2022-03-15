package com.mattboschetti.sandbox.es.inventory.application.command;

import java.math.BigDecimal;

import com.mattboschetti.sandbox.es.domain.Command;

public class CreateInventoryItem implements Command {
    public final String name;
    public final BigDecimal unitPrice;

    public CreateInventoryItem(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }
}
