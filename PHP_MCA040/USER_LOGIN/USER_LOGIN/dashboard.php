<?php
session_start();
require 'db.php';

if (!isset($_SESSION['user_id'])) {
    header("Location: index.php");
    exit();
}

// Initialize cart if empty
if (!isset($_SESSION['cart'])) { $_SESSION['cart'] = []; }

// Handle "Add to Cart" action
if (isset($_GET['add_id'])) {
    $_SESSION['cart'][] = $_GET['add_id'];
    header("Location: dashboard.php?status=added");
    exit();
}

$products = $conn->query("SELECT * FROM products");
?>

<!DOCTYPE html>
<html>
<head>
    <title>Product Dashboard</title>
    <style>
        .product-grid { display: flex; gap: 20px; flex-wrap: wrap; }
        .card { border: 1px solid #ddd; padding: 15px; border-radius: 8px; width: 200px; text-align: center; }
        .cart-count { background: red; color: white; padding: 2px 8px; border-radius: 50%; font-size: 12px; }
    </style>
</head>
<body>
    <h2>Welcome, <?php echo htmlspecialchars($_SESSION['user_id']); ?>!</h2>
    <nav>
        <a href="details.php">My Profile</a> | 
        <a href="cart.php">View Cart <span class="cart-count"><?php echo count($_SESSION['cart']); ?></span></a> | 
        <a href="logout.php">Logout</a>
    </nav>
    <hr>

    <?php if(isset($_GET['status'])) echo "<p style='color:green'>Item added to cart!</p>"; ?>

    <h3>Available Products</h3>
    <div class="product-grid">
        <?php while($item = $products->fetch_assoc()): ?>
            <div class="card">
                <h4><?php echo htmlspecialchars($item['name']); ?></h4>
                <p><?php echo htmlspecialchars($item['description']); ?></p>
                <p><strong><?php echo $item['price']; ?>/-</strong></p>
                <a href="dashboard.php?add_id=<?php echo $item['id']; ?>">
                    <button>Add to Cart</button>
                </a>
            </div>
        <?php endwhile; ?>
    </div>
</body>
</html>