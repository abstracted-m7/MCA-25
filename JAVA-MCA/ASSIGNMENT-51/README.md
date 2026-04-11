# Course Enrollment System

A console-based Java application for managing student course enrollments, featuring positional insertion, in-place updates, and iterator-based traversal.

## Overview

This system goes beyond basic add/delete by supporting insertion at a specific list index and in-place record updates — making it a closer model to real enrollment workflows where order and position can matter. It also demonstrates the `List` interface polymorphism pattern and Java's `Iterator` for safe traversal.

## Features

| Option | Operation | Description |
|---|---|---|
| 1 | Add Student | Append a new student to the end of the list |
| 2 | Add at Position | Insert a student at a specific index |
| 3 | Update Student | Modify name and course of an existing student |
| 4 | Display All | Iterate and print all enrolled students |
| 5 | Exit | Close the application |

## Sample Interaction

```
===== Course Enrollment System =====
1. Add Student
Enter ID: 301
Enter Name: Sneha Roy
Enter Course: Data Structures
Student added!

2. Add Student at Position
Enter index: 0
Enter ID: 300
Enter Name: Arjun Bose
Enter Course: Algorithms
Student added at position 0

4. Display All Students
--- Enrollment Order ---
ID: 300, Name: Arjun Bose, Course: Algorithms
ID: 301, Name: Sneha Roy, Course: Data Structures
```

## Key Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **Interface Polymorphism** | `List<Student>` reference with `ArrayList` object — swappable backend |
| **Positional Insertion** | `ArrayList.add(index, element)` shifts existing elements right |
| **In-place Update** | `students.get(index)` returns a reference; mutations reflect in the list |
| **Iterator Pattern** | `Iterator<Student>` used for explicit, safe traversal |
| **Bounds Validation** | Index checks before every positional operation |
| **Input Handling** | `sc.nextLine()` flush guards against newline-skip bugs |

## Software Engineering Implications

**Programming to an Interface** — Declaring `List<Student> students = new ArrayList<>()` is a best practice. Swapping to `LinkedList` (better for frequent mid-list insertions) requires changing only one line, with zero impact on the rest of the code.

**Positional Insertion Cost** — `ArrayList.add(index, element)` is `O(n)` due to element shifting. If frequent mid-list inserts are expected, `LinkedList` gives `O(1)` insertion (after traversal), though with higher memory overhead.

**Iterator vs. For-Each** — Using `Iterator` explicitly allows safe element removal during traversal (`it.remove()`), which a standard for-each loop does not. This is a useful pattern for future filtering or cleanup operations.

**Direct Field Mutation** — `s.name = newName` modifies the object in-place via reference. This works but bypasses encapsulation. Proper design uses setter methods (`setName()`, `setCourse()`) to allow validation or change-event hooks.

**No Persistence** — All records are lost on exit. A production enrollment system would persist to a relational database with tables for Students, Courses, and an Enrollments join table.

**Duplicate ID Risk** — No check prevents two students sharing the same ID. A `Set<Integer>` of used IDs or a `Map<Integer, Student>` would enforce uniqueness efficiently.

## Suggested Upgrades

- Replace `ArrayList` with `LinkedList` if positional inserts are the dominant operation
- Use `HashMap<Integer, Student>` for O(1) lookup by student ID
- Add setter methods to `Student` to enforce encapsulation
- Add a remove/unenroll feature
- Persist enrollment data to a file or database

## Requirements

- Java 8 or higher
- No external dependencies

## Run

```bash
javac CourseEnrollmentSystem.java
java CourseEnrollmentSystem
```
