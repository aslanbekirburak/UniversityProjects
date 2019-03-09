xquery version "3.1"; declare option exist:serialize "method=html5 media-type=text/html"; 
let $y:= request:get-parameter("customerid","") 
let $x:=doc("reservedb.xml")/reservedb/reserve[customerid[contains (.,$y)]]
    


return

    <html>

    <head>
        <title>Reservation</title>
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
                    <span class="sr-only"></span>
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
                            <h2>Edit</h2>
                        </div>
                        <div>

                            <form action="reserve_add.xql" method="POST" class="form form-horizontal">
                                <div class="form-group">
                                    <label for="name" class="col-md-2 hidden-xs control-label">Customer Name:</label>
                                    <div class="col-md-4 col-xs-12">
                                        <span class="input-group">
                    <input name="name" type="text" value="{$x/name}" data-template="templates:form-control" class="form-control"/>

                </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="customerid" class="col-md-2 hidden-xs control-label">Identity Number:</label>
                                    <div class="col-md-4 col-xs-12">
                                        <span class="input-group">
                    <input name="customerid" type="text" value="{$x/customerid}" data-tempreserveate="templates:form-control" class="form-control"/>

                </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phone_number" class="col-md-2 hidden-xs control-label">Phone Number:</label>
                                    <div class="col-md-4 col-xs-12">
                                        <select name="phone_number" data-template="templates:form-control" class="form-control" value="{$x/phone_number}">
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="numroom" class="col-md-2 hidden-xs control-label">Room Number:</label>
                                    <div class="col-md-4 col-xs-12">
                                        <span class="input-group">
                    <input name="numroom" value="{$x/process/numroom}"type="text" data-template="templates:form-control" class="form-control"/>

                </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dateentry" class="col-md-2 hidden-xs control-label">Entry Date:</label>
                                    <div class="col-md-4 col-xs-12">
                                        <span class="input-group">
                    <input name="dateentry" value="{$x/process/dateentry}" type="text" data-template="templates:form-control" class="form-control" placeholder="12/12/2019"/>

                </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dateexit" class="col-md-2 hidden-xs control-label">Exit Date:</label>
                                    <div class="col-md-4 col-xs-12">
                                        <span class="input-group">
                    <input name="dateexit" value="{$x/process/datefinish}"type="text" data-template="templates:form-control" class="form-control" placeholder="12/12/2019"/>

                </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="Submit" class="col-md-2 hidden-xs control-label">Save:</label>
                                    <div class="col-md-4 col-xs-12">

                                        <span class="input-group-btn">
                                                        <button type="submit" value="Submit">Send</button>

                </span>
                                    </div>
                                </div>

                            </form>

                        </div>
                        <!--      additional info      -->
                    </div>

                </div>

            </div>
        </nav>
    </body>

    </html>
