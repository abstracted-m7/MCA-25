# Calculator — Java Method Overloading Demo

A simple Java program demonstrating **Method Overloading** — a core **compile-time polymorphism** concept in Object-Oriented Programming, using a `Calculator` class that handles integers, doubles, and strings with the same method name `add()`.

---

## 📁 Files

| File | Description |
|------|-------------|
| `MethodOverloadingDemo.java` | Main source file containing `Calculator` class and entry point |

---

## 🚀 How to Run

```bash
javac MethodOverloadingDemo.java
java MethodOverloadingDemo
```

**Output:**
```
Sum of integers:  30
Sum of doubles :  30.8
Concatenation  :  Manish Giri
```

---

## 🧠 Concepts Demonstrated

- **Method Overloading** — same method name `add()` with different parameter types
- **Compile-Time Polymorphism** — JVM resolves the correct method at compile time based on argument types
- **Type Flexibility** — one intuitive interface handles `int`, `double`, and `String` operations cleanly

---

## 🔀 Overloaded Methods at a Glance

| Method Signature | Input Types | Operation |
|-----------------|-------------|-----------|
| `add(int, int)` | Two integers | Arithmetic sum |
| `add(double, double)` | Two decimals | Arithmetic sum |
| `add(String, String)` | Two strings | Concatenation |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Identifies the need for a **unified operation** (addition/concatenation) across multiple data types — keeps requirements clean and the user-facing API minimal.

### 2. 🏗️ Design
Overloading supports the **Single Responsibility Principle** — one class handles one concept (addition) across types without creating separate `addInt()`, `addDouble()` methods.

### 3. 💻 Development
Reduces method name clutter and makes code **intuitive to write** — developers call `calc.add()` regardless of data type, lowering cognitive load.

### 4. 🧪 Testing
Each overloaded version is an **independent unit** — can be tested separately with targeted test cases for integers, decimals, and strings.

### 5. 🔧 Maintenance
Adding a new type (e.g., `long` or `float`) requires only a **new overload**, leaving existing methods untouched — zero regression risk.

### 6. 📈 Scalability
The pattern scales naturally into larger frameworks — Java's own `System.out.println()` is a real-world example of overloading used at massive scale.

---

## 🛠️ Possible Enhancements

- Add overloads for `long`, `float`, or array inputs
- Extend to `subtract()`, `multiply()`, `divide()` with the same overloading pattern
- Add input validation (e.g., division by zero guard)
- Wrap in a REST API or GUI calculator interface

---

## 📄 License
Free to use for educational purposes.
