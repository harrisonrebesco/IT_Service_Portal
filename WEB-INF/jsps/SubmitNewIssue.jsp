<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     From this jsp, reporters may submit a new issue for staff to help them with

     The reporter's name and ID are taken from the user logged into the session,
     and are stored in a readonly field to be read with the rest of the form
 -->


<jsp:useBean id="user" class="classPackage.User" scope="session" />
<!DOCTYPE HTML>
<html lang="en">  
    <head>
        <link rel="stylesheet" type="text/css" href="Style.css" /> 
        <title>Submit New Issue</title>
    </head>
    <body>
        <!-- This page uses the shared site header -->
        <%@include file="SiteHeader.jsp" %>
        <h2>SubmitNewIssue</h2> 
        <div class="IssueDetailsBlock">
            <form id="IssueSubmitForm" method="post" onsubmit="classes/WebDispatch">
                <div class="TitleBlock">
                    <label for="SubmitTitle">Issue title:</label>
                    <input type="text" id="SubmitTitle" name="Title" placeholder="What are you having an issue with?" pattern="([^&lt;&gt;{}]){1,99}" autofocus required />
                </div>
                <div class="ReporterBlock">
                    <label for="SubmitUserID">User ID:</label>
                    <input type="text" id="SubmitUserID" name="ReporterID" value="<%= user.getUserID() %>" readonly />
                </div>
                <div class="CategoryBlock">
                    <label for="SubmitCategory">Category:</label>
                    <select id="SubmitCategory" name="Category" required>
                        <option value="" selected disabled>Choose a category</option>
                        <option value="Network">Network</option>
                        <option value="Software">Software</option>
                        <option value="Hardware">Hardware</option>
                        <option value="Email">Email</option>
                        <option value="Account">Account</option>
                    </select>
                </div>
                <div class="TagsBlock">
                    <!-- Tags are submitted as a plaintext string, and the server converts to an array to be displayed in the proper format -->
                    <label for="SubmitTags">Tags:</label>
                    <input id="SubmitTags" type="text" name="Tags" placeholder="Separate tags with a comma" pattern="([^&lt;&gt;{}]){1,999}" />
                </div>
                <div class="DescriptionBlock">
                    <label for="SubmitDescription">Description:</label>
                    <input id="SubmitDescription" type="text" name="Description" placeholder="Provide details of your issue here" pattern="([^&lt;&gt;{}]){1,999}" class="long" required />
                </div>
                <div>
                    <button onclick="submit" name="submitIssue" value="submitIssue">Submit Issue</button>
                </div>
            </form>
        </div>
        <script src="Script.js"></script>
    </body>
</html>