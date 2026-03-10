package com.processor.events.model;

public record Event(
        String id,
        long timestamp,
        double value
) {}
