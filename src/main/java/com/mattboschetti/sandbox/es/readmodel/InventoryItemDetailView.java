package com.mattboschetti.sandbox.es.readmodel;

import com.mattboschetti.sandbox.es.event.Event;
import com.mattboschetti.sandbox.es.event.InventoryItemCreated;
import com.mattboschetti.sandbox.es.event.InventoryItemDeactivated;
import com.mattboschetti.sandbox.es.event.InventoryItemRenamed;
import com.mattboschetti.sandbox.es.event.InventoryItemUnitPriceChanged;
import com.mattboschetti.sandbox.es.event.ItemsCheckedInToInventory;
import com.mattboschetti.sandbox.es.event.ItemsRemovedFromInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemDetailView {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryItemDetailView.class);
    private final InventoryItemDetailRepository repository;

    public InventoryItemDetailView(InventoryItemDetailRepository repository) {
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
        if (event instanceof ItemsRemovedFromInventory e) {
            handle(e);
        }
        if (event instanceof ItemsCheckedInToInventory e) {
            handle(e);
        }
        if (event instanceof InventoryItemDeactivated e) {
            handle(e);
        }
        if (event instanceof InventoryItemUnitPriceChanged e) {
            handle(e);
        }
    }

    public void handle(InventoryItemUnitPriceChanged message) {
        repository.findById(message.id).ifPresent(i -> {
            i.unitPrice = message.unitPrice.toString();
            i.version = message.version;
            repository.save(i);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }

    public void handle(InventoryItemCreated message) {
        repository.save(new InventoryItemDetail(message.id, message.name, 0, message.unitPrice.toString(), message.version));
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }

    public void handle(InventoryItemRenamed message) {
        repository.findById(message.id).ifPresent(i -> {
            i.name = message.newName;
            i.version = message.version;
            repository.save(i);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }

    public void handle(ItemsRemovedFromInventory message) {
        repository.findById(message.id).ifPresent(item -> {
            item.currentCount -= message.count;
            item.version = message.version;
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), message.id);
    }

    public void handle(ItemsCheckedInToInventory message) {
        repository.findById(message.id).ifPresent(item -> {
            item.currentCount += message.count;
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
