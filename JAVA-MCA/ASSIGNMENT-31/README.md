# Student Grade System — Java Package & Encapsulation Demo

A multi-file Java program that demonstrates **package organisation, encapsulation, and class collaboration** — the `Student` and `GradeCalculator` classes live in the `academics` package and are imported into `MainApp`, simulating a real-world modular project structure.

---

## 📁 Project Structure

```
project-root/
│
├── MainApp.java                   ← Entry point (default package)
│
└── academics/
      ├── Student.java             ← Encapsulated student entity
      └── GradeCalculator.java     ← Grade logic class
```

---

## 🚀 How to Compile & Run

```bash
# Step 1 — Compile the package classes first
javac academics/Student.java academics/GradeCalculator.java

# Step 2 — Compile the main app
javac MainApp.java

# Step 3 — Run from project root
java MainApp
```

**Output:**
```
Name: XYZ
Roll No: 101
Marks: 82
Grade: A
```

---

## 🧠 Concepts Demonstrated

- **Packages** — `academics` package groups related classes (`Student`, `GradeCalculator`) logically
- **`import` Statement** — `MainApp` imports specific classes from the `academics` package
- **Encapsulation** — `Student` fields are `private`; accessed only through `public` getters
- **Getter Methods** — `getName()`, `getRollNo()`, `getMarks()` provide controlled read-only access
- **Class Collaboration** — `GradeCalculator` uses `s1.getMarks()` — classes interact through public APIs
- **Separation of Concerns** — student data and grade logic are in separate, independent classes

---

## 📦 Package Breakdown

### `academics.Student`
| Member | Type | Access | Purpose |
|--------|------|--------|---------|
| `name` | `String` | `private` | Student's name |
| `rollNo` | `int` | `private` | Unique roll number |
| `marks` | `int` | `private` | Marks scored |
| `Student(name, rollNo, marks)` | Constructor | `public` | Initialises all fields |
| `getName()` | Getter | `public` | Returns name |
| `getRollNo()` | Getter | `public` | Returns roll number |
| `getMarks()` | Getter | `public` | Returns marks |
| `displayInfo()` | Method | `public` | Prints student details |

### `academics.GradeCalculator`
| Member | Type | Access | Purpose |
|--------|------|--------|---------|
| `calculateGrade(int marks)` | Method | `public` | Returns grade string based on marks |

---

## 🎓 Grading Criteria

| Marks Range | Grade |
|-------------|-------|
| ≥ 90 | A+ |
| ≥ 75 | A |
| ≥ 60 | B |
| ≥ 50 | C |
| < 50 | Fail |

---

## 🔄 Program Flow

```
MainApp.main()
  │
  ├── new Student("XYZ", 101, 82)   →  Creates encapsulated student object
  ├── s1.displayInfo()               →  Prints name, roll no, marks
  ├── new GradeCalculator()          →  Creates grade calculator object
  ├── s1.getMarks()                  →  Public getter — safe field access
  └── gc.calculateGrade(82)          →  Returns "A" → printed in main
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Two distinct domain entities — **Student data** and **Grade logic** — are identified as separate requirements and modelled as separate classes in a dedicated package, ensuring clean requirement-to-class traceability.

### 2. 🏗️ Design
The `academics` package follows **Domain-Driven Design** principles — all academic concerns are grouped together. `MainApp` acts as the **application layer**, orchestrating domain objects without containing any business logic itself.

### 3. 💻 Development
`private` fields with `public` getters enforce **encapsulation** — no external class can directly modify `marks` or `rollNo`. This prevents accidental corruption of student data across a large codebase.

### 4. 🧪 Testing
`GradeCalculator.calculateGrade()` is a **pure function** — it takes an `int` and returns a `String` with no side effects. It can be unit tested exhaustively for all boundary values (`49`, `50`, `59`, `60`, `74`, `75`, `89`, `90`) without any object setup.

### 5. 🔧 Maintenance
Changing the grade boundaries requires editing only `GradeCalculator.java` — `Student.java` and `MainApp.java` are completely unaffected. The encapsulation boundary ensures zero unintended side effects.

### 6. 📈 Scalability
The `academics` package can grow to include `Teacher`, `Course`, `Attendance`, and `ExamResult` classes — all organised in one place. `MainApp` remains a thin orchestration layer, and the package can be exported as a `.jar` library for reuse across projects.

---

## 🔐 Encapsulation Deep Dive

```
Outside World          Student Object
      │                 ┌─────────────────────┐
      │  getMarks() ──► │  private int marks   │
      │  getName()  ──► │  private String name │
      │                 │  private int rollNo  │
      │  ✗ s1.marks=99  └─────────────────────┘
      │  (not allowed — field is private)
```

Direct field access (`s1.marks = 99`) is **blocked at compile time** — the only way to read data is through the defined getter API.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Compilation order | Package classes must be compiled **before** `MainApp` — dependency order matters |
| No setter methods | `Student` has only getters — marks cannot be updated after construction (immutable design) |
| No input validation | Constructor accepts any `int` for marks — negative or >100 values are not rejected |
| `GradeCalculator` is stateless | It holds no fields — could be a `static` utility method or a `final` singleton |
| Package directory | `academics/` folder **must** exist at the project root — package name must match folder name exactly |

---

## 🛠️ Possible Enhancements

- Add **setter methods** with validation (e.g., `setMarks()` checks 0–100 range)
- Make `GradeCalculator.calculateGrade()` a **static method** — no object instantiation needed
- Add more classes to `academics` package: `Teacher`, `Course`, `Attendance`
- Use a **constructor overload** in `Student` for partial initialisation (name + roll only)
- Replace hardcoded student with **Scanner input** for dynamic object creation
- Package and export `academics` as a reusable `.jar` library

---

## 📄 License
Free to use for educational purposes.
