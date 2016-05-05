<html><head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/signup.js"></script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="css/styles0.css" rel="stylesheet" type="text/css">
  </head><body>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-3">
         
            <div class="tabbable" id="tabs-724690">
             ${error}
            <!--  there is a nav tab bar that can choose log in or sign up -->
              <ul class="nav nav-tabs">
              
                <li class="active">
                  <a href="#panel-724981" data-toggle="tab">Log In</a>
                </li>
                <li>
                  <a href="#panel-477991" data-toggle="tab">Sign Up</a>
                </li>
              </ul>
              <div class="tab-content">
                <div class="tab-pane active" id="panel-724981">
               
                <br>
                  <!--the first tab is log in -->
                  <div id="login" class="layer">
                    <div>
                      <form action="login" method="post">
                        <br>User name:
                        <input type="text" name="username">
                        <br>
                        <br>Password:
                        <input type="password" name="password" >
                        <label id="loginpass" class="hide"></label>
                        
                        <br><br>
                        <input type="submit" value="Log in">
                      </form>
                       
                    </div>
                  </div>
                </div>
                <!--The second tab is sign up, there are some limited-->
                <div class="tab-pane" id="panel-477991">
                  <div id="signup" class="layer">
                    <div>
                       <form id="personRegForm" class="form" action="signup" method="post">
                        <br>User name:
                        <!-- <input type="text" name="userName"> -->
                         <input type="text" id="username" name="username" class="text">
                         <label id="username_msg" class="hide"></label>
                        <br>
                        <br>Password:
                        
                        <input type="password" id="password" name="password" class="text">
 
                        <label id="pwd_msg" class="hide"></label>
                        <br>
                        <br>Confirm:
                        <!-- password and username cannot empty, two times should same -->
                        <input type="password" id="pwdRepeat" name="pwdRepeat" class="text">
 
                        <label id="pwdRepeat_msg" class="hide"></label>
                        <br>
                        <input type="checkbox" name="userAuth" id="checkbox_id0" value="2" />A Developer<br>
                        <p id="description">If you want to upload your apps, you can choose be a developer, If not your account can only buy apps.</p>
                        
                        <br><br>
                        <input id="signupSub" type="submit" value="Sign up">
                      </form>
                      
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-9">
            <h1 class="text-center" contenteditable="true">Sheffield Cloudbase</h1>
            <h3 class="text-center">
              <br><br><br><br>
              <br>You can choose applications to try.
              <br>You can install and manage your own apps on our platform.</h3>
          </div>
        </div>
      </div>
    </div>
  

</body></html>