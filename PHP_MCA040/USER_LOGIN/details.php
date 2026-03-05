<?php
session_start();
require 'db.php';

if (!isset($_SESSION['user_id'])) { 
    header("Location: index.php"); 
    exit(); 
}

$user_id = $_SESSION['user_id'];
$message = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name    = $_POST['name'];
    $email   = $_POST['email'];
    $phone   = $_POST['phone'];
    $address = $_POST['address'];

    // Note: If user_id is a string in your DB, use "sssssssss" 
    // If user_id is an integer, use "issssssss"
    $sql = "INSERT INTO user_details (user_id, name, email, phone, address) 
            VALUES (?, ?, ?, ?, ?) 
            ON DUPLICATE KEY UPDATE name=?, email=?, phone=?, address=?";
    
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssssss", $user_id, $name, $email, $phone, $address, $name, $email, $phone, $address);
    
    try {
        $stmt->execute();
        $message = "<b style='color: green;'>Saved successfully!</b>";
    } catch (Exception $e) {
        $message = "<b style='color: red;'>Error saving data.</b>";
    }
}

// SECURE FETCH: Use a prepared statement here too!
$stmt_fetch = $conn->prepare("SELECT * FROM user_details WHERE user_id = ?");
$stmt_fetch->bind_param("s", $user_id);
$stmt_fetch->execute();
$res = $stmt_fetch->get_result();
$data = $res->fetch_assoc();

// If no data exists yet, create an empty array so the form doesn't crash
if (!$data) {
    $data = ['name' => '', 'email' => '', 'phone' => '', 'address' => ''];
}
?>

<!DOCTYPE html>
<html>
<body>
    <h2>Your Details (Logged in as: <?php echo htmlspecialchars($user_id); ?>)</h2>
    <a href="logout.php">Logout</a><br><br>

    <?php echo $message; ?>

    <form method="post">
        Name: <input type="text" name="name" value="<?php echo htmlspecialchars($data['name']); ?>"><br><br>
        Email: <input type="email" name="email" value="<?php echo htmlspecialchars($data['email']); ?>"><br><br>
        Phone: <input type="text" name="phone" value="<?php echo htmlspecialchars($data['phone']); ?>"><br><br>
        Address: <textarea name="address"><?php echo htmlspecialchars($data['address']); ?></textarea><br><br>
        <button type="submit">Save Changes</button>
    </form>
</body>
</html>