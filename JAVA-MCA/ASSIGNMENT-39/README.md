# Online Ticket Booking — Java Synchronized Threading Demo

A Java program simulating a **concurrent ticket booking system** where multiple user threads attempt to book tickets from a shared `TicketCounter`. Demonstrates **`synchronized` methods for thread safety, custom exceptions for business rule violations, sequential thread execution via `join()`, and shared mutable state management**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `OnlineTicketBooking.java` | Single source file with ticket counter, user threads, custom exception, and entry point |

---

## 🚀 How to Run

```bash
javac OnlineTicketBooking.java
java OnlineTicketBooking
```

**Sample Output:**
```
User1 is trying to book 2 ticket(s)...
Booking successful for User1
Remaining tickets: 3
User2 is trying to book 3 ticket(s)...
Booking successful for User2
Remaining tickets: 0
User3 is trying to book 1 ticket(s)...
Booking Failed: Not enough tickets for User3!
User4 is trying to book 2 ticket(s)...
Booking Failed: Not enough tickets for User4!
```

---

## 🧠 Concepts Demonstrated

- **`synchronized` Method** — `bookTicket()` locks on the `TicketCounter` instance so only one thread executes it at a time
- **Shared Mutable State** — `availableTickets` is shared across all `UserThread` instances; `synchronized` prevents race conditions
- **Custom Exception** — `NoTicketAvailableException` encodes the business rule "cannot book more than available"
- **`join()` per thread** — `start()` + `join()` on each thread enforces strict sequential booking order
- **Thread as User** — each `UserThread` models a real-world concurrent user with a name and ticket request

---

## 🔒 Why `synchronized` Matters

```
Without synchronized (race condition risk):
  Thread1 reads availableTickets = 5  ─┐
  Thread2 reads availableTickets = 5   ├── Both see 5, both proceed
  Thread1 writes availableTickets = 3 ─┘   Total booked = 4, but only 5 existed!

With synchronized (safe):
  Thread1 enters bookTicket() → lock acquired
  Thread2 attempts bookTicket() → BLOCKED until Thread1 releases lock
  Thread1 finishes, releases lock
  Thread2 enters → reads updated availableTickets = 3 → proceeds correctly
```

`synchronized` guarantees **atomicity** — the check-then-deduct operation is never interrupted by another thread.

---

## 🔄 Booking Flow

```
main()
  │
  ├── TicketCounter(5)         →  availableTickets = 5
  │
  ├── u1.start() + u1.join()  →  User1 books 2  → remaining: 3
  ├── u2.start() + u2.join()  →  User2 books 3  → remaining: 0
  ├── u3.start() + u3.join()  →  User3 needs 1  → FAIL (0 left)
  └── u4.start() + u4.join()  →  User4 needs 2  → FAIL (0 left)
```

---

## 🗂️ Class Overview

| Class | Role | Key Detail |
|-------|------|-----------|
| `NoTicketAvailableException` | Business rule exception | Thrown when requested > available |
| `TicketCounter` | Shared resource | `synchronized bookTicket()` prevents race conditions |
| `UserThread` | Thread per user | Holds counter reference, username, ticket count |
| `OnlineTicketBooking` | Entry point | Sequential `start()` + `join()` per user |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Two business rules — **book if sufficient tickets exist**, **reject if insufficient** — are directly encoded as the `if/else` and `throw` inside `bookTicket()`, making each rule independently traceable and testable.

### 2. 🏗️ Design
`TicketCounter` is a **self-defending shared resource** — the synchronisation lock lives inside the class, not in `main()`. Any thread that calls `bookTicket()` is automatically protected, regardless of how many threads are added later.

### 3. 💻 Development
Throwing `NoTicketAvailableException` from inside the `synchronized` block is safe — Java **releases the lock** after the exception is thrown, preventing a deadlock if the caller fails to catch it.

### 4. 🧪 Testing
`bookTicket()` can be unit tested by calling it directly on a `TicketCounter` with a known initial value — no threads needed. The `synchronized` keyword does not affect single-threaded testing.

### 5. 🔧 Maintenance
Adding a new rule (e.g., max 3 tickets per user) requires only a new condition inside `bookTicket()` — all thread classes and `main()` remain unchanged.

### 6. 📈 Scalability
`synchronized` is suitable for low-to-medium concurrency. For high-throughput systems, `java.util.concurrent.atomic.AtomicInteger` or `ReentrantLock` with `tryLock()` offer better performance and finer control.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Sequential, not truly concurrent | `start()` + `join()` per thread makes execution sequential — no two users ever actually compete simultaneously |
| `synchronized` is still correct | Even though execution is sequential here, `synchronized` is good practice — removing `join()` would immediately expose a race condition without it |
| No partial booking | If `User2` requests 3 but only 2 are left, the entire request fails — no partial booking support |
| `availableTickets` not `volatile` | Safe here because `synchronized` already provides visibility guarantees — `volatile` would be redundant |
| `main` throws `InterruptedException` | Declared in the method signature but not caught — any `join()` interruption would propagate uncaught to the JVM |
| No booking confirmation ID | Successful bookings have no return value or receipt — a real system would return a booking reference |

---

## 🔄 `synchronized` vs Alternatives

| Mechanism | Use Case | Advantage |
|-----------|----------|-----------|
| `synchronized` method | Simple shared resource | Easy to read and implement |
| `ReentrantLock` | Need `tryLock()` or timeout | Non-blocking lock attempts |
| `AtomicInteger` | Single numeric counter | Lock-free, highest throughput |
| `Semaphore` | Limit concurrent access count | Controls number of simultaneous threads |

> For a real-world ticket system with thousands of concurrent users, `AtomicInteger` for the counter and `ReentrantLock` with `tryLock()` would replace `synchronized`.

---

## 🛠️ Possible Enhancements

- Remove `join()` calls and test true concurrency — observe race conditions disappear thanks to `synchronized`
- Replace `synchronized` with `ReentrantLock` and `tryLock()` to demonstrate non-blocking booking attempts
- Return a **booking confirmation ID** (`UUID.randomUUID()`) from `bookTicket()` on success
- Add a **waitlist** — if tickets are unavailable, queue the user and notify when tickets are released
- Use `AtomicInteger` for `availableTickets` for a lock-free, high-throughput alternative
- Add a **cancellation feature** that returns tickets to the pool and notifies waiting threads via `notify()`

---

## 📄 License
Free to use for educational purposes.
