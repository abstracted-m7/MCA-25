<?php
/**
 * Auth Controller
 * Handles login, logout, and session management
 */
require_once __DIR__ . '/../models/UserModel.php';

class AuthController {
    private $userModel;

    public function __construct() {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        $this->userModel = new UserModel();
    }

    /**
     * Handle login POST request
     */
    public function login(): void {
        if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
            $this->redirectTo('index.php');
            return;
        }

        $user_id  = trim($_POST['user_id']  ?? '');
        $password = trim($_POST['password'] ?? '');

        if (empty($user_id) || empty($password)) {
            $this->redirectTo('index.php?error=empty_fields');
            return;
        }

        $user = $this->userModel->authenticate($user_id, $password);

        if ($user) {
            $_SESSION['user_id'] = $user_id;
            $this->redirectTo('dashboard.php');
        } else {
            $this->redirectTo('index.php?error=invalid_credentials');
        }
    }

    /**
     * Destroy session and redirect to login
     */
    public function logout(): void {
        session_destroy();
        $this->redirectTo('index.php');
    }

    /**
     * Check if user is logged in
     */
    public function isLoggedIn(): bool {
        return isset($_SESSION['user_id']);
    }

    /**
     * Get the currently logged-in user_id
     */
    public function getCurrentUser(): string|null {
        return $_SESSION['user_id'] ?? null;
    }

    private function redirectTo(string $url): void {
        header("Location: $url");
        exit;
    }
}
?>
