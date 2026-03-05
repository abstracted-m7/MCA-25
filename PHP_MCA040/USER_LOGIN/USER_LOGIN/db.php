<?php
$host = "localhost";
$user = "root";
$pass = "";
$dbname = "user_info";

// Enable error reporting for debugging

$conn = new mysqli($host, $user, $pass, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>