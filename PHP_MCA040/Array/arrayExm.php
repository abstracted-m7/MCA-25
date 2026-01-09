<html>

<head>
    <title>Sum of N Numbers</title>
</head>
<body>
   
    <form method="post">
        <label>Enter the value of n:</label>
        <input type="number" name="number" required min="1">
        <button type="submit" name="calculate">Calculate</button>
    </form>

    <?php
    if (isset($_POST['calculate'])) {
        $n = $_POST['number'];
        $sum = 0;

        for ($i = 1; $i <= $n; $i++) {
            $sum += $i;
        }

        echo "<h3>Results:</h3>";
        echo $sum;
    }
    ?>
</body>
</html>