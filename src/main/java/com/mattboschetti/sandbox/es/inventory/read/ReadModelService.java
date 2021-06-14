package com.mattboschetti.sandbox.es.inventory.read;

import com.mattboschetti.sandbox.es.eventstore.EventStore;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReadModelService {

    private final InventoryItemDetailRepository itemDetailRepository;
    private final InventoryItemListRepository itemListRepository;
    private final EventStore eventStore;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ReadModelService(InventoryItemDetailRepository itemDetailRepository, InventoryItemListRepository itemListRepository, EventStore eventStore, ApplicationEventPublisher applicationEventPublisher) {
        this.itemDetailRepository = itemDetailRepository;
        this.itemListRepository = itemListRepository;
        this.eventStore = eventStore;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void rebuildAll() {
        itemDetailRepository.deleteAll();
        itemListRepository.deleteAll();
        eventStore.getAll().forEach(applicationEventPublisher::publishEvent);
    }

    @Transactional
    public void rebuildAggregate(UUID aggregateUUID) {
        itemDetailRepository.deleteById(aggregateUUID);
        itemListRepository.deleteById(aggregateUUID);
        eventStore.getEventsForAggregate(aggregateUUID).forEach(applicationEventPublisher::publishEvent);
    }
}
