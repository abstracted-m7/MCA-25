<?php
/**
 * UserController
 * Handles user detail insert and update operations
 */
require_once __DIR__ . '/../models/UserModel.php';
require_once __DIR__ . '/../controllers/AuthController.php';

class UserController {
    private $userModel;
    private $authController;

    public function __construct() {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        $this->userModel      = new UserModel();
        $this->authController = new AuthController();
    }

    /**
     * Insert new profile details
     */
    public function insertDetails(): void {
        if (!$this->authController->isLoggedIn()) {
            $this->redirectTo('index.php');
            return;
        }

        $user_id = $this->authController->getCurrentUser();
        $phone   = trim($_POST['phone']   ?? '');
        $name    = trim($_POST['name']    ?? '');
        $address = trim($_POST['address'] ?? '');

        if ($this->userModel->insertDetails($user_id, $phone, $name, $address)) {
            $this->redirectTo('dashboard.php?success=profile_saved');
        } else {
            $this->redirectTo('dashboard.php?error=insert_failed');
        }
    }

    /**
     * Update existing profile details
     */
    public function updateDetails(): void {
        if (!$this->authController->isLoggedIn()) {
            $this->redirectTo('index.php');
            return;
        }

        $udid    = (int)($_POST['udid']    ?? 0);
        $phone   = trim($_POST['phone']    ?? '');
        $name    = trim($_POST['name']     ?? '');
        $address = trim($_POST['address']  ?? '');

        if ($this->userModel->updateDetails($udid, $phone, $name, $address)) {
            $this->redirectTo('dashboard.php?success=profile_updated');
        } else {
            $this->redirectTo('dashboard.php?error=update_failed');
        }
    }

    private function redirectTo(string $url): void {
        header("Location: $url");
        exit;
    }
}
?>
