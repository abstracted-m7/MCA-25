# Inventory Management System — Java Synchronized Stock Control Demo

A Java program simulating **concurrent inventory operations** where multiple threads add and remove stock from a shared `Inventory` object simultaneously. Demonstrates **`synchronized` method locking for shared integer state, guard conditions for insufficient stock, `Thread.currentThread().getName()` for operation tracing, and true concurrent thread execution**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `code.java` | Single source file with `Inventory`, `AddStockThread`, `RemoveStockThread`, and `main` |

---

## 🚀 How to Run

```bash
javac code.java
java code
```

**Sample Output (thread order varies per run):**
```
Thread-0 added 10 items. Stock: 30
Thread-1 removed 5 items. Stock: 25
Thread-3 added 10 items. Stock: 35
Thread-2 removed 5 items. Stock: 30
```

> Output order is non-deterministic — `synchronized` guarantees correct stock values, not execution order.

---

## 🧠 Concepts Demonstrated

- **`synchronized` Methods** — `addStock()` and `removeStock()` both lock on the same `Inventory` instance, preventing simultaneous stock mutations
- **Guard Condition** — `if (stock >= quantity)` in `removeStock()` prevents negative stock without throwing an exception — a graceful degradation approach
- **`Thread.currentThread().getName()`** — auto-assigned names (`Thread-0` through `Thread-3`) printed inside synchronized methods for clear operation tracing
- **Shared Mutable Integer** — `stock` is the single shared state variable protected by two `synchronized` methods
- **True Concurrency** — no `join()`, no `sleep()` — all four threads start and compete simultaneously
- **Two Thread Types** — `AddStockThread` and `RemoveStockThread` model two real-world actor types operating on the same resource

---

## 🔄 Thread Operation Map

| Thread | Type | Operation | Quantity |
|--------|------|-----------|----------|
| t1 (Thread-0) | `AddStockThread` | `addStock(10)` | +10 |
| t2 (Thread-1) | `RemoveStockThread` | `removeStock(5)` | -5 |
| t3 (Thread-2) | `RemoveStockThread` | `removeStock(5)` | -5 |
| t4 (Thread-3) | `AddStockThread` | `addStock(10)` | +10 |

**Initial stock: 20 | Net change: +10 | Expected final stock: 30**  
(assuming all removes succeed — stock never drops below 5 with initial 20)

---

## 🔒 Why Both Methods Must Be `synchronized`

```
Scenario — addStock and removeStock without synchronization:
  Thread-0 reads stock = 20
  Thread-1 reads stock = 20          ← both see stale value
  Thread-0 writes stock = 30
  Thread-1 writes stock = 15         ← Thread-0's add is lost!

With synchronized on BOTH methods:
  Thread-0 enters addStock() → lock acquired
  Thread-1 enters removeStock() → BLOCKED (same lock object)
  Thread-0: stock = 20 + 10 = 30 → releases lock
  Thread-1: reads stock = 30 → 30 - 5 = 25 → correct
```

If only one method were `synchronized`, the other could still interleave — **both must lock on the same object**.

---

## 🔐 Guard Condition vs Exception

```java
// This program — graceful degradation (print warning, skip operation)
if (stock >= quantity) {
    stock -= quantity;
} else {
    System.out.println("...not enough stock!");
}

// Exception-based approach (from BankingSystem / TicketBooking)
if (quantity > stock) {
    throw new InsufficientStockException("Not enough stock!");
}
```

| Approach | Behaviour on failure | Best for |
|----------|---------------------|----------|
| Guard condition (this program) | Logs warning, continues silently | Non-critical stock warnings |
| Custom exception | Propagates failure to caller | Systems where failure must be handled upstream |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Two operations — **add stock** and **remove stock** — with one constraint (**cannot remove more than available**) map directly to two methods and one `if` guard, keeping the requirement-to-code mapping explicit and traceable.

### 2. 🏗️ Design
Both `addStock()` and `removeStock()` lock on `this` — the same `Inventory` instance. This means **no two operations of any kind can overlap**, which is the correct design for a single shared counter.

### 3. 💻 Development
The guard condition inside `removeStock()` is placed **within** the `synchronized` block — this is critical. Moving it outside would create a TOCTOU bug: the stock could change between the check and the deduction.

### 4. 🧪 Testing
`Inventory` can be unit tested directly — `addStock(10)` on a stock-20 inventory should yield 30; `removeStock(25)` should print the warning and leave stock unchanged. No threads needed for logic verification.

### 5. 🔧 Maintenance
Adding a new operation (e.g., `transferStock(Inventory target, int qty)`) requires only a new `synchronized` method — all existing thread classes and `main()` remain unchanged.

### 6. 📈 Scalability
For high-throughput warehousing systems, `AtomicInteger` for `stock` with `compareAndSet()` offers lock-free updates — dramatically higher throughput than `synchronized` when hundreds of threads compete simultaneously.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Auto thread names | Without `setName()`, threads get `Thread-0` through `Thread-3` in creation order — same as previous programs but less descriptive |
| Silent failure on remove | Insufficient stock prints a warning but does not throw — the calling thread has no programmatic way to detect the failure |
| No return value | Neither method returns the new stock value — callers cannot confirm the updated state without another method |
| Class named `code` | Violates Java naming convention — should be `InventorySystem` or `InventoryDemo` |
| Final stock is deterministic | With initial=20, +10+10-5-5=+10, final=30 — but intermediate states vary by thread order |
| No `volatile` needed | `synchronized` already provides full memory visibility — `volatile int stock` would be redundant |

---

## 🔄 Series Comparison: All `synchronized` Programs

| Program | Resource | Guard Type | Failure Response | Concurrency |
|---------|----------|-----------|-----------------|-------------|
| `OnlineTicketBooking` | Ticket count | Exception | Propagate to caller | Sequential (`join`) |
| `BankingSystem` | Balance (double) | 2 exceptions | Propagate to caller | Concurrent |
| `LoginSystem` | Credentials | Exception | Propagate to caller | Concurrent |
| **`InventorySystem`** | **Stock (int)** | **Guard condition** | **Silent warning** | **Concurrent** |

This program is the only one in the series that uses a **guard condition** instead of an exception for constraint violation — making it the simplest and most forgiving failure model.

---

## 🛠️ Possible Enhancements

- Add `setName()` calls: `t1.setName("Supplier-1")`, `t2.setName("Customer-1")` for meaningful output
- Return the updated `int stock` from both methods so callers can confirm the new state
- Introduce a `getStock()` getter for external balance queries
- Throw a custom `InsufficientStockException` instead of silently printing — allows callers to handle failure
- Replace `synchronized` with `AtomicInteger` for `stock` for lock-free, high-throughput updates
- Add a `setName()` call before `start()` to label threads as `"Supplier"` and `"Customer"` for clearer logs

---

## 📄 License
Free to use for educational purposes.
