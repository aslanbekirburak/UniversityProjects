<?php session_start(); ?>
<?php
          if(isset($_SESSION['username'])){
        echo $_POST['branch'];
        $branchname = $_POST['branch'];
        $dbname = "test";
        $server = "localhost";
        $dbusername = "root";
        $dbpass = "";
        $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

        if ($conn->connect_error){
          die("Connection Failed!");
        }
        $sql="DELETE FROM branch WHERE BranchName='".$branchname."'";
        if ($conn->query($sql) === TRUE) {
        echo " deleted successfully";  ?></br></br></br>
        <a class="opt"href="admin.php">Return Admin Page</a><?php
         } else {
        echo "Error deleting doctor: " . $conn->error;
         }
         $conn->close();
          }else{
           echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
         } 
         ?>
