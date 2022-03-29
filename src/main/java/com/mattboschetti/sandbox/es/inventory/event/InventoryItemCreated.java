package com.mattboschetti.sandbox.es.inventory.event;

import com.mattboschetti.sandbox.es.common.eventstore.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class InventoryItemCreated implements DomainEvent {
    public final UUID id;
    public final String name;
    public final String category;
    public final BigDecimal unitPrice;
    public final LocalDateTime createdAt;

    public InventoryItemCreated(UUID id, String name, String category, BigDecimal unitPrice, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.unitPrice = Objects.requireNonNullElse(unitPrice, BigDecimal.ZERO);
        this.category = category;
        this.createdAt = createdAt;
    }

    @Override
    public int version() {
        return 0;
    }
}
