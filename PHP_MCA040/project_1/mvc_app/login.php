<?php
/**
 * Route: login.php
 * Delegates to AuthController::login()
 */
require_once __DIR__ . '/controllers/AuthController.php';

$auth = new AuthController();
$auth->login();
?>
