# Banking System — Java Multi-Exception Synchronized Threading Demo

A Java program simulating **concurrent bank transactions** where multiple deposit and withdrawal threads operate on a shared `BankAccount`. Demonstrates **two custom exceptions, `synchronized` methods, multi-catch exception handling, `Thread.currentThread().getName()`, and dual-level input validation** in a realistic financial context.

---

## 📁 Files

| File | Description |
|------|-------------|
| `BankingSystem.java` | Single source file with bank account, thread classes, exceptions, and entry point |

---

## 🚀 How to Run

```bash
javac BankingSystem.java
java BankingSystem
```

**Expected Output (order may vary — threads run concurrently):**
```
Thread-0 depositing: 500.0
Balance after deposit: 1500.0
Thread-1 withdrawing: 300.0
Balance after withdrawal: 1200.0
Withdraw Error: Insufficient balance!
Deposit Error: Deposit amount must be positive!
Thread-4 withdrawing: 200.0
Balance after withdrawal: 1000.0
```

---

## 🧠 Concepts Demonstrated

- **Two Custom Exceptions** — `InsufficientBalanceException` (business rule) and `InvalidAmountException` (input validation) are separate, distinct exception types
- **`synchronized` Methods** — both `deposit()` and `withdraw()` lock on the `BankAccount` instance to prevent concurrent balance corruption
- **Multi-catch** — `catch (InsufficientBalanceException | InvalidAmountException e)` handles both exceptions in one block
- **`Thread.currentThread().getName()`** — prints auto-assigned thread names (`Thread-0`, `Thread-1`...) from inside `run()`
- **Dual Validation** — each method checks both amount validity (`<= 0`) and business constraint (`> balance`) before executing
- **True Concurrency** — no `join()` in `main()` — all five threads start and run simultaneously

---

## 🔄 Transaction Breakdown

| Thread | Type | Amount | Expected Outcome |
|--------|------|--------|-----------------|
| `d1` (Thread-0) | Deposit | +500 | Success — balance: 1000 → 1500 |
| `w1` (Thread-1) | Withdraw | -300 | Success — balance: 1500 → 1200 |
| `w2` (Thread-2) | Withdraw | -1500 | Fail — `InsufficientBalanceException` |
| `d2` (Thread-3) | Deposit | -100 | Fail — `InvalidAmountException` |
| `w3` (Thread-4) | Withdraw | -200 | Success — balance: 1200 → 1000 |

> Actual output order depends on thread scheduling — `synchronized` guarantees correctness, not order.

---

## 🔒 Synchronized Block Behaviour

```
Without synchronized (race condition):
  Thread-0 reads balance = 1000
  Thread-1 reads balance = 1000         ← both see same value
  Thread-0 writes balance = 1500
  Thread-1 writes balance = 700         ← Thread-0's deposit is lost!

With synchronized (safe):
  Thread-0 enters deposit() → lock acquired on BankAccount
  Thread-1 attempts withdraw() → BLOCKED
  Thread-0: balance = 1000 + 500 = 1500 → releases lock
  Thread-1: reads balance = 1500 → 1500 - 300 = 1200 → correct
```

Both `deposit()` and `withdraw()` lock on the **same object** (`this` = the `BankAccount` instance) — so a deposit and a withdrawal can never overlap.

---

## 🔐 Exception Hierarchy

```
Exception
  │
  ├── InsufficientBalanceException   ← business rule violation
  │     "Insufficient balance!"         (amount > current balance)
  │
  └── InvalidAmountException         ← input validation failure
        "Deposit amount must be positive!"
        "Withdrawal amount must be positive!"
```

Two separate exception types allow callers to **handle each failure differently** if needed — a single `Exception` would lose that distinction.

---

## 🔀 Multi-Catch Syntax

```java
// WithdrawThread uses multi-catch — handles both failures the same way
catch (InsufficientBalanceException | InvalidAmountException e) {
    System.out.println("Withdraw Error: " + e.getMessage());
}

// DepositThread uses single catch — only InvalidAmountException is possible
catch (InvalidAmountException e) {
    System.out.println("Deposit Error: " + e.getMessage());
}
```

`deposit()` cannot throw `InsufficientBalanceException` (deposits always increase balance) — so `DepositThread` only needs a single catch. `WithdrawThread` needs both.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Two distinct failure modes — **invalid input** and **insufficient funds** — are separated into two exceptions. This maps directly to two separate business requirements and allows them to be handled, logged, or reported independently in a real system.

### 2. 🏗️ Design
`BankAccount` is a **self-contained, thread-safe component** — the synchronisation is internal. Any number of threads can be added in `main()` without modifying `BankAccount`. This is the **Open/Closed Principle** applied to concurrent design.

### 3. 💻 Development
`Thread.currentThread().getName()` inside `run()` is a zero-setup way to identify which thread is executing — useful during development and debugging without adding any fields to the thread class.

### 4. 🧪 Testing
`deposit()` and `withdraw()` can be called directly on a `BankAccount` instance in unit tests — no threads needed. The `synchronized` keyword has no effect on single-threaded test execution, so all business logic paths are testable in isolation.

### 5. 🔧 Maintenance
Adding a new transaction type (e.g., `Transfer`) needs only a new `synchronized` method in `BankAccount` and a new thread class — all existing classes remain unchanged. The two exception types are already reusable for any new operation.

### 6. 📈 Scalability
`synchronized` works well for moderate concurrency. A real banking backend would use `ReentrantReadWriteLock` (multiple concurrent reads, exclusive writes) or `AtomicDouble` — but the structural design here scales directly to those upgrades.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| No `join()` in main | All five threads run truly concurrently — output order is non-deterministic |
| Auto thread names | `Thread-0` through `Thread-4` are assigned in creation order — these names are not meaningful in concurrent output without `join()` |
| `double` for balance | Floating-point arithmetic can cause precision errors (e.g., `0.1 + 0.2 ≠ 0.3`) — real banking systems use `BigDecimal` |
| No balance getter | `balance` has no getter — current balance is only visible through transaction output, not queryable externally |
| Lock scope | Both methods lock on `this` — a deposit and withdrawal can never run simultaneously, which is correct but reduces throughput vs read/write lock separation |
| w2 may shift balances | If `w2` runs before `w1`, the remaining balance changes — `w1` or `w3` could then fail depending on thread order |

---

## 🔄 This Program vs Previous Ticket Booking

| Feature | `OnlineTicketBooking` | `BankingSystem` |
|---------|----------------------|----------------|
| Thread execution | Sequential (`join()`) | Concurrent (no `join()`) |
| Custom exceptions | 1 (`NoTicketAvailableException`) | 2 (`Insufficient` + `Invalid`) |
| Exception catching | Single catch | Single + Multi-catch |
| Output order | Deterministic | Non-deterministic |
| Validation layers | 1 (availability check) | 2 (input + business rule) |

---

## 🛠️ Possible Enhancements

- Replace `double` with `BigDecimal` for precise monetary arithmetic
- Add a `getBalance()` method to query balance without a transaction
- Add `join()` calls and compare output order with the concurrent version
- Implement a `transfer(BankAccount target, double amount)` method with two synchronized accounts
- Use `ReentrantReadWriteLock` to allow multiple concurrent balance reads while keeping writes exclusive
- Add a **transaction history** using `ArrayList<String>` storing each completed operation with timestamp

---

## 📄 License
Free to use for educational purposes.
