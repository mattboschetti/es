package com.mattboschetti.sandbox.es.inventory.adapter.projection;

import com.mattboschetti.sandbox.es.eventstore.DispatchableDomainEvent;
import com.mattboschetti.sandbox.es.eventstore.DomainEvent;
import com.mattboschetti.sandbox.es.eventstore.EventStreamId;
import com.mattboschetti.sandbox.es.inventory.application.data.InventoryItemDetail;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemCreated;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemDeactivated;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemRenamed;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemUnitPriceChanged;
import com.mattboschetti.sandbox.es.inventory.event.ItemsCheckedInToInventory;
import com.mattboschetti.sandbox.es.inventory.event.ItemsRemovedFromInventory;
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
    public void handle(DispatchableDomainEvent event) {
        if (event.event() instanceof InventoryItemCreated e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof InventoryItemRenamed e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof ItemsRemovedFromInventory e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof ItemsCheckedInToInventory e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof InventoryItemDeactivated e) {
            handle(event.eventStreamId(), e);
        }
        if (event.event() instanceof InventoryItemUnitPriceChanged e) {
            handle(event.eventStreamId(), e);
        }
    }

    public void handle(EventStreamId eventStreamId, InventoryItemUnitPriceChanged message) {
        repository.findById(eventStreamId.id()).ifPresent(i -> {
            i.unitPrice = message.unitPrice.toString();
            i.version = eventStreamId.version();
            repository.save(i);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, InventoryItemCreated message) {
        repository.save(new InventoryItemDetail(eventStreamId.id(), message.name, 0, message.unitPrice.toString(), eventStreamId.version()));
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, InventoryItemRenamed message) {
        repository.findById(eventStreamId.id()).ifPresent(i -> {
            i.name = message.newName;
            i.version = eventStreamId.version();
            repository.save(i);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, ItemsRemovedFromInventory message) {
        repository.findById(eventStreamId.id()).ifPresent(item -> {
            item.currentCount -= message.count;
            item.version = eventStreamId.version();
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, ItemsCheckedInToInventory message) {
        repository.findById(eventStreamId.id()).ifPresent(item -> {
            item.currentCount += message.count;
            item.version = eventStreamId.version();
            repository.save(item);
        });
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }

    public void handle(EventStreamId eventStreamId, InventoryItemDeactivated message) {
        repository.deleteById(eventStreamId.id());
        LOG.debug("Handled {} id {}", message.getClass().getSimpleName(), eventStreamId);
    }
}
