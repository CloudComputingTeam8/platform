<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
    <link href="css/stylesmine.css" rel="stylesheet" type="text/css">
<title>My App</title>
</head>
<body>
<%@ page import="model.*,java.util.*"%>
<% UserBean user = (UserBean)session.getAttribute("user");%>
<div class="container-fluid" id="MD">
      <div class="row-fluid">
        <div class="span12">
          <div class="page-header">
            <h1>Sheffield Cloudbase
              <small>My Apps</small>
            </h1>
          </div>
        </div>
      </div>
      <div class="row-fluid">
        <div class="span3">
          <ul class="nav nav-list well">
            <li class="nav-header">Hello, <%=user.getUsername() %></li>
            <li>
              <a href="index.jsp"><span class="glyphicon glyphicon-folder-open"> All Apps</span></a>
            </li>
            <li></li>
            <li class="active">
              <a href="#"><span class="glyphicon glyphicon-folder-open"> My Apps</span></a>
            </li>
            <a href="MyAccount.jsp"><span class="glyphicon glyphicon-home"> My Account</span></a>
            <li class="divider"></li>
            <li>
              <a href="LogIn.html">Log Out</a>
            </li>
          </ul>
        </div>
        <div class="span9">
          <div class="container">
            <div class="row-fluid">
              <div class="span12">
                <div class="btn-group">
                  <a href="uploadApp.html" class="btn btn-default">+ Add</a>
                  <a href="#" class="btn btn-default">- Remove</a>
                  <!--<a href="#" class="btn btn-default">Right</a>-->
                </div>
              </div>
            </div>
            <br>
            <!-- <div class="container"> -->
            <div class="container-fluid" id="LG">
              <div class="row-fluid">
                <div class="span12">
                  <table class="table table-bordered table-hover table-condensed">
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Status</th>
                        <th>PV</th>
                        <th>Price</th>
                        <th>Income</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td><a href="appdetail.html">app0</a></td>
                        <td>Running</td>
                        <td>777</td>
                        <td>1</td>
                        <td>50</td>
                      </tr>
                     <%
                 	List<AppDetailBean> myApps = user.getMyApps();
                     for(AppDetailBean app:myApps){
                    		String statusLabel = "success";
                    		
                    		switch(app.getStatus()){
                    		case "Pause":
                    			statusLabel = "warning";break;
                    		case "Stop":
                    			statusLabel = "error";break;
                    			default:
                    				statusLabel = "success";                    				
                    		}
                    		
                    		/*
                    		String status = app.getStatus();
                    		if(status.equals("Pause"))
                    			statusLabel = "warning";
                    		else if(status.equals("Stop"))
                    			statusLabel = "error";
                    		*/
                      %>
                      <tr class=<%=  statusLabel%>>
                        <td><%= app.getName() %></td>
                        <td><%= app.getStatus() %></td>
                        <td><%= app.getPV() %></td>
                        <td><%= app.getPrice() %></td>
                        <td><%= app.getIncome() %></td>
                      </tr>
                      <%} %>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>