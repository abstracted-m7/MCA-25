<?php
//print_r($_POST);
 require "dbconection.php";
if($_POST['action']=="insertDetails"){
      $sql = "INSERT INTO user_details (user_id, phone, name, address) 
                VALUES (:user_id, :phone, :name, :address)";
    $user_id=$_POST['user_id'];
    $phone=$_POST['phone'];
    $name=$_POST['name'];
    $address=$_POST['address'];
    $stmt=$conn->prepare($sql);
    $stmt->bindParam(":user_id",$user_id);
    $stmt->bindParam(":phone",$phone);
    $stmt->bindParam(":name",$name);
    $stmt->bindParam(":address", $address);
    if($stmt->execute()){
        header('Location: dashboard.php');
    }else{
        echo "Error ";
    }
}
if($_POST['action']=="editDetails"){
   $sql = "UPDATE user_details 
            SET phone = :phone, name = :name, address = :address 
            WHERE udid = :udid";
    $udid=$_POST['udid'];
    $phone=$_POST['phone'];
    $name=$_POST['name'];
    $address=$_POST['address'];
    $stmt=$conn->prepare($sql);
    $stmt->bindParam(":udid",$udid);
    $stmt->bindParam(":phone",$phone);
    $stmt->bindParam(":name",$name);
    $stmt->bindParam(":address", $address);
    if($stmt->execute()){
        header('Location: dashboard.php');
    }else{
        echo "Error ";
    }
}

?>