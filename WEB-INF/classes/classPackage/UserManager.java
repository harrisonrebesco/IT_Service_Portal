/* SENG 2050 ASSIGNMENT 3 - USER MANAGER. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: User Manager creates and executes SQL commands to update and query the 'Users' table.
 */
package classPackage;


import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class UserManager {
	//sql connection details
	private String myDriver = "com.mysql.jdbc.Driver";
	private String myUrl = "jdbc:mysql://teachdb/c3237487_db";
	private String username = "c3237487";
	private String password = "250396";
	
	//traverses all notifications and returns a list that contains all notifications that are related to the provided userID
	public static Notification[] getRelatedNotifications(int id){
		List<Notification> relatedList = new LinkedList<>();
		List<Notification> notificationList = new LinkedList<>();
		notificationList = Notification.getAllNotifications();
		int count = 0;
		for (Notification n : notificationList){ //traverses list 
			if (n.getUserID() == id){
				relatedList.add(n); //if the notification userID matches ID provided add to a new list 
				count++; //inc count (for array size)
			}
		}
		if (count != 0){ //if there is at least 1 related notification 
			Notification[] notificationArray = relatedList.toArray(new Notification[count]); //add to array of notifications 	
			return notificationArray; //return 
		}
		else{
			return null;
		}
	}
	
	
}