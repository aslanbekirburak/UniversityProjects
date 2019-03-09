xquery version "3.1";
declare option exist:serialize "method=html5 media-type=text/html";
let $document:=doc("reservedb.xml")
let $customerid:=request:get-parameter("customerid","")
let $name:=request:get-parameter("name","")
let $phonenumber:=request:get-parameter("phonenumber","")
let $dateentry:=request:get-parameter("dateentry","")
let $datefinish:=request:get-parameter("datefinish","")
let $numroom:=request:get-parameter("numroom","")
let $reserve:=
    <reserve>
        <customerid>{$customerid}</customerid>
        <name>{$name}</name>
        <phonenumber>{$phonenumber}</phonenumber>
        <process>
            <dateentry>{$dateentry}</dateentry>
            <datefinish>{$datefinish}</datefinish>
            <numroom>{$numroom}</numroom>
        </process>
    </reserve>
let $process:=$reserve/process

   return

<html>
        <head>
    <title>Hotel reservedb</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta data-template="config:app-meta" />
    <link rel="shortcut icon" href="$shared/resources/images/exist_icon_16x16.ico" />
    <link rel="stylesheet" type="text/css" href="$shared/resources/css/bootstrap-3.0.3.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/style.css" />
    <script type="text/javascript" src="$shared/resources/scripts/jquery/jquery-1.7.1.min.js" />
    <script type="text/javascript" src="$shared/resources/scripts/loadsource.js" />
    <script type="text/javascript" src="$shared/resources/scripts/bootstrap-3.0.3.min.js" />
</head>

<body class="body">
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
       

        <div>


    <div class="row">
        <div class="col-md-8">
            <div class="page-header">
                <h1>Add</h1>
            </div>
            
               
 <form action="reserve_add.xql" method="POST" class="form form-horizontal">
        <div class="form-group">
            <label for="name" class="col-md-2 hidden-xs control-label">Customer Name:</label>
            <div class="col-md-4 col-xs-12">
                <span class="input-group">
                    <input name="name" type="text" data-template="templates:form-control" class="form-control"/>
                    
                </span>
            </div>
        </div>
        <div class="form-group">
            <label for="customerid" class="col-md-2 hidden-xs control-label">Identity Number:</label>
            <div class="col-md-4 col-xs-12">
                <span class="input-group">
                    <input name="customerid" type="text" data-template="templates:form-control" class="form-control"/>
                    
                </span>
            </div>
        </div>
        <div class="form-group">
            <label for="phonenumber" class="col-md-2 hidden-xs control-label">Phone Number:</label>
            <div class="col-md-4 col-xs-12">
                <span class="input-group">
                    <input name="phonenumber" type="text" data-template="templates:form-control" class="form-control"/>
                    
                </span>
            </div>
        </div>
        <div class="form-group">
            <label for="numroom" class="col-md-2 hidden-xs control-label">Room Number:</label>
            <div class="col-md-4 col-xs-12">
                <span class="input-group">
                    <input name="numroom" type="text" data-template="templates:form-control" class="form-control"/>
                    
                </span>
            </div>
        </div>
        <div class="form-group">
            <label for="dateentry" class="col-md-2 hidden-xs control-label">Entry Date:</label>
            <div class="col-md-4 col-xs-12">
                <span class="input-group">
                    <input name="dateentry" type="text" data-template="templates:form-control" class="form-control" placeholder="12/12/2012"/>
                    
                </span>
            </div>
        </div>
        <div class="form-group">
            <label for="datefinish" class="col-md-2 hidden-xs control-label">Exit Date:</label>
            <div class="col-md-4 col-xs-12">
                <span class="input-group">
                    <input name="datefinish" type="text" data-template="templates:form-control" class="form-control" placeholder="12/12/2012"/>
                    
                </span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-4 col-xs-12">
<label></label>
                <span class="input-group-btn">
                            <button type="submit" value="Submit">Send</button>
                </span>
            </div>
          </div>
          
    </form>
    
   
      
<!--      additional info      -->
        </div>
        
        
    </div>
    

</div>


            {
                if (not(string($customerid))) then(
                    
                    )
                else 
                    (if(not($document/reservedb/reserve[customerid=$customerid]))then
                    (update insert $reserve into $document/reservedb,
                    
                        
       <div class="alert alert-success">
        <span class="glyphicon glyphicon-ok"></span>Success Registered</div>                 
                    
                    )
                else(
                    if(not($document/reservedb/reserve[customerid=$customerid]/process[dateentry=$dateentry]))then
                        (update insert $process into $document/reservedb/reserve[customerid=$customerid],
                        
                      
       <div class="alert alert-success">
       <span class="glyphicon glyphicon-ok"></span>     Success Registered</div>                    
                        



                        )
                    else(
                        <div class="alert alert-warning">
        <span class="glyphicon glyphicon-ok"></span>Error You have registered recently.</div>   
                    )
                )
                    )
            }
                
      </nav>
</body>

</html>
    