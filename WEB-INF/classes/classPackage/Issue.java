/* SENG 2050 ASSIGNMENT 3 - ISSUE BEAN. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Issue bean containing all setters and getters required to manipulate and fetch data for Assignment 3
 */
package classPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
import java.lang.*; 

public class Issue implements Serializable {
	
	private int issueID; //pk
	private int userID; //fk
	private int category; //1 = network, 2 = software, 3 = hardware, 4 = email, 5 = account
	private String title;
	private int status; //1 = new, 2 = in progress, 3 = completed, 4 = resolved, 5 = waiting on 3rd party, 6 = waiting on reporter //7 = rejected 
	private String description;
	private String resolutionDetails;
	private boolean knowledgeBase; //true  = knowledge base
	//private String tags;
	private String timeOpened;
	private String timeClosed;
	private String[] tags;
	private Comment[] comments;
	
	//sets issue id 
	public void setIssueID(int id){
		issueID = id;
	}
	
	//returns issue id 
	public int getIssueID(){
		return issueID;
	}
	
	//sets user id assosicated with the issue
	public void setUserID(int id){
		userID = id;
	}

	//returns user id
	public int getUserID(){
		return userID;
	}
	
	//sets issue category 
	public void setCategory(int c){ 
		category = c;
	}
	
	//returns issue category 
	public int getCategory(){
		return category;
	}
	
	//sets issue status 
	public void setStatus(int s){
		status = s;
	}
	
	//returns issue status 
	public int getStatus(){
		
		return status;
	}
	
	//convert, and return the issue category as a string 
	public String getCategoryString(){
		String c;
		if (category == 1)
			c = "Network";
		else if (category == 2)
			c = "Software";
		else if (category == 3)
			c = "Hardware";
		else if (category == 4)
			c = "Email";
		else if (category == 5)
			c = "Account";
		else 
			c = null;
		return c;
	}
	
	//convert, and return the issue status as a string 
	public String getStatusString(){
		String s;
		if (status == 1)
			s = "New";
		else if (status == 2)
			s = "In Progress";
		else if (status == 3)
			s = "Completed";
		else if (status == 4)
			s = "Resolved";
		else if (status == 5)
			s = "Waiting on 3rd party";
		else if (status == 6)
			s = "Waiting on reporter";
		else 
			s = null;
		return s;
	}

	//return available statuses that issue can be - used for drop down menu staff interact with when viewing single issue 
	public String [] getAvailableStatuses(){
		//String [] statusArray = new String();
		String[] testArray = {"selected", "disabled", "disabled", "disabled", "disabled", "disabled"};

		if (status == 1){
			String [] statusArray = {"disabled", "value=\"2\" selected", "value=\"3\"", "disabled", "value=\"5\"", "value=\"6\""};
			return statusArray;
		}
		else if (status == 2){
			String [] statusArray = {"disabled", "value=\"100\" selected", "value=\"3\"", "disabled", "value=\"5\"", "value=\"6\""};
			return statusArray;
		}
		else if (status == 3){
			String [] statusArray = {"disabled", "disabled", "value=\"100\" selected", "disabled", "disabled", "disabled"};
			return statusArray;
		}
		else if (status == 4){
			String [] statusArray = {"disabled", "disabled", "disabled", "value=\"100\" selected", "disabled", "disabled"};
			return statusArray;
		}
		else if (status == 5){
			String [] statusArray = {"disabled", "value=\"2\"", "value=\"3\"", "disabled", "value=\"100\" selected", "value=\"6\""};
			return statusArray;
		}
		else if (status == 6){
			String [] statusArray = {"disabled", "disabled", "disabled", "disabled", "disabled", "value=\"100\" selected"};
			return statusArray;
		}
		else 
			return testArray ;
	}
	
	//sets the issue title 
	public void setTitle(String t){
		title = t;
	}
	
	//returns the issue title 
	public String getTitle(){
		return title;
	}

	//sets the description of the issue 
	public void setDescription(String d){
		description = d;
	}
	
	//returns the issue description
	public String getDescription(){
		return description;
	}

	//sets the resolution details for the issue 
	public void setResolutionDetails(String rd){
		resolutionDetails = rd;
	}
	
	//returns the resolution details 
	public String getResolutionDetails(){
		return resolutionDetails;
	}

	//sets the issue as a knowledgeBase topic 
	public void setKnowledgebase(boolean k){
		knowledgeBase = k;
	}
	
	//returns true if the issue is in the knowledgeBase, false if the issue is not
	public boolean getKnowledgebase(){
		return knowledgeBase;
	}
	
	//set tags 
	public void setTags(String t){
		//tags = t;
		tags = t.split(",");
	}
	
	// //return tags 
	// public String getTags(){
		// return tags;
	// }
	
	//set time issue is opened 
	public void setTimeOpened(String to){
		timeOpened = to;
	}
	
	//return the time issue is opened 
	public String getTimeOpened(){
		return timeOpened;
	}
	
	//set the time issue is closed 
	public void setTimeClosed(String tc){
		timeClosed = tc;
	}
	
	//return time issue is closed 
	public String getTimeClosed(){
		return timeClosed;
	}
	
	public int getTagsCount(){
		return tags.length;
	}
	
	//return the tags array as a single string with elements seperated by a comma 
	public String getTagsString(){
		String allTags="";
		for(int i=0;i<tags.length;i++){
			allTags+=tags[i]+",";
		}

		return allTags;
	}
	
	public String getTag(int i){
		return tags[i];
	}
	
	//compares userID contained in issue to all userIDs in the server, if they match return the username 
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
	

	// this will load an array of size 0 if no comments - not null
	public void loadComments() { 
		comments = IssueManager.getRelatedComments(issueID);
	}

	//returns total comments on issue 
	public int getCommentCount() { 
		if (comments == null) {this.loadComments();}
		return comments.length; }

	//returns comment at specified index 
	public Comment getComment(int i) { 
		if (comments == null) {this.loadComments();}
		return comments[i]; 
	}

	
// Static functions- for opertions seperate from the individual instantiation of an 'issue'. (i.e operations that access multiple issues)
	
	//returns a single issue if it mathes the id given, returns null if no issue is found that matches id 
	public static Issue getSingleIssue(int id){
		List<Issue> issues = new LinkedList<>();
		Issue issue = null;
		issues = Issue.getAllIssues();
		for (Issue i : issues){
			if (i.getIssueID() == id)
				issue = i;
		}
		return issue;
	}

	//querys sql database and returns a list containing all issues 
	public static List<Issue> getAllIssues(){
		String query = "SELECT * FROM Issues"; //query line 
		List<Issue> issues = new LinkedList<>(); 
		try(Connection connection = Config.getConnection(); //attemps to connect to sql server 
		Statement statement = connection.createStatement();  
		ResultSet result = statement.executeQuery(query);){ 
			while(result.next()){ 
				Issue i = new Issue();
				i.setIssueID(result.getInt(1)); //queries and returns all issue attributes 
				i.setUserID(result.getInt(2)); 
				i.setCategory(result.getInt(3));
				i.setTitle(result.getString(4));
				i.setStatus(result.getInt(5)); 
				i.setDescription(result.getString(6)); 
				i.setResolutionDetails(result.getString(7)); 
				i.setKnowledgebase(result.getBoolean(8));
				i.setTags(result.getString(9));
				i.setTimeOpened(result.getString(10));
				i.setTimeClosed(result.getString(11));
				issues.add(i); //adds issue to issue list 
			}
		}
		catch(SQLException sqle){ //exception handling 
			System.err.println(sqle.getMessage());
			System.err.println(sqle.getStackTrace());
		}
		catch (Exception e) { //exception handling 
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
		return issues; //return populated issue list containing all data contained within the sql table "Issues"
	}

}