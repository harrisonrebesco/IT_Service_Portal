/*
 Class: WebDispatch
 Purpose: Checks what was sent via get/post and forwards user to the correct jsp. 
 Author: Luke Royle	C3215435
		 Harry Rebesco C3237487
		 Mitchell Wallace C3293398
 Time: 05/19
*/

import classPackage.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.sql.*;

//note, maybe lognbutton should post to avoid having the password in the url. move to post???

@WebServlet(urlPatterns = {"/WebDispatch"})
public class WebDispatch extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		HttpSession session = request.getSession();	// Initialise session
		PrintWriter out = response.getWriter();	// Initialise PrintWriter
		RequestDispatcher d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/loginError.jsp"); //Initialises RD as the Login page so that if it isnt set to another page it will forward to error

		SessionTracker currentSessionTracker = (SessionTracker)session.getAttribute("currSession");	// Attempts to grab the sessiontracker off the session (null if doesnt exist)
		User currentUser = (User)session.getAttribute("user");	// Attempts to grab the user off the session (null if doesnt exist)

		
		if(currentSessionTracker==null){
			currentSessionTracker = new SessionTracker();	// Make a new sessionTracker
			session.setAttribute("currSession", currentSessionTracker);		// Set the sessiontracker on the session to the tracker just created
		}

		if (currentUser == null) {			// If there is no user on the session
			d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Login.jsp");	
		}
		
		d.forward(request,response);	// Forward to whichever page has been set as d
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		HttpSession session=request.getSession();	// Initialise the session
		SessionTracker currentSessionTracker = (SessionTracker)session.getAttribute("currSession");	// Attempts to grab the sessiontracker off the session (null if doesnt exist)
		User currentUser = (User)session.getAttribute("user");	// Attempts to grab the user off the session (null if doesnt exist)
		IssueManager issueMan=(IssueManager)session.getAttribute("issueManager");	
		String userN=null;	// A string to store the user name entered from the form
		String password = null; // A string to store the password entered from the form
		String logError=request.getParameter("logErrorButton");	//stores the result of the return button (from error page)
		String logResult=request.getParameter("loginButton");	// Stores the result of the login button 
		RequestDispatcher d; // Initialise request dispatcher
		
		// Stores the string values (null if not sent) of the parameters recieved
		String newIssueButton=request.getParameter("submitNewIssue");
		String retButtonResult = request.getParameter("retButton");
		String openIssueButton=request.getParameter("openIssueButton");
		String openNotifIssueButton=request.getParameter("openNotifIssueButton");
		String saveChangesButton=request.getParameter("saveChanges");
		String submitIssueButton=request.getParameter("submitIssue");
		String newSession=request.getParameter("newSession");
		String sortIssues=request.getParameter("sortIssues");
		String openKnowledgeBaseButton=request.getParameter("openKnowledgeBaseButton");
	
		
		if(issueMan==null){	//Currently not necessary as the issuemanager will only be null before the user even makes it to the dashboard since it is put on the session when the bean is used but good practise to check for future 
			issueMan=new IssueManager();	// initialise as new issue manager
			session.setAttribute("issueManager",issueMan);	// set on session
		}else{
			issueMan.loadAllIssues();	// if it already exists, update the issues on the session (overkill since this occurs on all pages that require them but left in incase a new page needs them and doesnt refresh)
		}

		if(currentSessionTracker.getLoggedIn()){	// if loggedin has been set to true, set error page to regular error page, otherwise keep it as loggedin error
			d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/errorPage.jsp");	//set default dispatch to the errorpage so that if it isnt set to another page it will forward to error
		}else{
			d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/loginError.jsp"); //Initialises RD as the login error page 
		}
		
		if(newSession!=null){	// iff newsession has been selected after logging out, return to login page
			if(newSession.equals("newSess")){
				d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Login.jsp");
			}
		}

		// Handles the case where a user has logged out on one page and tries to do something on another page in the same session. Takes them to the loggedout page
		if (currentUser == null&&currentSessionTracker.getState()==classPackage.SessionTracker.State.LOGGEDOUT&&newSession==null&&logResult==null){
			d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/loggedOut.jsp");
			currentSessionTracker.setLoggedIn(false);
			currentSessionTracker.setState(SessionTracker.State.LOGGINGIN);
		}else 
			if (currentUser == null) {			// If there is no user on the session
			userN = request.getParameter("username");	// Get the user from the posted param
			password = request.getParameter("password");	// Get the password from the posted param
			if(userN!=null && password!=null){	// if the entered u/p arent null
				List<User> allUsers= new LinkedList<>();	// initialise a list of all users
				allUsers = User.getAllUsers();	// fill the list with all the users from the sql
				for(User u:allUsers){	// for all users in the list
					if (u.getUsername().equals(userN)&&u.getPassword().equals(password)){		// check if the username and password match the given u/p
						currentUser=u;	// if a match is found set the current user to the user that matched
					}
				}
				session.setAttribute("user", currentUser);		// Set the user on the session
			}
		}
		
		if(logResult!=null && currentUser!=null){	// if user has pressed log in and the current suer has successfuly been found (i.e the user and pass were correct for one on the sql)
			if (logResult.equals("logIn")){	// Unnecessary for now- left incase a button option other than login can be clicked
				d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");	//Set dispatcher to dashboard page
				currentSessionTracker.setState(SessionTracker.State.DASHBOARD);	// Set the state of the sessiontracker to dashboard
				currentSessionTracker.setLoggedIn(true);	//set loggedin to true
			}
		}else if(logResult!=null&&currentUser==null){	//else if the login button was pressed but no user was loaded (i.e incorrect details)
			if(currentSessionTracker.getState()!=classPackage.SessionTracker.State.LOGGINGIN){	// if the state is logging in (handles the case where they hanvt just attempted to log in but arrive here by opening a new tab or smoething)
				currentSessionTracker.setState(SessionTracker.State.FAILEDLOG);	// set the status to failedlog for alerting purposes
			}
			d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Login.jsp");		// sets d back to login page
		}
		
		if (currentUser!=null){// ensures regular operations cant be performed witout a user-mostly for handling multiple pages in same session when one logs out
			if (newIssueButton!=null){	// if new issue has been selected
				if(newIssueButton.equals("SubmitNewIssue")){			// this is redundant but allows for easier expansion of buttons with same name
					d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/SubmitNewIssue.jsp");
					currentSessionTracker.setDoOnce(false); //resets the boolean that stops users from resubmitting issues by refreshing
					currentSessionTracker.setState(SessionTracker.State.CREATINGISSUE);
				}
			}
			
			if (saveChangesButton!=null){
				if(saveChangesButton.equals("saveChanges")){			// this is redundant but allows for easier expansion of buttons with same name
					if(currentSessionTracker.getState() != classPackage.SessionTracker.State.UPDATEDISSUE)	// if the state isnt alread updatedissue (stops issuechanges from being reapplied on refresh)
					{
						int issueID=Integer.parseInt(request.getParameter("IssueID"));
						String statusString = request.getParameter("Status");
						String tags = request.getParameter("Tags");
						String knowledgebase = request.getParameter("addToKnowledgeBase");
						String comment = request.getParameter("newComment");
						String resDetails = request.getParameter("markAsResolution");
						
						//update state
						currentSessionTracker.setState(SessionTracker.State.UPDATEDISSUE);
						
						//check status exists 
						if (statusString!=null){
							int status, tempStatus; 
							tempStatus = Integer.parseInt(request.getParameter("Status")); //parse to int 
							//convert to corresponding 'real' status (7, 8, 9 are unique states that dont actually exist - used for notification message)	
							if (tempStatus == 7 || tempStatus == 8)
								status = 2;
							else if (tempStatus == 9)
								status = 4;	
							else 
								status = tempStatus;

							
							//checks that there isnt already a notification that exists with current issueID
							if (NotificationManager.validateCurrentNotifications(issueID)){
								//creates user notification with tempStatus for correct message
								if (tempStatus == 2 || tempStatus == 3 || tempStatus == 6)	
									NotificationManager.createUserNotification(issueID, tempStatus); //create reporter notification
								else if (tempStatus != 100)
									NotificationManager.createStaffNotification(issueID, tempStatus); //create staff notification
							}
							
							//update the issue status 
							if (status != 100)
								issueMan.updateIssueStatus(issueID, status); //updates issue with correct status
						}
						//saves the tags
						if (tags!=null){
							issueMan.updateIssueTags(issueID, tags);
						}
						
						//saves the knowledgebase boolean based on selection
						if (knowledgebase!=null){
							boolean kb;
							if (knowledgebase.equals("yes"))
								kb = true;
							else 
								kb = false;
							issueMan.updateIssueKnowledgebase(issueID, kb);
						}
						
						if (comment!=null){
							if (!comment.equals("")){	//ensures the comment has content
								CommentManager.createComment(issueID, currentUser.getUserID(), comment);
							}
						}
						
						if (resDetails!=null){
							int userID = Integer.parseInt(resDetails);
							CommentManager.updateResolution(userID,true);
						}
					}
				}
				d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");	//set d to dahsboard to send user back there after makign changes
			}
			
			// checks for logging and retuirning to dashboard. often used when an error occurred 
			if(logError!=null){
				if(logError.equals("Return to Dashboard")){	//if the user selected return to dashboard
					d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");
					currentSessionTracker.setState(SessionTracker.State.DASHBOARD);
				}else if(logError.equals("Log Out")){	// if the user has selected logout
					session.setAttribute("user", null);	// set the user on the session to null
					currentSessionTracker.setState(SessionTracker.State.LOGGEDOUT);	// Set the state of the sessiontracker to LOGGGEDOUT
					currentSessionTracker.setLoggedIn(false);	//set loggedin to false to change the errorpage to login error
					d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/loggedOut.jsp");	//sets d to the loggedout page 
				}
			}
			
			// if user has selected sortissues (only available to staff)
			if(sortIssues!=null){
				if(sortIssues.equals("sortIssues")){
					d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");	// set d back to dashboard
					issueMan.sortIssuesArray();			// call the issuesort, which sorts the local instance of the issue array based on status in the issuemanager 
				}
			}
			
			// handles all the different ways an issue can be viewed (regular, via notification, viewing knowledgebase issue)
			if (openIssueButton!=null || openNotifIssueButton!=null || openKnowledgeBaseButton!=null){
				
				int openIssueInt=0;	// int to store the issueID being viewed
				if(openIssueButton!=null){
					openIssueInt=Integer.parseInt(openIssueButton);	// if the regular openissue button was pressed, store the openissueint to the passed value (the selected issue's id)
				}else if(openKnowledgeBaseButton!=null) {
					openIssueInt=Integer.parseInt(openKnowledgeBaseButton);// if the knowledgebase openissue button was pressed, store the openissueint to the passed value (the selected issue's id)
				}else if(openNotifIssueButton!=null){		// if the issues was viewed via a notification
					String [] array;	// an array to hold the two values sent by this button (issueID and the notifications ID that the issues was viiwed from
					int viewedNotif;	// int to store the notification that the issue was selected from
					array = openNotifIssueButton.split(",");	// grabs the paramater passed bythe button and splits it into usable strings (the issueid and the notificationid)into an array
					openIssueInt=Integer.parseInt(array[0]);	//sets the issue to be openede to the given issueid
					viewedNotif=Integer.parseInt(array[1]);		//sets the notification to be cleared to the given notification id
					NotificationManager.deleteNotification(viewedNotif);	//deletes the notification now that it has been opened			
				}
				request.setAttribute("currentIssue",openIssueInt);	// sets an attribute for the jsp to load data dynamically
				if (openKnowledgeBaseButton!=null) {d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/KnowledgeBaseIssue.jsp");} //handles the dispatches. if knowledgebase issue was selected go to that jsp, if not regular issue jsp
				else { d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Issue.jsp"); }
				currentSessionTracker.setState(SessionTracker.State.VIEWINGISSUE);	//updates the tracker's state
				currentSessionTracker.setCurrentlyViewedIssue(openIssueInt);
			}

			
			// handles submission of a new issue
			if(submitIssueButton!=null){
				if(currentSessionTracker.getState() != classPackage.SessionTracker.State.NEWISSUEDASHBOARD)	//if the state isnt already newissue dashboard(stops issues being created multiple times on refresh)
				{
					if(submitIssueButton.equals("submitIssue")){
						//required parametes
						int reporterID=Integer.parseInt(request.getParameter("ReporterID"));
						String category=request.getParameter("Category");	
						int catInt=0;	//category integer
						String title=request.getParameter("Title");
						String tags=request.getParameter("Tags");
						String desc=request.getParameter("Description");
						
						//checks which category param was recieved and sets the int relatively
						if(category.equals("Network")){
							catInt=1;
						}else if(category.equals("Software")){
							catInt=2;
						}else if(category.equals("Hardware")){
							catInt=3;
						}else if(category.equals("Email")){
							catInt=4;
						}else if(category.equals("Account")){
							catInt=5;
						}
						
						issueMan.createIssue(reporterID, catInt, title, desc, tags);	//calls creteissue on the issuemanager with the passed parameters in the required form
						currentSessionTracker.setState(SessionTracker.State.NEWISSUEDASHBOARD);	// Set the state of the sessiontracker to newissuedashboard
					}
				}
				d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");	//sets the redirect back to the dash
			}
			
			// If return button (from error page) was posted, set d to the page they were on prior to the error, based off the sessiontracker state
			if(retButtonResult!=null){
				if(currentSessionTracker!=null){ // If tracker already exists
					if(currentSessionTracker.getState() == classPackage.SessionTracker.State.NEWISSUEDASHBOARD){
						d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");
					}else if(currentSessionTracker.getState() == classPackage.SessionTracker.State.DASHBOARD){
						d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/UserDashboard.jsp");
					}else if(currentSessionTracker.getState() == classPackage.SessionTracker.State.VIEWINGISSUE){
						request.setAttribute("currentIssue",currentSessionTracker.getCurrentlyViewedIssue());
						d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Issue.jsp");
					}else if(currentSessionTracker.getState() == classPackage.SessionTracker.State.CREATINGISSUE){
						d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/SubmitNewIssue.jsp");
					}else if(currentSessionTracker.getState() == classPackage.SessionTracker.State.LOGGINGIN){
						d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Login.jsp");
					}else if(currentSessionTracker.getState() == classPackage.SessionTracker.State.LOGGEDOUT){
						d=getServletContext().getRequestDispatcher("/WEB-INF/jsps/Login.jsp");
					}
				}
			}

		}
		 d.forward(request,response);	// Forward to selected jsp
	}
}