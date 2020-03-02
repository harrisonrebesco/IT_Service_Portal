/* SENG 2050 ASSIGNMENT 3 - TAG BEAN. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Tag bean containing all setters and getters required to manipulate and fetch data for Assignment 3
 */


package classPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
public class Tag implements Serializable {
	
	private int tagID; //pk
	private int issueID; //fk
	private String content;
	
	//set tag id 
	public void setTagID(int id){
		tagID = id;
	}
	
	//return tag id 
	public int getTagID(){
		return tagID;
	}

	//set issue id 
	public void setIssueID(int id){
		issueID = id;
	}
	
	//return issue id 
	public int getIssueID(){
		return issueID;
	}
	
	//set tag content 
	public void setContent(String c){ 
		content = c;
	}
	
	//return content 
	public String getContent(){
		return content;
	}
	
	//querys sql database and returns a list containing all tags 
	public static List<Tag> getAllTags(){
		String query = "SELECT * FROM Tags"; //sql command 
		List<Tag> tags = new LinkedList<>();
		try(Connection connection = Config.getConnection(); //establish connection  
		Statement statement = connection.createStatement(); 
		ResultSet result = statement.executeQuery(query);){ 
			while(result.next()){ 
				Tag t = new Tag(); //populate tag 
				t.setTagID(result.getInt(1));
				t.setIssueID(result.getInt(2));
				t.setContent(result.getString(3));
				tags.add(t); //add to list containing all tags 
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
			
		return tags; //return list 
	}
}