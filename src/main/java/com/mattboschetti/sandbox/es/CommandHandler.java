package com.mattboschetti.sandbox.es;

import com.mattboschetti.sandbox.es.domain.InventoryItem;
import org.springframework.stereotype.Component;
import com.mattboschetti.sandbox.es.command.CheckInItemsToInventory;
import com.mattboschetti.sandbox.es.command.CreateInventoryItem;
import com.mattboschetti.sandbox.es.command.DeactivateInventoryItem;
import com.mattboschetti.sandbox.es.command.RemoveItemsFromInventory;
import com.mattboschetti.sandbox.es.command.RenameInventoryItem;
import com.mattboschetti.sandbox.es.domain.Repository;

@Component
public class CommandHandler {

    private final Repository<InventoryItem> repository;

    public CommandHandler(Repository<InventoryItem> repository) {
        this.repository = repository;
    }

    public void Handle(CreateInventoryItem message) {
        var item = new InventoryItem(message.inventoryItemId, message.name);
        repository.save(item, -1);
    }

    public void Handle(DeactivateInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.deactivate();
        repository.save(item, message.originalVersion);
    }

    public void Handle(RemoveItemsFromInventory message) {
        var item = repository.getById(message.inventoryItemId);
        item.remove(message.count);
        repository.save(item, message.originalVersion);
    }

    public void Handle(CheckInItemsToInventory message) {
        var item = repository.getById(message.inventoryItemId);
        item.checkIn(message.count);
        repository.save(item, message.originalVersion);
    }

    public void Handle(RenameInventoryItem message) {
        var item = repository.getById(message.inventoryItemId);
        item.changeName(message.newName);
        repository.save(item, message.originalVersion);
    }

}
