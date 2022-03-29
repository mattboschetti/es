package com.mattboschetti.sandbox.es.inventory.adapter.projection;

import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InventoryItemViewRepository extends CrudRepository<InventoryItem, UUID> {

    void deleteByIdAndVersion(UUID uuid, int version);
}
