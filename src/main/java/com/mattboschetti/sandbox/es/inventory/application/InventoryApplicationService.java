package com.mattboschetti.sandbox.es.inventory.application;

import com.mattboschetti.sandbox.es.inventory.application.command.*;
import com.mattboschetti.sandbox.es.inventory.domain.InventoryItem;
import com.mattboschetti.sandbox.es.inventory.domain.InventoryItemRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryApplicationService {

    private final InventoryItemRepository repository;

    public InventoryApplicationService(InventoryItemRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateInventoryItem message) {
        var item = new InventoryItem(message.inventoryItemId, message.name, message.unitPrice);
        repository.save(item);
    }

    public void handle(DeactivateInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.deactivate();
        repository.save(item);
    }

    public void handle(RemoveItemsFromInventory message) {
        var item = repository.getById(message.inventoryItemId);
        item.remove(message.count);
        repository.save(item);
    }

    public void handle(CheckInItemsToInventory message) {
        var item = repository.getById(message.inventoryItemId);
        item.checkIn(message.count);
        repository.save(item);
    }

    public void handle(RenameInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.changeName(message.newName);
        repository.save(item);
    }

    public void handle(RepriceInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.reprice(message.unitPrice);
        repository.save(item);
    }
}
