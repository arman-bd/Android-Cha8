<?php
require_once("config.php");

$conn = mysqli_connect($host, $user, $pass, $data);

if(isset($_GET['user']) and isset($_GET['pass']) and isset($_GET['last']))
{
    $user = $_GET['user'];
    $pass = $_GET['pass'];
    $last = $_GET['last'];
    
    $SQL = "SELECT `id` FROM `chat_user` WHERE `user` = '$user' AND `pass` = '$pass'";
    $MQ = mysqli_query($conn, $SQL);
    
    if(mysqli_num_rows($MQ) > 0){
        
        $SQL = "SELECT * FROM `chat_message` WHERE `id` > $last ORDER BY `id` ASC LIMIT 0, 10";
        $MQ = mysqli_query($conn, $SQL);
        
        if(mysqli_num_rows($MQ) > 0)
        {
            while($MFA = mysqli_fetch_array($MQ))
            {
                $SQL = "SELECT `name` FROM `chat_user` WHERE `id` = '".$MFA['sender']."'";
                $MQ2 = mysqli_query($conn, $SQL);
                $MFA2 = mysqli_fetch_array($MQ2);
                $last_id = $MFA['id'];
                
                $mails[] = array("sender" => $MFA2['name'], "message" => $MFA['message'], "time" => $MFA['time']);
            }
            
            echo json_encode(array("data" => $mails, "last" => array("id" => $last_id)));
            
        }else{
            
            echo "NULL";
            
        }
        
        
        
    }else{
        echo "FAILED";
    }
}else{
    echo "FAILED";
}


?>