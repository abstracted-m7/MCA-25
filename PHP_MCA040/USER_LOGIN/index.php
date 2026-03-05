<?php
session_start();
require 'db.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $user = $_POST['user_id'];
    $pass = $_POST['password'];

    $stmt = $conn->prepare("SELECT password FROM user_table WHERE user_id = ?");
    $stmt->bind_param("s", $user);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($row = $result->fetch_assoc()) {
        if (password_verify($pass, $row['password'])) {
            $_SESSION['user_id'] = $user;
            header("Location: details.php");
            exit(); // Always exit after a header redirect
        } else {
            $error = "Wrong password!";
        }
    } else {
        $hash = password_hash($pass, PASSWORD_DEFAULT);
        $reg = $conn->prepare("INSERT INTO user_table (user_id, password) VALUES (?, ?)");
        $reg->bind_param("ss", $user, $hash);
        $reg->execute();

        $_SESSION['user_id'] = $user;
        header("Location: details.php");
        exit();
    }
}
?>

<!DOCTYPE html>
<html>
<body>
    <form method="post">
        <h2>Login or Sign Up</h2>
        User_ID: <input type="text" name="user_id" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        <button type="submit">Continue</button>
        <?php if(isset($error)) echo "<p style='color:red'>$error</p>"; ?>
    </form>
</body>
</html>