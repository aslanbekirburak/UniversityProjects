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
  </style>
  <body>
    <div class="bas">
      <form name="form" action="SignUpHelper.php" method="post">
       <div class="sag">
         Kullanıcı Adı:
         <input type="text" name="isim" size="24"><br /><br />
       </div>
       <div class="sag">
         Şifre:
         <input type="password" name="sifre" size="24"><br /><br />
       </div>
        <input class="bas"type="submit" value="signup">
    </form>
    </div>
  </body>
</html>
