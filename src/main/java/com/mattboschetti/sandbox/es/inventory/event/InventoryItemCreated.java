package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.common.eventstore.DomainEvent;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class InventoryItemCreated implements DomainEvent {
    public final UUID id;
    public final String name;
    public final BigDecimal unitPrice;

    public InventoryItemCreated(UUID id, String name, BigDecimal unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = Objects.requireNonNullElse(unitPrice, BigDecimal.ZERO);
    }

    @Override
    public int version() {
        return 0;
    }
}
