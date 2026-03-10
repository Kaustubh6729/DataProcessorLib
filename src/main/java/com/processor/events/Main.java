package com.processor.events;

import com.processor.events.model.Event;
import com.processor.events.model.EventStats;
import com.processor.events.utils.EventAggregator;

import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Stream<Event> events = Stream.of(

                new Event("A",1001,10.5),
                new Event("B",1002,20.0),
                new Event("C",1003,30.0),

                new Event("A",1004,11.0),
                new Event("B",1005,21.0),
                new Event("C",1006,31.0),

                new Event("A",1007,12.0),
                new Event("B",1008,22.0),
                new Event("C",1009,32.0),

                new Event("A",1001,10.5), //duplicate

                new Event("B",1010,-5.0), //invalid
                new Event("C",1011,Double.NaN), //invalid


                new Event("D",1012,40.0),
                new Event("D",1013,41.0),

                new Event("E",1014,50.0),
                new Event("E",1015,51.0),
                new Event("A",990,8.0),
                new Event("B",995,18.0),

                new Event("C",1016,33.0),
                new Event("D",1017,42.0)
        );

        Map<String, EventStats> result = EventAggregator.aggregate(events);

        result.forEach((id, stats) ->
                System.out.println(id + " -> " + stats)
        );
    }
}