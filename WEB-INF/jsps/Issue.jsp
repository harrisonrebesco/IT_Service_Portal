<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp displays the details of a single issue

     Users can add comments and respond to notifications here

     Staff members can change status, edit tags, make comments,
     mark comments as resolution details, and add to knowledge base
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
            <form name="IssueDetails" id="issueEditForm" method="POST"></form>

                <div class="TitleBlock">
                    <label for="issueTitle">Issue title:</label>
                    <input type="text" form="issueEditForm" id="issueTitle" name="Title" value="<%= issueManager.getCurrentIssue().getTitle() %>" readonly />
                </div>

                <div class="UserIDBlock">
                    <label for="issueUserID">Reporter:</label>
                    <input type="text" form="issueEditForm" id="issueUserID" name="Reporter" value="<%= issueManager.getCurrentIssue().getUsername() %>" readonly />
                </div>

                <div class="IssueIDBlock">
                    <label for="issueIssueID">IssueID:</label>
                    <input type="text" form="issueEditForm" id="issueIssueID" name="IssueID" value="<%= issueManager.getCurrentIssue().getIssueID() %>" readonly />
                </div>

                <div class="CategoryBlock">
                    <label for="issueCategory">Category:</label>
                    <input type="text" form="issueEditForm" id="issueCategory" name="Category" value="<%= issueManager.getCurrentIssue().getCategoryString() %>" readonly />
                </div>

                <div class="TimeOpenedBlock">
                    <label for="issueTimeOpened">Time Opened:</label>
                    <input type="text" form="issueEditForm" id="issueTimeOpened" name="TimeOpened" value="<%= issueManager.getCurrentIssue().getTimeOpened() %>" readonly />
                </div>

                <!-- If the issue is not closed, like most will me, the time closed field is hidden -->
                <div class="TimeClosedBlock" <% if (issueManager.getCurrentIssue().getTimeClosed() == null) { %>hidden<% } %>>
                    <label for="iTimeClosed">Time Closed:</label>
                    <input type="text" form="issueEditForm" id="iTimeClosed" name="TimeClosed" value="<%= issueManager.getCurrentIssue().getTimeClosed() %>" readonly />
                </div>

                <!-- Status dropdown -->
                <div class="StatusBlock">                    
                    <% if (user.getUserType()) { %> 
                        <label for="issueStatus">Status:</label>
                        <select form="issueEditForm" id="issueStatus" name="Status">
                            <!-- The getAvailalbeStatuses method returns the value, disabled, and selected attributes as applicable -->
                            <!-- A value of 100 means the status has not been changed by the user and should not be changed on submission -->
                            <option <%= issueManager.getCurrentIssue().getAvailableStatuses()[0] %> >New</option>
                            <option <%= issueManager.getCurrentIssue().getAvailableStatuses()[1] %> >In Progress</option>
                            <option <%= issueManager.getCurrentIssue().getAvailableStatuses()[2] %> >Completed</option>
                            <option <%= issueManager.getCurrentIssue().getAvailableStatuses()[3] %> >Resolved</option>
                            <option <%= issueManager.getCurrentIssue().getAvailableStatuses()[4] %> >Waiting on 3rd party</option>
                            <option <%= issueManager.getCurrentIssue().getAvailableStatuses()[5] %> >Waiting on reporter</option>
                        </select>
                    <% } else { %> 
                        <!-- Reporters may only view the current status -->
                        <label for="issueStatusReadonly">Status:</label>
                        <input id="issueStatusReadonly" name="UpdatedStatus" value="<%=issueManager.getCurrentIssue().getStatusString()%>" readonly />
                    <% } %>
                </div>

                <!-- This is the formatted display block for the issue's tags -->
                <div id="TagsBlock" class="NoPadding">
                    <p>Tags:
                        <!-- Loops through the list of tags, placing each in a formatted display block -->
                    <% for(int j=0; j<issueManager.getCurrentIssue().getTagsCount(); j++) { %>
                        <span class="tag"><%=issueManager.getCurrentIssue().getTag(j)%></span>
                    <% } %></p>
                </div>

                <!-- This is the plaintext edit block for the issue's tags, made visible when Edit Tags is clicked -->
                <div id="TagsInputBlock" hidden>
                    <label for="iTags">Tags:</label>
                    <input form="issueEditForm" type="text" id="iTags" name="Tags" pattern="(([^&lt;&gt;{}]){1,999}" value="<%= issueManager.getCurrentIssue().getTagsString()%>" />
                </div>

                <!-- This is the Edit Tags button, only available for stuff, which enables editing of tags -->
                <!-- It will hide itself and Tagsblock, while making TagsInputBlock visible -->
                <div id="EditTagsBlock" <% if (!(user.getUserType())) { %>hidden<% } %> >
                    <button id="editTags" value="editTags" onclick="return enableTagEdit()" >Edit Tags</button>
                </div>

                <div class="NoPadding" id="DescriptionBlock">
                    <label for="idescription">Description:</label>
                    <div class="NoPadding">
                        <textarea form="issueEditForm" rows="5" cols="60" id="idescription" name="description" readonly><%= issueManager.getCurrentIssue().getDescription() %></textarea>
                    </div>
                </div>

                <!-- This is the scrollable list of comments -->
                <div id="CommentsListBlock" class="Scrollable">
                    <h2>Comments</h2>
                    <!-- Gets the number of comments to loop through -->
                    <%  for ( int i=0; i < issueManager.getCurrentIssue().getCommentCount(); i++ ) { %>
                        <div class="CommentDisplayBlock">
                        <!-- Adds an additional tag for highlighting if the comment is marked as being the resolution details -->
                        <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %><div class="ResolutionHighlight"><% } %>
                            <table class="ListTable">
                                <tr>
                                    <td><b>Username: </b><%= issueManager.getCurrentIssue().getComment(i).getUsername() %></td>
                                    <td><b>Published On: </b><%= issueManager.getCurrentIssue().getComment(i).getTimePublished() %></td>
                                    <!-- If the user is staff, they may select a comment to be marked as the resolution details -->
                                    <% if (user.getUserType()) { %>
                                        <td>
                                            <label for="resolution<%=i%>"><b>Resolution comment: </b></label>
                                            <input id="resolution<%=i%>" form="issueEditForm" type="radio" name="markAsResolution" value="<%=issueManager.getCurrentIssue().getComment(i).getCommentID()%>" 
                                                <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %>checked<% } %> >
                                        </td>
                                    <!-- Reporters will see a field designating a comment as resolution details if it is marked as such -->
                                    <% } else { %>
                                        <td>
                                            <span class="ResolutionText"><% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %><b>Resolution</b><% } %></span>
                                            <input hidden id="resolution<%=i%>" form="issueEditForm" type="radio" name="markAsResolution" value="<%=issueManager.getCurrentIssue().getComment(i).getCommentID()%>" 
                                                <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %>checked<% } %> >
                                        </td> 
                                    <% } %>
                                </tr>
                                <tr>
                                    <td colspan="3"><%= issueManager.getCurrentIssue().getComment(i).getContent() %></td>
                                </tr>
                            </table>
                        </div>
                        <!-- Closing the div tag conditionally opened to highlight the resolution details -->
                        <% if (issueManager.getCurrentIssue().getComment(i).getResolution()) { %></div><% } %>
                    <% } %>
                </div>

                <!-- Comments are submitted when 'save changes' is clicked -->
                <div id="AddCommentBlock">
                    <div class="NoPadding">Add a comment:</div>
                    <div class="NoPadding">
                        <input form="issueEditForm" id="newComment" type="text" name="newComment" placeholder="Type your comment in here" pattern="([^&lt;&gt;{}]){1,999}" autofocus />
                    </div>                   
                </div>
        
                <!-- A collection of conditional buttons -->
                <div class="BottomButtons">
                    <!-- Staff may mark an issue as knowledge base only if it is completed or resolved -->
                    <!-- Otherwise a hidden field will store and submit this value to make the back end easier -->
                    <div id="KnowledgeMarkBlock" class="NoPadding" <% if (!(user.getUserType() && ( issueManager.getCurrentIssue().getStatus() == 3 || 
                            issueManager.getCurrentIssue().getStatus() == 4))) { %> hidden <% } %> >
                        <p>Knowledge Base:  </p>
                        <label for="knowledgeYesButton">Yes</label>
                        <input form="issueEditForm" id="knowledgeYesButton" type="radio" name="addToKnowledgeBase" value="yes" 
                            <% if ( issueManager.getCurrentIssue().getKnowledgebase() ) { %>checked <% } %> />
                        <label for="knowledgeNoButton">No</label>
                        <input form="issueEditForm" id="knowledgeNoButton" type="radio" name="addToKnowledgeBase" value="no" 
                        <% if ( !(issueManager.getCurrentIssue().getKnowledgebase() )) { %>checked <% } %> />
                    </div>

                    <!-- Response options for users to respond to notifications with -->
                    <% if ( !user.getUserType() && issueManager.getCurrentIssue().getStatus() == 3) { %>
                        <div id="AcceptRejectBlock" class="NoPadding">
                            <p>Staff have marked your issue as completed, please select an option:</p>
                            <label for="acceptSolutionButton">Accept Solution</label>
                            <input form="issueEditForm" type="radio" id="acceptSolutionButton" name="Status" value="9">
                            <label for="rejectSolutionButton">Reject Solution</label>
                            <input form="issueEditForm" type="radio" id="rejectSolutionButton" name="Status" value="7">
                        </div>
                    <% } else if ( !user.getUserType() && issueManager.getCurrentIssue().getStatus() == 6) { %>
                        <div id="JobDoneBlock" class="NoPadding">
                            <label for="doneCheckbox">Check this box if you are ready for IT to continue working on your issue:</label>
                            <input form="issueEditForm" type="checkbox" id="doneCheckbox"  name="Status" value="8">
                        </div>
                    <% } %>
      
                    <!-- The global submit button -->
                    <div><button form="issueEditForm" onclick="submit" name="saveChanges" value="saveChanges">Save Changes</button></div>
                </div>
        </div>
        <script src="Scripts.js"></script>
   </body>
</html>