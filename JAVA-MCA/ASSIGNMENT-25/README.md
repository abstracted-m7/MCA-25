# Student Performance Analyzer — Java 2D Array Demo

A console-based Java program that accepts marks for multiple students across multiple subjects, then generates a structured **performance report** — covering subject-wise averages, highest scorer, and pass/fail counts. Demonstrates **2D arrays, modular design, wrapper classes, and formatted output**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `StudentPerformanceAnalyzer.java` | Single source file with full input, processing, and analysis logic |

---

## 🚀 How to Run

```bash
javac StudentPerformanceAnalyzer.java
java StudentPerformanceAnalyzer
```

**Sample Interaction:**
```
Student's pass marks is 40.

Enter number of students: 3
Enter number of subjects: 2
Entering marks for Student 1:
  Subject 1: 78
  Subject 2: 85
Entering marks for Student 2:
  Subject 1: 35
  Subject 2: 60
Entering marks for Student 3:
  Subject 1: 90
  Subject 2: 92

--- Performance Report ---
Average for Subject 1: 67.67
Average for Subject 2: 79.00
--------------------------
Highest Scorer: Student 3 with 182.0 marks.
Students Passed (All subjects): 2
Students Failed (One or more subjects): 1
```

---

## 🧠 Concepts Demonstrated

- **2D Arrays** — `double[numStudents][numSubjects]` stores all marks in a matrix structure
- **Modular Methods** — `inputMarks()` and `displayAnalysis()` separate concerns cleanly
- **Wrapper Class** — `Double sum` (instead of `double`) demonstrates autoboxing/unboxing
- **`printf` Formatting** — `%.2f` produces clean 2-decimal output
- **Static Constant** — `PASSING_MARK = 40` defined once and reused throughout
- **Nested Loops** — column-wise and row-wise traversal for averages and totals

---

## 🗂️ Program Flow

```
main()
  │
  ├── inputMarks()       →  Fills 2D array row by row (student × subject)
  │
  └── displayAnalysis()
        ├── Subject-wise averages  (column traversal)
        ├── Highest scorer         (row total comparison)
        └── Pass / Fail count      (check all subjects ≥ 40)
```

---

## 📊 2D Array Layout

```
              Subject 1   Subject 2   Subject 3
Student 1  [   marks[0][0]  marks[0][1]  marks[0][2] ]
Student 2  [   marks[1][0]  marks[1][1]  marks[1][2] ]
Student 3  [   marks[2][0]  marks[2][1]  marks[2][2] ]
```

- **Row traversal** → per-student total and pass/fail status
- **Column traversal** → per-subject average across all students

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Dynamic input for any number of students and subjects — requirements are captured as **configurable parameters**, not hardcoded values, making the program flexible from day one.

### 2. 🏗️ Design
The program follows **Separation of Concerns** — input logic (`inputMarks`) and analysis logic (`displayAnalysis`) are cleanly isolated into dedicated static methods, reflecting modular system design.

### 3. 💻 Development
Using a **constant** (`PASSING_MARK = 40`) instead of a magic number ensures the threshold is defined in one place — a standard best practice that prevents inconsistency across large codebases.

### 4. 🧪 Testing
Modular methods can be **unit tested independently** — `inputMarks()` can be tested with mock data, and `displayAnalysis()` can be validated against known inputs without user interaction.

### 5. 🔧 Maintenance
Changing the passing mark, adding a new metric (e.g., median, grade), or modifying output format requires changes in **one isolated method** — zero risk of breaking unrelated logic.

### 6. 📈 Scalability
The 2D array approach scales to hundreds of students and subjects. The modular structure makes it straightforward to replace the array with a database, add file I/O, or expose results via a REST API.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Input validation | No bounds check on marks — values outside 0–100 are accepted silently |
| Tie handling | If two students share the highest total, only the first is reported as topper |
| `sc.close()` | Scanner is properly closed — good resource management practice |
| Wrapper Class usage | `Double sum` triggers autoboxing — fine for small scale, prefer `double` in performance-critical code |
| Pass criteria | A student passes only if **all** subjects meet `PASSING_MARK` — partial pass is not considered |

---

## 🛠️ Possible Enhancements

- Add input validation to restrict marks between 0 and 100
- Display a per-student grade (A/B/C/Fail) alongside the report
- Handle ties in highest scorer — report all tied students
- Export the report to a `.txt` or `.csv` file using `FileWriter`
- Replace 2D array with an `ArrayList<Student>` for dynamic, object-oriented data management
- Add subject names as string inputs instead of generic "Subject 1", "Subject 2" labels

---

## 📄 License
Free to use for educational purposes.
