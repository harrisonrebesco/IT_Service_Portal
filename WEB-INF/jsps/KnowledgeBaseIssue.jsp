<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp displays the details of a single knowledge base issue

     No changes to the issue may be made from here
 -->

 
<jsp:useBean id="issueManager" class="classPackage.IssueManager" scope="page" />
<jsp:useBean id="user" class="classPackage.User" scope="session" />
<!DOCTYPE HTML>
<html lang="en"> 
    <!-- Receiving selected issue from the request, and updating comments on the issue bean -->
    <%issueManager.setCurrentIssue((int)request.getAttribute("currentIssue"));%>
    <%issueManager.getCurrentIssue().loadComments();%>    
    <head>
        <link rel="stylesheet" type="text/css" href="Style.css" />
        <!-- The page title will be the issue title -->
        <title><%= issueManager.getCurrentIssue().getTitle() %></title>
    </head>
    <body>
        <!-- This page uses the shared site header -->
        <%@include file="SiteHeader.jsp" %>
        <div class="IssueDetailsBlock">
            <!-- The form is only included so that input fields can be used for consistency of formatting -->
            <!-- with the issue page - the form is readonly and does not send information anywhere -->
            <form name="IssueDetails" id="issueEditForm" method="POST" onsubmit="return false"></form>

                <div class="TitleBlock">
                    <label for="kTitle">Issue title:</label>
                    <input type="text" form="issueEditForm" id="kTitle" name="Title" value="<%= issueManager.getCurrentIssue().getTitle() %>" readonly />
                </div>

                <div class="UserIDBlock">
                    <label for="kUserID">Reporter:</label>
                    <input type="text" form="issueEditForm" id="kUserID" name="Reporter" value="<%= issueManager.getCurrentIssue().getUserID() %>" readonly />
                </div>

                <div class="IssueIDBlock">
                    <label for="kIssueID">IssueID:</label>
                    <input type="text" form="issueEditForm" id="kIssueID" name="IssueID" value="<%= issueManager.getCurrentIssue().getIssueID() %>" readonly />
                </div>

                <div class="CategoryBlock">
                    <label for="kCategory">Category:</label>
                    <input type="text" form="issueEditForm" id="kCategory" name="Category" value="<%= issueManager.getCurrentIssue().getCategoryString() %>" readonly />
                </div>

                <div class="TimeOpenedBlock">
                    <label for="kTimeOpened">Time Opened:</label>
                    <input type="text" form="issueEditForm" id="kTimeOpened" name="TimeOpened" value="<%= issueManager.getCurrentIssue().getTimeOpened() %>" readonly />
                </div>

                <div class="TimeClosedBlock">
                    <label for="kTimeClosed">Time Closed:</label>
                    <!-- If the issue is not closed, it will display the reason why, else it will display the time -->
                    <input type="text" form="issueEditForm" id="kTimeClosed" name="TimeClosed" 
                        value="<% if (issueManager.getCurrentIssue().getTimeClosed() == "") { %>Issue not yet resolved<% }
                            else %><%= issueManager.getCurrentIssue().getTimeClosed() %><% } %>" readonly />
                </div>

                <div class="StatusBlock">
                    <label for="kStatus">Status:</label>
                    <input type="text" form="issueEditForm" id="kStatus" name="TimeClosed" value="<%= issueManager.getCurrentIssue().getStatusString() %>" readonly />
                </div>

                <div id="TagsBlock" class="NoPadding">
                    <p>Tags:
                        <!-- Loops through the list of tags, placing each in a formatted display block -->
                    <% for(int j=0; j<issueManager.getCurrentIssue().getTagsCount(); j++) { %>
                        <span class="tag"><%=issueManager.getCurrentIssue().getTag(j)%></span>
                    <% } %></p>
                </div>

                <!-- Not necessary, just a failsafe so that tags data is available if the form is mistakenly read from -->
                <div id="TagsInputBlock" hidden>
                    <input form="issueEditForm" type="text" id="iTags" name="Tags" value="<%= issueManager.getCurrentIssue().getTagsString()%>" />
                </div>

                <div class="NoPadding" id="DescriptionBlock">
                    <label for="kdescription">Description:</label>
                    <div class="NoPadding">
                        <textarea form="issueEditForm" rows="5" cols="60" id="kdescription" name="description" readonly><%= issueManager.getCurrentIssue().getDescription() %></textarea>
                    </div>
               </div>

               <!-- This section displays the resolution comment only, and holds all the same formatting as the other comments -->
               <div id="ResolutionBlock" class="Scrollable">
                    <h2>Resolution Details</h2>
                    <%  for ( int i=0; i < issueManager.getCurrentIssue().getCommentCount(); i++ ) { %>
                        
                        <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %>
                        <div class="CommentDisplayBlock">
                            <div class="ResolutionHighlight">
                            <table class="ListTable">
                                <tr>
                                    <td><b>User ID: </b><%= issueManager.getCurrentIssue().getComment(i).getUserID() %></td>
                                    <td><b>Published On: </b><%= issueManager.getCurrentIssue().getComment(i).getTimePublished() %></td>
                                    <td>
                                        <span class="ResolutionText"><% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %><b>Resolution</b><% } %></span>
                                        <input hidden id="resolution<%=i%>" form="issueEditForm" type="checkbox" name="markAsResolution" value="<%=i%>" 
                                            <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %>checked<% } %> >
                                    </td> 
                                </tr>
                                <tr>
                                    <td colspan="3"><%= issueManager.getCurrentIssue().getComment(i).getContent() %></td>
                                </tr>
                            </table>
                            </div>
                        </div>
                        <% } %>
                    <% } %>
                </div>

                <!-- Loops through and displays all comments -->
                <div id="CommentsListBlock" class="Scrollable">
                    <h2>All Comments</h2>
                    <%  for ( int i=0; i < issueManager.getCurrentIssue().getCommentCount(); i++ ) { %>
                        <div class="CommentDisplayBlock">
                        <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %><div class="ResolutionHighlight"><% } %>
                            <table class="ListTable">
                                <tr>
                                    <td><b>User ID: </b><%= issueManager.getCurrentIssue().getComment(i).getUserID() %></td>
                                    <td><b>Published On: </b><%= issueManager.getCurrentIssue().getComment(i).getTimePublished() %></td>
                                    <td>
                                        <span class="ResolutionText"><% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %><b>Resolution</b><% } %></span>
                                        <input hidden id="resolution<%=i%>" form="issueEditForm" type="checkbox" name="markAsResolution" value="<%=i%>" 
                                            <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %>checked<% } %> >
                                    </td> 
                                </tr>
                                <tr>
                                    <td colspan="3"><%= issueManager.getCurrentIssue().getComment(i).getContent() %></td>
                                </tr>
                            </table>
                        </div>
                        <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %></div><% } %>
                    <% } %>
                </div>

                <!-- Another hidden field that should not be necessary but is there in case the form data is read from -->
                <input form="issueEditForm" id="hiddenKnowledgeBaseStatus" type="radio" name="addToKnowledgeBase" value="yes" checked hidden/>                 
        </div>
   </body>  
</html>