# Text Analyzer — Java String Statistical Analysis Demo

A console-based Java program that performs **statistical analysis** on a user-entered block of text — counting total words, vowels, digits, and special characters. Demonstrates **string traversal, Character API, regex-based splitting, instance methods, and object creation**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `TextAnalyzer.java` | Single source file with analysis logic and entry point |

---

## 🚀 How to Run

```bash
javac TextAnalyzer.java
java TextAnalyzer
```

**Sample Interaction:**
```
Enter the Text for analyze: Hello World! Java 101 is great.

===Statistical Results===
Total Words        : 6
Total Vowels       : 9
Total Digits       : 3
Total Special Chars: 2
```

---

## 🧠 Concepts Demonstrated

- **String Traversal** — `charAt(i)` loops through every character in the input
- **Character API** — `isDigit()`, `isLetter()`, `isWhitespace()`, `toLowerCase()` classify characters
- **Regex Split** — `text.trim().split("\\s+")` splits on one or more whitespace characters to count words accurately
- **Instance Method** — `analyzerText()` is a non-static method called on a `TextAnalyzer` object
- **Object Creation** — `new TextAnalyzer()` demonstrates class instantiation in `main()`
- **`trim()`** — removes leading/trailing whitespace before splitting to avoid phantom empty words

---

## 📊 What Gets Counted

| Category | Detection Logic | Example Characters |
|----------|-----------------|--------------------|
| Words | `split("\\s+")` on trimmed text | `Hello`, `World`, `Java` |
| Vowels | `toLowerCase` match against `a e i o u` | `e`, `o`, `i`, `a` |
| Digits | `Character.isDigit()` | `1`, `0`, `9` |
| Special Chars | Not a letter, digit, or whitespace | `!`, `.`, `@`, `#` |

> **Note:** Consonants are not counted separately — they fall through all conditions silently.

---

## 🔄 Program Flow

```
main()
  │
  ├── sc.nextLine()          →  Reads full line including spaces
  ├── new TextAnalyzer()     →  Creates instance of the class
  │
  └── analyzerText(text)
        ├── trim().split("\\s+")   →  Word count via regex
        ├── charAt(i) loop         →  Character-by-character traversal
        │     ├── toLowerCase      →  Case-insensitive vowel check
        │     ├── isDigit()        →  Digit detection
        │     └── not letter/space →  Special character catch
        └── println output         →  Statistical results display
```

---

## 🔍 Character Classification Logic

```
For each character ch:
│
├── toLowerCase(ch) ∈ {a,e,i,o,u}?  →  vowelCount++
├── isDigit(ch)?                     →  digitCount++
├── !isLetter(ch) && !isWhitespace() →  specialCharCount++
└── isLetter but not a vowel         →  (consonant — not counted)
```

The conditions use `else if` — each character is classified into **exactly one** category.

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Four distinct metrics (words, vowels, digits, special chars) are captured as separate counters — each requirement maps to a single trackable variable, making completeness easy to verify.

### 2. 🏗️ Design
Separating `analyzerText()` as an **instance method** (rather than static) demonstrates object-oriented design — the `TextAnalyzer` class can be instantiated, extended, or injected as a dependency in larger systems.

### 3. 💻 Development
`text.trim().split("\\s+")` is a robust word-splitting strategy — it correctly handles **multiple consecutive spaces, tabs, and newlines** that `split(" ")` would fail on, improving reliability with real user input.

### 4. 🧪 Testing
`analyzerText()` accepts a plain `String` — it can be tested with any hard-coded input without user interaction. Each counter can be verified independently with targeted test strings (e.g., all vowels, all digits).

### 5. 🔧 Maintenance
Adding a new metric (e.g., consonant count, sentence count) requires only a new counter variable and an additional condition in the loop — **no changes** to existing conditions or output structure needed.

### 6. 📈 Scalability
The method can be extended to process **file input** (`BufferedReader`), analyze **multiple paragraphs**, or feed results into a reporting system — the core traversal logic remains unchanged.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| `trim()` before split | Prevents a leading/trailing space from creating a phantom empty word in the count |
| `\\s+` regex | Handles tabs and multiple spaces — plain `" "` split would miscount |
| Vowel check | Uses `toLowerCase()` first — correctly counts both `'A'` and `'a'` as vowels |
| `else if` chain | Ensures each character is counted in **at most one** category — no double-counting |
| Empty input | If user presses Enter on an empty string, `trim().split("\\s+")` returns array of length 1 with `""` — word count will show 1 incorrectly |
| Typo in code | `spacialCharCount` is a typo for `specialCharCount` — fix for production readability |

---

## 🛠️ Possible Enhancements

- Add **consonant count** as a fifth metric
- Count **sentences** using `split("[.!?]")` regex
- Add **empty input guard** — check `text.trim().isEmpty()` before processing
- Display a **character frequency map** using `HashMap<Character, Integer>`
- Process text from a **file** using `BufferedReader` instead of `Scanner`
- Add a **percentage breakdown** (e.g., vowels as % of total letters)

---

## 📄 License
Free to use for educational purposes.
