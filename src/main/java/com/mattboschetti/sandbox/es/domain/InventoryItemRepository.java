package com.mattboschetti.sandbox.es.domain;

import com.mattboschetti.sandbox.es.eventstore.EventStore;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InventoryItemRepository implements Repository<InventoryItem> {
    private final EventStore storage;

    public InventoryItemRepository(EventStore storage) {
        this.storage = storage;
    }

    public void save(AggregateRoot aggregate, int expectedVersion) {
        storage.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), expectedVersion);
    }

    public InventoryItem getById(UUID id) {
        var obj = new InventoryItem();//lots of ways to do this
        var e = storage.getEventsForAggregate(id);
        obj.loadsFromHistory(e);
        return obj;
    }
}
