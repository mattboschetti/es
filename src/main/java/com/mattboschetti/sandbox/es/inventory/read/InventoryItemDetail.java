package com.mattboschetti.sandbox.es.inventory.read;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

public class InventoryItemDetail implements Persistable<UUID> {
    @Id
    public UUID id;
    public String name;
    public int currentCount;
    public String unitPrice;
    public int version;

    public InventoryItemDetail(UUID id, String name, int currentCount, String unitPrice, int version) {
        this.id = id;
        this.name = name;
        this.currentCount = currentCount;
        this.unitPrice = unitPrice;
        this.version = version;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return version == 1;
    }
}
