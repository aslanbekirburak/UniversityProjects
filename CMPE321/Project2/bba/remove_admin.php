<?php session_start(); ?>
<?php
          if(isset($_SESSION['username'])){
        echo $_POST['doctor'];
        $token = $_POST['doctor'];
        $doctorname = $token;
        $dbname = "test";
        $server = "localhost";
        $dbusername = "root";
        $dbpass = "";
        $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

        if ($conn->connect_error){
          die("Connection Failed!");
        }
        $sql="DELETE FROM doctor WHERE Name='".$doctorname."'";
        if ($conn->query($sql) === TRUE) {
        echo "doctor deleted successfully";  ?></br></br></br>
        <a class="opt"href="admin.php">Return Admin Page</a><?php
         } else {
        echo "Error deleting doctor: " . $conn->error;
         }
         $conn->close();
          }else{
           echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
         } 
         ?>
