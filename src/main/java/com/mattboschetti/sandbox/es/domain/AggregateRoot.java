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
        events.forEach(this::apply);
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

    protected void applyChange(DomainEvent event) {
        apply(event);
        changes.add(event);
    }

    protected abstract void apply(DomainEvent event);
}
