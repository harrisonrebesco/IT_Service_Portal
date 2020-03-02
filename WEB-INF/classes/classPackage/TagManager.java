/* SENG 2050 ASSIGNMENT 3 - TAG MANAGER. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Tag Manager creates and executes SQL commands to update and query the 'Tags' table.
 */
 
 //**********CURRENTLY UNUSED*****************
package classPackage;

import java.sql.*;
import java.util.*;

public class TagManager {
	
	//sql connection details 
	private String myDriver = "com.mysql.jdbc.Driver";
	private String myUrl = "jdbc:mysql://teachdb/c3237487_db";
	private String username = "c3237487";
	private String password = "250396";
	
	//creates a new tag with required values in the SQL 'Tags' table
	public void createTag(int t, int i, String c){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "INSERT INTO Tags VALUES (?, ?, ?)"; //sql command to add tag with required data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, t); //set tagID
			ps.setInt(2, i); //set issueID
			ps.setString(3, c); //set content 
			ps.executeUpdate();
			System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
	}
	
	//queries SQL 'Tags' table to return the tagID with the highest value - this is used when setting tagID of a new tag (increment value returned by 1)
	public int getHighestTagID(){
		try {	
			int id;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "SELECT MAX(tagID) AS HighestID FROM Tags;"; //sql command to get highest value tagID
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery(); //query setver and return value 
			rs.next(); //step forward into next column 
			id = rs.getInt("HighestID"); //set highest value found 
			conn.close();
			return id;
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
			return 0;
		}
	}
	
	//traverse all tags that are related to the same issue (via provided issueID) and compile contents into string array 
	public String [] compileTagContents(int id){ //id here is issueID 
		List<Tag> tagList = new LinkedList<>();
		List<Tag> relatedList = new LinkedList<>();
		tagList = Tag.getAllTags(); //fetches all tags from SQL 'Tags' table 
		int count = 0; //count used as size for array 
		for (Tag t : tagList){
			if (t.getIssueID() == id){ //traverse all tags - if issueID matches add to new list 
				relatedList.add(t);
				count++;
			}
		}
		if (count != 0){ //if atleast one tag is found that matches issueID provided, convert into array 
			String [] tagContents = new String [count];
			count = 0; //reset count 
			for (Tag t : relatedList){
				tagContents[count] = new String();
				tagContents[count] = t.getContent();
				count++;
			}
			return tagContents;
		}
		else 
			return null;
	}
		

	//removes tag with corresponding ID from SQL 'Tags' table 
	public void deleteTag(int id){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection 
			String query = "DELETE FROM Tags WHERE tagID = ?"; //sql command to remove tag matching ID 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id); //set id of prep. statement 
			ps.executeUpdate(); //execute command 
			System.out.println("Delete successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
}