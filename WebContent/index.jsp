<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="js/upload.js"></script>
	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
   	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
   	<link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
   	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
	<title>Welcome to Sheffield Cloudbase</title>
</head>

<body>
<%@ page import="model.*,java.util.List"%>
<% UserBean user = (UserBean)session.getAttribute("user");%>
<div class="container-fluid" id="LG">
	<div class="row-fluid">
		<div class="span12">
			<div class="page-header">
				<h1>
					<!--This is the main page after login-->
					<!--Users can see all applications in this platform-->
					Sheffield Cloudbase<small> All apps</small>
				</h1>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span2">
			<!--The left nav bar-->
			<ul class="nav nav-list well">
				<li class="nav-header">Hello, <%=user.getUsername() %></li>
				<li class="active">
					<a href="#">All Apps</a>
				</li>
				<%if(user.getAuthorisation()==2){ %>
				<li>
					<a href="myApp.jsp">My Apps</a>
				</li>
				<%} %>
				<li>
					<a href="MyAccount.jsp">My Account</a>
				</li>
				<li class="divider">
				</li>
				<li>
					<a href="/CloudComputingTeam8/logout">Log Out</a>
				</li>
			</ul>
			<ol id="apprank">
				<h3>APP Rank</h3>
				<h4>Most popular apps</h4>
				<hr>
				<%List<AppBean> topApps= (List<AppBean>)session.getAttribute("topApp");
				for(AppBean app:topApps){%>
				<li><%=app.getName() %></li>
				<%} %>
            </ol>
		</div>
		<div class="span9">
			<ul class="thumbnails">
				<!--display all applications automatically. With the logo and run/buy function.-->
					
<% List<AppBean> apps= (List<AppBean>)session.getAttribute("appOverview");
int i = 0;
for(AppBean app:apps) {
	if(!app.getStatus().equals("Delete")){
	//String imagePath = application.getRealPath("/") + "imageFile\\" + app.getImage();
	String imagePath = "/CloudComputingTeam8/imageFile/"+app.getImage();
	if(i==0){
		out.println("<div class=\"row-fluid\">");
	}
	%>			
				<div class="span3">
					<div class="thumbnail">
						<img alt="100x100" src="<%=imagePath %>" />
						<!--Costomers can see the basic information of the application-->
						<div class="caption">
							<h3>
								<%=app.getName() %>
							</h3>
							<p>
								<%=app.getOwned() %>
							</p>
							<p>
								<%=app.getPrice() %> peanut
							</p>
							<p>
								<!--Costomers can buy/run or see the detail page-->
								<%if(app.getAuthorisation()){
								String path = "runApp?appID="+app.getAppID();%>
								<a class="btn btn-primary" onclick = "openWindows('<%=path%>')">Run</a>
								<% }else{%>
								<a id="buy" class="btn btn-primary" onclick = "message(<%=app.getAppID()%>)">Buy</a>
								<%} %>
								<!--If the account of this user is enough, success-->
								
								<a class="btn" onclick = "openWindows('AppDetail.jsp?appID=<%=app.getAppID()%>')">Detail</a>
							</p>
						</div>
					</div>
				</div>
				
				<%
				if(i==3){
					out.println("</div>");
					i = -1;
				}
				i++;
				}} %>						
			</ul>
		</div>
	</div>
</div>
<script>

</script>
</body>
</html>