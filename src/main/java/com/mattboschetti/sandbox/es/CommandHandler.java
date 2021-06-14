package com.mattboschetti.sandbox.es;

import com.mattboschetti.sandbox.es.command.CheckInItemsToInventory;
import com.mattboschetti.sandbox.es.command.CreateInventoryItem;
import com.mattboschetti.sandbox.es.command.DeactivateInventoryItem;
import com.mattboschetti.sandbox.es.command.RemoveItemsFromInventory;
import com.mattboschetti.sandbox.es.command.RenameInventoryItem;
import com.mattboschetti.sandbox.es.command.RepriceInventoryItem;
import com.mattboschetti.sandbox.es.domain.InventoryItem;
import com.mattboschetti.sandbox.es.domain.Repository;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {

    private final Repository<InventoryItem> repository;

    public CommandHandler(Repository<InventoryItem> repository) {
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
