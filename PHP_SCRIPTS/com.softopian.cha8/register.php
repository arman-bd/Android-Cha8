<?php
require_once("config.php");

$conn = mysqli_connect($host, $user, $pass, $data);

if(isset($_GET['user']) and isset($_GET['pass']) and isset($_GET['fullname']))
{
    $fullname = mysqli_real_escape_string($conn, $_GET['fullname']);
    $user = mysqli_real_escape_string($conn, $_GET['user']);
    $pass = mysqli_real_escape_string($conn, $_GET['pass']);
    
    $SQL = "INSERT INTO `chat_user` VALUES(NULL, '$fullname', '$user', '$pass')";
    $MQ = mysqli_query($conn, $SQL) or die(mysqli_error($conn));
    
    if(mysqli_insert_id($conn) > 0){
        echo "SUCCESS";
    }else{
        echo "FAILED";
    }
}else{
    echo "FAILED";
}


?>