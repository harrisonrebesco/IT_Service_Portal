package classPackage;
import java.util.*;

public class SessionTracker{

	public enum State {LOGGINGIN,DASHBOARD,FAILEDLOG,LOGGEDOUT,NEWISSUEDASHBOARD, CREATINGISSUE, VIEWINGISSUE,KNOWLEDGEBASE,UPDATEDISSUE};	// State to be set and checked in the servlet to stop user from performing forbidden operations
	private State currentState;	// The current state the game is in. Set via the enum above
	private boolean loggedIn;
	private boolean doOnce;
	private int currentlyViewedIssue;

	// constructor - sets variables to what is required for a new game
    public SessionTracker()
    {
		loggedIn=false;
		doOnce=false;
    }
		
	//GETTERS AND SETTERS
	//----------------------------------------------------------------------------

	public void setState(State newState){
		currentState=newState;
	}
	
	public void setLoggedIn(boolean bool){
		loggedIn=bool;
	}
	
	public void setDoOnce(boolean b){
		doOnce=b;
	}
	
	public boolean getDoOnce(){
		return doOnce;
	}
	
	
	public State getState(){
		return currentState;
	}
	
	public boolean getLoggedIn(){
		return loggedIn;
	}
	
	public void setCurrentlyViewedIssue(int i){
		currentlyViewedIssue=i;
	}
	
	public int getCurrentlyViewedIssue(){
		return currentlyViewedIssue;
	}
	
	//---------------------------------------------------------------------------------
}