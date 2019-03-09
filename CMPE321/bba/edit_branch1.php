<?php session_start(); ?>
<?php
  {
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

      $sql="DELETE FROM branch where BranchName='".$branchname."'";
       if ($conn->query($sql) === TRUE) {
        // $sql="DELETE FROM appointment where Date='".$date."'AND Time='".$time."' AND Doctor_ID IN (SELECT Doctor_ID FROM doctor WHERE Name='".$doctorname."')";
    //  $sql= "SELECT Doctor_ID FROM doctor WHERE Name IN (SELECT Name FROM doctor WHERE Name='".$_SESSION['username']."')";
      //$result = $conn->query($sql);?>
      <form action="edit_branch2.php" method="post">

     <p>Branch: <input type="text" name="branch" value = "<?php echo $branchname ?>" /></p>

          <input type="submit" value="edit" name="edit">&nbsp;&nbsp;&nbsp;&nbsp;
     </form><?php

}}
$conn->close();
   }else{
     echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
   }
?>
