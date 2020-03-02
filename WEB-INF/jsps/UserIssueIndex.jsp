<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp is included in UserDashboard to display a list of issues related to the current user
 -->


<div class="IssueListBlock">
    <div class="NoPadding">
        <h2>Your Issues:</h2>
    </div>
    <form name="IssueListButtons" method="post" onsubmit="return true">
        <div id="IssueList" class="scrollable">
            <!-- This loops through all issues and displays those related to the current user -->
            <% for ( int i=0; i < issueManager.getIssueCount(); i++ ) { 
                if (user.getUserType() == true || user.getUserID() == issueManager.getIssue(i).getUserID()) { %>
                    <div class="TableDisplayBlock">

                        <table class="ListTable">
                            <tr>
                                <td class="issueIDBlock">
                                    <p><b>Issue ID:</b> <%= issueManager.getIssue(i).getIssueID() %></p>
                                </td>
                                <td class="TitleBlock">
                                    <p><b>Title:</b> <%= issueManager.getIssue(i).getTitle() %></p>
                                </td>
                                <td class="StatusBlock">
                                    <p><b>Status:</b> <%= issueManager.getIssue(i).getStatusString() %></p>
                                </td>
                                <td class="ReporterBlock">
                                    <p><b>Username:</b> <%= issueManager.getIssue(i).getUsername() %></p>
                                </td>
                            </tr>
                            <tr>
                                <td class="TimeOpenedBlock">
                                    <p><b>Time Opened:</b> <%= issueManager.getIssue(i).getTimeOpened() %></p>
                                </td>
                                <td class="TagsBlock">
                                    <p><b>Tags:</b> <% for(int j=0; j<issueManager.getIssue(i).getTagsCount(); j++) {%>
                                    <span class="tag"><%=issueManager.getIssue(i).getTag(j)%></span><%}%></p>
                                </td>
                                <td class="CategoryBlock">
                                    <p><b>Category:</b> <%= issueManager.getIssue(i).getCategoryString() %></p>
                                </td>
                                <td class="OpenIssueBlock">
                                    <button onclick="submit" name="openIssueButton" value="<%= issueManager.getIssue(i).getIssueID() %>">Open Issue</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                <% }
            } %>
        </div>
    </form>

    <!-- Staff users are able to sort issues by status to help manage their significantly longer list -->
	<%if(user.getUserType() == true){%>
        <form id="sortButton" method="post" onsubmit="classes/WebDispatch">
            <button class="autowidth" onclick="submit" name="sortIssues" value="sortIssues">Sort issues by status</button>
        </form>
	<%}%>
</div>