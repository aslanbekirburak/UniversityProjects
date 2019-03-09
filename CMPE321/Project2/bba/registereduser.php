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
    <div class="logout">
        <a href="index.php">Logout</a>
    </div>
    <div class="bas">
      <?php
          if(isset($_SESSION['username'])){
                $dbname = "test";
                $server = "localhost";
                $dbusername = "root";
                $dbpass = "";
                $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

              ?>
                <form action="makeAppointment.php" method="post">
          <?php /* ?>        Branch:<select name="branch"> <?php
                if($result->num_rows > 0){
                  while($row = $result->fetch_assoc()){ ?>
                    <option value="branchname"><?php echo $row['BranchName'] ?> </option> <?php
                  }
                }*/

                $sql = "SELECT Name,Branch_ID FROM doctor";
                $result=$conn->query($sql);
                ?>
              </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              Doctor:<select name="doctor"><?php
            if($result->num_rows > 0){
              while($row = $result->fetch_assoc()){
                  $sql= "SELECT BranchName FROM branch WHERE Branch_ID='".$row['Branch_ID']."'";
                  $result1 = $conn->query($sql);
                  $row3 = $result1->fetch_assoc();
                  echo "<option>".$row['Name']." ".$row3['BranchName']." "."</option>";
              }
            }
            ?>
          </select>&nbsp;&nbsp;&nbsp;&nbsp;
        Time:<select name="time">
          <?php for($hours=8; $hours<17; $hours++)
                      for($mins=0; $mins<60; $mins+=5)
                                echo '<option>'.str_pad($hours,2,'0',STR_PAD_LEFT).':'
                                       .str_pad($mins,2,'0',STR_PAD_LEFT).'</option>';?>
         </select>&nbsp;&nbsp;&nbsp;&nbsp;
         Day:
         <input type="date" name="date" >


       <input class="bas"type="submit" value="approve"> <br></br>
       </form>
       <form action="removeappointment.php" method="post">
         <?php
             $sql= "SELECT Doctor_ID, Date, Time FROM appointment WHERE Patient_ID IN (SELECT Patient_ID FROM patient WHERE Name='".$_SESSION['username']."')";
             $result = $conn->query($sql);
             ?>

              My Appointments:<select name="appointment"><?php
          if($result->num_rows > 0){
           while($row = $result->fetch_assoc()){
             $sql = "SELECT Name FROM doctor WHERE Doctor_ID = '".$row['Doctor_ID']."'";
             $result1 = $conn->query($sql);
             $row3 = $result1->fetch_assoc();
          /*   $sql = "SELECT BranchName FROM branch WHERE Branch_ID IN (SELECT Branch_ID FROM doctor WHERE Doctor_ID = '".$row['Doctor_ID']."')" ;
             $result2 = $conn->query($sql);
             $row4 = $result2->fetch_assoc();*/
            echo "<option>".$row3['Name']." ".$row['Date']." ".$row['Time']."</option>";
         }
       }

       ?>
 </select>
        <input type="submit" value="remove" name="remove">&nbsp;&nbsp;&nbsp;&nbsp;
        </form>
        <form action="edit_result.php" method="post">
          <?php
              $sql= "SELECT Doctor_ID, Date, Time FROM appointment WHERE Patient_ID IN (SELECT Patient_ID FROM patient WHERE Name='".$_SESSION['username']."')";
              $result = $conn->query($sql);
              ?>

               My Appointments:<select name="appointment"><?php
           if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
              $sql = "SELECT Name FROM doctor WHERE Doctor_ID = '".$row['Doctor_ID']."'";
              $result1 = $conn->query($sql);
              $row3 = $result1->fetch_assoc();
             echo "<option>".$row3['Name']." ".$row['Date']." ".$row['Time']."</option>";
          }
        }
        ?>
  </select>
         <input type="submit" value="edit" name="edit">
         </form>
         <form >
           <?php
               $sql= "SELECT Doctor_ID, Date, Time FROM appointment WHERE Date >= NOW() AND Patient_ID IN (SELECT Patient_ID FROM patient WHERE Name='".$_SESSION['username']."')";
               $result = $conn->query($sql);
               ?>

                My Future Appointments:<select name="Fappointment"><?php
            if($result->num_rows > 0){
             while($row = $result->fetch_assoc()){
               $sql = "SELECT Name FROM doctor WHERE Doctor_ID = '".$row['Doctor_ID']."'";
               $result1 = $conn->query($sql);
               $row3 = $result1->fetch_assoc();
              echo "<option>".$row3['Name']." ".$row['Date']." ".$row['Time']."</option>";
           }
         }
         ?>
   </select>
          </form>
          <form >
            <?php
                $sql= "SELECT Doctor_ID, Date, Time FROM appointment WHERE Date < NOW() AND Patient_ID IN (SELECT Patient_ID FROM patient WHERE Name='".$_SESSION['username']."')";
                $result = $conn->query($sql);
                ?>

                 My Past Appointments:<select name="appointment"><?php
             if($result->num_rows > 0){
              while($row = $result->fetch_assoc()){
                $sql = "SELECT Name FROM doctor WHERE Doctor_ID = '".$row['Doctor_ID']."'";
                $result1 = $conn->query($sql);
                $row3 = $result1->fetch_assoc();
               echo "<option>".$row3['Name']." ".$row['Date']." ".$row['Time']."</option>";
            }
          }
          ?>
    </select>
           </form>
  <?php }else{
    echo "Please <a href='login.php'>login</a> first";
  } ?>
    </div>
  </body>
</html>
