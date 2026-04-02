# Student Result Processing — Java Staged Pipeline Threading Demo

A Java program simulating a **three-stage student result pipeline** — input marks, calculate grade, generate summary — using three threads sharing a common `Student` object. Demonstrates **boolean readiness flags for thread coordination, sleep-based pipeline staging, a single custom exception reused across all validation points, and cascading failure propagation**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `StudentResultProcessing.java` | Single source file with all thread classes, shared resource, and entry point |

---

## 🚀 How to Run

```bash
javac StudentResultProcessing.java
java StudentResultProcessing
```

**Normal Flow Output (`inputMarks = 85`):**
```
Entering marks...
Marks entered: 85
Calculating grade...
Grade calculated: B
Generating result summary...
Marks: 85
Grade: B
```

**Invalid Marks Flow (`inputMarks = -10`):**
```
Input Error: Marks should be between 0 and 100!
Grade Error: Marks not entered yet!
Result Error: Grade not calculated yet!
```

---

## 🧠 Concepts Demonstrated

- **Boolean Readiness Flags** — `isMarksEntered` and `isGradeCalculated` signal stage completion to downstream threads
- **Sleep-Based Staging** — `GradeThread` sleeps 1s, `ResultThread` sleeps 2s, creating natural pipeline ordering
- **Single Exception, Multiple Contexts** — `InvalidMarksException` is reused for three different failures: invalid input, missing marks, missing grade
- **Cascade Failure** — a single upstream failure (`InputThread`) propagates silently through both downstream threads via unset boolean flags
- **Shared Mutable Object** — `Student` fields are written by one thread and read by others without synchronisation (timing-dependent safety)
- **Test Switch** — `inputMarks` is a hardcoded value that can be changed to test both code paths without restructuring

---

## 🔄 Thread Execution Timeline

```
Time (ms)   t1 (InputThread)         t2 (GradeThread)         t3 (ResultThread)
────────────────────────────────────────────────────────────────────────────────
0           start immediately        start + sleep(1000)      start + sleep(2000)
~0          validates marks
            writes student.marks=85
            isMarksEntered = true
1000                                 wakes up
                                     reads isMarksEntered=true
                                     calculates grade='B'
                                     isGradeCalculated = true
2000                                                          wakes up
                                                             reads isGradeCalculated=true
                                                             prints summary
```

---

## 🚦 Readiness Flag State Machine

```
Initial State:
  isMarksEntered    = false
  isGradeCalculated = false

After InputThread:
  isMarksEntered    = true   ← unlocks GradeThread
  isGradeCalculated = false

After GradeThread:
  isMarksEntered    = true
  isGradeCalculated = true   ← unlocks ResultThread
```

Each thread checks the flag set by the **previous** stage — flags act as lightweight inter-thread signals without explicit `join()` or locks.

---

## 🔐 Exception Reuse Across Stages

| Thread | Trigger | Message |
|--------|---------|---------|
| `InputThread` | `marks < 0 or > 100` | `"Marks should be between 0 and 100!"` |
| `GradeThread` | `isMarksEntered == false` | `"Marks not entered yet!"` |
| `ResultThread` | `isGradeCalculated == false` | `"Grade not calculated yet!"` |

One exception class serves three semantically different errors — valid for a small program, but in a production system each stage would have its own exception type for finer error handling.

---

## 🎓 Grading Logic

| Marks Range | Grade |
|-------------|-------|
| ≥ 90 | A |
| ≥ 75 | B |
| ≥ 50 | C |
| < 50 | F |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Three sequential stages — **input, process, display** — are modelled as independent threads with explicit readiness conditions, mapping each requirement to a verifiable boolean state transition.

### 2. 🏗️ Design
The two boolean flags (`isMarksEntered`, `isGradeCalculated`) implement a lightweight **state machine** — each thread only proceeds when the required upstream state is `true`, creating a dependency graph without explicit thread coordination primitives.

### 3. 💻 Development
The hardcoded `inputMarks = 85` with an inline comment `// change to -10 for exception` is a deliberate **test switch** — a quick way to verify both success and failure paths during development without adding command-line argument parsing.

### 4. 🧪 Testing
Each thread class can be tested by pre-configuring the `Student` object:
- `InputThread`: verify `student.isMarksEntered == true` after `run()` with valid marks
- `GradeThread`: set `student.isMarksEntered = true` manually, then call `run()` and assert grade
- `ResultThread`: set both flags `true` manually, then verify output

### 5. 🔧 Maintenance
Adding a new pipeline stage (e.g., `ReportThread` that generates a PDF) requires only a new `Thread` subclass that checks `isGradeCalculated` and adds a new boolean flag — all existing classes are unchanged.

### 6. 📈 Scalability
The boolean flag pattern is the foundation of `CountDownLatch` in `java.util.concurrent` — replacing `isMarksEntered` with `latch.await()` / `latch.countDown()` gives the same staged coordination with true thread-safety and no sleep dependency.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| No `volatile` on flags | `isMarksEntered` and `isGradeCalculated` are not `volatile` — in theory, a thread may read a stale cached value; safe here only because of sleep timing gaps |
| No `synchronization` | `Student` fields are accessed without `synchronized` — safe only because sleep timings prevent true concurrent access |
| Sleep-dependent correctness | If `GradeThread` sleep were reduced to `0ms`, it could read `isMarksEntered = false` before `InputThread` writes it |
| One exception, three contexts | `InvalidMarksException` is overloaded with three different meanings — harder to catch and handle differently in a larger system |
| `e.printStackTrace()` | Used for `InterruptedException` — acceptable in demos but should be replaced with proper logging in production |
| No `join()` in main | `main()` exits after `start()` — threads complete independently with no guarantee of JVM shutdown timing |

---

## 🔄 Comparison: This Program vs FileProcessingSimulation

| Feature | `FileProcessingSimulation` | `StudentResultProcessing` |
|---------|---------------------------|--------------------------|
| Coordination flags | `isAvailable` (1 flag) | `isMarksEntered` + `isGradeCalculated` (2 flags) |
| Failure source | External simulation (`isMissing`) | Input validation (range check) |
| Exception reuse | 1 exception, 3 contexts | 1 exception, 3 contexts |
| Stage count | 3 (read, process, display) | 3 (input, grade, result) |
| Real-world domain | File I/O pipeline | Academic result pipeline |

Both programs share the same sleep-based coordination pattern — the key difference is two chained boolean flags here vs one flag in `FileProcessingSimulation`.

---

## 🛠️ Possible Enhancements

- Add `volatile` to `isMarksEntered` and `isGradeCalculated` for safe cross-thread visibility
- Replace sleep-based ordering with `CountDownLatch` for true, timing-independent coordination
- Accept `inputMarks` from the command line (`args[0]`) instead of hardcoding
- Add per-stage exception types (`MarksValidationException`, `GradeCalculationException`) for finer error handling
- Extend the grade logic to include `A+` for marks ≥ 95
- Add a `volatile boolean failed` flag in `Student` so downstream threads can abort immediately on upstream failure

---

## 📄 License
Free to use for educational purposes.
