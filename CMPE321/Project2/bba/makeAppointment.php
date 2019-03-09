<?php session_start(); ?>
<html>
  <style media="screen">
      body{
          margin-top: 200px;
          text-align: center;
      }
      .logout a{
        margin-top:450px;
        text-align: center;
        font-family: sans-serif;
        text-decoration: none;
      }
      .bas{
        font-size: 30px;
        text-decoration: none;
      }
      .alignin{
        width: 150px;
        height: 100px;
      }
      a:hover {
        color: red;
      }
  </style>
  <body>
    <div class="bas">
                <?php
                  if(isset($_SESSION['username'])){
                $token = strtok($_POST['doctor'],  " ");
                $doctor = $token;
                $time=$_POST["time"];
                $date=$_POST["date"];
                $dbname = "test";
                $server = "localhost";
                $dbusername = "root";
                $dbpass = "";
                $conn = new mysqli($server, $dbusername, $dbpass, $dbname);
                echo $date;
                if ($conn->connect_error){
                  die("Connection Failed!");
                }
                $sql = "SELECT Doctor_ID FROM doctor WHERE Name='".$doctor."'";

                $result = $conn->query($sql);
                if($result->num_rows > 0){
                  $row = $result->fetch_assoc();
                  $did = $row['Doctor_ID'];
                }

                $sql = "SELECT Patient_ID FROM patient WHERE Name='".$_SESSION['username']."'";

                $result = $conn->query($sql);
                if($result->num_rows > 0){
                  $row1= $result->fetch_assoc();
                  $pid = $row1['Patient_ID'];
                }
               $sql = "SELECT App_ID FROM appointment  WHERE  Date='".$date."'AND Time='".$time."' AND Doctor_ID IN (SELECT Doctor_ID FROM doctor WHERE Name='".$doctor."')";
              $result=$conn->query($sql);
              if($result->num_rows > 0){
                  echo "This appointment has already exist";}
                  else{
                $sql = "INSERT INTO appointment (Doctor_ID, Patient_ID,Date,Time) VALUES (".$did.",".$pid.",'".$date."','".$time."')";
                if ($conn->query($sql) === TRUE){
                  echo "Your Appointment Have Done Sucessfully <br>";?>
                  <a class="opt"href="registereduser.php">Return Appointment Page</a>
                      <?php  exit;}
                  else{
                    echo "Insetion Failed".$sql."<br>".$conn->error;
                  }
                }
                ?>

    </div>
    <div class="logout">
        <a href="index.php">Logout</a></br>
        <a href="registereduser.php">Back To Appointment Page</a>
    </div>
    <?php }else{
      echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
    } ?>
  </body>
</html>
