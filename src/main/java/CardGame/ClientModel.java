package CardGame;

import CardGame.GameEngine.Hand;
import CardGame.Gui.Screens;
import CardGame.Pushes.PushProtocol;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseRegisterUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

import static CardGame.ProtocolMessages.SUCCESS;
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

	// Screen state variable
	// this variable can only be set the predefined screens
	// in CardGame.Gui.Screens
	private int currentScreen;

	//fields
	User user;
	ArrayList<User> users;
	LinkedBlockingQueue<PushProtocol> pushRequestQueue;
	ArrayList<String> listOfGames;

	// Game variables
    private ArrayList<String> playerNames;
	private Hand dealerHand;
	private Map<String, Hand> playerHands;
	private Map<String, Integer> playerBets;
	private Map<String, Integer> playerBudgets;
	private Map<String, Boolean> playersFinished;
	private Map<String, Boolean> playersWon;
	private Map<String, Boolean> playersBust;



	/**
	 * Constructor.
	 */
	public ClientModel(){
		this.connected = false;
		this.loggedIn = false;
		this.pushRequestQueue = new LinkedBlockingQueue<PushProtocol>();
		this.currentScreen = Screens.LOGINSCREEN;
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
			if (responseRegisterUser.getRequestSuccess() == 1){
				System.out.println("registration succesful");
			}
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

	/**
	 * getter for current screen
	 * @return
	 */
	public int getCurrentScreen() {
		return currentScreen;
	}

	/**
	 * getter for user list
	 * @return
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * getter for list of games
	 * @return
	 */
	public ArrayList<String> getListOfGames() {
		return listOfGames;
	}

	/**
	 * Setter for current screen.
	 * @param currentScreen
	 */
	public void setCurrentScreen(int currentScreen) {
		this.currentScreen = currentScreen;
	}

	/**
	 * Setter for list of games.
	 * @param listOfGames
	 */
	public void setListOfGames(ArrayList<String> listOfGames) {
		this.listOfGames = listOfGames;
	}

	/**
	 * getter for player names
	 * @return
	 */
	public ArrayList<String> getPlayerNames() {
		return playerNames;
	}

	/**
	 * getter for dealer hand
	 * @return
	 */
	public Hand getDealerHand() {
		return dealerHand;
	}

	/**
	 * getter for player hands
	 * @return
	 */
	public Map<String, Hand> getPlayerHands() {
		return playerHands;
	}

	/**
	 * getter for player bets
	 * @return
	 */
	public Map<String, Integer> getPlayerBets() {
		return playerBets;
	}

	/**
	 * getter for player budgets
	 * @return
	 */
	public Map<String, Integer> getPlayerBudgets() {
		return playerBudgets;
	}

	/**
	 * getter for players finished
	 * @return
	 */
	public Map<String, Boolean> getPlayersFinished() {
		return playersFinished;
	}

	/**
	 * getter for players won
	 * @return
	 */
	public Map<String, Boolean> getPlayersWon() {
		return playersWon;
	}

	/**
	 * getter for players bust
	 * @return
	 */
	public Map<String, Boolean> getPlayersBust() {
		return playersBust;
	}

	/**
	 * setter for player names
	 * @param playerNames
	 */
	public void setPlayerNames(ArrayList<String> playerNames) {
		this.playerNames = playerNames;
	}

	/**
	 * setter for dealerHand
	 * @param dealerHand
	 */
	public void setDealerHand(Hand dealerHand) {
		this.dealerHand = dealerHand;
	}

	/**
	 * setter for playerhands
	 * @param playerHands
	 */
	public void setPlayerHands(Map<String, Hand> playerHands) {
		this.playerHands = playerHands;
	}

	/**
	 * setter for player bets
	 * @param playerBets
	 */
	public void setPlayerBets(Map<String, Integer> playerBets) {
		this.playerBets = playerBets;
	}

	/**
	 * setter for player budgets
	 * @param playerBudgets
	 */
	public void setPlayerBudgets(Map<String, Integer> playerBudgets) {
		this.playerBudgets = playerBudgets;
	}

	/**
	 * setter for players finished
	 * @param playersFinished
	 */
	public void setPlayersFinished(Map<String, Boolean> playersFinished) {
		this.playersFinished = playersFinished;
	}

	/**
	 * setter for players won
	 * @param playersWon
	 */
	public void setPlayersWon(Map<String, Boolean> playersWon) {
		this.playersWon = playersWon;
	}

	/**
	 * setter for players bust
	 * @param playersBust
	 */
	public void setPlayersBust(Map<String, Boolean> playersBust) {
		this.playersBust = playersBust;
	}
}
