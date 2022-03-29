package com.mattboschetti.sandbox.es.common.domain;

import java.util.UUID;

public interface Repository<T extends AggregateRoot> {
    void save(T aggregate);
    T getById(UUID id);
}
