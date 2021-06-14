package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.eventstore.Event;

import java.math.BigDecimal;
import java.util.UUID;

public class InventoryItemUnitPriceChanged extends Event {
    public final UUID id;
    public final BigDecimal unitPrice;

    public InventoryItemUnitPriceChanged(UUID id, BigDecimal unitPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
    }
}
