<?php
include "db.php";
$result = mysqli_query($conn, "SELECT * FROM tasks ORDER BY id ASC");
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
<?php 
$counter = 1; 
while ($row = mysqli_fetch_assoc($result)): 
?>
    <li>
        <?php if ($row['status'] === 'completed'): ?>
            <del><?= $counter; ?>: <?= htmlspecialchars($row['task']); ?></del>
        <?php else: ?>
            <?= $counter; ?>: <?= htmlspecialchars($row['task']); ?>
        <?php endif; ?>

        <span class="actions">
            <a href="update.php?id=<?= $row['id']; ?>">Complete</a>
            <a href="delete.php?id=<?= $row['id']; ?>" onclick="return confirm('Are you sure?')">Delete</a>
        </span>
    </li>
<?php 
$counter++; 
endwhile; 
?>
</ul>

</body>
</html>