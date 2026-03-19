<?php
/**
 * Route: controller.php
 * Dispatcher — routes POST actions to the correct controller method
 */
require_once __DIR__ . '/controllers/UserController.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: index.php');
    exit;
}

$action     = $_POST['action'] ?? '';
$controller = new UserController();

switch ($action) {
    case 'insertDetails':
        $controller->insertDetails();
        break;

    case 'editDetails':
        $controller->updateDetails();
        break;

    default:
        header('Location: dashboard.php');
        exit;
}
?>
