package com.mattboschetti.sandbox.es.inventory.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mattboschetti.sandbox.es.common.eventstore.DomainEvent;

import java.util.UUID;

public class InventoryItemDeactivated implements DomainEvent {
    public final UUID id;

    // Heuristics for finding single argument constructors have some weird behavior on jackson
    // need to explicitly tell which constructor to use
    // https://github.com/FasterXML/jackson-databind/issues/1498
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public InventoryItemDeactivated(UUID id) {
        this.id = id;
    }

    @Override
    public int version() {
        return 0;
    }
}
