<html>
  <style media="screen">
      body{
          margin-top: 200px;
          text-align: center;
      }
      .bas{
        font-size: 30px;
      }
      .sag{
        margin-right: : 300px;
      }
      .alignin{
        width: 150px;
        height: 100px;
      }
      .opt {
          margin-top: 30px;
          font-size: 30px;
          text-decoration: none;
      }
  </style>
  <body>
    <div class="bas">
    <?php
              $username=$_POST["isim"];
              $password=$_POST["sifre"];
              $dbname = "test";
              $server = "localhost";
              $dbusername = "root";
              $dbpass = "";
              $conn = new mysqli($server, $dbusername, $dbpass, $dbname);

        if ($conn->connect_error){
          die("Connection Failed!");
        }
        $sql = "SELECT Patient_ID FROM patient WHERE Name='".$username."' AND Password='".$password."'";
        $result=$conn->query($sql);
        if($result->num_rows > 0){
        echo "This username has already exist"; ?>
          </br><a class="opt"href="singup.php">Return Sign Up Page</a>
      <?php

        }else{
        $sql = "INSERT INTO patient (Patient_ID, Name, Password) VALUES (NULL,'".$username."','".$password."')";
        if ($conn->query($sql) === TRUE) {
          header("Location: /login.php");
          exit;
        } else {
          echo "İnsertion Error: " . $sql . "<br>" . $conn->error;
        }
      }
        $conn->close();
     ?>
    
    </div>
  </body>
</html>
