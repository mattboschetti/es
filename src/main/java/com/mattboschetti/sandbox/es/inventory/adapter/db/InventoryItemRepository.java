package com.mattboschetti.sandbox.es.inventory.adapter.db;

import java.util.UUID;

import com.mattboschetti.sandbox.es.common.eventstore.EventStore;
import com.mattboschetti.sandbox.es.common.eventstore.EventStreamId;
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
        storage.saveEvents(new EventStreamId(aggregate.getId(), aggregate.getVersion()), aggregate.getUncommittedChanges());
    }

    @Override
    public InventoryItem getById(UUID id) {
        var eventStream = storage.getEventsForAggregate(id);
        return new InventoryItem(eventStream.events(), eventStream.id().version());
    }
}
