<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp is included in UserDashboard to display a list of notifications for the current user
 -->


<div class="NotificationDisplay" <% if (user.getNotificationCount() == 0) { %>hidden<% } %> >
    <div class="NoPadding">
        <h2>Your Notifications:</h2>
    </div>
    <!-- Loads the list of notifications for the current user -->
    <%user.loadNotifications();%>
    <form name="NotificationButtons" method="post" onsubmit="return true">
        <div id="Notifications" class="scrollable">
            <!-- Loops through the list of notifications for the current user -->
            <% for (int i=0; i < user.getNotificationCount(); i++) { %>
                <div class="TableDisplayBlock">
                    <table class="ListTable">
                        <tr>
                            <td class="IssueIDBlock">
                                <p><b>IssueID:</b> <%= user.getNotification(i).getIssueID() %></p>
                            </td>
                            <td class="TitleBlock">
                                <p><b>Title:</b> <%= user.getNotification(i).getIssueTitle() %></p>
                            </td>
                            <td class="StatusBlock">
                                <p><b>Status:</b> <%= user.getNotification(i).getIssueStatus() %></p>
                            </td>
                            <td class="OpenIssueBlock">
                                <button onclick="submit" name="openNotifIssueButton" value="<%= user.getNotification(i).getIssueID() %>,<%=user.getNotification(i).getNotificationID()%>">Open Issue</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"><%= user.getNotification(i).getContent() %></td>
                        </tr>
                    </table>
                </div>
            <% } %>
        </div>
    </form>
</div>