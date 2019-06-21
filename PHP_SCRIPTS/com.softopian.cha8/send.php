<?php
require_once("config.php");

$conn = mysqli_connect($host, $user, $pass, $data);

if(isset($_GET['user']) and isset($_GET['pass']) and isset($_GET['message']))
{
    $user = $_GET['user'];
    $pass = $_GET['pass'];
    $message = $_GET['message'];
    
    $SQL = "SELECT `id` FROM `chat_user` WHERE `user` = '$user' AND `pass` = '$pass'";
    $MQ = mysqli_query($conn, $SQL);
    
    if(mysqli_num_rows($MQ) > 0){
        
        $MFA = mysqli_fetch_array($MQ);
        $from = $MFA['id'];
        
        $message = mysqli_real_escape_string($conn, $_GET['message']);
        $time = date('U');
        
        $SQL = "INSERT INTO `chat_message` VALUES(NULL, '$from', '0', '$message', '$time')";
        $MQ = mysqli_query($conn, $SQL) or die(mysqli_error($conn));
        
        echo "SUCCESS";
    }else{
        echo "FAILED";
    }
}else{
    echo "FAILED";
}


?>