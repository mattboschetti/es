package com.mattboschetti.sandbox.es.readmodel;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InventoryItemDetailRepository extends CrudRepository<InventoryItemDetail, UUID> {
}
