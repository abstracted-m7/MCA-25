# Modular Calculator — Java Interface-Based Design Demo

A menu-driven Java calculator that demonstrates **interface-based polymorphism** — each arithmetic operation is a separate class implementing a common `Operation` interface, selected dynamically at runtime through a `do-while` loop. Showcases **full abstraction, runtime polymorphism, exception handling, and modular OOP design**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `CalculatorApp.java` | Single source file containing the `Operation` interface, 5 concrete classes, and `main()` |

---

## 🚀 How to Run

```bash
javac CalculatorApp.java
java CalculatorApp
```

**Sample Interaction:**
```
---- Calculator Menu ----
1. Addition
2. Subtraction
3. Multiplication
4. Division
5. Modulo
6. Exit

--------------------------------
Enter your choice: 4
Enter first num: 10
Enter second num: 0
Error: Division by zero.
The result: 0.0

--------------------------------
Enter your choice: 6
Exiting..!!
```

---

## 🧠 Concepts Demonstrated

- **Interface** — `Operation` declares a single `calculate()` contract with no implementation
- **Full Abstraction** — interface provides 100% abstraction; all logic lives in concrete classes
- **Runtime Polymorphism** — `Operation op` reference holds different concrete objects per user choice
- **do-while Loop** — menu runs at least once and repeats until the user chooses Exit (ch == 6)
- **Exception Handling** — `Division` uses `try-catch` on `ArithmeticException` for divide-by-zero
- **Strategy Pattern** — each operation is a swappable strategy behind a unified interface reference

---

## 🏗️ Class & Interface Structure

```
«interface»
Operation
+ calculate(double a, double b): double
       │
       ├── Addition        →  return a + b
       ├── Subtraction     →  return a - b
       ├── Multiplication  →  return a * b
       ├── Division        →  return a / b  (with zero-check)
       └── Modulo          →  return a % b
```

`CalculatorApp.main()` uses a single `Operation op` reference — at runtime it points to whichever concrete class the user selects.

---

## 🔄 Program Flow

```
main()
  │
  └── do-while loop
        ├── Print menu & read choice (ch)
        ├── If ch 1-5 → read a, b
        ├── switch(ch) → assign concrete class to op
        │     case 1 → op = new Addition()
        │     case 2 → op = new Subtraction()
        │     case 3 → op = new Multiplication()
        │     case 4 → op = new Division()
        │     case 5 → op = new Modulo()
        │     case 6 → exit
        ├── op.calculate(a, b) → dispatched at runtime
        └── print result
```

---

## 🔐 Interface vs Abstract Class (This Program's Context)

| Feature | Interface (`Operation`) | Abstract Class |
|---------|------------------------|----------------|
| Abstraction | 100% — no method body | Partial — can have concrete methods |
| Fields | None (only constants) | Can have instance variables |
| Multiple impl | A class can implement many | Only one abstract class per class |
| Use case here | Pure operation contract | Not needed — no shared state |

> `Operation` is the right choice here because there is **no shared state or behaviour** across operations — each class only differs in its calculation formula.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Each arithmetic operation is a discrete, independent requirement — the interface models the **common contract** while keeping each operation's requirement cleanly isolated in its own class.

### 2. 🏗️ Design
This is a textbook implementation of the **Strategy Design Pattern** — `Operation` is the strategy interface, each concrete class is a strategy, and `CalculatorApp` is the context that selects and executes strategies dynamically.

### 3. 💻 Development
New operations (e.g., Power, Square Root) can be added by **creating a new class** that implements `Operation` — the `main()` switch gets one new `case`. Zero changes to any existing class.

### 4. 🧪 Testing
Each operation class is a **pure, stateless unit** — testable in complete isolation with `new Addition().calculate(3, 4)` without any menu or scanner involvement. Perfect for JUnit integration.

### 5. 🔧 Maintenance
Division's zero-check is **self-contained** inside the `Division` class — it never leaks into `main()` or other operations. Any change to division behaviour is isolated to one file.

### 6. 📈 Scalability
The interface-based structure is the foundation of **plugin architectures and dependency injection frameworks** — Spring's `@Service` beans follow this exact pattern at enterprise scale.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Division by zero | Caught via `try-catch` inside `Division` — returns `0.0`, which could mislead the user |
| Modulo with zero | `a % 0` returns `NaN` for doubles — no guard exists currently |
| `sc.nextLine()` after `nextInt()` | Correctly consumes the leftover newline to prevent input skipping |
| `op` null risk | If an invalid choice (e.g., 9) is entered, `op` stays `null` — the `ch >= 1 && ch <= 5` guard prevents a `NullPointerException` |
| `sc.close()` duplication | Scanner is closed inside `case 6` and again after the loop — redundant but harmless |
| Typos in code | `"Invalid chice"` should be `"Invalid choice"` — minor but worth fixing |

---

## 🛠️ Possible Enhancements

- Add a `Power` and `SquareRoot` operation class without touching existing code
- Guard `Modulo` against division by zero, matching `Division`'s safety
- Replace `switch` with a `Map<Integer, Operation>` for a cleaner, extensible dispatch table
- Add input validation to reject non-numeric entries gracefully
- Display a **calculation history** using `ArrayList<String>` across loop iterations
- Convert to a GUI calculator using `JFrame` with the same interface structure unchanged

---

## 📄 License
Free to use for educational purposes.
