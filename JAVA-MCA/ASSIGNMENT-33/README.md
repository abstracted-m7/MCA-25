# DataConversionUtility

A simple Java console application demonstrating core **type conversion** and **data parsing** techniques.

## What It Does

Accepts user input and performs the following conversions:

| Conversion | Method Used |
|---|---|
| String → Integer | `Integer.parseInt()` |
| Integer → Double / Float | `Double.valueOf()`, `Float.valueOf()` |
| Integer → String | `Integer.toString()` |
| Double → String | `Double.toString()` |
| String → Double | `Double.parseDouble()` |
| Wrapper → Primitive | Boxing / Unboxing |

## How to Run

```bash
javac DataConversionUtility.java
java DataConversionUtility
```

## Software Engineering Implications

**1. Type Safety**
Java is strongly typed — conversions must be explicit. This prevents silent data corruption that can occur in loosely typed languages.

**2. Input Validation Risk**
`Integer.parseInt()` and `Double.parseDouble()` throw `NumberFormatException` on invalid input. Production code must wrap these in try-catch blocks.

**3. Precision Loss**
Converting `double → float` can silently lose precision. This matters in financial, scientific, or safety-critical systems.

**4. Boxing & Unboxing Overhead**
`Integer.valueOf()` uses a cache (−128 to 127). Repeated boxing/unboxing in loops has a performance cost — relevant in high-throughput systems.

**5. Maintainability**
Centralizing conversions in a utility class (as done here) is a good pattern — it keeps conversion logic reusable and easy to test.

## Requirements

- Java 8+
- No external dependencies
