xquery version "3.1";
declare option exist:serialize "method=html5 media-type=text/html";
let $process:=
    <process>
    {
        for $x in doc("reservedb.xml")/reservedb/reserve/process
        return 
            <process>
                <customerid>{$x/../customerid}</customerid>
                <name>{$x/../name}</name>
                <phonenumber>{$x/../phonenumber}</phonenumber>
                <dateentry>{$x/dateentry}</dateentry>
                <datefinish>{$x/datefinish}</datefinish>
                <numroom>{$x/numroom}</numroom>
            </process>
    }
    </process>

return 
<html>

<head>
    <title>Hotel</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta data-template="config:app-meta" />
    <link rel="shortcut icon" href="$shared/resources/images/exist_icon_16x16.ico" />
    <link rel="stylesheet" type="text/css" href="$shared/resources/css/bootstrap-3.0.3.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/style.css" />
    <script type="text/javascript" src="$shared/resources/scripts/jquery/jquery-1.7.1.min.js" />
    <script type="text/javascript" src="$shared/resources/scripts/loadsource.js" />
    <script type="text/javascript" src="$shared/resources/scripts/bootstrap-3.0.3.min.js" />
    <script type="text/javascript" src="paging.js" />

    
    
  
</head>

<body class="body" >
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header-right">
            <img src="http://pngimages.net/sites/default/files/hotel-png-image-24504.png" class="img-rounded" style="width:80px" />
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar" />
                <span class="icon-bar" />
                <span class="icon-bar" />
            </button>
            <a class="navbar-brand" href="./index.xql">Reservation System</a>
            
           

        </div>
       

        <div xmlns="http://www.w3.org/1999/xhtml">
            <div class="row">
                <div class="col-md-8">
                    
                    <div>
                
                            <a style="margin-right: 20px; margin-left:300px;" href="reserved_list.xql" class="btn btn-success btn-lg" role="button"><span class="glyphicon glyphicon-pencil"></span>
EDIT RESERVATION</a>
                          
                            <a href="reserve_add.xql" class="btn btn-info mr-2 btn-lg" role="button"> <span class="glyphicon glyphicon-book"></span>     ADD RESERVATION</a>
                    </div>
                    
                    
                </div>
            </div>
        </div>
    </nav>
    <div class="col-md-9">
    <table class="table">
     
                            <tr>
                                <th></th>
                                <th><h3>Identity Number</h3> </th>
                                <th><h3>Customer Name</h3> </th>
                                <th><h3>Phone Number</h3> </th>
                                <th><h3>Date of Entry</h3> </th>
                                <th><h3>Date of Exit</h3> </th>
                                <th><h3>Room Number</h3> </th>
                            </tr>
                             
                            { for $x in $process/process return
                            <tr>
                                <th>
                                </th>
                                <th>{string($x/customerid)} </th>
                                <th>{string($x/name)} </th>
                                <th>{string($x/phonenumber)} </th>
                                <th>{string($x/dateentry)} </th>
                                <th>{string($x/datefinish)} </th>
                                <th>{string($x/numroom)} </th>
                            </tr>
                            }
                        </table>
                        </div>
                      
                        

</body>

</html>

   
