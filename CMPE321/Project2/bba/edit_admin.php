<?php session_start(); ?>
<html>
<body>
<?php
      if(isset($_SESSION['username'])){
    $doctor1=$_POST["doctor"];
    $branch=$_POST["branch"];
    $dbname = "test";
    $server = "localhost";
    $dbusername = "root";
    $dbpass = "";
    $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

    if ($conn->connect_error){
      die("Connection Failed!");
    }
    $sql="SELECT Branch_ID FROM branch WHERE BranchName='".$branch."'";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
      $row = $result->fetch_assoc();
      $did = $row['Branch_ID'];
    }

       $sql = "INSERT INTO doctor (Name,Branch_ID) VALUES ('".$doctor1."','".$did."')";
       if ($conn->query($sql) === TRUE){
         echo "Your Doctor Have Edited Sucessfully <br>";?>
         <a class="opt"href="admin.php">Return Admin Page</a>
             <?php  exit;
           }  $conn->close();
           ?>
           <div>
               <a href="index.php">Logout</a></br>
               <a href="admin.php">Back To Admin Page</a>
           </div>
           <?php   }else{
                echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
              }?>
         </body>
       </html>
