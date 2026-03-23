# Multilevel Inheritance — Java Demo

A simple Java program demonstrating **Multilevel Inheritance** — a fundamental **Object-Oriented Programming** concept where a class inherits from a class that is itself a subclass, forming a chain of inheritance across three levels: `Animal → Dog → Puppy`.

---

## 📁 Files

| File | Description |
|------|-------------|
| `MultilevelInheritanceExample.java` | Main source file containing all three classes and entry point |

---

## 🚀 How to Run

```bash
javac MultilevelInheritanceExample.java
java MultilevelInheritanceExample
```

**Output:**
```
Animal eats food
Dog barks
Puppy weeps
```

---

## 🧠 Concepts Demonstrated

- **Multilevel Inheritance** — a 3-level chain where each class extends the one above it
- **Method Inheritance** — `Puppy` object accesses methods from all three levels (`eat`, `bark`, `weep`)
- **IS-A Relationship** — Puppy IS-A Dog, Dog IS-A Animal, therefore Puppy IS-A Animal
- **Code Reusability** — child classes automatically inherit all parent behaviours without rewriting them

---

## 🔗 Inheritance Chain

```
Animal          →    eat()
  └── Dog       →    bark()
        └── Puppy →  weep()
```

A single `Puppy` object can invoke all three methods from all three levels of the hierarchy.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Real-world hierarchies (Animal → Dog → Puppy) map naturally to class hierarchies — inheritance keeps the domain model **intuitive and close to reality**.

### 2. 🏗️ Design
Multilevel inheritance enforces a **clear parent-child structure** — responsibilities are distributed across levels, making system architecture easier to reason about.

### 3. 💻 Development
Child classes **automatically acquire** parent behaviour — developers write only what is new at each level, drastically reducing boilerplate code.

### 4. 🧪 Testing
Each class can be **tested in isolation** before integrating the full chain — `Animal` and `Dog` are verified independently before testing `Puppy`.

### 5. 🔧 Maintenance
Changes to a parent class (e.g., updating `eat()` in `Animal`) **propagate automatically** to all child classes — a single fix applies everywhere in the hierarchy.

### 6. 📈 Scalability
New levels or sibling classes (e.g., `Cat extends Animal`) can be added **without touching existing code**, following the Open/Closed Principle.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Depth limit | Too many inheritance levels increase complexity — keep chains shallow (2–3 levels ideal) |
| Tight coupling | Child classes are tightly coupled to parents — changes in `Animal` affect all descendants |
| Java restriction | Java supports **single inheritance** only — a class cannot extend more than one class |
| Alternative | Use **interfaces** or **composition** when behaviour doesn't fit a strict IS-A hierarchy |

---

## 🛠️ Possible Enhancements

- Add `@Override` methods to demonstrate **runtime polymorphism**
- Introduce an `interface` (e.g., `Trainable`) to combine inheritance with abstraction
- Add constructors at each level to demonstrate **constructor chaining** via `super()`
- Extend the hierarchy with sibling classes like `Cat`, `Kitten`

---

## 📄 License
Free to use for educational purposes.
