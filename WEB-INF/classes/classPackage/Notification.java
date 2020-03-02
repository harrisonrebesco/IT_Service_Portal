/* SENG 2050 ASSIGNMENT 3 - NOTIFICATION BEAN. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Notification bean containing all setters and getters required to manipulate and fetch data for Assignment 3
 */
package classPackage; 

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
public class Notification implements Serializable {
	
	private int notificationID; //pk
	private int userID; //fk
	private int issueID; //fk
	private boolean viewed;
	private String content;

	//sets notificationID 
	public void setNotificationID(int id){
		notificationID = id;
	}
	
	//returns notificationID
	public int getNotificationID(){
		return notificationID;
	}
	
	//set issueID
	public void setIssueID(int id){
		issueID = id;
	}

	//return issueID 
	public int getIssueID(){
		return issueID;
	}
	
	//set userID 
	public void setUserID(int id){
		userID = id;
	}
	
	//return userID 
	public int getUserID(){
		return userID;
	}
	
	//sets the status of viewed (if false, will display notification, if true will not show)
	public void setViewed(boolean v){
		viewed = v;
	}
	
	public boolean getViewed(){
		return viewed;
	}
	
	
	//set notification content 
	public void setContent(String c){ 
		content = c;
	}
	
	//return content 
	public String getContent(){
		return content;
	}
	
	//returns the status of an issue (identified by issueID) related to the notification
	public String getIssueStatus(){
		List<Issue> issues = new LinkedList<>();
		String status = null; 
		issues = Issue.getAllIssues(); //compiles all issues contained in SQL 'Issues' table into a linked list 
		for (Issue i : issues){ //traverse all issues in list  
			if (issueID == i.getIssueID()) //if the issueID provided matches the notification's issueID retrieve issue status 
				status = i.getStatusString(); //sets status from corresponding issue 
		}
		return status; //return the status retrieved 
	}
	
	//returns the title of an issue (identified by issueID) related to the notification
	public String getIssueTitle(){
		List<Issue> issues = new LinkedList<>();
		String title = null;
		issues = Issue.getAllIssues(); //compiles all issues contained in SQL 'Issues' table into a linked list 
		for (Issue i : issues){
			if (issueID == i.getIssueID()) //if the issueID provided matches the notification's issueID retrieve issue title  
				title = i.getTitle(); //sets title from corresponding issue 
		}
		return title; //return the title retrieved 
	}
	
	//querys sql database and returns a list containing all notifications 
	public static List<Notification> getAllNotifications(){
		String query = "SELECT * FROM Notifications"; 
		List<Notification> notifications = new LinkedList<>();
		try(Connection connection = Config.getConnection(); 
		Statement statement = connection.createStatement(); 
		ResultSet result = statement.executeQuery(query);){ 
			while(result.next()){ 
				Notification n = new Notification(); //populate notification  
				n.setNotificationID(result.getInt(1)); 
				n.setUserID(result.getInt(2));
				n.setIssueID(result.getInt(3));
				n.setViewed(result.getBoolean(4));
				n.setContent(result.getString(5));
				notifications.add(n); //add to list 
			}
		}
		catch(SQLException sqle){
			System.err.println(sqle.getMessage());
			System.err.println(sqle.getStackTrace());
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
		return notifications;
	}
}