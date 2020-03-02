/* SENG 2050 ASSIGNMENT 3 - ISSUE MANAGER. 
 * 
 * AUTHORS: Harrison Rebesco - c3237487  
 * 			Luke Royle - c3215435
 * 			Mitchell Wallace - c3293398
 * 
 * DESCRIPTION: Issue Manager creates and executes SQL commands to update and query the Issues table.
 */
package classPackage;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class IssueManager {
	//sql connection deails 
	private String myDriver = "com.mysql.jdbc.Driver";
	private String myUrl = "jdbc:mysql://teachdb/c3237487_db";
	private String username = "c3237487";
	private String password = "250396";
	private Issue[] issuesArray;
	boolean sorted=false;
	//private Issue[] sortedIssues = new Issue[issuesArray.length];
	private Issue currentIssue;
	
	DateFormat df = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");  //date which will be used later for timeOpened 
	
	//creates a new issue with required values in the SQL 'Issues' table
	public void createIssue(int u, int c, String t, String d, String tgs){
		
		Date date = new Date();  //get current date for time opened 
		String to = df.format(date); //format date 
		int i=getHighestIssueID()+1; //generates issue ID
		
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "INSERT INTO Issues VALUES (?, ?, ?, ?, 1, ?, null, false, ?, ?, null)"; //sql command to insert new data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, i); //set issueID
			ps.setInt(2, u); //set userID
			ps.setInt(3, c); //set category
			ps.setString(4, t); //set title 
			ps.setString(5, d); //set description
			ps.setString(6, tgs);//set tags
			ps.setString(7, to); //set timeOpened
			ps.executeUpdate(); //execute command 
			//System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
		loadAllIssues();
		NotificationManager.createStaffNotification(i,1);	//creates a notification to alert staff of a new issue
	}
	
	public void updateIssueStatus(int i, int s){
		if (s == 4){
			updateTimeClosed(i); //set time closed when resolved 
		}
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "UPDATE Issues SET status = ? WHERE issueID = ?"; //sql command to insert new data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, s); //set status
			ps.setInt(2, i); //set issueID
			ps.executeUpdate(); //execute command 
			//System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
		loadAllIssues();
	}

	public void updateIssueTags(int i, String t){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "UPDATE Issues SET tags = ? WHERE issueID = ?"; //sql command to insert new data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, t); //set tag
			ps.setInt(2, i); //set issueID
			ps.executeUpdate(); //execute command 
			//System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
		loadAllIssues();
	}
	
	public void updateIssueKnowledgebase(int i, boolean k){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "UPDATE Issues SET knowledgeBase = ? WHERE issueID = ?"; //sql command to insert new data 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setBoolean(1, k); //set knowledgeBase
			ps.setInt(2, i); //set issueID
			ps.executeUpdate(); //execute command 
			//System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());	
		}
		loadAllIssues();
	}
	
	// public void updateIssue(int i, int s, String t, boolean k){
		// if (s == 4){
			// updateTimeClosed(i); //set time closed when resolved 
		// }
		// try {	
			// Class.forName(myDriver);
			// Connection conn = DriverManager.getConnection(myUrl, username, password);
			// String query = "UPDATE Issues SET status = ?, tags = ?, knowledgeBase = ? WHERE issueID = ?"; //sql command to insert new data 
			// PreparedStatement ps = conn.prepareStatement(query);
			// ps.setInt(1, s); //set issueID
			// ps.setString(2, t); //set userID
			// ps.setBoolean(3, k); //set category
			// ps.setInt(4, i); //set title 
			// ps.executeUpdate(); //execute command 
			// //System.out.println("Update successful");
			// conn.close();
		// }
		// catch(Exception e){
			// System.err.println("Error: " + e.getMessage());	
		// }
		// loadAllIssues();
	// }

	
	//sets the timeClosed of an issue within the SQL 'Issues' table - used when a issue is offically closed.
	public void updateTimeClosed(int i){
				
		Date date = new Date();  //get current date for time closed 
		String tc = df.format(date); //format date 
		
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection 
			String query = "UPDATE Issues SET timeClosed = ? WHERE issueID = ?"; //sql command to update Issues 
			PreparedStatement ps = conn.prepareStatement(query); 
			ps.setString(1, tc); //set time closed
			ps.setInt(2, i); //set issueID
			ps.executeUpdate(); //execute command 
			System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	//sets the resolutionDetails of an issue within the SQL 'Issues' table - used by Staff to provide a solution to a user issue 
	public void updateResolutionDetails(int i, String rd){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection 
			String query = "UPDATE Issues SET resolutionDetails = ? WHERE issueID = ?"; //sql command to update Issues 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, rd); //set time closed
			ps.setInt(2, i); //set issueID
			ps.executeUpdate(); //execute command 
			System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	//queries SQL 'Issue' table to return the issueID with the highest value - this is used when setting issueID of a new Issue,as it will simple increment the number returned by 1.
	public int getHighestIssueID(){
		if(Issue.getAllIssues().size()!=0){
			try {	
				int id; 
				Class.forName(myDriver);
				Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection 
				String query = "SELECT MAX(issueID) AS HighestID FROM Issues;"; //sql command to query Issues
				PreparedStatement ps = conn.prepareStatement(query); 
				ResultSet rs = ps.executeQuery(); //query table and return result 
				rs.next(); //'step' returned data by one 
				id = rs.getInt("HighestID"); //set id to the number returned 
				conn.close();
				return id; //return highest value issueID
			}
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
				return 0;
			}
		}else{return 999;}
	}
	
	//queries SQL 'Issue' table to return the total amount of tags that are related to an issueID that is provided 
	public int countTotalTags(int id){
		try {	
			int count;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection
			String query = "SELECT COUNT(tagID) AS total FROM Tags WHERE issueID = ?"; //sql command to count all tagID's that correspond to the issueID provided
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id); //set issueID
			ResultSet rs = ps.executeQuery(); //execute query and return results 
			rs.next(); //'step' returned data forward by one 
			count = rs.getInt("total"); //set count to the total returned 
			conn.close(); 
			System.out.println(id); //**** this is just a test line to see that its returning the right value **
			return count; //return the total 
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
			return 0;
		}
	}
	
	//queries SQL 'Issue' table to return the total amount of comments that are related to an issueID that is provided 
	public int countTotalComments(int id){
		try {	
			int count;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection
			String query = "SELECT COUNT(commentID) AS total FROM Comments WHERE issueID = ?"; //sql command to count all tagID's that correspond to the issueID provided
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id); //set issueID
			ResultSet rs = ps.executeQuery();//execute query and return results 
			rs.next();
			count = rs.getInt("total"); //set count to the total returned 
			conn.close();
			System.out.println(id); //**** this is just a test line to see that its returning the right value **
			return count; //return the total 
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
			return 0;
		}
	}
	
	//traverses all comments and returns a list that contains all comments that are related to the provided IssueID
	public static Comment[] getRelatedComments(int id){
		List<Comment> commentList = new LinkedList<>();
		List<Comment> relatedList = new LinkedList<>();
		Comment[] emptyArray = new Comment[0];
		commentList = Comment.getAllComments(); //fetches all comments from SQL 'Comments' table 
		int count = 0; //count used as size for array 
		for (Comment c : commentList){
			if(c.getContent()!=null||!c.getContent().equals(""))
			if (c.getIssueID() == id){ //traverses all comments and if issueID's match add to a new list 
				relatedList.add(c);
				count++;
			}
		}
		if (count != 0){ //if at least one comment is found that matches issueID provided convert the list to an array
			Comment[] commentArray = relatedList.toArray(new Comment[count]);
			return commentArray; 
		}
		else {
			return emptyArray;
		}
	}
	
	//traverses all tags and returns a list that contains all tags that are related to the provided IssueID
	public Tag[] getRelatedTags(int id){
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
			Tag[] tagArray = relatedList.toArray(new Tag[count]);	
			return tagArray;
		}
		else 
			return null;
	}
	
	//*******UPDATE: CREATED THIS IN BEAN INSTEAD***********
	//query SQL 'Issues' table and return the username that matches the userID attached to an Issue 
	// public String getIssueUsername(int id){
		// String u = null;
		// try {	
			// Class.forName(myDriver);
			// Connection conn = DriverManager.getConnection(myUrl, username, password); //establish connection 
			// String query = "SELECT u.username FROM Issues i, Users u WHERE u.userID = i.userID AND i.issueID = ?"; //sql command to get username from issue 
			// PreparedStatement ps = conn.prepareStatement(query);
			// ps.setInt(1, id); //set issueID 
			// ResultSet rs = ps.executeQuery(); //execute query and return results 
			// rs.next(); //'step' results forward 
			// u = rs.getString("username"); //return the username 
			// conn.close();
			// System.out.println(id);
			// return u;
		// }
		// catch(Exception e){
			// System.err.println("Error: " + e.getMessage());
			// return null;
		// }
	// }
	
	
	//**ignore this, just for testing delete functionality - issue will not have this in main program**
	public void deleteNotification(int i){
		try {	
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
			String query = "DELETE FROM Issues WHERE issueID = ?"; //sql command to get remove issue with corresponding ID 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, i);
			ps.executeUpdate();
			System.out.println("Update successful");
			conn.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void setCurrentIssue(int issueID){
		currentIssue=Issue.getSingleIssue(issueID);
	}
	
	public Issue getCurrentIssue(){
		return currentIssue;
	}

	public void sortIssuesArray() {
		
		ArrayList<Issue> statusNew = new ArrayList<Issue>();
		ArrayList<Issue> statusInp = new ArrayList<Issue>();
		ArrayList<Issue> statusComp = new ArrayList<Issue>();
		ArrayList<Issue> statusResol = new ArrayList<Issue>();
		ArrayList<Issue> statusWOTP = new ArrayList<Issue>();
		ArrayList<Issue> statusWOR = new ArrayList<Issue>();
		
		
		System.out.println("starting array length is:"+issuesArray.length);
		
		for (int i=0;i<issuesArray.length;i++){
			if(issuesArray[i].getStatus()==1){
				statusNew.add(issuesArray[i]);
			}else if(issuesArray[i].getStatus()==2){
				statusInp.add(issuesArray[i]);
			}else if(issuesArray[i].getStatus()==3){
				statusComp.add(issuesArray[i]);
			}else if(issuesArray[i].getStatus()==4){
				statusResol.add(issuesArray[i]);
			}else if(issuesArray[i].getStatus()==5){
				statusWOTP.add(issuesArray[i]);
			}else if(issuesArray[i].getStatus()==6){
				statusWOR.add(issuesArray[i]);
			}			
		}
		

            System.out.println("statusNew has:"+statusNew.size()); 
            System.out.println("statusInp has:"+statusInp.size()); 
            System.out.println("statusComp has:"+statusComp.size()); 
            System.out.println("statusResol has:"+statusResol.size()); 
            System.out.println("statusWOTP has:"+statusWOTP.size()); 
            System.out.println("statusWOR has:"+statusWOR.size()); 
		
		int count=0;
		for (int i=0; i<statusNew.size(); i++){
			issuesArray[count]=statusNew.get(i);
			count++;
		}for (int i=0; i<statusInp.size(); i++){
			issuesArray[count]=statusInp.get(i);
			count++;
		}for (int i=0; i<statusComp.size(); i++){
			issuesArray[count]=statusComp.get(i);
			count++;
		}for (int i=0; i<statusResol.size(); i++){
			issuesArray[count]=statusResol.get(i);
			count++;
		}for (int i=0; i<statusWOTP.size(); i++){
			issuesArray[count]=statusWOTP.get(i);
			count++;
		}for (int i=0; i<statusWOR.size(); i++){
			issuesArray[count]=statusWOR.get(i);
			count++;
		}
		
		sorted=true;

	}
	
	
	

	public void loadAllIssues() {
		if(sorted==false){
			issuesArray=Issue.getAllIssues().toArray(new Issue[Issue.getAllIssues().size()]);	
		}else{sorted=false;}
	}

	public Issue getIssue(int index)
	{
		if ( issuesArray == null ) { this.loadAllIssues();}
		return issuesArray[index];
	}

	public int getIssueCount()
	{
		if ( issuesArray == null ) { this.loadAllIssues();}
		if (issuesArray!=null){
			return issuesArray.length;
		}else{
			return 0;
		}
	}
	

}