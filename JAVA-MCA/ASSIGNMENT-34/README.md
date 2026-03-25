# StatisticsWithWrappers

A Java console application that collects a list of integers from the user and computes basic descriptive statistics — minimum, maximum, and average.

## What It Does

| Output | Description |
|---|---|
| Minimum | Smallest value in the input list |
| Maximum | Largest value in the input list |
| Average | Arithmetic mean as a `double` |

## How to Run

```bash
javac StatisticsWithWrappers.java
java StatisticsWithWrappers
```

## Sample Output

```
Enter how many numbers you want to input: 4
Enter number 1: 10
Enter number 2: 45
Enter number 3: 7
Enter number 4: 30

--- Statistical Results ---
Minimum value: 7
Maximum value: 45
Average value: 23.0
```

## Software Engineering Implications

**1. Autoboxing in Collections**
`numbers.add(value)` silently converts `int → Integer`. While syntactically clean, each autobox allocates a new object on the heap. In tight loops with millions of values, this creates measurable GC pressure — libraries like Eclipse Collections or primitive arrays (`int[]`) are preferred in performance-sensitive contexts.

**2. Unboxing in Comparisons**
`if (num < min)` triggers unboxing (`Integer → int`) at runtime. If `min` were ever `null`, this would throw a `NullPointerException` — a subtle but dangerous edge case in real applications where data may come from databases or APIs.

**3. Integer Caching Trap**
`Integer min = numbers.get(0)` stores a reference. Comparisons using `==` instead of `<` / `>` would compare references, not values — correct only for integers in the −128 to 127 cached range. The code correctly avoids this by using arithmetic operators.

**4. Average Precision**
`(double) sum / numbers.size()` correctly casts before division to avoid integer truncation. Forgetting the cast is a classic off-by-one-type bug (e.g., `7/2` returns `3` not `3.5`).

**5. Empty Input Risk**
`numbers.get(0)` on an empty list throws `IndexOutOfBoundsException` if the user enters `0`. Production code must guard against zero or negative input for `n`.

**6. No Input Validation**
Non-integer input causes `InputMismatchException`. Robust applications wrap `scanner.nextInt()` in a try-catch or use `scanner.nextLine()` with manual parsing.

**7. Scalability**
For very large datasets, loading all values into memory is unnecessary for min/max/average — these can be computed in a single pass with O(1) space using running variables, without storing the full list.

## Requirements

- Java 8+
- No external dependencies
