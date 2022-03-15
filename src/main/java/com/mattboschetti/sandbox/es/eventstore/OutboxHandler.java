package com.mattboschetti.sandbox.es.eventstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OutboxHandler {
    private static final Logger LOG = LoggerFactory.getLogger(OutboxHandler.class);
    private final OutboxDao outboxDao;
    private final EventStore eventStore;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OutboxHandler(OutboxDao outboxDao, EventStore eventStore, ApplicationEventPublisher applicationEventPublisher) {
        this.outboxDao = outboxDao;
        this.eventStore = eventStore;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @TransactionalEventListener
    public void handleEvent(EventPersisted event) {
        outboxDao.save(event.eventId());
    }

    @Scheduled(fixedDelay = 1_000)
    @Transactional
    public void publishEvents() {
        var ids = outboxDao.getAll();
        if (ids.isEmpty()) {
            LOG.debug("Empty outbox");
            return;
        }
        var eventStream = eventStore.getEventsById(ids);
        eventStream.events().forEach(applicationEventPublisher::publishEvent);
        outboxDao.delete(ids);
    }
}
