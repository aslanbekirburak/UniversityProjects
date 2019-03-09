<?php session_start(); ?>
<html>
  <style media="screen">
      body{
          margin-top: 20px;
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
  <?php
              if(isset($_SESSION['username'])){
            $dbname = "test";
            $server = "localhost";
            $dbusername = "root";
            $dbpass = "";
            $conn = new mysqli($server, $dbusername, $dbpass, $dbname);
            if ($conn->connect_error){
              die("Connection Failed!");
                }
            $sql = "SELECT BranchName FROM branch";
            $result=$conn->query($sql);

           ?>
           <form name="form" action="addDoctor.php" method="post">
            Doctor Adı:
            <input type="text" name="isim" size="24">

            Branch:<select name="branch"><?php
          if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){?>
                <option><?php echo $row['BranchName'] ?></option><?php
            }
          }
          ?>
        </select>&nbsp;&nbsp;&nbsp;&nbsp;

        <input type="submit" value="add"> <br></br>
            </form>
            <form action="remove_admin.php" method="post">
              <?php
              $sql = "SELECT Name FROM doctor";
              $result=$conn->query($sql);
                  ?>
                  Doctors:<select name="doctor"><?php
               if($result->num_rows > 0){
                while($row = $result->fetch_assoc()){
                 echo "<option>".$row['Name']."</option>";
              }
            }
            ?>
      </select>
             <input type="submit" value="delete" name="delete">&nbsp;&nbsp;&nbsp;&nbsp;</br></br>
             </form>
             <form action="edit_admin1.php" method="post">
               <?php
               $sql = "SELECT Name FROM doctor";
               $result=$conn->query($sql);
                   ?>
                   Doctors:<select name="doctor"><?php
                if($result->num_rows > 0){
                 while($row = $result->fetch_assoc()){
                  echo "<option>".$row['Name']."</option>";
               }
             }
             ?>
       </select>
              <input type="submit" value="edit" name="edit">
              </form>
              <form name="form" action="addBranch.php" method="post">
               Branch Name:
               <input type="text" name="isim" size="24">
               <input type="submit" value="add"> <br></br>
                   </form>
              <form action="remove_branch.php" method="post">
                <?php
                $sql = "SELECT BranchName FROM branch";
                $result=$conn->query($sql);
                    ?>
                    Branches:<select name="branch"><?php
                 if($result->num_rows > 0){
                  while($row = $result->fetch_assoc()){
                   echo "<option>".$row['BranchName']."</option>";
                }
              }
              ?>
        </select>
               <input type="submit" value="delete" name="delete">&nbsp;&nbsp;&nbsp;&nbsp;</br></br>
               </form>
               <form action="edit_branch1.php" method="post">
                 <?php
                $sql = "SELECT BranchName FROM branch";
                 $result=$conn->query($sql);
                     ?>
                     Branches:<select name="branch"><?php
                  if($result->num_rows > 0){
                   while($row = $result->fetch_assoc()){
                    echo "<option>".$row['BranchName']."</option>";
                 }
               }
               ?>
         </select>
                <input type="submit" value="edit" name="edit">
                </form>
                  <a href="index.php">Logout</a></br>
                <?php   }else{
                     echo "Please <a href='login.php'>login</a> first";
                   }?>
      </html>
