package com.mattboschetti.sandbox.es.inventory.read;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

public class InventoryItemList implements Persistable<UUID> {
    @Id
    public UUID id;
    public String name;
    public int version;

    public InventoryItemList(UUID id, String name, int version) {
        this.id = id;
        this.name = name;
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
