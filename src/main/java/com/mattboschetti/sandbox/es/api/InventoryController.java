package com.mattboschetti.sandbox.es.api;

import com.mattboschetti.sandbox.es.CommandHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mattboschetti.sandbox.es.command.CheckInItemsToInventory;
import com.mattboschetti.sandbox.es.command.CreateInventoryItem;
import com.mattboschetti.sandbox.es.command.DeactivateInventoryItem;
import com.mattboschetti.sandbox.es.command.RemoveItemsFromInventory;
import com.mattboschetti.sandbox.es.command.RenameInventoryItem;

import java.util.UUID;

@RestController
@RequestMapping("/inventory/item")
public class InventoryController {

    private final CommandHandler commandHandler;

    public InventoryController(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public void newInventoryItem(@RequestParam("name") String name) {
        commandHandler.Handle(new CreateInventoryItem(UUID.randomUUID(), name));
    }

    @DeleteMapping("/{uuid}")
    public void deactivateInventoryItem(@PathVariable("uuid") UUID uuid, @RequestParam("version") int version) {
        commandHandler.Handle(new DeactivateInventoryItem(uuid, version));
    }

    @PostMapping("/{uuid}/remove")
    public void removeItemsFromInventory(@PathVariable("uuid") UUID uuid, @RequestParam("count") int count, @RequestParam("version") int version) {
        commandHandler.Handle(new RemoveItemsFromInventory(uuid, count, version));
    }

    @PostMapping("/{uuid}/checkin")
    public void checkinItemsToInventory(@PathVariable("uuid") UUID uuid, @RequestParam("count") int count, @RequestParam("version") int version) {
        commandHandler.Handle(new CheckInItemsToInventory(uuid, count, version));
    }

    @PutMapping("/{uuid}")
    public void renameItem(@PathVariable("uuid") UUID uuid, @RequestParam("name") String name, @RequestParam("version") int version) {
        commandHandler.Handle(new RenameInventoryItem(uuid, name, version));
    }

}
