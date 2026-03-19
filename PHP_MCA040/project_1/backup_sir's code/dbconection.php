<?php
    $dbpath = __DIR__ ."/mydatabase.db";   
    if (file_exists($dbpath)) {       
        $conn = new PDO("sqlite:$dbpath");
    } else {
        echo "File Not Found";
    }
?>