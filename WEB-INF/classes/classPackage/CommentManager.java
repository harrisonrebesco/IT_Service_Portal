/* SENG 2050 ASSIGNMENT 3 - COMMENT MANAGER. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Comment Manager creates and executes SQL commands to update and query the Comments table.
 */
 
package classPackage;

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class CommentManager {
	
	private static String myDriver = "com.mysql.jdbc.Driver";
	private static String myUrl = "jdbc:mysql://teachdb/c3237487_db";
	private static String username = "c3237487";
	private static String password = "250396";
	
	static DateFormat df = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss"); 
	

	//creates a new comment with required values in the SQL 'Comments' table
	public static void createComment(int i, int u, String c){
		int id = getHighestCommentID()+1;	
		Date date = new Date();  //get current date for time opened 
		String tp = df.format(date); //format date
		
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "INSERT INTO Comments VALUES (?, ?, ?, ?, ?, false)"; //sql command to add comment with required data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id); //set commentID
			ps.setInt(2, u); //set issueID
			ps.setInt(3, i); //set userID
			ps.setString(4, tp); //set time published 
			ps.setString(5, c); //set content 
			ps.executeUpdate();
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
	}
	
	//updates the resolution of a comment within the SQL 'Comments' table (used to 'flag' a comment as resolution)
	public static void updateResolution(int c, boolean r){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "UPDATE Comments SET resolution = ? WHERE commentID = ?"; //sql command to update resolutionDetails
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setBoolean(1, r); //mark if resolution comment 
			ps.setInt(2, c); //set commentID
			ps.executeUpdate();
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	//returns the highest valued commentID - used when creating comment, will return current highest value, we can just increment this by 1 and use 
	private static int getHighestCommentID(){
		if(Comment.getAllComments().size()!=0){
			try {	
				int id;
				Class.forName(myDriver);
				Connection conn = DriverManager.getConnection(myUrl, username, password);
				String query = "SELECT MAX(commentID) AS HighestID FROM Comments;"; //sql command to get highest value issueID
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				rs.next();
				id = rs.getInt("HighestID");
				conn.close();
				return id;
			}
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
				return 0;
			}
		}
		else 
			return 2999; //return default value 
	}
}