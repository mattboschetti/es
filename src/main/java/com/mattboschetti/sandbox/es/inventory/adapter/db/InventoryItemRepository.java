package com.mattboschetti.sandbox.es.inventory.adapter.db;

import java.util.UUID;

import com.mattboschetti.sandbox.es.eventstore.EventStore;
import com.mattboschetti.sandbox.es.inventory.domain.InventoryItem;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemRepository implements com.mattboschetti.sandbox.es.inventory.domain.InventoryItemRepository {
    private final EventStore storage;

    public InventoryItemRepository(EventStore storage) {
        this.storage = storage;
    }

    @Override
    public void save(InventoryItem aggregate) {
        storage.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges());
    }

    @Override
    public InventoryItem getById(UUID id) {
        var eventStream = storage.getEventsForAggregate(id);
        return new InventoryItem(eventStream.events(), eventStream.version());
    }
}
