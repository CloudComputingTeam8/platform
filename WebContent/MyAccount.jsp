<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="js/upload.js"></script>
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
            <h1>Sheffield Cloudbase
              <!--This is the home page of every users-->
              <small>My Account</small>
            </h1>
          </div>
        </div>
      </div>
      <div class="row-fluid">
        <div class="span3">
          <!--The left nav bar-->
          <ul class="nav nav-list well">
            <li class="nav-header">Hello, <%=user.getUsername() %></li>
            <li>
              <a href="index.jsp"> All Apps</a>
            </li>
            <%if(user.getAuthorisation()==2){ %>
				<li>
					<a href="myApp.jsp">My App</a>
				</li>
			<%} %>
            <li>
              <a href="myAccount.jsp"> My Account</a>
            </li>

            <li class="divider"></li>
            <li>
              <a href="/CloudComputingTeam8/logout">Log Out</a>
            </li>
          </ul>
        </div>
        <div class="span9">
          <div class="row-fluid">
            <div class="span6">
              <!--Users can check his account information-->
              <%if(user.getAuthorisation()==2){ %>
              <ul>
                <h3>My Apps</h3>
                <!--The income of the account in total-->
                <% int totalIncome = 0;
                for(AppDetailBean app:user.getMyApps()){
                	totalIncome += app.getIncome();%>
                <li><%= app.getName() %><%if(app.getStatus().equals("Delete")){out.print("(Deleted)");} %></li>
                <%} %>
              </ul>
              <%} %>
              <ul>
                <h3>Purchased Apps</h3>
                <!--Show all apps that the users buy-->
                <% for(AppBean app:user.getPurchasedApps()){%>
                <li><%= app.getName() %><%if(app.getStatus().equals("Delete")){out.print("(Deleted)");} %></li>
                <%} %>
              </ul>
              <ul>
                <!--Users can top up there peanuts here-->
                <h3>Top Up</h3>
                <div>
                      <form action="topup" method="post">
                        <br>
                        <input name="amount" id="topup" class="form-control" placeholder="100" onkeydown="onlyNum();" style="ime-mode:Disabled">Peanuts
                        <br>
                        <br>Please enter Password:
                        <input type="password" name="password" >
                        <label id="loginpass" class="hide"></label>                        
                        <br>${error}<br>
                        <input type="submit" value="Top up">
                      </form>
                      
                    </div>
              </ul>
            </div>
            <div class="span6">
                <h4>Acount</h4>
                    <div class="row-fluid">
                        <div class="span3">
                            <p>Total:</p>
                        </div>
                        <div class="span3">
                            <p><%=user.getCredit() %></p>
                        </div>
                    </div>
                    <h4>Transform detail</h4>
                    <div id="detaildisplay">
                    

                                            
                    </div>
                    
                        <ul class="pager">
                            <li>
                            <a id="lastten">←  Prev</a>
                        </li><li>
                            <a id="nextten">Next  →</a>
                        </li>
                        </ul>
                        <script>
                        var mycars=new Array();                            
                        <%
                        String content = "";                           
                        for(int i=0; i< user.getTransactions().size();i++){
                        	TransactionBean t = user.getTransactions().get(i);
                        	content = String.format("%s | %15s - %3s | P %3d | P %5d ", 
                    				t.getDate(),t.getAppName(),t.getDescription(),t.getAmount(),t.getTotal());
                        %>
                    mycars[<%=i%>]="<%=content%>"
                    <%}%>
                    var max=<%=user.getTransactions().size()%>;
                    $(document).ready(transaction(mycars,max));                        
                        </script>
                        
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>