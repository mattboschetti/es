package com.mattboschetti.sandbox.es.inventory.domain;

import com.google.common.base.Strings;
import com.mattboschetti.sandbox.es.common.domain.AggregateRoot;
import com.mattboschetti.sandbox.es.common.eventstore.DomainEvent;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemCreated;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemDeactivated;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemRenamed;
import com.mattboschetti.sandbox.es.inventory.event.InventoryItemUnitPriceChanged;
import com.mattboschetti.sandbox.es.inventory.event.ItemsCheckedInToInventory;
import com.mattboschetti.sandbox.es.inventory.event.ItemsRemovedFromInventory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class InventoryItem extends AggregateRoot {
    private boolean activated;
    private UUID id;

    public InventoryItem(List<DomainEvent> events, int version) {
        super(events, version);
    }

    public InventoryItem(UUID id, String name, BigDecimal unitPrice) {
        super();
        applyChange(new InventoryItemCreated(id, name, unitPrice));
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void changeName(String newName) {
        if (Strings.isNullOrEmpty(newName)) {
            throw new IllegalArgumentException("newName");
        }
        applyChange(new InventoryItemRenamed(id, newName));
    }

    public void remove(int count) {
        if (count <= 0) {
            throw new IllegalStateException("cant remove negative count from inventory");
        }
        applyChange(new ItemsRemovedFromInventory(id, count));
    }


    public void checkIn(int count) {
        if (count <= 0) {
            throw new IllegalStateException("must have a count greater than 0 to add to inventory");
        }
        applyChange(new ItemsCheckedInToInventory(id, count));
    }

    public void deactivate() {
        if (!activated) {
            throw new IllegalStateException("already deactivated");
        }
        applyChange(new InventoryItemDeactivated(id));
    }

    public void reprice(BigDecimal unitPrice) {
        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("item can't have a negative price");
        }
        applyChange(new InventoryItemUnitPriceChanged(id, unitPrice));
    }

    private void apply(InventoryItemCreated e) {
        id = e.id;
        activated = true;
    }

    private void apply(InventoryItemDeactivated e) {
        activated = false;
    }
}
