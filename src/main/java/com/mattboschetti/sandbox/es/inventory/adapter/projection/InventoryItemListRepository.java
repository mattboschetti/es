package com.mattboschetti.sandbox.es.inventory.adapter.projection;

import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItemList;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InventoryItemListRepository extends CrudRepository<InventoryItemList, UUID> {
}
