# Multithreaded Number Analyzer — Java Threading & Custom Exception Demo

A Java program demonstrating **multithreading, custom exceptions, and thread coordination** — an input thread (`NumberGenerator`) validates a user-entered number, then two threads run concurrently to check if it is prime (`PrimeChecker`) and compute its factorial (`FactorialCalculator`), with `join()` ensuring correct sequencing.

---

## 📁 Files

| File | Description |
|------|-------------|
| `MultiThread.java` | Single source file containing all four classes and entry point |

---

## 🚀 How to Run

```bash
javac MultiThread.java
java MultiThread
```

**Sample Interaction — Valid Input:**
```
Enter a positive number: 7
You entered: 7
[Prime Checker] 7 is a prime number.
[Factorial] 7! = 5040
```

**Sample Interaction — Negative Input:**
```
Enter a positive number: -5
Negative numbers are not allowed.
```

---

## 🧠 Concepts Demonstrated

- **Custom Exception** — `InvalidNumberException extends Exception` with a parameterised message constructor
- **Thread Subclassing** — all three workers extend `Thread` and override `run()`
- **`start()` vs `run()`** — `t1.start()` launches a new OS thread; calling `run()` directly would execute on the main thread
- **`join()`** — `t1.join()` blocks `main` until `NumberGenerator` finishes before spawning the next two threads
- **Concurrent Execution** — `t2` and `t3` start and run simultaneously without waiting for each other
- **`try-catch-finally`** — exception handling inside `run()` with `finally` block for `Scanner` cleanup
- **Early Exit Guard** — `if (t1.number <= 0) return` prevents downstream threads from running on invalid input

---

## 🔄 Thread Execution Timeline

```
main thread
   │
   ├── t1.start()  ─────────────────────────────────┐
   │                                                 │ NumberGenerator
   ├── t1.join()   ← blocks until t1 finishes ←─────┘
   │
   ├── t2.start()  ──────────────────┐  PrimeChecker
   ├── t3.start()  ──────────────────┼──────────────┐  FactorialCalculator
   │                                 │              │
   ├── t2.join()   ← waits ←─────────┘              │
   ├── t3.join()   ← waits ←────────────────────────┘
   │
   └── main exits
```

`t2` and `t3` run **in parallel** — their output order may vary between executions.

---

## 🧵 Thread Classes Overview

| Class | Extends | Input | Output | Key Logic |
|-------|---------|-------|--------|-----------|
| `NumberGenerator` | `Thread` | User keyboard input | Validated `int number` | Throws `InvalidNumberException` if negative |
| `PrimeChecker` | `Thread` | `int number` via constructor | Prime / Not prime | Loops from 2 to `√number` |
| `FactorialCalculator` | `Thread` | `int number` via constructor | `n!` as `long` | Iterative multiplication |
| `InvalidNumberException` | `Exception` | Error message string | — | Passes message to `super()` |

---

## 🔐 Custom Exception Flow

```
NumberGenerator.run()
  │
  ├── number < 0?
  │     └── throw new InvalidNumberException("Negative numbers are not allowed.")
  │           └── catch(InvalidNumberException e)
  │                 ├── print e.getMessage()
  │                 └── number = -1  ← signals error to main thread
  │
  └── finally { sc.close() }  ← always runs, valid or not
```

`number = -1` is the **error sentinel** — `main()` checks `t1.number <= 0` and exits before spawning `t2`/`t3`.

---

## ⚙️ Prime Checking Logic

```
For number = 7:
  Loop i = 2 to √7 ≈ 2.64 → only i = 2
  7 % 2 ≠ 0 → isPrime stays true → "7 is a prime number"

For number = 9:
  Loop i = 2 to √9 = 3 → i = 2, 3
  9 % 3 == 0 → isPrime = false → "9 is not a prime number"
```

Using `Math.sqrt(number)` reduces the loop from `O(n)` to `O(√n)` — a standard optimisation for primality testing.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Three distinct operations — **input validation**, **prime checking**, and **factorial calculation** — are identified as independent, parallelisable tasks, each mapped to its own thread class.

### 2. 🏗️ Design
The **sequential-then-parallel** flow (`t1` first, then `t2`+`t3` together) reflects a real pipeline design pattern — validate input once, then fan out to independent processors. `join()` enforces the dependency boundary cleanly.

### 3. 💻 Development
Using `long` for factorial (not `int`) correctly handles large values — `13! = 6,227,020,800` overflows `int` (max ~2.1 billion). This is a deliberate and important type choice.

### 4. 🧪 Testing
Each thread class accepts its input via constructor — `new PrimeChecker(7)` and `new FactorialCalculator(7)` can be instantiated and tested without any user input or `NumberGenerator` involvement.

### 5. 🔧 Maintenance
Adding a new computation (e.g., `FibonacciCalculator`) requires only a new `Thread` subclass — `main()` adds one `start()` and one `join()`. No existing thread class is touched.

### 6. 📈 Scalability
The fan-out pattern (`t2`, `t3` in parallel) scales naturally to a thread pool (`ExecutorService`) for processing many numbers simultaneously — the same class structure works unchanged in a pooled architecture.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Shared field access | `t1.number` is accessed from `main` after `join()` — safe here, but direct field sharing between threads is risky without `volatile` or synchronisation in larger programs |
| `number = -1` sentinel | Relies on convention — `0` is also blocked (`<= 0` guard), but 0! = 1 is mathematically valid |
| `sc.close()` in thread | Closing `Scanner(System.in)` inside a thread closes `System.in` globally — any subsequent `Scanner` use elsewhere would fail |
| Concurrent output order | `t2` and `t3` run in parallel — the order of their `println` output is **non-deterministic** |
| `long` factorial limit | `long` overflows beyond `20!` (9.2 × 10¹⁸) — `BigInteger` is needed for larger values |
| `InterruptedException` handling | `Thread.currentThread().interrupt()` correctly restores the interrupt flag before exiting |

---

## 🛠️ Possible Enhancements

- Use `volatile int number` in `NumberGenerator` to ensure safe cross-thread visibility
- Replace `number = -1` sentinel with a proper `AtomicBoolean` error flag
- Use `BigInteger` in `FactorialCalculator` to support numbers beyond 20
- Add a `FibonacciCalculator` thread to run alongside `t2` and `t3`
- Replace manual thread management with `ExecutorService` and `Future<>` for scalable parallel execution
- Loop the program to process multiple numbers without restarting using a `while` loop in `main`

---

## 📄 License
Free to use for educational purposes.
