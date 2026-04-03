# Concurrent Login System — Java Synchronized Authentication Demo

A Java program simulating **multiple concurrent login attempts** on a shared `Login` resource. Four threads attempt authentication simultaneously with different credentials — some valid, some invalid — demonstrating **`synchronized` method locking, custom named threads, `Thread.currentThread().getName()` for identity tracking, and exception-based authentication failure**.

---

## 📁 Files

| File | Description |
|------|-------------|
| `code.java` | Single source file with `Login`, `LoginThread`, `InvalidLoginException`, and `main` |

---

## 🚀 How to Run

```bash
javac code.java
java code
```

**Sample Output (thread order varies):**
```
Thread-1 trying to login...
Thread-1 login Successfully..!!
Thread-3 trying to login...
Thread-3 login Failed..!!
Thread-2 trying to login...
Thread-2 login Failed..!!
Thread-4 trying to login...
Thread-4 login Failed..!!
```

> Thread-4 uses a correct username but wrong password (`"admin"` vs `"MG@123"`) — it will always fail.

---

## 🧠 Concepts Demonstrated

- **`synchronized` Method** — only one thread can execute `login()` at a time — concurrent login attempts are serialised
- **`setName()`** — explicitly names threads (`Thread-1` through `Thread-4`) for readable, traceable output
- **`Thread.currentThread().getName()`** — identifies the active thread from inside `run()` and inside the `synchronized` method
- **Exception as Auth Signal** — `InvalidLoginException` models authentication failure — thrown on wrong credentials, caught in `run()`
- **Credential Validation** — `String.equals()` used for safe string comparison (not `==`)
- **True Concurrency** — no `join()` in `main()` — all four threads compete simultaneously for the login lock

---

## 🔐 Login Attempt Breakdown

| Thread | Username | Password | Result |
|--------|----------|----------|--------|
| Thread-1 | `MG@123` | `1234` | ✅ Success |
| Thread-2 | `user` | `1234` | ❌ Wrong username |
| Thread-3 | `admin` | `0000` | ❌ Wrong username & password |
| Thread-4 | `admin` | `1234` | ❌ Wrong username (password correct) |

Only Thread-1 matches both `validUsername` and `validPassword` exactly.

---

## 🔒 How `synchronized` Protects Login

```
Without synchronized:
  Thread-1 reads validUsername ─┐
  Thread-2 reads validUsername  ├── Both execute simultaneously
  Thread-3 reads validUsername ─┘   Interleaved output, unpredictable state

With synchronized:
  Thread-1 enters login() → lock acquired on Login object
  Thread-2, 3, 4 → BLOCKED at method entry
  Thread-1 completes → releases lock
  Next thread enters → gets consistent read of credentials
```

Authentication requires **atomicity** — checking username AND password must be a single uninterrupted operation.

---

## 🔄 Program Flow

```
main()
  │
  ├── new Login()              →  Shared auth system with hardcoded credentials
  ├── new LoginThread(×4)      →  Each carries its own username/password pair
  ├── setName("Thread-N")      →  Labels each thread for readable output
  │
  └── t1–t4.start()            →  All four threads compete for login() lock
        │
        └── login.login(u, p)
              ├── Credentials match? → print success
              └── No match?          → throw InvalidLoginException → caught in run()
```

---

## 💡 Software Life Cycle Implications

### 1. 🔍 Requirement Analysis
The authentication rule — **both username AND password must match exactly** — is a single `if` condition inside `login()`, making the core business rule directly readable and verifiable in code.

### 2. 🏗️ Design
The `Login` class is a **self-defending authentication gate** — the `synchronized` lock ensures no two threads can simultaneously test credentials, preventing timing-based attacks like TOCTOU (Time-of-Check-Time-of-Use) in this context.

### 3. 💻 Development
Using `setName()` before `start()` is a best practice — named threads make logs, stack traces, and debug output significantly easier to trace in multi-threaded systems, especially when thread count grows.

### 4. 🧪 Testing
`login.login(username, password)` can be unit tested directly — four test cases (correct/correct, wrong username, wrong password, both wrong) cover all logical branches without any threads.

### 5. 🔧 Maintenance
Changing valid credentials means updating two fields in `Login` — no thread class changes needed. In production, credentials would come from a database or config file, not hardcoded strings.

### 6. 📈 Scalability
A single `synchronized` method becomes a bottleneck at high concurrency — all threads queue behind one lock. Real auth systems use **stateless JWT validation** or **connection pool + database lookup** to handle thousands of concurrent logins.

---

## ⚠️ Key Considerations

| Point | Detail |
|-------|--------|
| Hardcoded credentials | `validUsername` and `validPassword` are plaintext in source code — a critical security anti-pattern in any real system |
| No password hashing | Passwords should never be stored or compared as plain strings — use `BCrypt` or `SHA-256` hashing |
| Class named `code` | `public class code` violates Java naming convention — class names should be `PascalCase` (e.g., `LoginSystem`) |
| `InvalidLoginException` in message | The exception message contains the thread name — mixing identity with error message couples concerns |
| Thread-4 misconception | Thread-4 uses `"admin"` as username with the correct password — it still fails, which is correct auth behaviour worth noting |
| No `join()` | `main()` exits after `start()` — thread execution completes independently with no guaranteed finish before JVM shutdown |

---

## 🔄 Comparison Across Threading Programs in This Series

| Program | Shared Resource | Coordination | Exception Type |
|---------|----------------|-------------|----------------|
| `OnlineTicketBooking` | `TicketCounter` | `synchronized` + `join()` | Business rule |
| `BankingSystem` | `BankAccount` | `synchronized` (concurrent) | 2 custom exceptions |
| `ProducerConsumerDemo` | `Buffer` | `synchronized` + sleep | State violation |
| **`LoginSystem`** | **`Login`** | **`synchronized` (concurrent)** | **Auth failure** |

All four use `synchronized` — this program is the clearest example of using it purely for **access serialisation** rather than state protection.

---

## 🛠️ Possible Enhancements

- Rename `code.java` → `LoginSystem.java` and `public class code` → `public class LoginSystem`
- Move credentials out of source code into a `.properties` file or environment variable
- Add **login attempt counter** — lock the account after 3 failed attempts using `AtomicInteger`
- Add `Thread.sleep()` to simulate real network latency per login attempt
- Hash the password using `MessageDigest` (`SHA-256`) and compare hashes instead of plain strings
- Return a **session token** (`UUID`) on successful login instead of just printing success

---

## 📄 License
Free to use for educational purposes.
