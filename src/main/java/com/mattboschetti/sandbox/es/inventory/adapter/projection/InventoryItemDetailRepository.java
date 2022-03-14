package com.mattboschetti.sandbox.es.inventory.adapter.projection;

import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItemDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InventoryItemDetailRepository extends CrudRepository<InventoryItemDetail, UUID> {
}
