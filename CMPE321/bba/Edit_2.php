<?php session_start(); ?>
<html>
<body>
<?php
        //$branch=$_POST["branch"];
          if(isset($_SESSION['username'])){
          $doctor1=$_POST["doctor"];
          $time1=$_POST["time"];
          $date1=$_POST["date"];
          $dbname = "test";
          $server = "localhost";
          $dbusername = "root";
          $dbpass = "";
          $conn = new mysqli($server, $dbusername, $dbpass, $dbname);
          //echo $date;
          if ($conn->connect_error){
            die("Connection Failed!");
          }

          $sql = "SELECT Doctor_ID FROM doctor WHERE Name='".$doctor1."'";

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
        $sql = "SELECT App_ID FROM appointment  WHERE  Date='".$date1."'AND Time='".$time1."' AND Doctor_ID IN (SELECT Doctor_ID FROM doctor WHERE Name='".$doctor1."')";
         $result=$conn->query($sql);
         if($result->num_rows > 0){
             echo "This appointment has already exist";}
             else{
           $sql = "INSERT INTO appointment (Doctor_ID, Patient_ID,Date,Time) VALUES (".$did.",".$pid.",'".$date1."','".$time1."')";
           if ($conn->query($sql) === TRUE){
             echo "Your Appointment Have Edited Sucessfully <br>";?>
             <a class="opt"href="registereduser.php">Return Appointment Page</a>
                 <?php  exit;}
               }  $conn->close();?>
       <div>
           <a href="index.php">Logout</a></br>
           <a href="registereduser.php">Back To Appointment Page</a>
       </div>
       <?php   }else{
            echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
          }?>
     </body>
   </html>
