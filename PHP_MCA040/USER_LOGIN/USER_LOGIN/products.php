<?php
session_start();
require 'db.php';

// Initialize cart if it doesn't exist
if (!isset($_SESSION['cart'])) {
    $_SESSION['cart'] = [];
}

// Handle adding to cart
if (isset($_GET['add'])) {
    $product_id = $_GET['add'];
    $_SESSION['cart'][] = $product_id;
    header("Location: products.php");
    exit();
}

$result = $conn->query("SELECT * FROM products");
?>

<!DOCTYPE html>
<html>
<body>
    <h2>Store</h2>
    <a href="details.php">My Profile</a> | 
    <a href="cart.php">View Cart (<?php echo count($_SESSION['cart']); ?>)</a> | 
    <a href="logout.php">Logout</a>
    <hr>

    <div style="display: flex; gap: 20px;">
        <?php while($row = $result->fetch_assoc()): ?>
            <div style="border: 1px solid #ccc; padding: 10px; border-radius: 8px;">
                <h3><?php echo htmlspecialchars($row['name']); ?></h3>
                <p><?php echo htmlspecialchars($row['description']); ?></p>
                <p><b><?php echo $row['price']; ?>/-</b></p>
                <a href="products.php?add=<?php echo $row['id']; ?>">
                    <button>Add to Cart</button>
                </a>
            </div>
        <?php endwhile; ?>
    </div>
</body>
</html>