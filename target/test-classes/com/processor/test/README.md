# Data Processing Library

A Java 17 library that processes a stream of events and computes aggregated statistics per event ID.
The implementation is **thread-safe**, **memory efficient**, and supports **parallel stream processing**.

---

## Event Model

```java
record Event(
    String id,
    long timestamp,
    double value
) {}
```

Each event contains an identifier, timestamp (epoch millis), and a numeric value.

---

## Aggregation

For each unique `id`, the library computes:

* Total count of valid events
* Minimum timestamp
* Maximum timestamp
* Average value

---

## Data Cleaning

The following rules are applied during processing:

* Invalid events are discarded (`null id`, `NaN`, or negative value)
* Duplicate events (`id + timestamp`) are ignored
* Out-of-order events are supported

---

## Design Highlights

* **Stream-based processing** (no full dataset materialization)
* **Parallel execution** using Java Streams
* **Thread-safe aggregation** using `ConcurrentHashMap`
* **Incremental statistics** for memory efficiency

Time Complexity: `O(N)`
Memory Complexity: `O(U)` where `U` = number of unique IDs.

---


## Build

```bash
mvn clean install
```

---

## Run Tests

```bash
mvn test
```

---

## Example Usage

```java
Stream<Event> events = Stream.of(
    new Event("A",1001,10),
    new Event("A",1002,12),
    new Event("B",1003,20)
);

Map<String, EventStats> result =
        EventAggregator.aggregate(events);
```
