package com.processor.events.model;

public record EventStats(
        String id,
        long count,
        long minTimestamp,
        long maxTimestamp,
        double average
) {}