<?php
session_start();
require 'db.php';

// 1. Check if user is logged in
if (!isset($_SESSION['user_id'])) { 
    header("Location: index.php"); 
    exit(); 
}

$user_id = $_SESSION['user_id'];
$message = "";

// 2. Handle Form Submission
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name    = $_POST['name'];
    $email   = $_POST['email'];
    $phone   = $_POST['phone'];
    $address = $_POST['address'];

    // If user_id is a string, we use "sssssssss" (9 strings)
    $sql = "INSERT INTO user_details (user_id, name, email, phone, address) 
            VALUES (?, ?, ?, ?, ?) 
            ON DUPLICATE KEY UPDATE name=?, email=?, phone=?, address=?";
    
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssssss", $user_id, $name, $email, $phone, $address, $name, $email, $phone, $address);
    
    try {
        $stmt->execute();
        // SUCCESS: Redirect to the product dashboard
        header("Location: dashboard.php");
        exit(); 
    } catch (Exception $e) {
        $message = "<b style='color: red;'>Error saving data: " . $e->getMessage() . "</b>";
    }
}

// 3. Fetch existing data to populate the form
$stmt_fetch = $conn->prepare("SELECT * FROM user_details WHERE user_id = ?");
$stmt_fetch->bind_param("s", $user_id);
$stmt_fetch->execute();
$res = $stmt_fetch->get_result();
$data = $res->fetch_assoc();

// If no data exists yet, create empty values
if (!$data) {
    $data = ['name' => '', 'email' => '', 'phone' => '', 'address' => ''];
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>My Details</title>
</head>
<body>
    <h2>Your Details (Logged in as: <?php echo htmlspecialchars($user_id); ?>)</h2>
    <p>Please complete your profile to continue to the shop.</p>
    <a href="logout.php">Logout</a><br><br>

    <?php echo $message; ?>

    <form method="post">
        Name: <input type="text" name="name" value="<?php echo htmlspecialchars($data['name']); ?>" required><br><br>
        Email: <input type="email" name="email" value="<?php echo htmlspecialchars($data['email']); ?>" required><br><br>
        Phone: <input type="text" name="phone" value="<?php echo htmlspecialchars($data['phone']); ?>" required><br><br>
        Address: <br>
        <textarea name="address" required><?php echo htmlspecialchars($data['address']); ?></textarea><br><br>
        
        <button type="submit">Save and Go to Shop</button>
    </form>
</body>
</html>