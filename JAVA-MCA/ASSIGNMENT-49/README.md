# Student Management System

A console-based Java application for managing student records using an in-memory list with full CRUD operations.

## Overview

This system provides an interactive menu-driven interface to add, delete, display, and access student records. It uses Java's `ArrayList` as a dynamic in-memory data store, making it a practical demonstration of collections, OOP modelling, and basic data management.

## Features

| Option | Operation | Description |
|---|---|---|
| 1 | Add | Insert a new student (roll no, name, marks) |
| 2 | Delete | Remove a student by roll number |
| 3 | Display All | List every student record |
| 4 | Access by Index | Fetch a specific student by list position |
| 5 | Exit | Close the application |

## Sample Interaction

```
===== Student Management System =====
1. Add Student
Enter Roll No: 101
Enter Name: Aryan Das
Enter Marks: 88.5
Student added successfully!

3. Display All Students
--- Student List ---
Roll No: 101, Name: Aryan Das, Marks: 88.5
```

## Key Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **OOP Modelling** | `Student` class encapsulates data and behavior (`display()`) |
| **Dynamic Collections** | `ArrayList<Student>` grows/shrinks at runtime |
| **CRUD Operations** | Add, Read, Delete all implemented manually |
| **Input Handling** | `Scanner` with `nextLine()` flush to avoid input-skip bugs |
| **Bounds Checking** | Index validation before `ArrayList.get()` prevents crashes |
| **Control Flow** | `do-while` loop with `switch-case` for menu navigation |

## Software Engineering Implications

**MVC Separation** — `Student` acts as the Model; the `main` method handles both View and Controller logic. In a larger system, these would be split into dedicated layers.

**Persistence Gap** — Data lives only in memory; restarting the program wipes all records. A production system would connect to a database (JDBC, Hibernate) or write to a file.

**Scalability** — Linear search (`O(n)`) for delete works for small datasets. At scale, a `HashMap<Integer, Student>` keyed by roll number gives `O(1)` lookups.

**Input Validation** — No guards against duplicate roll numbers or negative marks. A real system should enforce these constraints at the model or service layer.

**Testability** — Business logic is embedded in `main()`, making unit testing hard. Refactoring into a `StudentService` class would allow proper JUnit testing.

## Suggested Upgrades

- Replace linear search with `HashMap` for faster lookups
- Add file I/O or SQLite persistence
- Extract service and repository layers for clean architecture
- Add input validation (duplicate rolls, mark range 0–100)

## Requirements

- Java 8 or higher
- No external dependencies

## Run

```bash
javac StudentManagementSystem.java
java StudentManagementSystem
```
