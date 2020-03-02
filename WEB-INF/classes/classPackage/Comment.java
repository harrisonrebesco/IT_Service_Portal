/* SENG 2050 ASSIGNMENT 3 - COMMENT BEAN. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Comment bean containing all setters and getters required to manipulate and fetch data for Assignment 3
 */
 
package classPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
public class Comment implements Serializable {
	
	private int commentID; //pk
	private int userID; //fk
	private int issueID; //fk
	private String timePublished;
	private String content;
	private boolean resolution; //true = resolution issue
	
	//set comment id 
	public void setCommentID(int id){
		commentID = id;
	}
	
	//return comment id 
	public int getCommentID(){
		return commentID;
	}
	
	//set user id 
	public void setUserID(int id){
		userID = id;
	}
	
	//return user id 
	public int getUserID(){
		return userID;
	}
	
	//set issue id 
	public void setIssueID(int id){
		issueID = id;
	}
	
	//return issue id 
	public int getIssueID(){
		return issueID;
	}
	
	//set time published 
	public void setTimePublished(String tb){
		timePublished = tb;
	}
	
	//get time published 
	public String getTimePublished(){
		return timePublished;
	}
	
	//set content 
	public void setContent(String c){
		content = c;
	}
	
	//get content 
	public String getContent(){
		return content;
	}
	
	//set comment resolution 
	public void setResolution(boolean r){
		resolution = r;
	}
	
	//get comment resolution 
	public boolean getResolution(){
		return resolution;
	}
	
	//compares userID contained a comment to all userIDs in the server, if they match return the username 
	public String getUsername(){
		List<User> users = new LinkedList<>();
		users = User.getAllUsers();
		String username = null;
		for (User u : users){ 
			if (u.getUserID() == userID)
				username = u.getUsername();
		}
		return username;
	}
	
// Static functions- for opertions seperate from the individual instantiation of an 'issue'. (i.e operations that access multiple issues)
	
	//querys sql database and returns a list containing all comments 
	public static List<Comment> getAllComments(){
		String query = "SELECT * FROM Comments"; //start query 
		List<Comment> comments = new LinkedList<>(); 
		try(Connection connection = Config.getConnection(); //establish connection  
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);){ 
			while(result.next()){ 
				Comment c = new Comment(); //populate comment 
				c.setCommentID(result.getInt(1));
				c.setUserID(result.getInt(2));
				c.setIssueID(result.getInt(3));
				c.setTimePublished(result.getString(4));
				c.setContent(result.getString(5));
				c.setResolution(result.getBoolean(6));
				if(!c.getContent().equals("")||c.getContent()!=null){
					comments.add(c); //add to list containing all comments 
				}
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
		return comments;
	}
}