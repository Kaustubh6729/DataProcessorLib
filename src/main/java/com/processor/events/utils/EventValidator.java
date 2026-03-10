package com.processor.events.utils;

import com.processor.events.model.Event;

public final class EventValidator {

    private EventValidator() {}

    public static boolean isValid(Event event) {

        if (event == null) return false;
        if (event.id() == null) return false;
        if (Double.isNaN(event.value())) return false;
        if (event.value() < 0) return false;

        return true;
    }
}