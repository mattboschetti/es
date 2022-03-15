package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.eventstore.DomainEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class InventoryItemUnitPriceChanged implements DomainEvent {
    public final UUID id;
    public final BigDecimal unitPrice;

    public InventoryItemUnitPriceChanged(UUID id, BigDecimal unitPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
    }

    @Override
    public int version() {
        return 0;
    }
}
