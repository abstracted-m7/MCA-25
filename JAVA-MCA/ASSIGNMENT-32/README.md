# Date & Time Manager — Java `java.time` API Demo

A console-based Java program that demonstrates the modern **`java.time` API** (introduced in Java 8) — displaying the current date and time, identifying the day of the week, and calculating the number of days between two user-entered dates.

---

## 📁 Files

| File | Description |
|------|-------------|
| `DateTimeManager.java` | Single source file with all date-time operations and entry point |

---

## 🚀 How to Run

```bash
javac DateTimeManager.java
java DateTimeManager
```

**Sample Interaction:**
```
Current Date and Time: 25-03-2026 14:35:22
Today is: WEDNESDAY
Enter first date (yyyy-mm-dd): 2026-01-01
Enter second date (yyyy-mm-dd): 2026-03-25
Number of days between the two dates: 83
```

---

## 🧠 Concepts Demonstrated

- **`LocalDateTime`** — immutable date-time object with no timezone, representing current system time
- **`LocalDate`** — date-only object (no time component) used for day difference calculations
- **`DateTimeFormatter`** — formats date-time into a human-readable pattern (`dd-MM-yyyy HH:mm:ss`)
- **`DayOfWeek`** — enum extracted from `LocalDateTime` to identify the current weekday
- **`ChronoUnit.DAYS.between()`** — calculates signed difference in days between two dates
- **`Math.abs()`** — ensures the result is always positive regardless of date input order
- **`LocalDate.parse()`** — converts ISO-format string (`yyyy-MM-dd`) directly to a `LocalDate` object

---

## 📦 Classes & Methods Used

| Class / Enum | Method / Field | Purpose |
|-------------|----------------|---------|
| `LocalDateTime` | `now()` | Gets current date and time |
| `LocalDateTime` | `format(formatter)` | Formats using a pattern |
| `LocalDateTime` | `getDayOfWeek()` | Returns `DayOfWeek` enum |
| `DateTimeFormatter` | `ofPattern(String)` | Defines custom display format |
| `DayOfWeek` | — | Enum: `MONDAY` to `SUNDAY` |
| `LocalDate` | `parse(String)` | Parses ISO date string to `LocalDate` |
| `ChronoUnit` | `DAYS.between(d1, d2)` | Returns signed day count between dates |
| `Math` | `abs(long)` | Converts signed result to absolute value |

---

## 🗓️ Format Patterns Explained

```
DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

dd    →  Day of month (01–31)
MM    →  Month (01–12)
yyyy  →  4-digit year
HH    →  Hour in 24-hour format (00–23)
mm    →  Minutes (00–59)
ss    →  Seconds (00–59)
```

**Input date format:** `yyyy-MM-dd` (ISO 8601 — the default for `LocalDate.parse()`)

---

## 🔄 Program Flow

```
main()
  │
  ├── LocalDateTime.now()              →  Capture current moment
  ├── formatter.format()               →  Display as "dd-MM-yyyy HH:mm:ss"
  ├── getDayOfWeek()                   →  Print day name (e.g., WEDNESDAY)
  │
  ├── scanner.nextLine() × 2           →  Read two date strings from user
  ├── LocalDate.parse()                →  Convert strings to LocalDate objects
  ├── ChronoUnit.DAYS.between(d1, d2)  →  Calculate signed day difference
  └── Math.abs()                       →  Ensure positive result → display
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Three distinct date-time features (current time display, weekday identification, date difference) are each mapped to a specific API class — requirements remain modular and independently verifiable.

### 2. 🏗️ Design
Using `java.time` (JSR-310) over the legacy `java.util.Date` and `Calendar` is a deliberate architectural choice — the modern API is **immutable, thread-safe, and expressive**, making it the industry standard for all new Java applications.

### 3. 💻 Development
`ChronoUnit.DAYS.between()` returns a **signed value** (negative if date1 is after date2) — wrapping it in `Math.abs()` is a robust development decision that handles both input orderings without requiring the user to enter dates in a specific sequence.

### 4. 🧪 Testing
All three operations are independently testable — current time display can be mocked with `LocalDateTime.of(2026, 3, 25, 10, 0, 0)`, and `DAYS.between()` can be verified with known date pairs without any user input.

### 5. 🔧 Maintenance
Adding new operations (e.g., adding days to a date, finding age from birthdate) uses the same immutable `LocalDate`/`LocalDateTime` API — no structural changes required to existing code.

### 6. 📈 Scalability
The `java.time` API is the backbone of scheduling systems, booking platforms, and payroll engines. The same classes used here (`LocalDate`, `ChronoUnit`) scale directly to enterprise applications with timezone support via `ZonedDateTime`.

---

## 🕰️ Legacy vs Modern Date API

| Feature | `java.util.Date` (Legacy) | `java.time` (Modern — Java 8+) |
|---------|--------------------------|-------------------------------|
| Mutability | Mutable (unsafe) | Immutable (thread-safe) |
| Clarity | Confusing month indexing (0–11) | Intuitive (1–12) |
| Formatting | `SimpleDateFormat` (not thread-safe) | `DateTimeFormatter` (thread-safe) |
| Day difference | Manual calculation required | `ChronoUnit.DAYS.between()` |
| Recommended | ✗ Avoid in new code | ✓ Use always |

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Input format strict | `LocalDate.parse()` expects exactly `yyyy-MM-dd` — any other format throws `DateTimeParseException` |
| No try-catch | Invalid date strings (e.g., `"25-03-2026"`) will crash the program — no exception handling present |
| No timezone | `LocalDateTime.now()` uses system default timezone — not suitable for multi-timezone applications |
| Signed difference | `ChronoUnit.DAYS.between()` is negative if date1 > date2 — `Math.abs()` handles this correctly |
| `LocalDate` vs `LocalDateTime` | Date difference uses `LocalDate` (date only) — time component is irrelevant for day counting |

---

## 🛠️ Possible Enhancements

- Add `try-catch` for `DateTimeParseException` to handle invalid date input gracefully
- Support **multiple input formats** using `DateTimeFormatterBuilder` with optional patterns
- Add **timezone support** using `ZonedDateTime` for global applications
- Calculate **age from birthdate** using `Period.between(birthDate, today)`
- Add **date arithmetic** — display the date 30/60/90 days from today using `date.plusDays(n)`
- Build a **full calendar view** showing all days in a given month using a loop over `LocalDate`

---

## 📄 License
Free to use for educational purposes.
