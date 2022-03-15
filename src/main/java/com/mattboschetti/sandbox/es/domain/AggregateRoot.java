package com.mattboschetti.sandbox.es.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.mattboschetti.sandbox.es.ESException;
import com.mattboschetti.sandbox.es.eventstore.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AggregateRoot {
    private static final Logger LOG = LoggerFactory.getLogger(AggregateRoot.class);
    private static final Map<String, Method> METHOD_CACHE = new HashMap<>();
    private final List<DomainEvent> changes = new ArrayList<>();
    private int version = -1;

    public AggregateRoot() {}

    public AggregateRoot(List<DomainEvent> events, int version) {
        events.forEach(this::apply);
        this.version = version;
    }

    public abstract UUID getId();

    public int getVersion() {
        return version;
    }

    public List<DomainEvent> getUncommittedChanges() {
        return changes;
    }

    protected void applyChange(DomainEvent event) {
        apply(event);
        changes.add(event);
    }

    private void apply(DomainEvent event) {
        var aggregate = this.getClass();
        var eventType = event.getClass();
        getEventHandlerMethod(aggregate, eventType)
                .ifPresent(m -> invokeHandlerMethod(m, event));
    }

    private void invokeHandlerMethod(Method method, DomainEvent event) {
        try {
            method.invoke(this, event);
        } catch (IllegalAccessException e) {
            var msg = "Underlying apply method for event %s is not accessible".formatted(event.getClass().getSimpleName());
            throw new ESException(msg, e);
        } catch (InvocationTargetException e) {
            throw new ESException(e);
        }
    }

    private Optional<Method> getEventHandlerMethod(Class<? extends AggregateRoot> aggregate, Class<? extends DomainEvent> parameterType) {
        final var key = generateCacheKey(aggregate, parameterType);
        return Optional.ofNullable(METHOD_CACHE.computeIfAbsent(key, k -> this.getApplyMethod(aggregate, parameterType)));
    }

    private String generateCacheKey(Class<? extends AggregateRoot> aggregate, Class<? extends DomainEvent> parameterType) {
        return "%s-%s".formatted(aggregate.getSimpleName(), parameterType.getSimpleName());
    }

    private Method getApplyMethod(Class<? extends AggregateRoot> root, Class<? extends DomainEvent> parameterType) {
        try {
            var method = root.getDeclaredMethod("apply", parameterType);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            LOG.warn("Could not find AggregateRoot method that applies event %s".formatted(parameterType.getSimpleName()));
            return null;
        }
    }
}
