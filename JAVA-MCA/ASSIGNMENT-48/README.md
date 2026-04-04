# Smart Service System

A Java-based multithreaded service simulation demonstrating core OOP and concurrency concepts through a ride, food, and payment booking system.

## Overview

This project models a simplified smart service platform (similar to a super-app like Uber or Swiggy) where multiple services run concurrently as independent threads, each with its own validation and error handling logic.

## How It Works

Each service (Ride, Food, Payment) extends `SmartService`, which itself extends `Thread`. On startup, all three services launch simultaneously — processing requests in parallel without blocking one another.

```
main()
 ├── RideService.start()    → "MGiri booked a ride"
 ├── FoodService.start()    → Error: Username too short
 └── PaymentService.start() → "SBajani made payment of rs: 500.0"
```

## Key Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **Inheritance** | `RideService`, `FoodService`, `PaymentService` extend `SmartService` |
| **Abstraction** | `SmartService` is abstract; forces `execute()` override |
| **Multithreading** | Each service runs as an independent `Thread` |
| **Custom Exceptions** | `ServiceException` provides domain-specific error messages |
| **Encapsulation** | Each service owns its validation logic internally |

## Software Engineering Implications

**Modularity** — Adding a new service (e.g., `HotelService`) requires no changes to existing classes; just extend `SmartService`.

**Separation of Concerns** — Business logic, threading, and error handling are cleanly separated across layers.

**Scalability** — Thread-per-service model can be upgraded to a thread pool (`ExecutorService`) for production use.

**Fault Isolation** — A failure in one service (e.g., Food) does not crash the others, making the system resilient.

**Testability** — Each service's `execute()` method can be unit-tested independently of threading.

## Limitations & Production Considerations

- Thread-per-request does not scale beyond small loads — use `ExecutorService` or reactive patterns instead.
- No shared state management; concurrent access to shared resources would require synchronization (`synchronized`, `Lock`).
- Validation logic is minimal and should be externalized to a validation layer in real systems.

## Requirements

- Java 8 or higher
- No external dependencies

## Run

```bash
javac code.java
java code
```
