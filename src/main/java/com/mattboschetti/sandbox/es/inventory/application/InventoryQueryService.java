package com.mattboschetti.sandbox.es.inventory.application;

import com.mattboschetti.sandbox.es.common.eventstore.EventStore;
import com.mattboschetti.sandbox.es.inventory.adapter.projection.InventoryItemViewRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InventoryQueryService {

    private final InventoryItemViewRepository itemListRepository;
    private final EventStore eventStore;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InventoryQueryService(InventoryItemViewRepository itemListRepository,
                                 EventStore eventStore,
                                 ApplicationEventPublisher applicationEventPublisher) {
        this.itemListRepository = itemListRepository;
        this.eventStore = eventStore;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void rebuildAll() {
        itemListRepository.deleteAll();
        eventStore.getAll().events().forEach(applicationEventPublisher::publishEvent);
    }

    @Transactional
    public void rebuildAggregate(UUID aggregateUUID) {
        itemListRepository.deleteById(aggregateUUID);
        eventStore.getEventsForAggregate(aggregateUUID).events().forEach(applicationEventPublisher::publishEvent);
    }
}
