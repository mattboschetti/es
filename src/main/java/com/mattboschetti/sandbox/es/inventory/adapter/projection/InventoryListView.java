package com.mattboschetti.sandbox.es.inventory.adapter.projection;

import com.mattboschetti.sandbox.es.eventstore.Event;
import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItemList;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemCreated;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemDeactivated;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemRenamed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListView {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryListView.class);
    private final InventoryItemListRepository repository;

    public InventoryListView(InventoryItemListRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handle(Event event) {
        if (event instanceof InventoryItemCreated e) {
            handle(e);
        }
        if (event instanceof InventoryItemRenamed e) {
            handle(e);
        }
        if (event instanceof InventoryItemDeactivated e) {
            handle(e);
        }
    }

    public void handle(InventoryItemCreated message) {
        repository.save(new InventoryItemList(message.id, message.name, message.version));
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }

    public void handle(InventoryItemRenamed message) {
        repository.findById(message.id).ifPresent(item -> {
            item.name = message.newName;
            item.version = message.version;
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }

    public void handle(InventoryItemDeactivated message) {
        repository.deleteById(message.id);
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }
}