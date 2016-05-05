<html><head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="js/upload.js"></script>
        <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
        <link href="css/stylesmine.css" rel="stylesheet" type="text/css">
    </head><body>
    <div class="section">
            <div class="container-fluid">
                <div class="row">
                    <div class="span12">
                    <%@ page import="model.*,java.util.List"%>
                    <%int appID = Integer.parseInt(request.getParameter("appID"));
                    AppBean app = null;
                    List<AppBean> apps= (List<AppBean>)session.getAttribute("appOverview");
                    for(AppBean a:apps){
                    	if(a.getAppID()==appID)
                    		app = a;
                    }
                    %>
                    	<!--This is the detail page of a application. Users can check it when they want to buy it-->
                        <h1>Sheffield Cloudbase <small> <%=app.getName() %></small>
                        <hr></h1>
                    </div>
                </div>
            </div>
        </div>
    <div class="section">
            <div class="container-fluid">
                <div class="row">
                    <div class="span12">
                    	<div class="row">
                    		<div class="span5">
                    			 <!--SHOW THE LOGO OF APPLICATION-->
                    	        <img src="/CloudComputingTeam8/imageFile/<%=app.getImage() %>" class="img-responsive img-rounded" height="150" width="150">
                                 <!--SHOW THE Some information OF APPLICATION-->
                    	        <hr><li id="fdes"><br>Authour:<p><%=app.getOwned() %></p><hr>
                                <li id="fdes"><br>Price:<p><%=app.getPrice() %></p><hr>
                                <li id="fdes"><br>Size:<p>XXX</p><hr>
                            </div>
                            <div class="span1">
                                
                            </div>
                            <div class="span6">
                            	<!--Show the description of the application-->
                            	<h4 id="hd">Application Detail</h4>
                                <hr>
                                <p id="descrption">
                            	<p><%=app.getDescription() %></p>
                            </div>
                            <div class="span1">
                                
                            </div>
                            <div class="span3">
                            <%if(app.getAuthorisation()){
								String path = "runApp?appID="+app.getAppID();%>
								<a class="btn btn-primary" onclick = "openWindows('<%=path%>')">Run</a>
								<% }else{%>
								<a id="buy" class="btn btn-primary" onclick = "message(<%=app.getAppID()%>)">Buy</a>
								<%} %>
                            </div>
                         </div>
                    </div>
                </div>
            </div>
        </div>


</body>
</html>