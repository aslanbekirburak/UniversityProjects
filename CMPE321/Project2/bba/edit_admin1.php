<?php session_start(); ?>
<?php
          if(isset($_SESSION['username'])){
          echo $_POST['doctor'];
          $token = strtok($_POST['doctor'],  " ");
          $doctorname = $token;
          $dbname = "test";
          $server = "localhost";
          $dbusername = "root";
          $dbpass = "";
          $conn = new mysqli($server, $dbusername, $dbpass, $dbname);
    if ($conn->connect_error){
    die("Connection Failed!");
      }
      $sql="SELECT Doctor_ID FROM doctor WHERE Name='".$doctorname."'";
      $result = $conn->query($sql);
      if($result->num_rows > 0){
        $row = $result->fetch_assoc();
        $did = $row['Doctor_ID'];
      }
      $sql="DELETE FROM doctor where Name='".$doctorname."'";
       if ($conn->query($sql) === TRUE) {
         $sql = "SELECT BranchName FROM branch";
         $result=$conn->query($sql);

        // $sql="DELETE FROM appointment where Date='".$date."'AND Time='".$time."' AND Doctor_ID IN (SELECT Doctor_ID FROM doctor WHERE Name='".$doctorname."')";
    //  $sql= "SELECT Doctor_ID FROM doctor WHERE Name IN (SELECT Name FROM doctor WHERE Name='".$_SESSION['username']."')";
      //$result = $conn->query($sql);?>
      <form action="edit_admin.php" method="post">
        Branch:<select name="branch"><?php
      if($result->num_rows > 0){
        while($row = $result->fetch_assoc()){?>
            <option><?php echo $row['BranchName'] ?></option><?php
        }
      }
      ?>
    </select>
     <p>Doctor: <input type="text" name="doctor" value = "<?php echo $doctorname ?>" /></p>

          <input type="submit" value="edit" name="edit">&nbsp;&nbsp;&nbsp;&nbsp;
     </form><?php
     $conn->close();
}}

   else{
     echo "Please <a href='login.php'>login</a> first".$_SESSION['username'];
   }
?>
