
//Write a php program to compute basic mathematic operations.

<html>
<head>
    <title>Your Calculator</title>
</head>
<body>
    <h1>Static Calculator: PHP-HTML</h1>

    <form action="" method="POST">
        <p>
            <label for="num1">Enter First Number:</label>
            <input type="number" name="num1" id="num1" required>
        </p>
        <p>
            <label for="num2">Enter Second Number:</label>
            <input type="number" name="num2" id="num2" required>
        </p>
        <p>
            <label for="operator">Select Operator:</label>
            <select id="operator" name="operator">
                <option value="+">+</option>
                <option value="-">-</option>
                <option value="*">*</option>
                <option value="/">/</option>
            </select>
        </p>
        <button type="submit" name="submit">Calculate</button>
    </form>

<?php

if (isset($_POST['submit'])) {

    echo "<h3>Result:</h3>";

    $num1 = $_POST['num1'];
    $num2 = $_POST['num2'];
    $operator = $_POST['operator'];

    echo "The operand1: $num1 and operand2: $num2 <br>";
    echo "The operator is: $operator <br>";

    if ($operator == "/" && $num2 == 0) {
        $result = "Error: Division by zero is not allowed.";
    } else {
        switch ($operator) {
            case "+":
                $result = $num1 + $num2 ;
                break;
            case "-":
                $result = $num1 - $num2 ;
                break;
            case "*":    
                $result = $num1 * $num2 ;
                break;
            case "/":    
                $result = $num1 / $num2 ;
                break;
            default:
                $result = "Invalid Operator!";
        }
    }
    
    echo "<strong>The result of the operation is: " . $result . "</strong>";
}
?>
</body>
</html>