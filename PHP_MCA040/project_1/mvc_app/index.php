<?php
/**
 * View: Login Page (index.php)
 * Entry point — redirects to dashboard if already logged in
 */
require_once __DIR__ . '/controllers/AuthController.php';

$auth = new AuthController();

if ($auth->isLoggedIn()) {
    header('Location: dashboard.php');
    exit;
}

$error = $_GET['error'] ?? '';
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MVC Modeling</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="page-center">
    <div class="card">

        <!-- Brand -->
        <div class="brand">
            <div class="brand-icon">
                <img src="https://img.icons8.com/ios-filled/50/6366f1/fingerprint.png"
                     alt="App Logo" width="30" height="30">
            </div>
            <h1>Welcome back</h1>
            <p>Sign in to continue to your account</p>
        </div>

        <!-- Error alert -->
        <?php if ($error === 'invalid_credentials'): ?>
        <div class="alert alert-error">
            <img src="https://img.icons8.com/ios-filled/16/be123c/warning--v1.png" alt="Error">
            Invalid credentials. Please try again.
        </div>
        <?php elseif ($error === 'empty_fields'): ?>
        <div class="alert alert-error">
            <img src="https://img.icons8.com/ios-filled/16/be123c/warning--v1.png" alt="Error">
            Please fill in all fields.
        </div>
        <?php endif; ?>

        <!-- Login form -->
        <form action="login.php" method="post" id="loginForm">
            <div class="form-group">
                <label for="user_id">User ID</label>
                <div class="input-wrap">
                    <input type="text" id="user_id" name="user_id"
                           placeholder="Enter your user ID" autocomplete="username" required>
                    <img class="icon" src="https://img.icons8.com/ios/24/9999b8/user--v1.png"
                         alt="User" width="17" height="17">
                </div>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <div class="input-wrap">
                    <input type="password" id="password" name="password"
                           placeholder="Enter your password" autocomplete="current-password" required>
                    <img class="icon" src="https://img.icons8.com/ios/24/9999b8/lock--v1.png"
                         alt="Password" width="17" height="17">
                </div>
            </div>

            <div style="margin-top:1.5rem">
                <button type="submit" class="btn btn-primary" id="submitBtn">
                    <span class="btn-text">
                        <img src="https://img.icons8.com/ios-filled/18/ffffff/login-rounded-right.png"
                             alt="" style="vertical-align:middle; margin-right:6px;">
                        Sign In
                    </span>
                    <div class="spinner"></div>
                </button>
            </div>
        </form>

    </div>
</div>

<script>
document.getElementById('loginForm').addEventListener('submit', function() {
    const btn = document.getElementById('submitBtn');
    btn.classList.add('loading');
});
</script>
</body>
</html>
