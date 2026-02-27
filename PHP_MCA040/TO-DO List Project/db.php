<?php
$host = "localhost";
$user = "root";
$password = "";
$database = "todolist";
$portnumber = 3306;
$conn = mysqli_connect($host, $user, $password, $database, $portnumber);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>