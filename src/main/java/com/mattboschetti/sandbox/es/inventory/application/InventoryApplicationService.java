package com.mattboschetti.sandbox.es.inventory.application;

import com.mattboschetti.sandbox.es.inventory.domain.InventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.CheckInItemsToInventory;
import com.mattboschetti.sandbox.es.inventory.application.command.CreateInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.DeactivateInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.RemoveItemsFromInventory;
import com.mattboschetti.sandbox.es.inventory.application.command.RenameInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.RepriceInventoryItem;
import com.mattboschetti.sandbox.es.domain.Repository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class InventoryApplicationService {

    private final Repository<InventoryItem> repository;

    public InventoryApplicationService(Repository<InventoryItem> repository) {
        this.repository = repository;
    }

    public void handle(CreateInventoryItem message) {
        var item = new InventoryItem(message.inventoryItemId, message.name, message.unitPrice);
        repository.save(item, -1);
    }

    public void handle(DeactivateInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.deactivate();
        repository.save(item, message.originalVersion);
    }

    public void handle(RemoveItemsFromInventory message) {
        var item = repository.getById(message.inventoryItemId);
        item.remove(message.count);
        repository.save(item, message.originalVersion);
    }

    public void handle(CheckInItemsToInventory message) {
        var item = repository.getById(message.inventoryItemId);
        item.checkIn(message.count);
        repository.save(item, message.originalVersion);
    }

    public void handle(RenameInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.changeName(message.newName);
        repository.save(item, message.originalVersion);
    }

    public void handle(RepriceInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.reprice(message.unitPrice);
        repository.save(item, message.originalVersion);
    }
}
