<?php session_start();
  require "dbconection.php";
if(isset($_SESSION['user_id'])){
    $sql="select *from user_details where user_id='{$_SESSION['user_id']}'";
    echo $sql;
    $result=$conn->query($sql);
    $response=$result->fetchAll();
    print_r($response);
    echo count($response);
    ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Welcome <?php echo $_SESSION["user_id"] ;?> </h1>
    <?php
    if(count($response)==0){?>
       <form action="controller.php" method="post">
        <input type="tel" name="phone" id="" placeholder="enter your phone no">
        <input type="text" name="name" id="" placeholder="enter your name">
        <input type="text" name="address" id="" placeholder="enter your address">
        <input type="hidden" name="user_id" value="<?php echo $_SESSION['user_id']?>">
        <button type="submit" name="action" value="insertDetails">Submit Data</button>

       </form>
    <?php
    }else{ ?>
       <form action="controller.php" method="post">
        <input type="tel" name="phone" id="" value="<?php echo $response[0]['phone']?>">
        <input type="text" name="name" id="" value="<?php echo $response[0]['name']?>">
        <input type="text" name="address" id="" value="<?php echo $response[0]['address']?>">
        <input type="hidden" name="udid" value="<?php echo $response[0]['udid']?>">
        <button type="submit" name="action" value="editDetails">Edit Data</button>

       </form>
   <?php }
    ?>
    <h4><a href="logout.php">Logout</a></h4>

</body>
</html>
<?php } else{
    header("Location:index.php");
    }?>