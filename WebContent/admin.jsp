<html><head>
        <meta charset="utf-8">
        <script type="text/javascript" src="js/upload.js"></script>
        <script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
        <script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
        <link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
        <script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
        <link href="css/stylesmine.css" rel="stylesheet" type="text/css">
    </head><body>
    <%@ page import="model.*,java.util.*"%>
    <% UserBean user = (UserBean)session.getAttribute("user");
    List<AppBean> apps= (List<AppBean>)session.getAttribute("appOverview");%>
        <div class="container-fluid" id="MD">
            <div class="row-fluid">
                <div class="span12">
                    <h1>Hello, Admin!</h1>
                    <a href="/CloudComputingTeam8/logout">Log Out</a>
                <hr>
                    
                </div>
            </div>
            <div class="row-fluid">
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
                <div class="span6">
                    <h4>Apps</h4>
                    <form name="input" action="deleteApp" method="post">
                    <input type = "submit" value="DELETE" class="btn btn-primary"/>
                    <div id="applist">
                    <%for(AppBean app:apps){ 
                    	if(!app.getStatus().equals("Delete")){%>
                    	<input type="checkbox" name="delete" id="checkbox_id0" value=<%=app.getAppID() %>>
                    	<%=app.getName() %>
                        <br>
                    <%}} %>
                        </div>
                </div>
            </div>
        </div>
    

</body></html>