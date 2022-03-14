package com.mattboschetti.sandbox.es.domain;

import java.util.UUID;

public interface Repository<T extends AggregateRoot> {
    void save(T aggregate);
    T getById(UUID id);
}
