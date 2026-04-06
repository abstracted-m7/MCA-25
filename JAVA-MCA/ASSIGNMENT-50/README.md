# Employee Directory

A console-based Java application for managing employee records using a thread-safe `Vector`, with menu-driven CRUD operations.

## Overview

This system allows users to add, remove, and display employee records interactively. The key distinction from a typical list-based system is the use of `Vector<Employee>` — a synchronized collection — making it safe for concurrent access in multi-threaded environments.

## Features

| Option | Operation | Description |
|---|---|---|
| 1 | Add | Insert a new employee (ID, name, salary) |
| 2 | Remove | Delete an employee by ID |
| 3 | Display All | List all employee records |
| 4 | Exit | Close the application |

## Sample Interaction

```
===== Employee Directory =====
1. Add Employee
Enter ID: 201
Enter Name: Riya Sharma
Enter Salary: 75000
Employee added successfully!

3. Display All Employees
--- Employee List ---
ID: 201, Name: Riya Sharma, Salary: 75000.0
```

## Key Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **OOP Modelling** | `Employee` encapsulates fields and `display()` behavior |
| **Thread-Safe Collection** | `Vector` provides built-in synchronization unlike `ArrayList` |
| **CRUD Operations** | Add, Read, Delete implemented with linear traversal |
| **Input Handling** | `sc.nextLine()` flush prevents newline-skip bugs |
| **Bounds-Safe Removal** | Index-based removal inside a validated loop |
| **Control Flow** | `do-while` + `switch-case` drives the menu loop |

## Software Engineering Implications

**Why `Vector` over `ArrayList`?** — `Vector` synchronizes every method call, making it safe when multiple threads access the list simultaneously. However, this comes with a performance overhead due to locking, even in single-threaded contexts like this one.

**Concurrency Consideration** — In a real multi-threaded HR system (e.g., multiple admins editing records), `Vector` offers basic safety. For finer control, `Collections.synchronizedList()` or `CopyOnWriteArrayList` are preferred modern alternatives.

**Persistence Gap** — Records exist only for the session. A production directory would persist data to a relational database (MySQL, PostgreSQL) or an LDAP/AD directory service.

**Search Efficiency** — Employee lookup is `O(n)` linear scan. Replacing `Vector` with a `Hashtable<Integer, Employee>` or `ConcurrentHashMap` would give `O(1)` lookups while preserving thread safety.

**MVC Violation** — All logic lives in `main()`. Extracting an `EmployeeService` and `EmployeeRepository` class would separate concerns and enable unit testing.

**No Duplicate ID Guard** — Two employees can share the same ID. Production systems enforce uniqueness at the database level (primary key) or in the service layer before insertion.

## Suggested Upgrades

- Use `ConcurrentHashMap<Integer, Employee>` for O(1) thread-safe lookups
- Add a search/filter by name or salary range
- Persist data to a file or database between sessions
- Validate salary (must be > 0) and enforce unique IDs
- Refactor into `Employee`, `EmployeeService`, and `EmployeeDirectory` (UI) layers

## Requirements

- Java 8 or higher
- No external dependencies

## Run

```bash
javac EmployeeDirectory.java
java EmployeeDirectory
```
