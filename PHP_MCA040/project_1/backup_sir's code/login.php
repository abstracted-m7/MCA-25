<?php
session_start();
require "dbconection.php";
$user_name=$_POST['user_id'];
$password=$_POST['password'];
$sql="select *from Users where user_id='$user_name' and password='$password' and isavtive='Yes'";
//echo $sql;
$result=$conn->query($sql);
$response=$result->fetchAll();
if(count($response)==0){
    header("Location:index.php");
}else{
    $_SESSION["user_id"]=$user_name;
    header('Location: dashboard.php');
}
?>