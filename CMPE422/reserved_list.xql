xquery version "3.1";
declare option exist:serialize "method=html5 media-type=text/html";

let $sort:= request:get-parameter("sort","customerid")
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
let $process:=
    <process>
    {
        if($sort='customerid')then(
            for $x in $process/process
            order by $x/customerid
            return $x)
        else(
            if($sort='name')then(
                for $x in $process/process
                order by $x/name
                return $x)
            else(
                if($sort='phonenumber')then(
                    for $x in $process/process
                    order by $x/phonenumber
                    return $x)
                else(
                    if($sort='dateentry')then(
                        for $x in $process/process
                        order by $x/dateentry
                        return $x)
                    else(
                        if($sort='datefinish')then(
                            for $x in $process/process
                            order by $x/datefinish
                            return $x)
                        else(
                            if($sort='numroom')then(
                                for $x in $process/process
                                order by $x/numroom
                                return $x)
                                
                                else()
                            
                            )
                        )
                    )
                )
            )
        
    }
        
    </process>
return
<html>

<head>
    <title>Hotel Reservation</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta data-template="config:app-meta" />
    <link rel="shortcut icon" href="$shared/resources/images/exist_icon_16x16.ico" />
    <link rel="stylesheet" type="text/css" href="$shared/resources/css/bootstrap-3.0.3.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/style.css" />
    <script type="text/javascript" src="$shared/resources/scripts/jquery/jquery-1.7.1.min.js" />
    <script type="text/javascript" src="$shared/resources/scripts/loadsource.js" />
    <script type="text/javascript" src="$shared/resources/scripts/bootstrap-3.0.3.min.js" />
</head>

<body id="body">
        <div class="navbar-header-right">
            <img src="http://pngimages.net/sites/default/files/hotel-png-image-24504.png"  class="img-rounded" style="width:80px" />
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar" />
                <span class="icon-bar" />
                <span class="icon-bar" />
            </button>
            <a style=" color: red; " class="navbar-brand" href="./index.xql">Reservation System</a>


        </div>

        <table class="table">
            <tr>
            <th><a style=" color: red; " href="reserved_list.xql?sort=customerid">Customer Number </a> </th>
            <th><a  style=" color: red; "href="reserved_list.xql?sort=name">Customer Name</a></th>
            <th><a style=" color: red; " href="reserved_list.xql?sort=phonenumber">Phone Number</a></th>
            <th><a  style=" color: red; "href="reserved_list.xql?sort=dateentry">Entry Date</a></th>
            <th><a style=" color: red; " href="reserved_list.xql?sort=datefinish">Exit Date</a></th>
            <th><a style=" color: red; " href="reserved_list.xql?sort=numroom">Room Number</a></th>
                            <th></th>


            </tr>
            { for $x in $process/process return
            <tr>
                
                <th>{string($x/customerid)} </th>
                <th>{string($x/name)} </th>
                <th>{string($x/phonenumber)} </th>
                <th>{string($x/dateentry)} </th>
                <th>{string($x/datefinish)} </th>
                <th>{string($x/numroom)} </th>
                <th>
                <a style=" color: red; " href="delete_process.xql?customerid={$x/customerid}">

                      Clean </a>
                    <a style=" color: orange; "href="process_edit.xql?customerid={$x/customerid}">
                      Edit </a>

                </th>
            </tr>
            }
        </table>

</body>

</html>

