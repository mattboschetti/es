package com.mattboschetti.sandbox.es.inventory.domain;

import java.util.UUID;

import com.mattboschetti.sandbox.es.common.domain.Repository;

public interface InventoryItemRepository extends Repository<InventoryItem> {
    void save(InventoryItem inventoryItem);

    InventoryItem getById(UUID id);
}
