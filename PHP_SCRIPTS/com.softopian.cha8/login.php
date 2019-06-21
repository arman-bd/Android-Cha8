<?php
require_once("config.php");

$conn = mysqli_connect($host, $user, $pass, $data);

if(isset($_GET['user']) and isset($_GET['pass']))
{
    $user = $_GET['user'];
    $pass = $_GET['pass'];
    
    $SQL = "SELECT `id` FROM `chat_user` WHERE `user` = '$user' AND `pass` = '$pass'";
    $MQ = mysqli_query($conn, $SQL) or die(mysqli_error($conn));
    
    if(mysqli_num_rows($MQ) > 0){
        echo "SUCCESS";
    }else{
        echo "FAILED";
    }
}else{
    echo "FAILED";
}


?>