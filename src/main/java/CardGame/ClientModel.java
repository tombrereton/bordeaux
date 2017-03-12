package CardGame;

import static CardGame.ProtocolMessages.SUCCESS;

import java.io.IOException;
import java.util.Observable;

import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseRegisterUser;

public class ClientModel extends Observable {
	//connectors
	CardGameClient cardGameClient = new CardGameClient();
	
	//Booleans for states
	private boolean connected, loggedIn;
	
	//fields
	User user;
	
	public ClientModel(){
		this.connected = false;
		this.loggedIn = false;
	}
	
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
	
	public void setLoggedIn(boolean bool, User user){
		this.loggedIn = bool;
		this.user = user;
		setChanged();
		notifyObservers(loggedIn);
	}
	
	public void registerUser(String username, String password, String first, String last){
		User user = new User(username,password,first,last);
		try {
			ResponseRegisterUser responseRegisterUser = cardGameClient.sendRequestRegisterUser(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public CardGameClient getCardGameClient() {
		return cardGameClient;
	}

	public boolean isConnected() {
		return connected;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public User getUser() {
		return user;
	}
	

}
