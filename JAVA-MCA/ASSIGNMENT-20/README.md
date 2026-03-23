# Student Grade Calculator — Java Constructor Demo

A simple Java program demonstrating **Object-Oriented Programming (OOP)** concepts using a `Student` class with a parameterized constructor, grade calculation logic, and console output.

---

## 📁 Files

| File | Description |
|------|-------------|
| `ConstructorDemo.java` | Main source file containing `Student` class and entry point |

---

## 🚀 How to Run

```bash
javac ConstructorDemo.java
java ConstructorDemo
```

**Output:**
```
Roll No : 40
Name    : Manish
Marks   : 92.0
Grade   : A
```

---

## 🧠 Concepts Demonstrated

- **Parameterized Constructor** — initializes student data at object creation
- **Encapsulation** — data and behavior bundled inside the `Student` class
- **Method Abstraction** — `calculateGrade()` hides grading logic from the caller

---

## 📊 Grading Criteria

| Marks | Grade |
|-------|-------|
| ≥ 80  | A     |
| ≥ 60  | B     |
| ≥ 40  | C     |
| < 40  | Fail  |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Defines core entities (Student) and business rules (grading thresholds) — forming the backbone of the system spec.

### 2. 🏗️ Design
The `Student` class is a reusable, self-contained unit — a foundation for larger MVC or layered architectures.

### 3. 💻 Development
Constructor-based initialization enforces **mandatory field population**, reducing null/incomplete object bugs during development.

### 4. 🧪 Testing
Each method (`calculateGrade`, `displayDetails`) can be **unit tested independently**, supporting TDD and clean test coverage.

### 5. 🔧 Maintenance
Business rule changes (e.g., updating grade boundaries) are **isolated to one method**, minimizing regression risk across the codebase.

### 6. 📈 Scalability
The class can be easily extended with features like subject-wise marks, weighted averages, or database persistence without breaking existing logic.

---

## 🛠️ Possible Enhancements

- Add input validation (marks between 0–100)
- Accept multiple students via arrays or `ArrayList`
- Persist data using file I/O or a database
- Build a GUI or REST API layer on top

---

## 📄 License
Free to use for educational purposes.
