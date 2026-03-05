<?php
session_start();
require 'db.php';

if (!isset($_SESSION['user_id'])) { header("Location: index.php"); exit(); }

$total = 0;
$items_to_show = [];

if (!empty($_SESSION['cart'])) {
    // Convert array of IDs into a string for SQL: "1,2,3"
    $ids = implode(',', array_map('intval', $_SESSION['cart']));
    $result = $conn->query("SELECT * FROM products WHERE id IN ($ids)");
    
    while ($row = $result->fetch_assoc()) {
        // Count how many times this specific ID appears in the cart array
        $qty = array_count_values($_SESSION['cart'])[$row['id']];
        $subtotal = $row['price'] * $qty;
        $total += $subtotal;
        $row['qty'] = $qty;
        $row['subtotal'] = $subtotal;
        $items_to_show[] = $row;
    }
}

// Handle Checkout
if (isset($_POST['place_order']) && $total > 0) {
    $user = $_SESSION['user_id'];
    $stmt = $conn->prepare("INSERT INTO orders (user_id, total_amount) VALUES (?, ?)");
    $stmt->bind_param("sd", $user, $total);
    $stmt->execute();
    
    $_SESSION['cart'] = []; // Clear cart
    echo "<script>alert('Order Placed Successfully!'); window.location='dashboard.php';</script>";
    exit();
}
?>

<!DOCTYPE html>
<html>
<body>
    <h2>Your Shopping Cart</h2>
    <table border="1" cellpadding="10">
        <tr>
            <th>Product</th>
            <th>Price</th>
            <th>Qty</th>
            <th>Subtotal</th>
        </tr>
        <?php foreach($items_to_show as $item): ?>
        <tr>
            <td><?php echo $item['name']; ?></td>
            <td><?php echo $item['price']; ?>/-</td>
            <td><?php echo $item['qty']; ?></td>
            <td><?php echo number_format($item['subtotal'], 2); ?>/-</td>
        </tr>
        <?php endforeach; ?>
    </table>
    
    <h3>Total: <?php echo number_format($total, 2); ?>/-</h3>
    
    <a href="dashboard.php">Continue Shopping</a>
    <?php if($total > 0): ?>
        <form method="post" style="display:inline;">
            <button type="submit" name="place_order" style="background:green; color:white;">Confirm Order</button>
        </form>
    <?php endif; ?>
</body>
</html>