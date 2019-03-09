<?php session_start(); ?>
<html>
<body>
<?php
    if(isset($_SESSION['username'])){
    $branch=$_POST["isim"];
    $dbname = "test";
    $server = "localhost";
    $dbusername = "root";
    $dbpass = "";
    $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

    if ($conn->connect_error){
      die("Connection Failed!");
    }
       $sql = "INSERT INTO branch (BranchName) VALUES ('".$branch."')";
       if ($conn->query($sql) === TRUE){
         echo "Branch Have added Sucessfully <br>";?>
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
