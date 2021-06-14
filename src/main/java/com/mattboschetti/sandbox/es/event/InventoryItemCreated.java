package com.mattboschetti.sandbox.es.event;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class InventoryItemCreated extends Event {
    public final UUID id;
    public final String name;
    public final BigDecimal unitPrice;

    public InventoryItemCreated(UUID id, String name, BigDecimal unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = Objects.requireNonNullElse(unitPrice, BigDecimal.ZERO);
    }
}
