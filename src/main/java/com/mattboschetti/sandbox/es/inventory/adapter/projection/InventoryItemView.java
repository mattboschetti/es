package com.mattboschetti.sandbox.es.inventory.adapter.projection;

import com.mattboschetti.sandbox.es.common.eventstore.DispatchableDomainEvent;
import com.mattboschetti.sandbox.es.common.eventstore.EventStreamId;
import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItem;
import com.mattboschetti.sandbox.es.inventory.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemView {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryItemView.class);
    private final InventoryItemViewRepository repository;

    public InventoryItemView(InventoryItemViewRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handle(DispatchableDomainEvent event) {
        if (event.event() instanceof InventoryItemCreated e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof InventoryItemRenamed e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof InventoryItemDeactivated e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof ItemsRemovedFromInventory e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof ItemsCheckedInToInventory e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof InventoryItemUnitPriceChanged e) {
            handle(event.eventStreamId(), e);
        }
    }

    public void handle(EventStreamId eventStreamId,  InventoryItemCreated message) {
        repository.save(new InventoryItem(eventStreamId.id() , message.name, message.category, message.unitPrice, 0, eventStreamId.version()));
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, InventoryItemRenamed message) {
        repository.findById(eventStreamId.id()).ifPresent(item -> {
            item.name = message.newName;
            item.version = eventStreamId.version();
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, InventoryItemDeactivated message) {
        repository.deleteByIdAndVersion(eventStreamId.id(), eventStreamId.version());
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, InventoryItemUnitPriceChanged message) {
        repository.findById(eventStreamId.id()).ifPresent(i -> {
            i.unitPrice = message.unitPrice;
            i.version = eventStreamId.version();
            repository.save(i);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, ItemsRemovedFromInventory message) {
        repository.findById(eventStreamId.id()).ifPresent(item -> {
            item.quantity -= message.count;
            item.version = eventStreamId.version();
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, ItemsCheckedInToInventory message) {
        repository.findById(eventStreamId.id()).ifPresent(item -> {
            item.quantity += message.count;
            item.version = eventStreamId.version();
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }
}
