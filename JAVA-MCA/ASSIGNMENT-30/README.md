# Payment Gateway System — Java Multiple Interface Implementation Demo

A console-based Java program simulating a **payment gateway** with three payment modes — Card, Wallet, and UPI. Each mode implements both a `Payment` and `Refund` interface, demonstrating **multiple interface implementation, interface constants, runtime polymorphism through dual references, and real-world OOP design**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `PaymentGatewaySystem.java` | Single source file with interfaces, concrete classes, and entry point |

---

## 🚀 How to Run

```bash
javac PaymentGatewaySystem.java
java PaymentGatewaySystem
```

**Sample Interaction — Successful Payment with Refund:**
```
Select Payment Mode
1. Card Payment
2. Wallet Payment
3. UPI Payment

Enter choice: 1
Enter amount: 80
Card payment of rs.80.0 processed successfully.
Do you want refund? (1-Yes / 0-No): 1
Refund of rs.80.0 issued to Card.
```

**Sample Interaction — Limit Exceeded:**
```
Enter choice: 3
Enter amount: 150
UPI limit exceeded.
```

---

## 🧠 Concepts Demonstrated

- **Multiple Interface Implementation** — `CardPayment`, `WalletPayment`, `UPIPayment` all implement both `Payment` and `Refund`
- **Interface Constant** — `MAX_LIMIT = 100` in `Payment` is implicitly `public static final` — shared across all implementing classes
- **Dual Interface References** — same object assigned to both `Payment p` and `Refund r` references simultaneously
- **Runtime Polymorphism** — `p.processPayment()` and `r.processRefund()` dispatch to the correct class at runtime
- **Boolean Return from Interface** — `processPayment()` returns `boolean` to signal success/failure and control program flow
- **Early Exit Pattern** — `return` after failure prevents the refund prompt from appearing on invalid payment

---

## 🏗️ Interface & Class Structure

```
«interface»               «interface»
  Payment                   Refund
+ MAX_LIMIT: 100          + processRefund(double): void
+ processPayment(double): boolean
         │                      │
         └──────────┬───────────┘
                    │ implements both
         ┌──────────┼──────────────┐
    CardPayment  WalletPayment  UPIPayment
```

One object, two interface references:
```java
CardPayment card = new CardPayment();
Payment p = card;   // used for processPayment()
Refund  r = card;   // used for processRefund()
```

---

## 🔄 Program Flow

```
main()
  │
  ├── Display menu → read choice & amount
  │
  ├── switch(choice) → create concrete object
  │     └── assign same object to both p (Payment) and r (Refund)
  │
  ├── p.processPayment(amount)
  │     ├── amount > MAX_LIMIT? → print error, return false → exit
  │     └── success? → return true → continue
  │
  └── Ask refund prompt
        ├── Yes (1) → r.processRefund(amount) → exit
        └── No  (0) → "Money debited" message → exit
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Two separate concerns — **payment processing** and **refund handling** — are modelled as two distinct interfaces. This keeps each responsibility independently specifiable, traceable, and changeable without affecting the other.

### 2. 🏗️ Design
Implementing **two interfaces on one class** mirrors real-world payment processors that both charge and refund. The dual-reference pattern (`Payment p`, `Refund r`) enforces **interface segregation** — code that only needs to process payments never sees refund methods, and vice versa.

### 3. 💻 Development
`MAX_LIMIT` as an **interface constant** (`public static final`) ensures the threshold is defined **once** and shared automatically across all payment modes — no risk of different classes using different limits.

### 4. 🧪 Testing
Each concrete class is independently testable — `new UPIPayment().processPayment(150)` can be verified without any scanner or menu interaction. The `boolean` return makes assertions trivial: `assert !upi.processPayment(150)`.

### 5. 🔧 Maintenance
Changing `MAX_LIMIT` updates it globally across all three payment modes instantly. Adding a new payment mode (e.g., `NetBankingPayment`) requires **zero changes** to existing classes — just implement both interfaces.

### 6. 📈 Scalability
This design is the direct foundation of real payment SDKs — Razorpay, Stripe, and PayPal internally use interface-based dispatch for payment methods. The pattern scales to hundreds of payment modes without structural change.

---

## 🔐 Interface Constant Behaviour

```java
interface Payment {
    double MAX_LIMIT = 100; // implicitly: public static final double MAX_LIMIT = 100
}
```

| Property | Value |
|----------|-------|
| Access modifier | `public` (always) |
| Mutability | `final` (cannot be reassigned) |
| Scope | `static` (belongs to the interface, not instances) |
| Accessible via | `Payment.MAX_LIMIT` or directly as `MAX_LIMIT` inside implementing classes |

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| `MAX_LIMIT` is shared | All three payment modes use the same `100` limit — individual per-mode limits are not possible with a single interface constant |
| Refund without payment check | `processRefund()` can be called even if payment logically failed — the `boolean` guard in `main()` prevents this, but `Refund` itself has no internal guard |
| `sc.nextLine()` after `nextInt()` | Used correctly to consume leftover newline before reading the next input |
| Multiple `sc.close()` paths | Scanner is closed in every exit branch — thorough resource management, though `try-with-resources` would be cleaner |
| No amount validation | Negative amounts are accepted and processed silently — no lower-bound guard exists |
| Typos in code | `"chnages"` appears in comments — cosmetic, but worth cleaning up |

---

## 🔄 Multiple Interface vs Single Interface (Design Comparison)

| Approach | Advantage | Drawback |
|----------|-----------|----------|
| Two interfaces (`Payment` + `Refund`) | Follows Interface Segregation Principle — callers only see what they need | Slightly more setup with dual references |
| One interface (both methods) | Simpler reference management | Forces all callers to know about both payment and refund even if irrelevant |

> Using two interfaces is the **correct enterprise approach** — it matches how real payment SDKs separate charge and refund APIs.

---

## 🛠️ Possible Enhancements

- Allow **per-mode limits** by overriding limit logic in each class (interface constants cannot be overridden — use an abstract class or method instead)
- Add **partial refund** support — `processRefund(double original, double refundAmount)`
- Introduce a `TransactionLogger` interface for a third layer of behaviour
- Replace `switch` with a `Map<Integer, Payment>` for a cleaner, extensible dispatch
- Add **input validation** to reject negative or zero amounts before processing
- Wrap in `try-with-resources` for automatic `Scanner` management

---

## 📄 License
Free to use for educational purposes.
