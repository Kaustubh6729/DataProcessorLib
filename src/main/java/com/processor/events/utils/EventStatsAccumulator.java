package com.processor.events.utils;

import com.processor.events.model.Event;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class EventStatsAccumulator {

	private long count = 0;
	private double sum = 0;
	private long minTimestamp = Long.MAX_VALUE;
	private long maxTimestamp = Long.MIN_VALUE;

	private final Set<Long> seenTimestamps = ConcurrentHashMap.newKeySet();
	
	
	public synchronized void add(Event event) {

		if (!seenTimestamps.add(event.timestamp())) {
			return;
		}

		count++;
		sum += event.value();

		minTimestamp = Math.min(minTimestamp, event.timestamp());
		maxTimestamp = Math.max(maxTimestamp, event.timestamp());
	}

	public synchronized long getCount() {
		return count;
	}

	public synchronized double getAverage() {
		return count == 0 ? 0 : sum / count;
	}

	public synchronized long getMinTimestamp() {
		return minTimestamp;
	}

	public synchronized long getMaxTimestamp() {
		return maxTimestamp;
	}
}