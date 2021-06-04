package com.mattboschetti.sandbox.es.api;

import com.mattboschetti.sandbox.es.CommandHandler;
import com.mattboschetti.sandbox.es.readmodel.InventoryItemDetail;
import com.mattboschetti.sandbox.es.readmodel.InventoryItemDetailRepository;
import com.mattboschetti.sandbox.es.readmodel.InventoryItemList;
import com.mattboschetti.sandbox.es.readmodel.InventoryItemListRepository;
import com.mattboschetti.sandbox.es.readmodel.ReadModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final InventoryItemListRepository itemListRepository;
    private final InventoryItemDetailRepository itemDetailRepository;
    private final ReadModelService readModelService;

    public InventoryController(CommandHandler commandHandler, InventoryItemListRepository itemListRepository, InventoryItemDetailRepository itemDetailRepository, ReadModelService readModelService) {
        this.commandHandler = commandHandler;
        this.itemListRepository = itemListRepository;
        this.itemDetailRepository = itemDetailRepository;
        this.readModelService = readModelService;
    }

    @PostMapping
    public void newInventoryItem(@RequestParam("name") String name) {
        commandHandler.handle(new CreateInventoryItem(UUID.randomUUID(), name));
    }

    @DeleteMapping("/{uuid}")
    public void deactivateInventoryItem(@PathVariable("uuid") UUID uuid, @RequestParam("version") int version) {
        commandHandler.handle(new DeactivateInventoryItem(uuid, version));
    }

    @PostMapping("/{uuid}/remove")
    public void removeItemsFromInventory(@PathVariable("uuid") UUID uuid, @RequestParam("count") int count, @RequestParam("version") int version) {
        commandHandler.handle(new RemoveItemsFromInventory(uuid, count, version));
    }

    @PostMapping("/{uuid}/checkin")
    public void checkinItemsToInventory(@PathVariable("uuid") UUID uuid, @RequestParam("count") int count, @RequestParam("version") int version) {
        commandHandler.handle(new CheckInItemsToInventory(uuid, count, version));
    }

    @PutMapping("/{uuid}")
    public void renameItem(@PathVariable("uuid") UUID uuid, @RequestParam("name") String name, @RequestParam("version") int version) {
        commandHandler.handle(new RenameInventoryItem(uuid, name, version));
    }

    @GetMapping
    public Iterable<InventoryItemList> getItems() {
        return itemListRepository.findAll();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InventoryItemDetail> getItem(@PathVariable("uuid") UUID uuid) {
        return itemDetailRepository.findById(uuid).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/rebuild")
    public void rebuildAll() {
        readModelService.rebuildAll();
    }

    @PostMapping("/rebuild/{uuid}")
    public void rebuildAll(@RequestParam("uuid") UUID uuid) {
        readModelService.rebuildAggregate(uuid);
    }
}
