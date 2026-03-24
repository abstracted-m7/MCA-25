# Password Strength Evaluator — Java String Manipulation Demo

A console-based Java program that analyzes a user-entered password and evaluates its strength using the **Character API**, scoring logic, and a `switch` statement. Demonstrates **string traversal, Character class methods, boolean flags, modular design, and conditional formatting**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `PasswordStrengthCheck.java` | Single source file with evaluation and display logic |

---

## 🚀 How to Run

```bash
javac PasswordStrengthCheck.java
java PasswordStrengthCheck
```

**Sample Interaction:**
```
--- Password Strength Evaluator ---
Enter a password to analyze: Hello@123

--- Analysis Report ---
Your Score : 4
Length: 9 (Pass)
Uppercase & Lowercase: Yes
Numeric Digits: Yes
Special Characters: Yes
-----------------------
PASSWORD STRENGTH: VERY STRONG
```

---

## 🧠 Concepts Demonstrated

- **String Traversal** — `charAt(i)` iterates character by character through the password
- **Character API** — `isUpperCase()`, `isLowerCase()`, `isDigit()`, `isWhitespace()` classify each character
- **Boolean Flags** — four `boolean` variables track which character categories are found
- **Scoring Logic** — incremental `int score` accumulates based on fulfilled criteria
- **Switch Statement** — maps score (0–4) to a human-readable strength category
- **Ternary Operator** — used inside `println` for inline conditional display
- **Modular Design** — `evaluateStrength()` and `displayResult()` separate analysis from presentation

---

## 📊 Scoring Criteria

| Criterion | Condition | Points |
|-----------|-----------|--------|
| Length | Password ≥ 8 characters | +1 |
| Case Mix | Has both uppercase AND lowercase | +1 |
| Digits | Contains at least one numeric digit | +1 |
| Special Chars | Contains non-alphanumeric, non-whitespace character | +1 |

---

## 🔐 Strength Categories

| Score | Category |
|-------|----------|
| 4 | VERY STRONG |
| 3 | STRONG |
| 2 | MODERATE |
| 0–1 | WEAK |

---

## 🔄 Program Flow

```
main()
  │
  ├── sc.nextLine()         →  Reads full password (spaces allowed)
  │
  └── evaluateStrength()
        ├── charAt() loop   →  Classifies each character via Character API
        ├── Score tallying  →  Checks 4 criteria, increments score
        │
        └── displayResult()
              ├── Per-criterion pass/fail output
              ├── switch(score) → strength label
              └── Suggestion if score < 4
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Password strength rules (length, case, digits, special characters) are clearly defined as **discrete, measurable criteria** — each maps directly to a boolean flag and a score point, keeping requirements traceable and verifiable.

### 2. 🏗️ Design
`evaluateStrength()` handles **analysis only** while `displayResult()` handles **presentation only** — a clean separation following the **Single Responsibility Principle**. Changing the UI output never touches the scoring logic.

### 3. 💻 Development
Using `sc.nextLine()` instead of `sc.next()` is a deliberate choice — it correctly handles **passwords with spaces**, a common real-world edge case that `next()` would silently truncate.

### 4. 🧪 Testing
`evaluateStrength()` is a pure logic method — it can be tested with any string input programmatically without user interaction. Each of the four boolean flags can be verified independently with targeted test strings.

### 5. 🔧 Maintenance
Adding a new rule (e.g., no repeated characters, minimum 2 digits) requires only a new flag variable, an additional `if` in the loop, and a `score++` line — **zero changes** to existing logic or display code.

### 6. 📈 Scalability
The scoring pattern scales naturally — increasing `MAX_SCORE` beyond 4, adding weighted criteria, or integrating with a registration API requires only extending the existing modular structure without restructuring.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| `nextLine()` usage | Correctly captures passwords with spaces — `next()` would stop at the first space |
| Special char detection | Defined as "not uppercase, lowercase, digit, or whitespace" — no explicit special char list |
| Score cap | Maximum score is 4 — no bonus for longer passwords or multiple special characters |
| Case sensitivity | `isUpperCase` and `isLowerCase` treat accented chars (é, ñ) per Unicode standard |
| No masking | Password is displayed as plain text in console — not suitable for production use |
| `private` access | `displayResult()` is `private` — correctly restricted as an internal helper, unlike the `public` evaluator |

---

## 🛠️ Possible Enhancements

- Add a rule penalizing **common passwords** (e.g., "password123") using a blocklist
- Introduce **minimum 2 digits** or **minimum 1 special character** as stricter sub-rules
- Use `StringBuilder` to generate a **masked version** of the password for display (`****`)
- Add a **real-time strength meter** in a GUI using `JProgressBar`
- Extend to check against a **dictionary of previously breached passwords**
- Return score and flags as a structured object (POJO) for API integration

---

## 📄 License
Free to use for educational purposes.
