<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp is what users will first see when opening the website, enabling them to log in
 -->


<jsp:useBean id="currSession" class="classPackage.SessionTracker" scope="session"/>
<!DOCTYPE HTML>
<html lang="en"> 
    <head> 
        <link rel="stylesheet" type="text/css" href="Style.css" />
        <title>Login</title>
    </head>
    <body>
        <div class="SiteHeader">
            <div class="LogoBlock">
                <img class="logo" src="images/BasicHelpDeskLogo.png" alt="BasicHelpDesk" />
            </div>
        </div>
        <h2>Login</h2>
        <!-- After a failed login, the servlet will flag the page to display feedback indicating as such -->
        <% if ( currSession.getState() == classPackage.SessionTracker.State.FAILEDLOG ) { %>
            <script>
                alert("Incorrect username or password");
            </script>
        <!-- After a user logs out, a confirmation dialog may be displayed here -->
        <% }else if ( currSession.getState() == classPackage.SessionTracker.State.LOGGEDOUT ) { %>
            <script>
                alert("You have successfully logged out");
            </script>
        <% } %> 
        <div class="LoginBlock">
            <form id="LoginForm" method="post" onsubmit="classes/WebDispatch" name="loginForm">
                <div>
                    <!-- HTML5 validation checks that no angle brackets or curly brackets are entered to compromise the page -->
                    <input type="text" name="username" placeholder="Username" pattern="([^&lt;&gt;{}]){1,50}"  autofocus required/>
                </div>
                <div>
                    <input type="password" name="password" placeholder="Password" pattern="([^&lt;&gt;{}]){1,20}"  required/>
                </div>
                <div>
                    <button type="submit" id="logIn" name="loginButton" value="logIn">Log In</button>
                </div>
            </form>
        </div>
    </body>
</html>