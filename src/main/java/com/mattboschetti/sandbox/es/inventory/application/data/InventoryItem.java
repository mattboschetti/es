package com.mattboschetti.sandbox.es.inventory.application.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.UUID;

public class InventoryItem implements Persistable<UUID> {
    @Id
    public UUID id;
    public String name;
    public String category;
    public BigDecimal unitPrice;
    public int quantity;
    public int version;

    public InventoryItem(UUID id, String name, String category, BigDecimal unitPrice, int quantity, int version) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.version = version;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return version == 0;
    }
}
