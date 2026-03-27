# 🔗 Doubly Linked List in C

> A complete, menu-driven implementation of a **Doubly Linked List** in C — where every node holds pointers to both its previous and next neighbor, enabling bidirectional traversal, cleaner deletions, and more powerful list manipulation.

---

## 📁 File Structure

```
doubly_linked_list.c   →  Single source file with all operations and main menu
```

---

## ⚙️ Compile & Run

```bash
gcc doubly_linked_list.c -o doubly_linked_list
./doubly_linked_list
```

---

## 🧱 Node Structure

Unlike a singly linked list where each node only knows its successor, a doubly linked list node carries **three fields**:

```c
typedef struct node {
    int data;         // The value stored
    struct node* prev; // Pointer to the previous node
    struct node* next; // Pointer to the next node
} node;
```

This extra `prev` pointer is what makes the list bidirectional — and is the key concept you learn by studying this implementation.

---

## 📋 Menu Operations

### ➕ Insertion

| Option | Operation | Description |
|--------|-----------|-------------|
| 1 | Insert at Beginning | Creates a new head; updates old head's `prev` to point back |
| 2 | Insert at End | Traverses to tail and appends; sets `prev` of new node to old tail |

### ➖ Deletion

| Option | Operation | Description |
|--------|-----------|-------------|
| 3 | Delete First Node | Moves head forward; sets new head's `prev` to `NULL` |
| 4 | Delete Last Node | Uses `temp->prev->next = NULL` directly — no need for a `prev` tracker variable |

### 🖥️ Display & Search

| Option | Operation | Description |
|--------|-----------|-------------|
| 5 | Display All Nodes | Prints the list in `A <-> B <-> C <-> NULL` format |
| 6 | Search Element | Linearly scans for a value and reports if found |
| 7 | Exit | Terminates the program |

---

## 🧠 What You Learn from This Project

### Doubly Linked List vs Singly Linked List

| Feature | Singly Linked List | Doubly Linked List |
|---------|-------------------|--------------------|
| Pointers per node | 1 (`next`) | 2 (`prev` + `next`) |
| Traversal direction | Forward only | Forward and Backward |
| Delete last node | Needs `prev` tracker variable | Direct via `temp->prev->next = NULL` |
| Memory per node | Less | More (one extra pointer) |
| Complexity of deletion | Slightly more code | Cleaner with `prev` access |

### Core C & Pointer Concepts
- **Bidirectional Pointer Linking** — Every insertion must correctly set *both* `prev` and `next` pointers on the new node *and* update the neighbors' pointers — teaching you to think in two directions simultaneously.
- **Pointer-to-Pointer Relationships** — Understanding that `head->prev = NULL` and `tail->next = NULL` are the boundary conditions that define the list's edges.
- **Dynamic Memory with `malloc()` and `free()`** — Allocating nodes on the heap at runtime and releasing them cleanly on deletion.
- **Struct Self-Reference** — A `node` struct containing pointers to its own type (`struct node* prev` and `struct node* next`).

### Data Structure Concepts
- **Why `prev` Simplifies Deletion** — In a singly linked list, deleting the last node requires carrying a `prev` variable through the traversal loop. In a doubly linked list, `temp->prev` is always available directly — see `delete_last()` for this elegant difference.
- **Boundary/Edge Case Management** — Correctly handling: empty list, single-node list (where `head->next == NULL`), inserting at head (must update old head's `prev`), and deleting head (must nullify new head's `prev`).
- **Linear Search on Linked Structures** — Sequential traversal with a flag variable to detect presence, since linked lists have no index-based access.

### Software Design Habits
- **Separation of Concerns** — Each operation is its own function, keeping `main()` as a clean dispatcher via `switch-case`.
- **Defensive Programming** — Every operation checks `if (head == NULL)` before attempting to traverse or dereference.
- **Interactive CLI Design** — `while(1)` loop with a reprinted menu on every iteration keeps the program usable without restarting.

---

## 🌍 Real-World Applications of Doubly Linked Lists

The bidirectional nature of doubly linked lists makes them ideal wherever you need to move in both directions efficiently:

| Domain | Real-World Use |
|--------|---------------|
| **Web Browsers** | Back/Forward navigation — each page is a node; `prev` goes back, `next` goes forward. Both directions need O(1) pointer access. |
| **Text Editors** | Cursor movement in a document — moving left or right character by character uses `prev` and `next` on a DLL of characters or lines. |
| **Undo / Redo Systems** | Each action is a node; undoing moves to `prev`, redoing moves to `next`. Doubly linked makes both operations O(1). |
| **Music Players** | Previous / Next track navigation — the playlist is a DLL where both directions can be followed without re-scanning from the head. |
| **LRU Cache (Least Recently Used)** | One of the most common interview and systems problems — an LRU cache is almost always implemented using a DLL + hashmap for O(1) access and eviction. |
| **Operating System Schedulers** | The Linux kernel's process scheduler (`task_struct` list) uses a doubly linked list internally to manage the run queue. |
| **Thread-Safe Queues** | Many concurrent data structures use DLLs because inserting/removing from both ends is O(1) without full traversal. |
| **Deck / Deque (Double-Ended Queue)** | A deque — used in sliding window algorithms and many STL containers — is implemented as a doubly linked list under the hood. |

---

## 🐛 Known Issues & Learning Opportunities

Spotted bugs to fix as a practice exercise:

| Location | Bug | Fix |
|----------|-----|-----|
| `searching()` | `flag` is uninitialized before the loop — undefined behavior if the list is somehow bypassed | Initialize as `int key, flag = 0;` |
| `searching()` | Setting `flag = 0` inside the `else` branch on every non-matching iteration is redundant and masks intent | Set `flag = 0` once before the loop; set `flag = 1` and `break` on match |
| `insert_end()` | Returns early without printing confirmation when `head == NULL` (the new node becomes the head) | Move the `printf` after the if-else block or add it inside the early-return branch |
| `display()` | Trailing `NULL` has no newline — output runs into the next prompt on the same line | Change to `printf("NULL\n");` |

---

## 💡 Sample Output

```
============================================
    Doubly Linked List Menu
------------------------------------------
    1. Insert at the beginning
    2. Insert at the end
    ...
============================================

Enter the choice: 2
Enter value: 10
Inserted Element: 10

Enter the choice: 2
Enter value: 20
Inserted Element: 20

Enter the choice: 1
Enter value: 5
Inserted Element: 5

Enter the choice: 5
List: 5 <-> 10 <-> 20 <-> NULL

Enter the choice: 3
Deleted element: 5.

Enter the choice: 5
List: 10 <-> 20 <-> NULL
```

---

## 📌 Time Complexity Reference

| Operation | Time Complexity | Space Complexity |
|-----------|----------------|-----------------|
| Insert at beginning | O(1) | O(1) |
| Insert at end | O(n) | O(1) |
| Delete first node | O(1) | O(1) |
| Delete last node | O(n) | O(1) |
| Display all nodes | O(n) | O(1) |
| Search element | O(n) | O(1) |

> 💡 **Note:** `delete_last()` is O(n) here because we traverse to the tail first. With a `tail` pointer maintained alongside `head`, it becomes O(1) — a natural next improvement to implement.

---

## 🔼 Suggested Next Steps

Once comfortable with this implementation, try extending it:

- [ ] Add **Insert After / Insert Before** a specific value
- [ ] Add **Delete by Value**
- [ ] Add **Reverse Display** (use `prev` pointers to walk backward from the tail)
- [ ] Maintain a `tail` pointer alongside `head` to make `insert_end()` and `delete_last()` O(1)
- [ ] Implement a **circular doubly linked list** (tail's `next` points to head; head's `prev` points to tail)
- [ ] Build an **LRU Cache** using a DLL + hashmap — the classic interview problem

---

## 👨‍💻 Author Notes

Built as a hands-on learning project to understand bidirectional pointer linking, memory management, and the structural advantages a doubly linked list offers over its singly linked counterpart.

> *"The `prev` pointer costs one extra word of memory per node — but in return, it gives you the ability to walk backward through history. That tradeoff appears everywhere in computing."*
