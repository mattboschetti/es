package com.mattboschetti.sandbox.es.inventory.adapter.rest;

import com.mattboschetti.sandbox.es.inventory.adapter.projection.InventoryItemViewRepository;
import com.mattboschetti.sandbox.es.inventory.application.InventoryApplicationService;
import com.mattboschetti.sandbox.es.inventory.application.InventoryQueryService;
import com.mattboschetti.sandbox.es.inventory.application.command.*;
import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItem;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/inventory/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {

    private final InventoryApplicationService inventoryCommandHandler;
    private final InventoryItemViewRepository itemListRepository;
    private final InventoryQueryService readModelService;

    public InventoryController(InventoryApplicationService inventoryCommandHandler, InventoryItemViewRepository itemListRepository, InventoryQueryService readModelService) {
        this.inventoryCommandHandler = inventoryCommandHandler;
        this.itemListRepository = itemListRepository;
        this.readModelService = readModelService;
    }

    @Operation(summary = "Add a new item to the inventory")
    @PostMapping
    public void newInventoryItem(@RequestBody CreateInventoryItem createInventoryItem) {
        inventoryCommandHandler.handle(createInventoryItem);
    }

    @Operation(summary = "Remove item from inventory")
    @DeleteMapping("/{uuid}")
    public void deactivateInventoryItem(@PathVariable("uuid") UUID uuid, @RequestParam("version") int version) {
        inventoryCommandHandler.handle(new DeactivateInventoryItem(uuid, version));
    }

    @Operation(summary = "Reduces the amount of an item in the inventory")
    @PutMapping("/{uuid}/remove")
    public void removeItemsFromInventory(@PathVariable("uuid") UUID uuid, @RequestBody RemoveItemsFromInventory removeItemsFromInventory) {
        inventoryCommandHandler.handle(removeItemsFromInventory);
    }

    @Operation(summary = "Increases the amount of an item in the inventory")
    @PutMapping("/{uuid}/checkin")
    public void checkinItemsToInventory(@PathVariable("uuid") UUID uuid, @RequestBody CheckInItemsToInventory checkInItemsToInventory) {
        inventoryCommandHandler.handle(checkInItemsToInventory);
    }

    @Operation(summary = "Rename an item")
    @PutMapping("/{uuid}/name")
    public void renameItem(@PathVariable("uuid") UUID uuid, @RequestBody RenameInventoryItem renameInventoryItem) {
        inventoryCommandHandler.handle(renameInventoryItem);
    }

    @Operation(summary = "Reprice an item")
    @PutMapping("/{uuid}/price")
    public void repriceItem(@PathVariable("uuid") UUID uuid, @RequestBody RepriceInventoryItem repriceInventoryItem) {
        inventoryCommandHandler.handle(repriceInventoryItem);
    }

    @Operation(summary = "Get all inventory items")
    @GetMapping
    public Iterable<InventoryItem> getItems() {
        return itemListRepository.findAll();
    }


    @Operation(summary = "Rebuild all aggregates")
    @PostMapping("/rebuild")
    public void rebuildAll() {
        readModelService.rebuildAll();
    }

    @Operation(summary = "Rebuild a specific aggregate")
    @PostMapping("/rebuild/{uuid}")
    public void rebuildAll(@RequestParam("uuid") UUID uuid) {
        readModelService.rebuildAggregate(uuid);
    }
}
