<?php
/**
 * Route: logout.php
 * Delegates to AuthController::logout()
 */
require_once __DIR__ . '/controllers/AuthController.php';

$auth = new AuthController();
$auth->logout();
?>
