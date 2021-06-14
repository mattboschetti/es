package com.mattboschetti.sandbox.es.domain;

import com.mattboschetti.sandbox.es.eventstore.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {
    private final List<Event> changes = new ArrayList<>();
    private int version;

    public abstract UUID getId();

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public List<Event> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    public void loadsFromHistory(List<Event> history) {
        for (Event e : history) {
            applyChange(e, false);
            version = e.version;
        }
    }

    protected void applyChange(Event event) {
        applyChange(event, true);
    }

    // push atomic aggregate changes to local history for further processing (EventStore.SaveEvents)
    private void applyChange(Event event, boolean isNew) {
        // call each distinct apply on aggregate root
        apply(event);
        if (isNew) {
            // new events have no version, so we continue increasing the aggregate version and assigning that value
            event.version = ++version;
            changes.add(event);
        }
    }

    protected abstract void apply(Event event);
}
