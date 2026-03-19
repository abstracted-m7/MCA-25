<?php session_start();
if(isset($_SESSION['user_id'])){
     header('Location: dashboard.php');
}else{?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <h1>This is SQLite Database coneection</h1>
    <form action="login.php" method="post">
        <input type="text" name="user_id" id="">
        <input type="password" name="password" id="">
        <button type="submit">Login</button>
    </form>

</body>

</html>
<?php } ?>