/* SENG 2050 ASSIGNMENT 3 - NOTIFICATION MANAGER. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Notification Manager creates and executes SQL commands to update and query the Notifications table.
 */
package classPackage;

import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class NotificationManager {
	//sql connection details
	private static String myDriver = "com.mysql.jdbc.Driver";
	private static String myUrl = "jdbc:mysql://teachdb/c3237487_db";
	private static String username = "c3237487";
	private static String password = "250396";
	
	//creates a new notification with required values in the SQL 'Notifications' table
	public static void createNotification(int u, int i, String c){
		int n = getHighestNotificationID() + 1; //gets unique notificiationID
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "INSERT INTO Notifications VALUES (?, ?, ?, false, ?)"; //sql command to add notification with required data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, n); //set notificationID
			ps.setInt(2, u); //set issueID
			ps.setInt(3, i); //set userID
			ps.setString(4, c); //set content 
			ps.executeUpdate();
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
	}
	
	//sets the notification for user message depending on status provided, then executes create notification
	public static void createUserNotification(int i, int s){
		int userID=Issue.getSingleIssue(i).getUserID();
		String c = "";  //gets notification content 
		if (s == 2)
			c = "This issue is in progress.";
		else if (s == 3)
			c = "This issue has been marked as complete.";
		else if (s == 6)
			c = "This issue is waiting on your response.";
		
		createNotification(userID, i, c);
	}

	//sets the notification message for staff depending on status provided, then executes create notification
	public static void createStaffNotification(int i, int s){
		int u = 9999; //unique ID that will alert all staff members 

		String c = "";  //gets notification content 
		if (s == 1)
			c = "New issue has been submitted.";
		else if (s == 4)
			c = "User has marked this issue as resolved.";
		else if (s == 5)
			c = "This issue is waiting on a third party response.";
		else if (s == 7)
			c = "This issues resolution has been rejected by user.";
		
		else if (s == 8)
			c = "No longer waiting on reporter.";
		else if (s == 9)
			c = "This issues resolution has been accepted by user.";
		createNotification(u, i, c);
	}
		
	//checks that there arent any notifications pending with a given issueID - to avoid multiple notifcations being made at once 
	public static boolean validateCurrentNotifications(int id){
		List<Notification> notifs = new LinkedList<>();
		boolean check = true; 
		Notification notif = null;
		notifs = Notification.getAllNotifications();
		for (Notification n : notifs){
			if (n.getNotificationID() == id)
				check = false;
		}
		return check;
	}
	
	//queries SQL 'Notification' table to return the notificationID with the highest value
	public static int getHighestNotificationID(){
		if(Notification.getAllNotifications().size()!=0){
			try {	
				int id;
				Class.forName(myDriver);
				Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection 
				String query = "SELECT MAX(notificationID) AS HighestID FROM Notifications;"; //sql command to get highest value notificationID
				PreparedStatement ps = conn.prepareStatement(query); 
				ResultSet rs = ps.executeQuery(); //query table and return result 
				rs.next(); //step into next 
				id = rs.getInt("HighestID"); //get the value returned (highest ID value)
				conn.close();
				return id; //returns highest value which will be incremented by 1 
			}
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
				return 0;
			}
		}
		else 
			return 1999; //returns default value
	}

	//passes notification id to mark a notification as viewed, this will remove the notification alert 
	public static void markNotificationViewed(int id){
		List<Notification> notifs = new LinkedList<>();
		Notification notif = null;
		notifs = Notification.getAllNotifications();
		for (Notification n : notifs){
			if (n.getNotificationID() == id)
				n.setViewed(true); //traverse all notifications and set as viewed if they match id provided
		}
	}
		
	//removes notification with corresponding ID from SQL 'Notifications' table 
	public static void deleteNotification(int id){
		try {	
			Class.forName(myDriver); 
			Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection
			String query = "DELETE FROM Notifications WHERE notificationID = ?"; //sql command to remove notification matching ID 
			PreparedStatement ps = conn.prepareStatement(query); 
			ps.setInt(1, id); //set notificationID to value provided 
			ps.executeUpdate(); //execute command 
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
}