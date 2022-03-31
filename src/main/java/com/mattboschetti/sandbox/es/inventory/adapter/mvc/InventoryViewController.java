package com.mattboschetti.sandbox.es.inventory.adapter.mvc;

import com.mattboschetti.sandbox.es.inventory.adapter.projection.InventoryItemViewRepository;
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
    private final InventoryItemViewRepository itemListRepository;

    public InventoryViewController(InventoryQueryService inventoryQueryService,
                                   InventoryItemViewRepository itemListRepository) {
        this.inventoryQueryService = inventoryQueryService;
        this.itemListRepository = itemListRepository;
    }


    @GetMapping
    public String index(ModelMap model) {
        var items = itemListRepository.findAll();
        model.put("items", items);
        return "inventory";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        var items = itemListRepository.findAll();
        model.put("items", items);
        return "inventory/list";
    }

    @GetMapping("/add")
    public String add() {
        return "inventory/add";
    }
}
