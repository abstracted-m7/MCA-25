<?php
include "db.php";
$result = mysqli_query($conn, "SELECT * FROM tasks ORDER BY id DESC");
?>

<!DOCTYPE html>
<html>
<head>
    <title>To-Do List</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>To-Do List</h2>

<form action="add.php" method="POST">
    <input type="text" name="task" placeholder="Enter new task" required>
    <button type="submit">Add</button>
</form>

<ul>
<?php while ($row = mysqli_fetch_assoc($result)): ?>
    <li>
        <?= ($row['status'] === 'completed') ? "<del>{$row['task']}</del>" : $row['task']; ?>

        <a href="update.php?id=<?= $row['id']; ?>">Complete</a>
        <a href="delete.php?id=<?= $row['id']; ?>">Delete</a>
    </li>
<?php endwhile; ?>
</ul>

</body>
</html>