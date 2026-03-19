# MyApp — PHP MVC Login & Profile Manager

A lightweight PHP web application built with the **MVC (Model-View-Controller)** pattern, using **SQLite** as the database. Features user authentication, profile creation, and profile editing — all wrapped in a clean, responsive sky-blue UI.

---

## 📁 Project Structure

```
mvc_app/
│
├── index.php                  # View: Login page (entry point)
├── dashboard.php              # View: User profile dashboard
├── login.php                  # Route → AuthController::login()
├── logout.php                 # Route → AuthController::logout()
├── controller.php             # Dispatcher → UserController (insert/edit)
├── mydatabase.db              # SQLite database file
│
├── assets/
│   └── style.css              # Shared CSS (sky blue theme)
│
├── controllers/
│   ├── AuthController.php     # Handles login, logout, session
│   └── UserController.php     # Handles profile insert & update
│
└── models/
    ├── Database.php           # Singleton PDO connection
    └── UserModel.php          # All DB queries
```

---

## 🧱 MVC Architecture

| Layer | Files | Responsibility |
|---|---|---|
| **Model** | `Database.php`, `UserModel.php` | Database connection and all SQL queries |
| **View** | `index.php`, `dashboard.php` | HTML rendering, user interface |
| **Controller** | `AuthController.php`, `UserController.php`, `controller.php` | Business logic, session management, redirects |

---

## ✨ Features

- 🔐 **User Login** — Authenticate with User ID and password
- 👤 **Profile Management** — Create or edit name, phone, and address
- 🔒 **Session Handling** — Secure PHP sessions with login/logout
- 🛡️ **SQL Injection Protection** — All queries use PDO prepared statements
- 📱 **Responsive Design** — Works on desktop and mobile
- 🎨 **Sky Blue UI** — Clean card-centered layout with smooth animations

---

## 🗄️ Database Schema

The app uses an **SQLite** database (`mydatabase.db`) with two tables:

```sql
-- Users table (authentication)
CREATE TABLE Users (
    user_id  TEXT PRIMARY KEY,
    password TEXT,
    isavtive TEXT   -- 'Yes' = active account
);

-- User details table (profile data)
CREATE TABLE user_details (
    udid     INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id  TEXT REFERENCES Users(user_id),
    name     TEXT,
    phone    TEXT,
    address  TEXT
);
```

### Default Test Accounts

| User ID | Password |
|---|---|
| `a@test.com` | `1234` |
| `b@test.com` | `1234567` |

---

## ⚙️ Requirements

- PHP **7.4+** (PHP 8.x recommended)
- PHP **PDO** extension enabled
- PHP **PDO SQLite** driver enabled
- A local web server — **XAMPP**, **WAMP**, **MAMP**, or PHP's built-in server

---

## 🚀 Installation & Setup

### 1. Clone or download the project

```bash
git clone https://github.com/your-username/mvc_app.git
```

Or simply copy the `mvc_app/` folder into your server's web root.

### 2. Place in your web server root

| Server | Web Root |
|---|---|
| XAMPP | `C:/xampp/htdocs/mvc_app/` |
| WAMP | `C:/wamp64/www/mvc_app/` |
| MAMP | `/Applications/MAMP/htdocs/mvc_app/` |

### 3. Make sure `mydatabase.db` is in the project root

The database file must sit alongside `index.php`. The path is resolved automatically via `__DIR__` in `Database.php`.

### 4. Run with PHP's built-in server (optional)

```bash
cd mvc_app
php -S localhost:8000
```

Then open [http://localhost:8000](http://localhost:8000) in your browser.

---

## 🔄 Application Flow

```
User visits index.php
        │
        ▼
   Already logged in? ──Yes──▶ dashboard.php
        │
       No
        │
        ▼
  Submits login form
        │
        ▼
   login.php ──▶ AuthController::login()
        │
        ├── Invalid? ──▶ index.php?error=invalid_credentials
        │
        └── Valid?  ──▶ $_SESSION['user_id'] set ──▶ dashboard.php
                              │
                              ▼
                   Has profile? ──No──▶ Show Create Profile form
                              │
                             Yes
                              │
                              ▼
                        Show Edit Profile form
                              │
                              ▼
                   Submits ──▶ controller.php ──▶ UserController
                                                  ├── insertDetails()
                                                  └── updateDetails()
```

---

## 🎨 UI Design

- **Theme:** Sky blue gradient background (`#bae6fd → #e0f2fe → #f0f9ff`)
- **Card:** White, centered, with soft blue shadow
- **Fonts:** DM Serif Display (headings) + DM Sans (body) via Google Fonts
- **Icons:** Icons8 CDN PNG images (user, lock, phone, marker, etc.)
- **Animations:** Fade-up card entrance, focus glow on inputs, hover lift on buttons
- **Alerts:** Auto-dismiss after 3.5 seconds on the dashboard

---

## 🛡️ Security Notes

- All database queries use **PDO prepared statements** — no raw string interpolation
- Sessions are started with `session_start()` and destroyed cleanly on logout
- User input is sanitized with `htmlspecialchars()` before rendering
- ⚠️ **Passwords are stored in plain text** in the current database — for production use, replace with `password_hash()` / `password_verify()`

---

## 📌 Known Limitations

- No password hashing (plain text passwords in DB)
- No CSRF token protection on forms
- No user registration flow (users must be added directly to the DB)
- Single user profile per account (one-to-one relationship)

---

## 📄 License

This project is open source and free to use for learning and personal projects.
