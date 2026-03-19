<?php
/**
 * View: Dashboard (dashboard.php)
 * Shows user profile form — insert if new, edit if existing
 */
require_once __DIR__ . '/controllers/AuthController.php';
require_once __DIR__ . '/models/UserModel.php';

$auth = new AuthController();

if (!$auth->isLoggedIn()) {
    header('Location: index.php');
    exit;
}

$currentUser = $auth->getCurrentUser();
$userModel   = new UserModel();
$profile     = $userModel->getUserDetails($currentUser);
$isNew       = ($profile === false);

$success = $_GET['success'] ?? '';
$error   = $_GET['error']   ?? '';
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard — MyApp</title>
    <link rel="stylesheet" href="assets/style.css">
    <style>
        /* Dashboard-specific extras */
        .page-center { align-items: center; padding-top: 2rem; padding-bottom: 2rem; }
        .card { max-width: 520px; }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        @media (max-width: 480px) {
            .form-row { grid-template-columns: 1fr; }
        }

        .logout-link {
            display: inline-flex; align-items: center; gap: 0.4rem;
            font-size: 0.83rem;
            color: var(--text-muted);
            text-decoration: none;
            transition: color var(--transition);
        }
        .logout-link:hover { color: var(--error); }
        .logout-link img { width: 15px; height: 15px; opacity: 0.6; transition: opacity 0.2s; }
        .logout-link:hover img { opacity: 1; }

        .header-row {
            display: flex; align-items: flex-start; justify-content: space-between;
            margin-bottom: 1.75rem;
        }
    </style>
</head>
<body>
<div class="page-center">
    <div class="card">

        <!-- Header row -->
        <div class="header-row">
            <div>
                <h2 class="section-heading">
                    <?= $isNew ? 'Create Profile' : 'Edit Profile' ?>
                </h2>
                <p class="section-sub">
                    <?= $isNew ? 'Fill in your details to get started.' : 'Update your account information below.' ?>
                </p>
            </div>
            <a href="logout.php" class="logout-link" title="Logout">
                <img src="https://img.icons8.com/ios/20/6b6b88/exit--v1.png" alt="Logout">
                Logout
            </a>
        </div>

        <!-- Logged-in user strip -->
        <div class="user-strip">
            <div class="avatar"><?= strtoupper(substr($currentUser, 0, 1)) ?></div>
            <div class="info">
                <div class="uid"><?= htmlspecialchars($currentUser) ?></div>
                <div class="role">Authenticated user</div>
            </div>
            <div class="profile-status">
                <span class="dot <?= $isNew ? 'new' : '' ?>"></span>
                <?= $isNew ? 'No profile yet' : 'Profile active' ?>
            </div>
        </div>

        <!-- Success / Error alerts -->
        <?php if ($success === 'profile_saved'): ?>
        <div class="alert alert-success">
            <img src="https://img.icons8.com/ios-filled/16/047857/checkmark--v1.png" alt="Success">
            Profile saved successfully!
        </div>
        <?php elseif ($success === 'profile_updated'): ?>
        <div class="alert alert-success">
            <img src="https://img.icons8.com/ios-filled/16/047857/checkmark--v1.png" alt="Success">
            Profile updated successfully!
        </div>
        <?php elseif (!empty($error)): ?>
        <div class="alert alert-error">
            <img src="https://img.icons8.com/ios-filled/16/be123c/warning--v1.png" alt="Error">
            Something went wrong. Please try again.
        </div>
        <?php endif; ?>

        <!-- Profile form -->
        <form action="controller.php" method="post" id="profileForm">
            <input type="hidden" name="action"
                   value="<?= $isNew ? 'insertDetails' : 'editDetails' ?>">
            <?php if (!$isNew): ?>
            <input type="hidden" name="udid" value="<?= (int)$profile['udid'] ?>">
            <?php endif; ?>

            <!-- Full name -->
            <div class="form-group">
                <label for="name">Full Name</label>
                <div class="input-wrap">
                    <input type="text" id="name" name="name"
                           placeholder="Your full name"
                           value="<?= htmlspecialchars($profile['name'] ?? '') ?>" required>
                    <img class="icon" src="https://img.icons8.com/ios/24/9999b8/user--v1.png" alt="Name" width="17" height="17">
                </div>
            </div>

            <!-- Phone -->
            <div class="form-group">
                <label for="phone">Phone Number</label>
                <div class="input-wrap">
                    <input type="tel" id="phone" name="phone"
                           placeholder="+91 98765 43210"
                           value="<?= htmlspecialchars($profile['phone'] ?? '') ?>" required>
                    <img class="icon" src="https://img.icons8.com/ios/24/9999b8/phone--v1.png" alt="Phone" width="17" height="17">
                </div>
            </div>

            <!-- Address -->
            <div class="form-group">
                <label for="address">Address</label>
                <div class="input-wrap">
                    <input type="text" id="address" name="address"
                           placeholder="Street, City, State"
                           value="<?= htmlspecialchars($profile['address'] ?? '') ?>" required>
                    <img class="icon" src="https://img.icons8.com/ios/24/9999b8/marker--v1.png" alt="Address" width="17" height="17">
                </div>
            </div>

            <div style="margin-top:1.75rem">
                <button type="submit" class="btn btn-primary" id="submitBtn">
                    <span class="btn-text">
                        <img src="https://img.icons8.com/ios-filled/18/ffffff/save--v1.png"
                             alt="" style="vertical-align:middle; margin-right:6px;">
                        <?= $isNew ? 'Save Profile' : 'Update Profile' ?>
                    </span>
                    <div class="spinner"></div>
                </button>
            </div>
        </form>

    </div>
</div>

<script>
// Auto-dismiss alerts
setTimeout(() => {
    document.querySelectorAll('.alert').forEach(el => {
        el.style.transition = 'opacity 0.5s';
        el.style.opacity = '0';
        setTimeout(() => el.remove(), 500);
    });
}, 3500);

// Loading state on submit
document.getElementById('profileForm').addEventListener('submit', function() {
    document.getElementById('submitBtn').classList.add('loading');
});
</script>
</body>
</html>
