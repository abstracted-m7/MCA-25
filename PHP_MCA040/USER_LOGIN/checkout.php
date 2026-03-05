<?php
session_start();
require 'db.php';

if (!isset($_SESSION['user_id']) || empty($_SESSION['cart'])) {
    header("Location: products.php");
    exit();
}

$user_id = $_SESSION['user_id'];
$total = $_POST['total'];

// Save order to DB
$stmt = $conn->prepare("INSERT INTO orders (user_id, total_amount) VALUES (?, ?)");
$stmt->bind_param("sd", $user_id, $total);

if ($stmt->execute()) {
    // Clear cart after successful purchase
    $_SESSION['cart'] = [];
    $order_id = $conn->insert_id;
} else {
    die("Error processing order.");
}
?>

<!DOCTYPE html>
<html>
<body>
    <div style="text-align: center; margin-top: 50px;">
        <h1 style="color: green;">Success!</h1>
        <p>Thank you for your purchase, <?php echo htmlspecialchars($user_id); ?>!</p>
        <p>Your Order ID is: <b>#<?php echo $order_id; ?></b></p>
        <p>Amount Paid: <b>$<?php echo number_format($total, 2); ?></b></p>
        <br>
        <a href="products.php">Return to Store</a>
    </div>
</body>
</html>