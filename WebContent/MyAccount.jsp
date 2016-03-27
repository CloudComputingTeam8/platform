<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
    <link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
  	<title>My Account</title>
</head>
<body>
<%@ page import="model.*,java.util.*"%>
<% UserBean user = (UserBean)session.getAttribute("user"); 
	List<AppDetailBean> myApps = user.getMyApps();%>
<div class="container-fluid" id="MD">
      <div class="row-fluid">
        <div class="span12">
          <div class="page-header">
            <h1>Team 8
              <small>My Account</small>
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
            <li>
              <a href="myApp.jsp"><span class="glyphicon glyphicon-folder-open"> My Apps</span></a>
            </li>
            <li class="active">
              <a href="#"><span class="glyphicon glyphicon-home"> My Account</span></a>
            </li>
            <li class="divider"></li>
            <li>
              <a href="LogIn.html">Log Out</a>
            </li>
          </ul>
        </div>
        <div class="span9">
          <div class="row-fluid">
            <div class="span6">
              <ul>
                <h3>My Apps</h3>
                <% int totalIncome = 0;
                for(AppDetailBean app:user.getMyApps()){
                	totalIncome += app.getIncome();%>
                <li><%= app.getName() %></li>
                <%} %>
              </ul>
              <ul>
                <h3>Purchased Apps</h3>
                <% for(AppBean app:user.getPurchasedApps()){%>
                <li><%= app.getName() %></li>
                <%} %>
              </ul>
            </div>
            <div class="span6">
              <p>
                Income<br>
                <strong>Total:<%=totalIncome %> credits</strong>
                <br>
                <img alt="200x200" src="data/Appincome.png">
                <br>
              </p>
              Storage <p>
                <strong>Left: 1G</strong>
              </p><br>
              <img alt="200x200" src="data/storage.png">
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>