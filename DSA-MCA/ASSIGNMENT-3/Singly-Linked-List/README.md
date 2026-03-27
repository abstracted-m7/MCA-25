# 🔗 Singly Linked List in C

> A complete, menu-driven implementation of a **Singly Linked List** in C — covering insertion, deletion, traversal, reversal, and searching operations from scratch using dynamic memory allocation and pointer manipulation.

---

## 📁 File Structure

```
singly_linked_list.c   →  Single source file with all operations and main menu
```

---

## ⚙️ Compile & Run

```bash
gcc singly_linked_list.c -o linked_list
./linked_list
```

---

## 📋 Menu Operations

### ➕ Insertion

| Option | Operation | Description |
|--------|-----------|-------------|
| 1 | Insert at Beginning | Adds a new node before the current head |
| 2 | Insert at End | Traverses to the tail and appends a new node |
| 3 | Insert After Element | Searches for a key and inserts the new node right after it |
| 4 | Insert Before Element | Searches for a key and inserts the new node just before it |

### ➖ Deletion

| Option | Operation | Description |
|--------|-----------|-------------|
| 5 | Delete First Node | Removes and frees the head node |
| 6 | Delete Last Node | Traverses to tail and unlinks it |
| 7 | Delete Middle Node | Uses the **slow/fast pointer technique** to find and remove the midpoint |
| 8 | Delete by Value | Searches for a key and removes that specific node |

### 🖥️ Display & Traversal

| Option | Operation | Description |
|--------|-----------|-------------|
| 9  | Forward Display | Prints all nodes from head to tail |
| 10 | Reverse Display | Uses **recursion** to print nodes from tail to head |
| 11 | Physically Reverse | Reverses the actual pointer directions in-place |

### 🔍 Other

| Option | Operation | Description |
|--------|-----------|-------------|
| 12 | Search Element | Scans the list for a given value and reports if found |
| 13 | Exit | Terminates the program |

---

## 🧠 What You Learn from This Project

### Core C Skills
- **Dynamic Memory Allocation** — Using `malloc()` to create nodes at runtime and `free()` to release them, avoiding fixed-size limitations of arrays.
- **Pointers & `->` Operator** — Navigating and manipulating struct members through pointers, the foundation of all linked data structures.
- **Self-Referential Structs** — Defining a `node` struct that holds a pointer to another `node`, enabling the chain-like structure.

### Data Structure Concepts
- **Linked List Traversal** — Iterating with a `temp` pointer to reach any position without random access.
- **Two-Pointer (Slow/Fast) Technique** — Used in `delete_middle()` — moving one pointer one step and another two steps simultaneously to find the midpoint in a single pass. This same pattern appears in real interview problems.
- **In-Place List Reversal** — Reversing pointer directions using only `prev`, `curr`, and `next` pointers — no extra memory needed.
- **Recursion on Linked Lists** — The `reverse_display()` function demonstrates how the call stack itself acts as temporary storage to reverse traversal order.

### Software Engineering Habits
- **Edge Case Handling** — Checking for empty lists, single-node lists, head deletions, and element-not-found scenarios before performing operations.
- **Modular Function Design** — Each operation is its own function, making the code readable, testable, and maintainable.
- **Menu-Driven Program Architecture** — Using `while(1)` with `switch-case` to build interactive, loop-based CLI applications.

---

## 🌍 Real-World Applications of Linked Lists

Linked lists are not just academic — they power real systems you use every day:

| Domain | How Linked Lists Are Used |
|--------|--------------------------|
| **Operating Systems** | The OS maintains a linked list of ready processes in the CPU scheduler. Each process node points to the next one to run. |
| **Web Browsers** | Your browser's Back/Forward history is a linked list of visited pages — navigating back just follows the previous pointer. |
| **Music / Video Players** | Playlists use linked lists so you can add, remove, or reorder songs without shifting everything like in an array. |
| **Text Editors** | Undo/Redo stacks are implemented as linked chains of editor states — each action is a node you can step back through. |
| **Memory Allocators** | The heap's free-block list is a classic linked list. When you call `malloc()`, the allocator walks this list to find a suitable chunk. |
| **File Systems** | FAT (File Allocation Table) uses a linked structure to track which disk sectors belong to each file in sequence. |
| **Blockchain** | Every block stores a hash of the previous block — conceptually a linked list where each node points backward in time. |
| **Social Media Feeds** | Dynamically loaded posts are prepended to the top of a linked structure so infinite scrolling works without shifting data. |
| **Compilers** | Symbol tables in compilers often use linked lists inside hash buckets (chaining) to resolve variable names. |
| **Graph Adjacency Lists** | Each vertex in a graph stores its neighbors as a linked list — the standard representation for sparse graphs. |

---

## 🐛 Known Bugs to Fix (Learning Opportunities)

These are intentional observations — debugging them deepens your understanding:

| Location | Bug | Fix |
|----------|-----|-----|
| `delete_value()` | `free(temp)` is called before printing `temp->data` — accessing freed memory is **undefined behavior** | Print `temp->data` *before* calling `free(temp)` |
| `delete_middle()` | `prev` is uninitialized when the list has only one node — causes a crash | Add a check: if `head->next == NULL`, handle the single-node case separately |
| `searching()` | `flag` is declared but not initialized before the loop — garbage value if loop somehow skips | Initialize with `int key, flag = 0;` |
| `insert_before()` | The prompt says `"Insert after which element:"` but the function inserts *before* — misleading UX | Change the prompt to `"Insert before which element:"` |
| `reverse_display()` | Does not print `NULL` at the end like `display()` does — inconsistent output format | Add `printf("NULL\n")` after calling `reverse_display(head)` in `main()` |

---

## 💡 Sample Output

```
====================================================
        Singly Linked List Menu
----------------------------------------------------
    1. Insert at the beginning.
    2. Insert at the end.
    ...
====================================================

Enter choice: 2
Enter the value: 10
The 10 inserted successfully.

Enter choice: 2
Enter the value: 20
The 20 inserted successfully.

Enter choice: 9
The data in linked list: 10->20->NULL

Enter choice: 11
List reversed physically.

Enter choice: 9
The data in linked list: 20->10->NULL
```

---

## 📌 Time Complexity Reference

| Operation | Time Complexity |
|-----------|----------------|
| Insert at beginning | O(1) |
| Insert at end | O(n) |
| Insert after/before element | O(n) |
| Delete first node | O(1) |
| Delete last node | O(n) |
| Delete middle node | O(n) |
| Delete by value | O(n) |
| Search | O(n) |
| Reverse (in-place) | O(n) |
| Reverse display (recursive) | O(n) |

---

## 👨‍💻 Author Notes

Built as a hands-on learning project to master dynamic data structures and pointer-based programming in C. Every operation here mirrors concepts used in technical interviews and systems-level software development.

> *"If you truly understand pointers and linked lists, you understand how memory works — and that is the foundation of all systems programming."*
