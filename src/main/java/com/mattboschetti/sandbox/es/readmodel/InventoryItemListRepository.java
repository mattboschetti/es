package com.mattboschetti.sandbox.es.readmodel;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface InventoryItemListRepository extends CrudRepository<InventoryItemList, UUID> {
}
