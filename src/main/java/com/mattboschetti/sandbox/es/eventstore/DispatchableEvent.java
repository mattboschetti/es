package com.mattboschetti.sandbox.es.eventstore;

public interface DispatchableEvent {

    String id();
    DomainEvent event();

}
