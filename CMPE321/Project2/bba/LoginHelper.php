<?php session_start(); ?>
<html>
  <style media="screen">
      body{
          margin-top: 200px;
          text-align: center;
      }
      .bas{
        font-size: 30px;
      }
      .opt {
          margin-top: 30px;
          font-size: 30px;
          text-decoration: none;
      }

      .sag{
        margin-right: : 300px;
      }
      .alignin{
        width: 150px;
        height: 100px;
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
              $_SESSION['username']=$username;
              $_SESSION['password']=$password;

        if ($conn->connect_error){
          die("Connection Failed!");
        }
        $sql = "SELECT Patient_ID FROM patient WHERE Name='".$username."' AND Password='".$password."'";

        $result = $conn->query($sql);
        if ( $result->num_rows > 0 ){

          header("Location: /registereduser.php");
          exit;
        }else{

          $sql = "SELECT Admin_ID FROM admin WHERE Name='".$username."' AND Password='".$password."'";

          $result = $conn->query($sql);
          if ( $result->num_rows > 0 ){
            header("Location: /admin.php");
            }
            else{
          echo "Please login or insert valid values " . $sql . "<br>" . $conn->error."</br>";?>
            <a class="opt"href="singup.php">SignUp</a></br>
            <a class="opt"href="Login.php">SignIn</a><?php
        }}
        $conn->close();
     ?>

    </div>
  </body>
</html>
