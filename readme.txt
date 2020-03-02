SENG2050 Assignment 3 - Semester 1 2019
c3237487_c3215435_c3293398_FinalProject

Harrison Rebesco - c3237487  
Luke Royle - c3215435
Mitchell Wallace - c3293398

==========ADDITIONAL REQUIREMENTS========================================
For this assignment we completed the following additonal requirements:

11. “Sometimes our issues don’t fit into a single category. 
Can we tag them with keywords as well?” – User (weight 20) 

12. “The above suggestion sounds like a good idea. 
However, sometimes users have typos or ambiguous keywords. 
I think we should be able to change these before the issue becomes a 
Knowledge-Base article” – IT staff. (weight 10) 

==========DATABASE CONNECTION INSTRUCTIONS===============================

putty login:

1. login to jumpgate.newcastle.edu.au (this is the host name)
2. login first with your personal username/pw (pw will be blanked out)
3. login to mysql using "mysql -h teachdb -u c3237487 -p" hit enter 
4. enter pw for mysql pw: "250396" (this will also be blanked out)
5. open the database with "use c3237487_db"

The database is created with /WEB-INF/sql/sql_scripts.sql
context.xml is located in /META-INF/context.xml


==========OPENING THE WEBPAGE============================================

localhost:8080/c3237487_c3215435_c3293398_FinalProject/WebDispatch

This will place you on the login page
You may use login details from the scrips, or one of the following users:

        USERNAME        PASSWORD        ROLE
        staff           staff           staff
        user            user            reporter

==========KNOWN ISSUES===================================================

On the "view issue" page (issue.jsp), where the details of a single issue
is displayed, there is an issue in Microsoft Edge. The details are:
    1. The 'Edit Tags' button is clicked
    2. As intended, the 'Edit Tags' button and segmented tags display
       become hidden
    3. The editable tags field will not become visible
SOLUTION:
    1. Right click near the 'tags' label and click 'Inspect Element'; if
       unavailable, open Developer Tools from the top-right menu of Edge
    2. Locate the element <div id="TagsInputBlock">
    3. Click to select on the input tag within it
    4. It will become visible with no editing of the tag required