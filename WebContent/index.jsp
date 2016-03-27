<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
					Sheffield Cloudbase<small> All apps</small>
				</h1>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span2">
			<ul class="nav nav-list well">
				<li class="nav-header">Hello, <%=user.getUsername() %></li>
				<li class="active">
					<a href="#">All Apps</a>
				</li>
				<li>
					<a href="myApp.jsp">My Apps</a>
				</li>
				<li>
					<a href="MyAccount.jsp">My Account</a>
				</li>
				<li class="divider">
				</li>
				<li>
					<a href="LogIn.html">Log Out</a>
				</li>
			</ul>
		</div>
		<div class="span9">
			<ul class="thumbnails">
<% List<AppBean> apps= (List<AppBean>)session.getAttribute("appOverview");
for(AppBean app:apps) {%>			
				<li class="span3">
					<div class="thumbnail">
						<img alt="100x100" src="data/Under_my_feet_ok.png" />
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
								<a class="btn btn-primary" href="#">
								<%if(app.getAuthorisation())
									out.println("Run");
									else
										out.println("Buy");%>
								</a>
								<a class="btn" href="#">Detail</a>
							</p>
						</div>
					</div>
				</li>
<%} %>			
				
			</ul>
		</div>
	</div>
</div>
</body>
</html>