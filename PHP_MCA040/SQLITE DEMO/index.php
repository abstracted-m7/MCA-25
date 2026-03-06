<?php
// 1. Setup Database Connection
$db = new SQLite3('studentDB.db');

// 2. Handle the Form Submission
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['add_student'])) {
    $name  = $_POST['stud_name'];
    $add   = $_POST['stud_add'];
    $phone = $_POST['stud_phone'];

    // Prepare statement to prevent SQL Injection
    $query = "INSERT INTO student (stud_name, stud_add, stud_phone) VALUES (:name, :add, :phone)";
    $stmt = $db->prepare($query);
    
    $stmt->bindValue(':name', $name, SQLITE3_TEXT);
    $stmt->bindValue(':add', $add, SQLITE3_TEXT);
    $stmt->bindValue(':phone', $phone, SQLITE3_TEXT);

    if ($stmt->execute()) {
        echo "<p style='color: green;'>Success: Student $name added!</p>";
    } else {
        echo "<p style='color: red;'>Error: Could not add student.</p>";
    }
}
?>

<h2>Add New Student</h2>
<form method="POST" action="">
    <label>Name:</label><br>
    <input type="text" name="stud_name" required><br><br>

    <label>Address:</label><br>
    <input type="text" name="stud_add" required><br><br>

    <label>Phone:</label><br>
    <input type="text" name="stud_phone" required><br><br>

    <input type="submit" name="add_student" value="Save to Database">
</form>

<?php
// 4. Optional: Close connection
$db->close();
?>