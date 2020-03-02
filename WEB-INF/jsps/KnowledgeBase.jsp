<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp is included in UserDashboard to display a list of knowledge base issues
 -->


<jsp:useBean id="issueManagerBean" class="classPackage.Issue" scope="session" />
<div class="KnowledgeBaseListBlock">
    <div class="NoPadding">
        <h2>Knowledge Base Issues:</h2>
    </div>
    <form name="KnowledgeBaseButtons" method="post" onsubmit="return true">
        <div id="KnowledgeBaseList" class="scrollable">
            <!-- This section loops through all issues and displays those marked as knowledge base issues -->
            <%  for ( int i=0; i < issueManager.getIssueCount(); i++ ) { %>
                <% if (issueManager.getIssue(i).getKnowledgebase() == true) { %>
                    <div class="TableDisplayBlock">
                        <table class="ListTable">
                            <tr>
                                <td class="IssueIDBlock">
                                    <p><b>Issue ID:</b> <%= issueManager.getIssue(i).getIssueID() %></p>
                                </td>
                                <td class="TitleBlock">
                                    <p><b>Title:</b> <%= issueManager.getIssue(i).getTitle() %></p>
                                </td>
                                <td class="StatusBlock">
                                    <p><b>Status:</b> <%= issueManager.getIssue(i).getStatusString() %></p>
                                </td>
                                <td class="ReporterBlock">
                                    <p><b>User ID:</b> <%= issueManager.getIssue(i).getUsername() %></p>
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
                                    <button onclick="submit" name="openKnowledgeBaseButton" value="<%= issueManager.getIssue(i).getIssueID() %>">Open Issue</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                <% } %>
            <% } %>
        </div>
    </form>
</div>