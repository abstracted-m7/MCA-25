# File Processing Simulation — Java Shared-Resource Multithreading Demo

A Java program simulating a **read → process → display pipeline** using three concurrent threads sharing a common `Data` object. Demonstrates **shared-resource threading, custom exceptions in threads, `Thread.sleep()` for timing, and graceful error handling** when data is unavailable at each pipeline stage.

---

## 📁 Files

| File | Description |
|------|-------------|
| `FileProcessingSimulation.java` | Single source file with all thread classes, shared resource, and entry point |

---

## 🚀 How to Run

```bash
javac FileProcessingSimulation.java
java FileProcessingSimulation
```

**Normal Flow Output (`isMissing = false`):**
```
Reading data...
Data read successfully.
Processing data...
Data processed.
Displaying data:
SAMPLE FILE DATA
```

**Missing Data Flow (`isMissing = true`):**
```
Reading data...
Reader Error: No data found in file!
Processor Error: Cannot process: Data not available!
Display Error: Nothing to display!
```

---

## 🧠 Concepts Demonstrated

- **Shared Resource** — `Data` object is passed by reference to all three threads; mutations in one thread are visible to others
- **`Thread.sleep()`** — simulates real I/O timing delays: Reader (1s), Processor (1.5s), Display (2s)
- **Custom Exception** — `MissingDataException` signals missing or unavailable data at each pipeline stage
- **`boolean` Flag** — `data.isAvailable` acts as a readiness signal between threads
- **Per-thread Exception Handling** — each thread has its own `try-catch` to handle failures independently
- **Pipeline Simulation** — staggered sleep timings create a natural read → process → display sequence

---

## 🔄 Thread Execution Timeline

```
Time (ms)     t1 (Reader)           t2 (Processor)        t3 (Display)
──────────────────────────────────────────────────────────────────────
0             start + sleep(1000)   start + sleep(1500)   start + sleep(2000)
1000          writes data           still sleeping        still sleeping
              isAvailable = true
1500                                wakes up              still sleeping
                                    reads isAvailable=true
                                    processes → UPPERCASE
2000                                                      wakes up
                                                          reads content
                                                          displays output
```

Sleep timings are **deliberately staggered** so each stage completes before the next reads from `sharedData`.

---

## 🗂️ Class Overview

| Class | Extends | Sleep | Role | Error Condition |
|-------|---------|-------|------|-----------------|
| `MissingDataException` | `Exception` | — | Custom checked exception | — |
| `Data` | — | — | Shared mutable resource | — |
| `ReaderThread` | `Thread` | 1000ms | Writes to `data.content`, sets `isAvailable` | `isMissing == true` |
| `ProcessorThread` | `Thread` | 1500ms | Uppercases `data.content` | `isAvailable == false` |
| `DisplayThread` | `Thread` | 2000ms | Prints `data.content` | `isAvailable == false` |

---

## 🔐 Error Propagation Chain

```
isMissing = true in ReaderThread
  │
  ├── throws MissingDataException → caught → data.isAvailable stays false
  │
  ├── ProcessorThread wakes at 1500ms
  │     └── data.isAvailable == false → throws MissingDataException
  │           └── "Cannot process: Data not available!"
  │
  └── DisplayThread wakes at 2000ms
        └── data.isAvailable == false → throws MissingDataException
              └── "Nothing to display!"
```

A single failure at the reader stage **cascades** through all downstream threads via the shared boolean flag.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
A three-stage data pipeline (read, process, display) is modelled as three independent threads — each stage has a clearly defined input condition (`isAvailable`) and a specific error case, making requirements directly verifiable.

### 2. 🏗️ Design
The `Data` class acts as a **Data Transfer Object (DTO)** shared across threads — a common enterprise pattern for passing state between pipeline stages without tight coupling between the thread classes themselves.

### 3. 💻 Development
The `isMissing` boolean flag inside `ReaderThread` is a built-in **test switch** — toggling it between `true` and `false` tests both the happy path and error path without changing any other code, a practical development convenience.

### 4. 🧪 Testing
Each thread class can be instantiated with a pre-configured `Data` object for isolated testing — `new ProcessorThread(data).run()` with `data.isAvailable = true` tests the processor independently of the reader.

### 5. 🔧 Maintenance
Adding a new pipeline stage (e.g., `ValidatorThread`) requires only a new `Thread` subclass that reads from the same `Data` object — existing thread classes are untouched.

### 6. 📈 Scalability
The shared-object pattern scales to more complex pipelines with `BlockingQueue` replacing direct field access — the same conceptual design underpins Java's `java.util.concurrent` producer-consumer framework.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| No `join()` in main | `main()` exits immediately after `start()` — threads continue independently; no guarantee all threads finish before JVM shutdown in complex programs |
| No synchronisation | `data.content` and `data.isAvailable` are accessed without `synchronized` or `volatile` — safe here only because sleep timings prevent true overlap |
| Race condition risk | If sleep timings were shorter or removed, `t2` could read `isAvailable` before `t1` sets it — the design is timing-dependent, not thread-safe by structure |
| `isMissing` is hardcoded | The missing-data scenario is toggled manually in source code — not controllable at runtime |
| `e.printStackTrace()` | Used for `InterruptedException` — acceptable for demos but should use a logger in production |
| Cascade failure | All three threads fail independently when reader fails — there is no built-in mechanism to cancel downstream threads on upstream failure |

---

## 🛠️ Possible Enhancements

- Add `volatile` to `data.isAvailable` and `data.content` for correct cross-thread visibility without `synchronized`
- Use `join()` in `main()` or replace sleep-based sequencing with a `CountDownLatch` or `BlockingQueue` for true thread coordination
- Allow `isMissing` to be controlled at runtime via a constructor parameter or command-line argument
- Introduce a `CancellationToken` flag so downstream threads abort immediately when the reader fails
- Replace the `Data` DTO with a `BlockingQueue<String>` for a proper producer-consumer pipeline
- Add logging using `java.util.logging.Logger` instead of `System.out.println` for production-grade traceability

---

## 📄 License
Free to use for educational purposes.
