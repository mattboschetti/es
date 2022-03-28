package com.mattboschetti.sandbox.es.inventory.adapter.rest;

import com.mattboschetti.sandbox.es.inventory.application.InventoryApplicationService;
import com.mattboschetti.sandbox.es.inventory.application.command.CheckInItemsToInventory;
import com.mattboschetti.sandbox.es.inventory.application.command.CreateInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.DeactivateInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.RemoveItemsFromInventory;
import com.mattboschetti.sandbox.es.inventory.application.command.RenameInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.command.RepriceInventoryItem;
import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItemDetail;
import com.mattboschetti.sandbox.es.inventory.adapter.projection.InventoryItemDetailRepository;
import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItemList;
import com.mattboschetti.sandbox.es.inventory.adapter.projection.InventoryItemListRepository;
import com.mattboschetti.sandbox.es.inventory.application.InventoryQueryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(value = "/inventory/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {

    private final InventoryApplicationService inventoryCommandHandler;
    private final InventoryItemListRepository itemListRepository;
    private final InventoryItemDetailRepository itemDetailRepository;
    private final InventoryQueryService readModelService;

    public InventoryController(InventoryApplicationService inventoryCommandHandler, InventoryItemListRepository itemListRepository, InventoryItemDetailRepository itemDetailRepository, InventoryQueryService readModelService) {
        this.inventoryCommandHandler = inventoryCommandHandler;
        this.itemListRepository = itemListRepository;
        this.itemDetailRepository = itemDetailRepository;
        this.readModelService = readModelService;
    }

    @Operation(summary = "Add a new item to the inventory")
    @PostMapping
    public void newInventoryItem(@RequestParam("name") String name, @RequestParam String unitPrice) {
        inventoryCommandHandler.handle(new CreateInventoryItem(name, new BigDecimal(unitPrice)));
    }

    @Operation(summary = "Remove item from inventory")
    @DeleteMapping("/{uuid}")
    public void deactivateInventoryItem(@PathVariable("uuid") UUID uuid, @RequestParam("version") int version) {
        inventoryCommandHandler.handle(new DeactivateInventoryItem(uuid, version));
    }

    @Operation(summary = "Reduces the amount of an item in the inventory")
    @PostMapping("/{uuid}/remove")
    public void removeItemsFromInventory(@PathVariable("uuid") UUID uuid, @RequestParam("count") int count, @RequestParam("version") int version) {
        inventoryCommandHandler.handle(new RemoveItemsFromInventory(uuid, count, version));
    }

    @Operation(summary = "Increases the amount of an item in the inventory")
    @PostMapping("/{uuid}/checkin")
    public void checkinItemsToInventory(@PathVariable("uuid") UUID uuid, @RequestParam("count") int count, @RequestParam("version") int version) {
        inventoryCommandHandler.handle(new CheckInItemsToInventory(uuid, count, version));
    }

    @Operation(summary = "Rename an item")
    @PutMapping("/{uuid}/name")
    public void renameItem(@PathVariable("uuid") UUID uuid, @RequestParam("name") String name, @RequestParam("version") int version) {
        inventoryCommandHandler.handle(new RenameInventoryItem(uuid, name, version));
    }

    @Operation(summary = "Reprice an item")
    @PutMapping("/{uuid}/price")
    public void repriceItem(@PathVariable("uuid") UUID uuid, @RequestParam("price") String price, @RequestParam("version") int version) {
        inventoryCommandHandler.handle(new RepriceInventoryItem(uuid, new BigDecimal(price), version));
    }

    @Operation(summary = "Get all inventory items")
    @GetMapping
    public Iterable<InventoryItemList> getItems() {
        return itemListRepository.findAll();
    }

    @Operation(summary = "Get inventory item by id")
    @GetMapping("/{uuid}")
    public ResponseEntity<InventoryItemDetail> getItem(@PathVariable("uuid") UUID uuid) {
        return itemDetailRepository.findById(uuid).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
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
