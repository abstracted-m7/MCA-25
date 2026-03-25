# StudentRegistry

A Java console application for managing student records using a menu-driven interface backed by a `Vector` collection.

## What It Does

Provides a simple CRUD registry for student data with the following operations:

| Option | Operation |
|---|---|
| 1 | Add a new student (Roll No + Name) |
| 2 | Remove a student by Roll No |
| 3 | Search a student by Roll No |
| 4 | Display all registered students |
| 5 | Exit the program |

## How to Run

```bash
javac StudentRegistry.java
java StudentRegistry
```

## Project Structure

```
StudentRegistry.java
├── class Student         # Model: stores rollNo (Integer) and name (String)
└── class StudentRegistry # Entry point: menu loop + Vector operations
```

## Software Engineering Implications

**1. Use of `Vector` vs `ArrayList`**
`Vector` is thread-safe (synchronized), making it suitable for multi-threaded environments. However, in single-threaded applications `ArrayList` is preferred for better performance. This is a common design decision in real systems.

**2. Autoboxing**
`Integer roll = scanner.nextInt()` auto-boxes a primitive `int` into an `Integer` wrapper. While convenient, excessive autoboxing in performance-critical loops can introduce overhead.

**3. `.equals()` for Wrapper Comparison**
`s.rollNo.equals(removeRoll)` correctly compares `Integer` wrapper values. Using `==` would compare object references, not values — a classic Java bug source, especially for values outside the −128 to 127 cache range.

**4. ConcurrentModificationException Risk**
Removing an element from a `Vector` inside a for-each loop can throw `ConcurrentModificationException`. The code mitigates this with an immediate `break`, but production code should use `Iterator.remove()` for safety.

**5. Input Validation**
No guard exists against non-numeric input — entering letters when a number is expected causes `InputMismatchException`. Real-world applications must validate and sanitize all user input.

**6. OOP Design**
Separating `Student` as its own class with a `display()` method follows the **Single Responsibility Principle** — the model manages its own data representation, keeping the main class focused on control flow.

**7. Scalability**
Linear search (O(n)) is used for lookup and deletion. For large datasets, a `HashMap<Integer, Student>` keyed by roll number would reduce lookup to O(1).

## Requirements

- Java 8+
- No external dependencies
