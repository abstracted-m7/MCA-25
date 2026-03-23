# Runtime Polymorphism — Java Demo

A Java program demonstrating **Runtime Polymorphism** (Dynamic Method Dispatch) using a base class reference `Animal` that points to different subclass objects (`Dog`, `Cat`) at runtime — with each resolving to its own overridden `sound()` method.

---

## 📁 Files

| File | Description |
|------|-------------|
| `RuntimePolymorphismExample.java` | Main source file containing `Animal`, `Dog`, `Cat` classes and entry point |

---

## 🚀 How to Run

```bash
javac RuntimePolymorphismExample.java
java RuntimePolymorphismExample
```

**Output:**
```
Animal makes a sound
Dog barks
Cat meows
```

---

## 🧠 Concepts Demonstrated

- **Runtime Polymorphism** — method call is resolved at **runtime**, not compile time
- **Dynamic Method Dispatch** — JVM decides which `sound()` to call based on the actual object type
- **Base Class Reference** — a single `Animal` reference (`obj`) holds objects of different subclass types
- **Method Overriding** — `@Override` ensures child classes replace the parent's `sound()` behaviour
- **Upcasting** — assigning `Dog` / `Cat` objects to an `Animal` reference is implicit upcasting

---

## 🔄 How Dynamic Dispatch Works

```
Animal obj;

obj = new Animal()  →  calls Animal.sound()   →  "Animal makes a sound"
obj = new Dog()     →  calls Dog.sound()      →  "Dog barks"
obj = new Cat()     →  calls Cat.sound()      →  "Cat meows"
```

Same reference variable `obj`, same method call `obj.sound()` — but **three different behaviours** based on the actual object assigned at runtime.

---

## ⚙️ Compile-Time vs Runtime Polymorphism

| Feature | Compile-Time (Overloading) | Runtime (Overriding) |
|--------|---------------------------|----------------------|
| Resolved at | Compile time | Runtime |
| Mechanism | Method Overloading | Method Overriding |
| Keyword | — | `@Override` |
| Flexibility | Low | High |
| Example | `calc.add(int, int)` | `obj.sound()` via base ref |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
When multiple entities share a common action (e.g., all animals make sounds), polymorphism allows a **single unified interface** to be defined — keeping requirements clean and extensible.

### 2. 🏗️ Design
Follows the **Open/Closed Principle** — the `Animal` base class is closed for modification but open for extension. New animals can be added without changing existing code or logic.

### 3. 💻 Development
A single `Animal` reference can drive any subclass — developers write **generic, reusable code** (e.g., loops, method parameters) that works across all subclasses automatically.

### 4. 🧪 Testing
Each subclass can be **tested independently** against the base class contract. Mocking and test substitution are natural — any `Animal` reference can be swapped with a test double.

### 5. 🔧 Maintenance
Adding a new animal (e.g., `Bird`) requires **zero changes** to existing classes — just create a new subclass with `@Override`. This dramatically reduces maintenance risk.

### 6. 📈 Scalability
Runtime polymorphism is the engine behind **frameworks, plugin systems, and APIs** — Spring, JDBC drivers, and Java Collections all rely heavily on this pattern at scale.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| `@Override` annotation | Always use it — catches typos and ensures true overriding at compile time |
| Base ref limitation | `Animal obj` cannot access Dog-specific methods (e.g., `bark()`) without downcasting |
| Downcasting risk | Casting `obj` back to `Dog` without `instanceof` check can throw `ClassCastException` |
| Abstract alternative | Declare `sound()` as `abstract` in `Animal` to **force** every subclass to provide its own version |

---

## 🛠️ Possible Enhancements

- Make `Animal` an **abstract class** or **interface** to enforce overriding
- Use a `List<Animal>` to call `sound()` on all objects in a loop — the cleanest real-world use of this pattern
- Add `instanceof` checks and **downcasting** to access subclass-specific methods
- Introduce an `interface` like `Speakable` to decouple from the inheritance chain

---

## 📄 License
Free to use for educational purposes.
