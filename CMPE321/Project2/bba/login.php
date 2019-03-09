<html>
  <style media="screen">
      body{
          margin-top: 200px;
          text-align: center;
      }
      .bas{
        font-size: 30px;
      }
      .alignin{
        width: 150px;
        height: 100px;
      }
  </style>
  <body>
    <div class="bas">
      <form name="form" action="LoginHelper.php" method="post">
       <b>Kullanıcı Adı:</b><br />
       <input type="text" name="isim" size="24"><br /><br />
       <b>Şifre:</b><br />
       <input type="password" name="sifre" size="24"><br /><br />
       <input class="bas"type="submit" value="signin">
    </form>
    </div>
  </body>
</html>
