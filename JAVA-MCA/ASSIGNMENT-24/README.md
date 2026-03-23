# Partial Abstraction — Java Abstract Class Demo

A Java program demonstrating **Partial Abstraction** using an `abstract class` — where the base class `Shape` defines both an **abstract method** (`draw()`) that subclasses must implement, and a **concrete method** (`message()`) that is shared by all subclasses as-is.

---

## 📁 Files

| File | Description |
|------|-------------|
| `PartialAbstractionDemo.java` | Main source file containing `Shape`, `Circle`, `Rectangle` classes and entry point |

---

## 🚀 How to Run

```bash
javac PartialAbstractionDemo.java
java PartialAbstractionDemo
```

**Output:**
```
Drawing a Circle
This is a shape.
Drawing a Rectangle
This is a shape.
```

---

## 🧠 Concepts Demonstrated

- **Partial Abstraction** — abstract class mixes abstract and concrete methods (0%–99% abstraction)
- **Abstract Method** — `draw()` has no body; every subclass **must** provide its own implementation
- **Concrete Method** — `message()` has a body; inherited and shared by all subclasses unchanged
- **Abstract Class as Reference** — `Shape s1 = new Circle()` uses base reference with runtime dispatch
- **Forced Contract** — any class extending `Shape` is **compiler-enforced** to implement `draw()`

---

## 🔍 Abstract vs Concrete Method in This Program

| Method | Type | Defined in | Behaviour |
|--------|------|------------|-----------|
| `draw()` | Abstract | `Shape` | Must be overridden by every subclass |
| `message()` | Concrete | `Shape` | Inherited as-is — no override needed |

---

## 🏛️ Abstraction Levels in Java

```
Concrete Class       →   0% abstraction   (all methods have body)
Abstract Class       →   1–99% abstraction (mix of abstract + concrete)  ← This Program
Interface (pre-J8)   →   100% abstraction  (no method has body)
Interface (Java 8+)  →   Partial allowed   (default & static methods added)
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Partial abstraction maps perfectly to systems where **some behaviour is universal** (all shapes display a message) and **some is specific** (each shape draws differently) — requirements stay cleanly separated.

### 2. 🏗️ Design
Follows the **Template Method Pattern** — the abstract class defines the skeleton of behaviour, while subclasses fill in the specifics. This is a foundational enterprise design pattern.

### 3. 💻 Development
The compiler **enforces the contract** — if a subclass forgets to implement `draw()`, the code will not compile. This eliminates an entire class of runtime bugs during development.

### 4. 🧪 Testing
The concrete `message()` method needs to be tested **only once** in `Shape`. Abstract methods are tested per subclass — clean, targeted, and non-repetitive test suites.

### 5. 🔧 Maintenance
Common logic lives in **one place** (`message()` in `Shape`). Any update to shared behaviour is made once and propagates to all subclasses — zero duplication risk.

### 6. 📈 Scalability
Adding new shapes (e.g., `Triangle`, `Hexagon`) requires **no changes to existing code** — just extend `Shape` and implement `draw()`. The system grows without breaking anything.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Cannot instantiate | `new Shape()` is illegal — abstract classes cannot be directly instantiated |
| Partial, not full | Unlike an interface, abstract class can hold state (fields) and constructors |
| Single inheritance | A class can extend only **one** abstract class — use interfaces for multiple contracts |
| Abstract ≠ Interface | Abstract classes are for **IS-A** relationships with shared state; interfaces are for **CAN-DO** contracts |

---

## 🔄 Abstract Class vs Interface (Quick Comparison)

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Methods | Abstract + Concrete | Abstract (+ default in Java 8+) |
| Fields | Allowed | Only `public static final` |
| Constructor | Allowed | Not allowed |
| Inheritance | Single | Multiple |
| Use case | Shared base with common logic | Pure capability contract |

---

## 🛠️ Possible Enhancements

- Add an `area()` abstract method to enforce shape-specific area calculation
- Introduce a constructor in `Shape` to initialize a `color` field — demonstrating abstract class state
- Add a `Triangle` class to show how easily the hierarchy extends
- Convert `Shape` to an `interface` and compare the structural difference

---

## 📄 License
Free to use for educational purposes.
