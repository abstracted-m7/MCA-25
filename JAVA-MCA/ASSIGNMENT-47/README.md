# Concurrent Input Validator — Java Multi-threaded Validation Demo

A Java program running **three independent validation checks in parallel** — numeric format, string character set, and range bounds — each as its own thread. Demonstrates **single custom exception reused across different validation contexts, exception wrapping (`NumberFormatException` → `ValidationException`), regex-based validation, and parallel independent validators with no shared state**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `code.java` | Single source file with three validator thread classes, one custom exception, and `main` |

---

## 🚀 How to Run

```bash
javac code.java
java code
```

**Output (with sample inputs — order may vary):**
```
Numeric Validation Failed: Not a valid number!
String Validation Failed: String contains invalid characters!
Range Validation Failed: Number not in range (1-100)!
```

**All Pass (change inputs to `"123"`, `"Hello"`, `50`):**
```
Numeric Validation Passed: 123
String Validation Passed: Hello
Range Validation Passed: 50
```

---

## 🧠 Concepts Demonstrated

- **Parallel Independent Validators** — three threads run simultaneously with zero shared state — the fastest possible concurrent design
- **Exception Wrapping** — `NumberFormatException` (JDK built-in) is caught and re-thrown as `ValidationException` (custom) — translating low-level errors to domain-level language
- **Regex Validation** — `input.matches("[a-zA-Z]+")` checks for letters-only strings using a Java built-in pattern
- **Private `validate()` Method** — validation logic is separated from thread lifecycle (`run()`) inside each class
- **Single Exception, Three Domains** — one `ValidationException` class covers numeric, string, and range errors
- **No Shared Resource** — no `synchronized` needed — each validator works only on its own local input

---

## 🔄 Validation Rules

| Validator | Input Type | Rule | Fail Condition |
|-----------|-----------|------|---------------|
| `NumericValidator` | `String` | Must parse as `int` | Any non-numeric character |
| `StringValidator` | `String` | Only `[a-zA-Z]` letters | Digits, spaces, symbols |
| `RangeValidator` | `int` | Must be between 1–100 | `< 1` or `> 100` |

---

## 🔄 Test Cases in `main()`

| Variable | Value | Validator | Expected |
|----------|-------|-----------|----------|
| `numInput` | `"123a"` | `NumericValidator` | ❌ Fail — `'a'` prevents `parseInt()` |
| `strInput` | `"Hello123"` | `StringValidator` | ❌ Fail — digits not in `[a-zA-Z]+` |
| `rangeInput` | `150` | `RangeValidator` | ❌ Fail — exceeds 100 |

All three inputs are deliberately chosen to fail — change any to a valid value to see a pass.

---

## 🔁 Exception Wrapping Pattern

```
NumericValidator.validate()
  │
  ├── Integer.parseInt("123a")
  │     └── throws NumberFormatException (JDK internal)
  │           │
  │           └── catch (NumberFormatException e)
  │                 └── throw new ValidationException("Not a valid number!")
  │                           ↑ domain-level exception replaces technical one
  │
  └── caught in run() → "Numeric Validation Failed: Not a valid number!"
```

This is the **Exception Translation pattern** — internal technical exceptions (`NumberFormatException`) are wrapped in domain exceptions (`ValidationException`) so callers only deal with application-level language.

---

## 🧵 Thread Architecture — No Shared State

```
main()
  │
  ├── t1 (NumericValidator)   ─── validates "123a" independently
  ├── t2 (StringValidator)    ─── validates "Hello123" independently
  └── t3 (RangeValidator)     ─── validates 150 independently
         │
         No shared resource — no synchronized needed
         All three run truly parallel with zero contention
```

This is the **simplest concurrent design** — embarrassingly parallel tasks with no coordination overhead.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Three independent validation rules — numeric format, character set, range bounds — are mapped to three separate classes. Each rule is independently changeable, testable, and deployable without touching the others.

### 2. 🏗️ Design
Separating `validate()` (logic) from `run()` (thread lifecycle) inside each class follows the **Single Responsibility Principle** — `run()` handles pass/fail output, `validate()` defines the rule. Testing `validate()` directly doesn't require starting a thread.

### 3. 💻 Development
`Integer.parseInt()` throwing `NumberFormatException` is caught and re-thrown as `ValidationException` — the **Exception Translation pattern** prevents internal JDK exceptions from leaking into the domain layer.

### 4. 🧪 Testing
Each `validate()` method is `private` but its behaviour is fully exercised through `run()` — or the access can be changed to package-private for direct unit testing. All three validators are stateless and deterministic — the same input always produces the same result.

### 5. 🔧 Maintenance
Adding a new validation rule (e.g., `EmailValidator`) requires only a new `Thread` subclass — `main()` adds one new thread start, no existing class is modified.

### 6. 📈 Scalability
The no-shared-state design scales perfectly — 100 validators could run simultaneously with zero contention. In a real form validation system, this pattern maps directly to parallel field validation via `ExecutorService.invokeAll()`.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| All inputs hardcoded | `numInput`, `strInput`, `rangeInput` are fixed in `main()` — not runtime configurable |
| No result aggregation | Each thread prints independently — `main()` has no way to know if all validations passed or any failed |
| `matches()` is full-match | `"[a-zA-Z]+"` matches the **entire string** — `"He llo"` fails because `matches()` is not `find()` |
| Class named `code` | Violates Java naming convention — should be `ValidationDemo` or `InputValidator` |
| `private validate()` | Cannot be called directly in unit tests without changing access to package-private |
| No `join()` in main | `main()` exits after `start()` — validation results print asynchronously, no guaranteed completion before JVM shutdown in heavier programs |

---

## 🔄 This Program vs Previous Threading Programs

| Feature | Synchronized Programs (Banking, Inventory...) | **Validation System** |
|---------|----------------------------------------------|----------------------|
| Shared state | Yes — shared resource object | **No** — each thread is independent |
| `synchronized` needed | Yes | **No** |
| Thread interaction | Threads contend for locks | **Threads never interact** |
| Output order | Depends on lock acquisition | Depends on thread scheduling |
| Concurrency benefit | Safety (no corruption) | **Speed** (parallel validation) |

This is the only program in the series where concurrency is used purely for **performance** (parallel validation) rather than for **safety** (protecting shared state).

---

## 🛠️ Possible Enhancements

- Use `Future<Boolean>` with `ExecutorService` to collect validation results and check if all passed
- Add `setName()` calls: `t1.setName("NumericValidator")` for more descriptive output
- Accept inputs from command-line args (`args[0]`, `args[1]`, `args[2]`) instead of hardcoding
- Add an `EmailValidator` thread using regex `^[\\w.]+@[\\w]+\\.[a-z]{2,}$`
- Chain validators — only run `RangeValidator` if `NumericValidator` passes using `join()` and a result flag
- Introduce a `ValidationResult` object returned via `Callable<ValidationResult>` and `Future` for structured result collection

---

## 📄 License
Free to use for educational purposes.
