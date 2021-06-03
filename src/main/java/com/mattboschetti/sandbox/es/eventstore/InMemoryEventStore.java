package com.mattboschetti.sandbox.es.eventstore;

import com.mattboschetti.sandbox.es.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//@Component
public class InMemoryEventStore implements EventStore {

    private record EventDescriptor(UUID Id, Event EventData, int Version) {}

    private final Map<UUID, List<EventDescriptor>> current = new ConcurrentHashMap<>();

    public void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion)
    {
        List<EventDescriptor> eventDescriptors = current.computeIfAbsent(aggregateId, uuid -> new ArrayList<>());

        if(!eventDescriptors.isEmpty() && eventDescriptors.get(eventDescriptors.size() - 1).Version != expectedVersion && expectedVersion != -1)
        {
            throw new ConcurrencyException();
        }
        var i = expectedVersion;

        // iterate through current aggregate events increasing version with each processed event
        for (Event event : events)
        {
            i++;
            event.version = i;

            // push event to the event descriptors list for current aggregate
            eventDescriptors.add(new EventDescriptor(aggregateId, event, i));
        }
    }

    // collect all processed events for given aggregate and return them as a list
    // used to build up an aggregate from its history (Domain.LoadsFromHistory)
    public List<Event> getEventsForAggregate(UUID aggregateId)
    {
        if (!current.containsKey(aggregateId))
        {
            throw new AggregateNotFoundException();
        }

        List<EventDescriptor> eventDescriptors = current.get(aggregateId);
        return eventDescriptors.stream().map(desc -> desc.EventData).collect(Collectors.toList());
    }
}
