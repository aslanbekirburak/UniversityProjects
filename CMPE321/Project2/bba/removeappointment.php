<?php session_start(); ?>
<?php

  if(isset($_SESSION['username'])){
echo $_POST['appointment'];
    $token = strtok($_POST['appointment'],  " ");
    $doctorname = $token;
    $token = strtok(" ");
    $date = $token;
    $token = strtok(" ");
    $time = $token;
    $dbname = "test";
    $server = "localhost";
    $dbusername = "root";
    $dbpass = "";
    $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

    if ($conn->connect_error){
      die("Connection Failed!");
    }

     $sql="DELETE FROM appointment WHERE Date='".$date."' AND Time='".$time."' AND Doctor_ID IN (SELECT Doctor_ID FROM doctor WHERE Name='".$doctorname."')";
     if ($conn->query($sql) === TRUE) {
    echo "Record deleted successfully";  ?></br></br></br>
     <a class="opt"href="registereduser.php">Return Appointment Page</a><?php
      } else {
    echo "Error deleting record: " . $conn->error;
      }
      $conn->close();
       }else{
        echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
      } 
      ?>
