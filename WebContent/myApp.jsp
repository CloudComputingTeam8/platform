<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
              <!--This is the page to show the developers' applications-->
              <small>My Apps</small>
            </h1>
          </div>
        </div>
      </div>
      <div class="row-fluid">
        <!--The left nav bar-->
        <div class="span3">
          <ul class="nav nav-list well">
            <li class="nav-header">Hello, <%=user.getUsername() %></li>
            <li>
              <a href="index.jsp"><span> All Apps</span></a>
            </li>
            <li></li>
            <li class="active">
              <a href="#"><span> My Apps</span></a>
            </li>
            <a href="MyAccount.jsp"><span> My Account</span></a>
            <li class="divider"></li>
            <li>
              <a href="/CloudComputingTeam8/logout">Log Out</a>
            </li>
          </ul>
        </div>
        <div class="span9">
          <div class="container">
            <div class="row-fluid">
              <div class="span12">
                <div class="btn-group">
                <form name="input" action="deleteApp" method="post">
                  <!--go to a upload page to uploadd a new app OR Delete apps-->
                  <a href="upload.html" class="btn btn-default">+ Add</a>
                  
                  <input type = "submit" value="- Remove" class="btn btn-default"/>
                  <!--<a href="#" class="btn btn-default">Right</a>-->
                </div>
              </div>
            </div>
            <br>
            <!-- <div class="container"> -->
            <div class="container-fluid" id="LG">
              <div class="row-fluid">
                <div class="span12">
                  <!--The table show some basic information of applications-->
                  <table class="table table-bordered table-hover table-condensed">
                    <thead>
                      <tr>
                      <th>#</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>PV</th>
                        <th>Price</th>
                        <th>Income</th>
                      </tr>
                    </thead>
                    <tbody>
                    
                     <%
                 	List<AppDetailBean> myApps = user.getMyApps();
                     for(AppDetailBean app:myApps){
                    		String statusLabel = "success";
                    		
                    		switch(app.getStatus()){
                    		case "Delete":
                    			statusLabel = "error";break;
                    			default:
                    				statusLabel = "success";                    				
                    		}
                      %>
                      <tr class=<%=  statusLabel%>>
                      <td><input type="checkbox" name="delete" id=<%="checkbox_id"+app.getAppID() %> value=<%=app.getAppID() %>></td>
                      
                        <td>
                        <%if(!app.getStatus().equals("Delete")){ %>
                        <a href="/CloudComputingTeam8/AppDetail.jsp?appID=<%=app.getAppID()%>" target="_blank"><%=app.getName() %></a>
                        <%} else{ out.print(app.getName());} %></td>
                        
                        <td><%= app.getStatus() %></td>
                        <td><%= app.getPV() %></td>
                        <td><%= app.getPrice() %></td>
                        <td><%= app.getIncome() %></td>
                      </tr>
                      <%} %>
                      </form>
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