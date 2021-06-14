package com.mattboschetti.sandbox.es.domain;

import com.google.common.base.Strings;
import com.mattboschetti.sandbox.es.event.Event;
import com.mattboschetti.sandbox.es.event.InventoryItemCreated;
import com.mattboschetti.sandbox.es.event.InventoryItemDeactivated;
import com.mattboschetti.sandbox.es.event.InventoryItemRenamed;
import com.mattboschetti.sandbox.es.event.InventoryItemUnitPriceChanged;
import com.mattboschetti.sandbox.es.event.ItemsCheckedInToInventory;
import com.mattboschetti.sandbox.es.event.ItemsRemovedFromInventory;

import java.math.BigDecimal;
import java.util.UUID;

public class InventoryItem extends AggregateRoot {
    private boolean activated;
    private UUID id;

    private void apply(InventoryItemCreated e) {
        id = e.id;
        activated = true;
    }

    private void apply(InventoryItemDeactivated e) {
        activated = false;
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

    @Override
    public UUID getId() {
        return id;
    }

    public InventoryItem() {
        // used to create in repository ... many ways to avoid this, eg making private constructor
    }

    public InventoryItem(UUID id, String name, BigDecimal unitPrice) {
        applyChange(new InventoryItemCreated(id, name, unitPrice));
    }

    @Override
    void apply(Event event) {
        if (event instanceof InventoryItemCreated e) {
            apply(e);
        }
        if (event instanceof InventoryItemDeactivated e) {
            apply(e);
        }
    }
}
