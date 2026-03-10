package com.processor.events.utils;

import com.processor.events.model.Event;
import com.processor.events.model.EventStats;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class EventAggregator {

	public static Map<String, EventStats> aggregate(Stream<Event> events) {

		ConcurrentHashMap<String, EventStatsAccumulator> accumulatorMap = new ConcurrentHashMap<>();

		processEvents(events, accumulatorMap);

		return buildResult(accumulatorMap);
	}

	private static void processEvents(Stream<Event> events,
			ConcurrentHashMap<String, EventStatsAccumulator> accumulatorMap) {

		events.parallel().filter(EventValidator::isValid).forEach(event -> updateAccumulator(event, accumulatorMap));
	}

	private static void updateAccumulator(Event event, ConcurrentHashMap<String, EventStatsAccumulator> accumulatorMap) {

		accumulatorMap.computeIfAbsent(event.id(), id -> new EventStatsAccumulator()).add(event);
	}

	private static Map<String, EventStats> buildResult(ConcurrentHashMap<String, EventStatsAccumulator> accumulatorMap) {

		ConcurrentHashMap<String, EventStats> result = new ConcurrentHashMap<>();

		accumulatorMap.forEach((id, acc) -> {
			result.put(id, toEventStats(id, acc));
		});

		return result;
	}

	private static EventStats toEventStats(String id, EventStatsAccumulator acc) {

		return new EventStats(id, acc.getCount(), acc.getMinTimestamp(), acc.getMaxTimestamp(), acc.getAverage());
	}

}