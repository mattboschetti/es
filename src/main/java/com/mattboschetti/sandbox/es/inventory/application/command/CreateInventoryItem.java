package com.mattboschetti.sandbox.es.inventory.application.command;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import com.mattboschetti.sandbox.es.common.domain.Command;

public class CreateInventoryItem implements Command {
    public final String name;
    public final String category;
    public final BigDecimal unitPrice;

    @ConstructorProperties({"name", "category", "unitPrice"})
    public CreateInventoryItem(String name, String category, BigDecimal unitPrice) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
    }
}
