/* SENG 2050 ASSIGNMENT 3 - USER BEAN. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: User bean containing all setters and getters required to manipulate and fetch data for Assignment 3
 */
package classPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
public class User implements Serializable {
	
	private int userID; //pk 
	private boolean userType; //true = staff , false = reporter
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String contactNumber;
	private Notification[] userNotifications;
	
	//set user id 
	public void setUserID(int id){
		userID = id;
	}
	
	//return user id 
	public int getUserID(){
		return userID;
	}
	
	//set type 
	public void setUserType(boolean ut){ //true = staff, false = reporter 
		userType = ut;
	}
	
	//return type 
	public boolean getUserType(){
		return userType;
	}
	
	//return type as string 
	public String getUserTypeString(){
		String typeString;
		if (userType == true)
			typeString = "Staff";
		else
			typeString = "Reporter";
		return typeString;
	}
	
	//set first name
	public void setFirstName(String fn){
		firstName = fn;
	}
	
	//return first name 
	public String getFirstName(){
		return firstName;
	}

	//set last name 
	public void setLastName(String ln){
		lastName = ln;
	}
	
	//return last name 
	public String getLastName(){
		return lastName;
	}

	//set username 
	public void setUsername(String u){
		username = u;
	}
	
	//return username 
	public String getUsername(){
		return username;
	}

	//set password
	public void setPassword(String p){
		password = p;
	}

	//return password
	public String getPassword(){
		return password;
	}

	//set email
	public void setEmail(String e){
		email = e;
	}

	//return email
	public String getEmail(){
		return email;
	}

	//set contact number 
	public void setContactNumber(String cn){
		contactNumber = cn;
	}
	
	//return contact number 
	public String getContactNumber(){
		return contactNumber;
	}
	
	//loads all notifications from sql 
	public void loadNotifications() {
		if (userType == false)
			userNotifications = UserManager.getRelatedNotifications(userID); //if userType = reporter attach userID
		else 
			userNotifications = UserManager.getRelatedNotifications(9999); //if userType = staff, attach unique id tag so all staff can view this issue 
	}

	//return a notification at specificed index 
	public Notification getNotification(int index)
	{
		if ( userNotifications == null ) { this.loadNotifications();}
		return userNotifications[index];
	}

	//count and return all notifications in sql 
	public int getNotificationCount()
	{
		if ( userNotifications == null ) { this.loadNotifications();}
		if(userNotifications!=null){
			return userNotifications.length;
		}else{
			return 0;
		}

	}
	
// Static functions- for opertions seperate from the individual instantiation of a 'user'. (i.e operations that access multiple issues)
	
	//querys sql database and returns a list containing all users 
	public static List<User> getAllUsers(){
		String query = "SELECT * FROM Users";
		List<User> users = new LinkedList<>();
		try(Connection connection = Config.getConnection(); //establish connection 
		Statement statement = connection.createStatement(); 
		ResultSet result = statement.executeQuery(query);){ 
			while(result.next()){  
				User u = new User(); //populate user from sql 
				u.setUserID(result.getInt(1));
				u.setUserType(result.getBoolean(2));
				u.setFirstName(result.getString(3));
				u.setLastName(result.getString(4));
				u.setUsername(result.getString(5));
				u.setPassword(result.getString(6));
				u.setEmail(result.getString(7));
				u.setContactNumber(result.getString(8));
				users.add(u); //add to lsit containing all users 
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
			
		return users;
	}
}