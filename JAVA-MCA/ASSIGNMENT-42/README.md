# Producer-Consumer Demo — Java Classic Concurrency Pattern

A Java program implementing the **Producer-Consumer problem** — one of the most fundamental concurrency patterns in computer science. A `Producer` thread generates data into a shared `Buffer` and a `Consumer` thread reads from it, with two custom exceptions guarding against buffer overflow and underflow, coordinated via a `synchronized` boolean flag.

---

## 📁 Files

| File | Description |
|------|-------------|
| `ProducerConsumerDemo.java` | Single source file with buffer, producer, consumer, exceptions, and entry point |

---

## 🚀 How to Run

```bash
javac ProducerConsumerDemo.java
java ProducerConsumerDemo
```

**Typical Output (timing-dependent):**
```
Produced: 1
Consumed: 1
Produced: 2
Consumed: 2
Produced: 3
Consumer Error: Buffer is empty! Cannot consume.
Consumed: 3
Produced: 4
Consumer Error: Buffer is empty! Cannot consume.
...
```

> Output varies per run — the 500ms produce vs 800ms consume gap means the consumer occasionally tries to read before the next item is ready.

---

## 🧠 Concepts Demonstrated

- **Producer-Consumer Pattern** — a classic OS-level concurrency model where one thread generates data and another consumes it via a shared resource
- **Two Custom Exceptions** — `BufferFullException` (produce when full) and `BufferEmptyException` (consume when empty) model two distinct failure states
- **`synchronized` Methods** — both `produce()` and `consume()` lock on the `Buffer` instance to prevent simultaneous access
- **Single-Slot Buffer** — `isAvailable` boolean tracks one item's presence — the buffer holds at most one value at a time
- **Asymmetric Sleep Timings** — producer sleeps 500ms, consumer sleeps 800ms — consumer is slower, causing predictable empty-buffer errors
- **Exception-Based Flow Control** — instead of blocking (`wait()`/`notify()`), failures throw exceptions and the loop continues

---

## 🔄 Thread Interaction Timeline

```
Time (ms)   Producer                    Buffer State        Consumer
──────────────────────────────────────────────────────────────────────
0           produce(1)                  [1] full            tries consume()
~0          sleep(500)                  [1] full            → Consumed: 1
                                        [] empty            sleep(800)
500         produce(2)                  [2] full
            sleep(500)
800                                     [2] full            tries consume()
                                                            → Consumed: 2
                                        [] empty            sleep(800)
1000        produce(3)                  [3] full
            sleep(500)
1500        produce(4) → BufferFull!   [3] still full      (consumer sleeping)
                                                            (still at 800ms cycle)
1600                                    [3] full            → Consumed: 3
...
```

The 500ms vs 800ms gap makes the producer **outpace** the consumer, leading to periodic `BufferFullException` errors.

---

## 🔐 Buffer State Machine

```
             produce(value)              consume()
  [Empty] ─────────────────► [Full] ─────────────────► [Empty]
    │                           │
    │ consume() → BufferEmptyException
    │ produce() ← OK
    │                           │ produce() → BufferFullException
    │                           │ consume() ← OK
```

`isAvailable` is the sole state variable — `true` = full, `false` = empty. Both exceptions represent attempts to violate a valid state transition.

---

## 🗂️ Class Overview

| Class | Role | Key Behaviour |
|-------|------|---------------|
| `BufferFullException` | Exception | Thrown when `produce()` is called on a full buffer |
| `BufferEmptyException` | Exception | Thrown when `consume()` is called on an empty buffer |
| `Buffer` | Shared resource | `synchronized` single-slot produce/consume |
| `Producer` | Thread | Loops `count` times, produces sequential integers, sleeps 500ms |
| `Consumer` | Thread | Loops `count` times, consumes data, sleeps 800ms |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Two buffer constraints — **do not produce when full**, **do not consume when empty** — are modelled as two distinct exceptions, each representing a specific invariant violation with a clear message.

### 2. 🏗️ Design
The `Buffer` class encapsulates **all state and all guards** — producer and consumer classes contain zero buffer logic. This separation means the buffer's behaviour can be changed (e.g., add `wait()`/`notify()`) without modifying either thread class.

### 3. 💻 Development
Using exceptions instead of `wait()`/`notify()` is a deliberate simplification — it trades **blocking coordination** (thread waits until condition is met) for **exception-based signalling** (thread fails fast and moves to the next iteration). Easier to read, but not suitable for tight loops.

### 4. 🧪 Testing
`Buffer` can be tested in isolation — `produce(10)` sets state, `consume()` clears it, and calling either method in the wrong state throws a verifiable exception without any threads involved.

### 5. 🔧 Maintenance
Changing buffer capacity from 1 to N items requires only replacing the `boolean isAvailable` with an `int count` and adjusting the guards — neither `Producer` nor `Consumer` is touched.

### 6. 📈 Scalability
Exception-based flow control breaks down at high throughput — every collision generates an exception object. The production upgrade is `wait()`/`notify()` (built into Java's `Object` class) or `BlockingQueue` from `java.util.concurrent`, which blocks instead of throwing.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Not truly blocking | Consumer does not **wait** for data — it throws an exception and skips that iteration, potentially consuming fewer items than produced |
| Count mismatch risk | Both loops run `count = 5` times, but due to exceptions, the consumer may successfully consume fewer than 5 items |
| No `volatile` on `isAvailable` | Safe only because `synchronized` provides visibility guarantees — the flag is always read/written inside a lock |
| `synchronized` not `wait()`/`notify()` | This is exception-based coordination, not true blocking synchronisation — a key distinction from the classic textbook producer-consumer |
| Asymmetric sleep timing | The 500ms vs 800ms gap is intentional to demonstrate exceptions — equal timings would produce near-perfect interleaving with no errors |
| `e.printStackTrace()` for `InterruptedException` | Should use a logger in production code |

---

## 🔄 Exception-Based vs Blocking Producer-Consumer

| Approach | Mechanism | On Conflict | Use Case |
|----------|-----------|-------------|----------|
| **This program** | `synchronized` + exceptions | Throw and skip | Learning, simple demos |
| **`wait()`/`notify()`** | Object monitors | Thread pauses until notified | Low-throughput coordination |
| **`BlockingQueue`** | `java.util.concurrent` | Thread blocks automatically | Production systems |
| **`Semaphore`** | Permit-based | Controls concurrent access count | Resource pool management |

> `BlockingQueue` (e.g., `ArrayBlockingQueue`) is the **production standard** — it eliminates all manual synchronisation and exception handling for this pattern entirely.

---

## 🛠️ Possible Enhancements

- Replace exception-based flow with `wait()` and `notify()` inside `Buffer` for true blocking coordination
- Replace the entire `Buffer` class with `ArrayBlockingQueue<Integer>` from `java.util.concurrent`
- Increase buffer capacity to N slots using an `int[]` array and `head`/`tail` pointers
- Add multiple producers and consumers to demonstrate true concurrent multi-producer/consumer scenarios
- Make `count` configurable via command-line arguments (`args[0]` for producer count, `args[1]` for consumer count)
- Log each transaction with a timestamp using `System.currentTimeMillis()` to visualise the timing gaps

---

## 📄 License
Free to use for educational purposes.
