package com.mattboschetti.sandbox.es.domain;

import com.mattboschetti.sandbox.es.eventstore.DomainEvent;
import com.mattboschetti.sandbox.es.eventstore.EventStream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {
    private final List<DomainEvent> changes = new ArrayList<>();
    private int version = 0;

    public AggregateRoot() {}

    public AggregateRoot(List<DomainEvent> events, int version) {
        events.forEach(this::applyChange);
        this.version = version;
    }

    public abstract UUID getId();

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public List<DomainEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    public void loadsFromHistory(List<DomainEvent> history) {
        for (DomainEvent e : history) {
            applyChange(e, false);
            version = e.version();
        }
    }

    protected void applyChange(DomainEvent event) {
        applyChange(event, true);
    }

    // push atomic aggregate changes to local history for further processing (EventStore.SaveEvents)
    private void applyChange(DomainEvent event, boolean isNew) {
        // call each distinct apply on aggregate root
        apply(event);
        if (isNew) {
            // new events have no version, so we continue increasing the aggregate version and assigning that value
            changes.add(event);
        }
    }

    protected abstract void apply(DomainEvent event);
}
