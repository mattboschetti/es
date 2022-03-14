package com.mattboschetti.sandbox.es.eventstore;

import java.util.List;

public record EventStream(StreamId streamId, List<Event> events) { }
