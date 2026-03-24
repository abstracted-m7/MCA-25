# Matrix Toolkit — Java 2D Array Operations Demo

A console-based Java program that performs three fundamental **matrix operations** — Addition, Transpose, and Diagonal Sum — on user-defined matrices. Demonstrates **2D arrays, modular static methods, wrapper classes, dimension validation, and formatted matrix display**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `MatrixToolkit.java` | Single source file with all matrix operations and entry point |

---

## 🚀 How to Run

```bash
javac MatrixToolkit.java
java MatrixToolkit
```

**Sample Interaction:**
```
--- Matrix A ---
Enter rows and columns: 2 2
Enter elements:
1 2
3 4

--- Matrix B (for Addition) ---
Enter rows and columns: 2 2
Enter elements:
5 6
7 8

Result of Addition (A + B):
6       8
10      12

Transpose of Matrix A:
1       3
2       4

Sum of Diagonal Elements (Matrix A): 5
```

---

## 🧠 Concepts Demonstrated

- **2D Arrays** — matrices stored and processed as `int[rows][cols]`
- **Modular Static Methods** — each operation is a self-contained, reusable method
- **Wrapper Class** — `Integer` return type in `calculateDiagonalSum()` enables `null` as a meaningful signal
- **Dimension Validation** — addition checks matching dimensions; diagonal sum checks square matrix
- **Transpose Logic** — dimensions are swapped (`int[c][r]`) and indices flipped (`temp[j][i] = matrix[i][j]`)
- **Enhanced For Loop** — `displayMatrix()` uses `for (int[] row : matrix)` for clean iteration

---

## 🔧 Methods Overview

| Method | Parameters | Returns | Purpose |
|--------|-----------|---------|---------|
| `inputMatrix()` | `Scanner` | `int[][]` | Reads matrix dimensions and elements from user |
| `addMatrices()` | `int[][], int[][]` | `int[][]` or `null` | Adds two matrices; validates dimensions first |
| `transpose()` | `int[][]` | `int[][]` | Returns transposed matrix with swapped dimensions |
| `calculateDiagonalSum()` | `int[][]` | `Integer` or `null` | Sums main diagonal; returns `null` if not square |
| `displayMatrix()` | `int[][]` | `void` | Prints matrix in tabular format using `\t` |

---

## 📐 Matrix Operations Explained

### ➕ Addition
```
A = [1  2]    B = [5  6]    A+B = [6   8]
    [3  4]        [7  8]          [10  12]

Condition: A and B must have identical dimensions (rows × cols)
```

### 🔄 Transpose
```
A   = [1  2]    A' = [1  3]
      [3  4]         [2  4]

Logic: temp[j][i] = matrix[i][j]  (row becomes column)
Dimensions: m×n matrix → n×m transposed matrix
```

### 📏 Diagonal Sum
```
A = [1  2]
    [3  4]

Main diagonal: matrix[0][0] + matrix[1][1] = 1 + 4 = 5

Condition: Only valid for square matrices (rows == cols)
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
Three distinct matrix operations are cleanly identified as separate functional requirements — each maps to its own method, keeping the requirement-to-implementation traceability crystal clear.

### 2. 🏗️ Design
Follows **Single Responsibility Principle** — `inputMatrix`, `addMatrices`, `transpose`, `calculateDiagonalSum`, and `displayMatrix` each do exactly one thing. The `main()` method acts as an **orchestrator**, not a processor.

### 3. 💻 Development
Returning `null` from `Integer calculateDiagonalSum()` is a deliberate use of the **wrapper class** to signal invalid state (non-square matrix) without exceptions — a practical, lightweight error communication pattern.

### 4. 🧪 Testing
Every method accepts arrays as input and returns arrays as output — **pure functions** with no side effects (except display). This makes each method trivially testable with pre-defined test matrices, independent of user input.

### 5. 🔧 Maintenance
Adding new operations (e.g., multiplication, determinant, inverse) requires only a **new method** — `main()` calls it without touching any existing logic. Zero regression risk on existing operations.

### 6. 📈 Scalability
The modular structure is directly portable to larger systems — methods can be extracted into a `MatrixUtils` utility class, wrapped in a REST API, or integrated into a scientific computation library with no restructuring.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Dimension mismatch | `addMatrices()` returns `null` on mismatch — caller must null-check before use |
| Square matrix check | `calculateDiagonalSum()` returns `null` for non-square — handled gracefully in `main()` |
| No multiplication | Matrix multiplication is not included — would require a third matrix and `O(n³)` logic |
| Fixed input type | Only `int` is supported — `double` matrices would need a parallel set of methods |
| `a[0].length` assumption | Assumes no empty rows — could throw `ArrayIndexOutOfBoundsException` on malformed input |

---

## 🛠️ Possible Enhancements

- Add **matrix multiplication** with dimension compatibility check (`A cols == B rows`)
- Support `double[][]` matrices for decimal precision
- Add **anti-diagonal sum** (matrix[i][n-1-i]) alongside the main diagonal
- Detect and report **symmetric matrices** (`A == A'`)
- Export matrix results to a `.csv` file using `FileWriter`
- Build a menu-driven loop so users can perform multiple operations without restarting

---

## 📄 License
Free to use for educational purposes.
