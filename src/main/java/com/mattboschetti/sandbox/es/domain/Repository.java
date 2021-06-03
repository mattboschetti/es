package com.mattboschetti.sandbox.es.domain;

import java.util.UUID;

public interface Repository<T> {
    void save(AggregateRoot aggregate, int expectedVersion);
    T getById(UUID id);
}
