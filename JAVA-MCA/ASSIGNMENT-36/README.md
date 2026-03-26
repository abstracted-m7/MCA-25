# University Resource Manager — Java Multi-Package OOP System

A fully modular, menu-driven Java console application for managing **students and courses** in a university environment. Built across four packages — `model`, `service`, `util`, and `app` — it demonstrates **interface-based service design, encapsulation, ArrayList collections, case-insensitive search, and real-world Java package architecture**.

---

## 📁 Project Structure

```
UniversityResourceManager/
│
└── university/
      ├── model/
      │     ├── Student.java          ← Student entity (id, name, department)
      │     └── Course.java           ← Course entity (courseId, courseName)
      │
      ├── service/
      │     ├── UniversityService.java      ← Interface declaring all operations
      │     └── UniversityServiceImpl.java  ← Concrete implementation using ArrayList
      │
      ├── util/
      │     └── SearchUtil.java        ← Reusable case-insensitive string matcher
      │
      └── app/
            └── UniversityManager.java ← Entry point with menu-driven loop
```

---

## 🚀 How to Compile & Run

```bash
# Step 1 — Navigate to project root
cd UniversityResourceManager

# Step 2 — Compile all packages (order: model → service → util → app)
javac university/model/*.java
javac university/service/*.java
javac university/util/*.java
javac university/app/UniversityManager.java

# Step 3 — Run from project root
java university.app.UniversityManager
```

**Sample Interaction:**
```
====================================
University Resource Manager
1. Add Student
2. Add Course
3. View Students
4. View Courses
5. Search Student
6. Search Course
7. Exit
====================================

Enter choice: 1
Enter ID: 101
Enter Name: Manish
Enter Department: BCA
Student Added Successfully

Enter choice: 5
Enter student name to search: man
ID: 101
 Name: Manish
 Department: BCA
```

---

## 🧠 Concepts Demonstrated

- **Multi-Package Architecture** — 4 packages with distinct, non-overlapping responsibilities
- **Interface-Based Service Design** — `UniversityService` defines the contract; `UniversityServiceImpl` fulfils it
- **Encapsulation** — all fields in `Student` and `Course` are `private`; accessed via `public` getters
- **ArrayList (Collections)** — dynamic storage for students and courses with no fixed size
- **Case-Insensitive Search** — `toLowerCase().contains()` enables partial name matching
- **Utility Class** — `SearchUtil.match()` is a `static` reusable helper method
- **Infinite Menu Loop** — `while(true)` with `System.exit(0)` on choice 7 for clean termination
- **Interface Reference** — `UniversityService service = new UniversityServiceImpl()` uses interface type

---

## 🏗️ Architecture Overview

```
«interface»
UniversityService
+ addStudent(Student)
+ addCourse(Course)
+ viewStudents()
+ viewCourses()
+ searchStudentByName(String)
+ searchCourseByName(String)
         │
         └── UniversityServiceImpl
               ├── ArrayList<Student> students
               └── ArrayList<Course>  courses

UniversityManager (app layer)
  └── Creates UniversityService reference
  └── Delegates all operations to service
  └── Never contains business logic itself
```

---

## 📦 Package Responsibilities

| Package | Class(es) | Role |
|---------|-----------|------|
| `university.model` | `Student`, `Course` | Data entities — encapsulated fields + display |
| `university.service` | `UniversityService`, `UniversityServiceImpl` | Business logic — CRUD + search operations |
| `university.util` | `SearchUtil` | Reusable utilities — static string matcher |
| `university.app` | `UniversityManager` | Entry point — menu loop + user input handling |

---

## 🔍 Search Logic

```java
// Case-insensitive partial match
source.toLowerCase().contains(target.toLowerCase())
```

- Searching `"man"` matches `"Manish"`, `"Manohar"`, `"Raman"`
- Searching `"java"` matches `"Java Programming"`, `"Advanced Java"`
- The `SearchUtil.match()` utility centralises this logic for reuse across both student and course search

---

## ⏱️ Time Complexity

| Operation | Complexity | Reason |
|-----------|-----------|--------|
| Add Student / Course | O(1) | `ArrayList.add()` is amortised constant |
| View Students / Courses | O(n) | Linear traversal of the list |
| Search by Name | O(n × m) | n = records, m = average name length for `contains()` |

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Six functional requirements (add, view, search for each entity) are cleanly mapped to six interface methods — every requirement is directly traceable to a method signature in `UniversityService`.

### 2. 🏗️ Design
The **3-layer architecture** (app → service → model) mirrors enterprise MVC/Service-layer patterns. `UniversityManager` never touches `ArrayList` directly — all data access is abstracted through the service interface.

### 3. 💻 Development
`SearchUtil.match()` as a `static` utility follows the **DRY principle** — the same case-insensitive logic is written once and can be reused by any future class without duplication.

### 4. 🧪 Testing
`UniversityServiceImpl` can be **unit tested in isolation** — test methods can call `addStudent()` and `searchStudentByName()` programmatically without a menu or scanner. The interface allows **mock implementations** to be injected for testing.

### 5. 🔧 Maintenance
Adding a new entity (e.g., `Teacher`, `Classroom`) requires only: a new `model` class, new methods in the interface, and implementation in `UniversityServiceImpl` — `UniversityManager` and `SearchUtil` remain **completely unchanged**.

### 6. 📈 Scalability
The interface-service architecture makes the storage layer swappable — replacing `ArrayList` with a **JDBC database** or **file I/O** requires only changes inside `UniversityServiceImpl`, with zero impact on the app or model layers.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| No input validation | Duplicate IDs are accepted silently — no uniqueness check on student/course ID |
| In-memory only | All data is lost when the program exits — no persistence layer |
| `SearchUtil` unused | `SearchUtil.match()` is defined but not called in `UniversityServiceImpl` — the same logic is re-implemented inline instead |
| Empty list display | `viewStudents()` / `viewCourses()` print nothing if lists are empty — no "No records found" message |
| `sc.nextLine()` after `nextInt()` | Used correctly throughout to consume leftover newline characters |
| Default case missing | `switch` in `UniversityManager` has no `default` — unrecognised menu choices are silently ignored |

---

## 🛠️ Possible Enhancements

- **Use `SearchUtil.match()`** inside `UniversityServiceImpl` to eliminate duplicated search logic
- Add **duplicate ID validation** before inserting a student or course
- Add a `"No records found"` message when search or view returns empty results
- Introduce **Student-Course Enrollment** — link students to courses with a `Map<Integer, List<Course>>`
- Add **file persistence** using `FileWriter`/`BufferedReader` to save and restore records between sessions
- Replace `ArrayList` with a **HashMap** keyed by ID for O(1) lookup by ID
- Add a `default` case in the menu switch for invalid input handling
- Introduce **exception handling** (`try-catch`) around `sc.nextInt()` for non-numeric input

---

## 📄 License
Free to use for educational purposes.
