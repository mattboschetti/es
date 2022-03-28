package com.mattboschetti.sandbox.es.inventory.adapter.mvc;

import com.mattboschetti.sandbox.es.inventory.adapter.projection.InventoryItemListRepository;
import com.mattboschetti.sandbox.es.inventory.application.InventoryQueryService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/inventory", produces = MediaType.TEXT_HTML_VALUE)
public class InventoryViewController {

    private final InventoryQueryService inventoryQueryService;
    private final InventoryItemListRepository itemListRepository;

    public InventoryViewController(InventoryQueryService inventoryQueryService,
                                   InventoryItemListRepository itemListRepository) {
        this.inventoryQueryService = inventoryQueryService;
        this.itemListRepository = itemListRepository;
    }

    @GetMapping
    public String list(ModelMap model) {
        var items = itemListRepository.findAll();
        model.put("items", items);
        return "inventory/list";
    }



}
