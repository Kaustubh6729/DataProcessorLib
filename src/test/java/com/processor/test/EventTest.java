package com.processor.test;

import com.processor.events.model.Event;
import com.processor.events.model.EventStats;
import com.processor.events.utils.EventAggregator;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEmptyStream() {

        Map<String, EventStats> result =
                EventAggregator.aggregate(Stream.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleEvent() {

        Event event = new Event("A", 1000, 10.0);

        Map<String, EventStats> result =
                EventAggregator.aggregate(Stream.of(event));

        EventStats stats = result.get("A");

        assertEquals(1, stats.count());
        assertEquals(1000, stats.minTimestamp());
        assertEquals(1000, stats.maxTimestamp());
        assertEquals(10.0, stats.average());
    }

    @Test
    void testDuplicatesIgnored() {

        Event e1 = new Event("A", 1000, 10);
        Event e2 = new Event("A", 1000, 20);

        Map<String, EventStats> result =
                EventAggregator.aggregate(Stream.of(e1, e2));

        EventStats stats = result.get("A");

        assertEquals(1, stats.count());

        assertTrue(
                stats.average() == 10.0 || stats.average() == 20.0
        );
    }
    
    @Test
    void testInvalidEventsFiltered() {

        Event valid = new Event("A", 1000, 10);
        Event invalid = new Event("A", 2000, -5);

        Map<String, EventStats> result =
                EventAggregator.aggregate(Stream.of(valid, invalid));

        EventStats stats = result.get("A");

        assertEquals(1, stats.count());
    }
}