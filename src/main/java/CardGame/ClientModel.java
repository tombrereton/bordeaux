package CardGame;

import static CardGame.ProtocolMessages.SUCCESS;

import java.io.IOException;
import java.util.Observable;

import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseRegisterUser;
/**
 * The observable class that contains all the information to be displayed on the client gui and methods for sending to server.
 * @author Lloyd
 *
 */
public class ClientModel extends Observable {
	//connectors
	CardGameClient cardGameClient = new CardGameClient();
	
	//Booleans for states
	private boolean connected, loggedIn;
	
	//fields
	User user;
	/**
	 * Constructor.
	 */
	public ClientModel(){
		this.connected = false;
		this.loggedIn = false;
	}
	/**
	 * method that sends login request to server and updates loggedin and user fields.
	 * @param username
	 * @param password
	 */
	public void login(String username, String password){
		User user = new User(username,password);
		try {
			ResponseLoginUser responseLoginUser = cardGameClient.sendRequestLoginUser(user);
			if(responseLoginUser.getRequestSuccess() == SUCCESS){
				setLoggedIn(true, responseLoginUser.getUser());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * helper method for login.
	 * @param bool
	 * @param user
	 */
	public void setLoggedIn(boolean bool, User user){
		this.loggedIn = bool;
		this.user = user;
		setChanged();
		notifyObservers(loggedIn);
	}
	/**
	 * Method for sending a request to register a user.
	 * @param username
	 * @param password
	 * @param first
	 * @param last
	 */
	public void registerUser(String username, String password, String first, String last){
		User user = new User(username,password,first,last);
		try {
			ResponseRegisterUser responseRegisterUser = cardGameClient.sendRequestRegisterUser(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
/**
 * getter for cardgameclient object.
 * @return
 */
	public CardGameClient getCardGameClient() {
		return cardGameClient;
	}
/**
 * getter for connected
 * @return
 */
	public boolean isConnected() {
		return connected;
	}
/**
 * getter for boolean logged in
 * @return
 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
/**
 * getter for user
 * @return
 */
	public User getUser() {
		return user;
	}
	

}
