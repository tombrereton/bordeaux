package CardGame;

import CardGame.GameEngine.Hand;
import CardGame.Gui.GameScreen;
import CardGame.Gui.Screens;
import CardGame.Pushes.PushProtocol;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;
import com.sun.tools.internal.ws.processor.model.Response;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

import static CardGame.Gui.Screens.*;
import static CardGame.ProtocolMessages.SUCCESS;


/**
 * The observable class that contains all the information to be displayed on the
 * client gui and methods for sending to server.
 *
 * @author Lloyd
 */
public class ClientModel extends Observable {
    // connectors
    CardGameClient cardGameClient = new CardGameClient();
    ClientSideThread thread;
    Thread runningThread;
    PipedInputStream pin = new PipedInputStream(); // for listener thread
    PipedOutputStream pout = new PipedOutputStream(); // for listener thread
    DataInputStream threadDataIn;

    // Booleans for states
    private boolean connected, loggedIn;

    // Screen state variable
    // this variable can only be set the predefined screens
    // in CardGame.Gui.Screens
    private int currentScreen;

    // fields
    Gson gson = new Gson();
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
     *
     * @throws IOException
     */
    public ClientModel() throws IOException {
        pin.connect(pout);
        threadDataIn = new DataInputStream(pin);
        thread = new ClientSideThread(this, cardGameClient);
        runningThread = new Thread(thread);
        runningThread.start();

        this.connected = false;
        this.loggedIn = false;
        this.pushRequestQueue = new LinkedBlockingQueue<PushProtocol>();
        this.currentScreen = Screens.LOGINSCREEN;
    }

    /**
     * method that sends login request to server and updates loggedin and user
     * fields.
     *
     * @param username
     * @param password
     */
    public void requestLogin(String username, String password) {
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);
        try {
            RequestLoginUser request = new RequestLoginUser(user);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseLoginUser responseLoginUser = gson.fromJson(responseString, ResponseLoginUser.class);
            if (responseLoginUser.getRequestSuccess() == SUCCESS) {
                setLoggedIn(true, responseLoginUser.getUser());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void requestLogOut() {
        try {
            RequestLogOut request = new RequestLogOut(this.user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseLogOut responseLogOut = gson.fromJson(responseString, ResponseLogOut.class);
            if (responseLogOut.getRequestSuccess() == SUCCESS) {
                setLoggedIn(false, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * helper method for login and logout
     *
     * @param bool
     * @param user
     */
    public void setLoggedIn(boolean bool, User user) {
        this.loggedIn = bool;
        this.user = user;
        if (loggedIn == true) {
            setCurrentScreen(HOMESCREEN);
        } else {
            setCurrentScreen(LOGINSCREEN);
        }
    }

    /**
     * Method for sending a request to register a user.
     *
     * @param username
     * @param password
     * @param first
     * @param last
     */
    public void requestRegisterUser(String username, String password, String first, String last) {
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword, first, last);
        try {
            RequestRegisterUser request = new RequestRegisterUser(user);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseRegisterUser responseRegisterUser = gson.fromJson(responseString, ResponseRegisterUser.class);
            if (responseRegisterUser.getRequestSuccess() == 1) {
                System.out.println("registration succesful");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String hashPassword(String password) {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return sha256hex;
    }

    public void requestCreateGame() {
        try {
            RequestCreateGame request = new RequestCreateGame(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseCreateGame responseCreateGame = gson.fromJson(responseString, ResponseCreateGame.class);
            if (responseCreateGame.getRequestSuccess() == 1) {
                System.out.println("Created Game");
                listOfGames.add(responseCreateGame.getGameName());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public void requestJoinGame(String gamename){
        try {
            RequestJoinGame request = new RequestJoinGame(gamename,user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseJoinGame responseJoinGame = gson.fromJson(responseString, ResponseJoinGame.class);
            if (responseJoinGame.getRequestSuccess() == 1){
                setCurrentScreen(GAMESCREEN);
                System.out.println("Joined the Game");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public void requestBet(int betAmount){
        try {
            RequestBet request = new RequestBet(betAmount,user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseBet responseBet = gson.fromJson(responseString, ResponseBet.class);
            if (responseBet.getRequestSuccess() == 1){
                System.out.println("Bet!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void requestDoubleBet(){
        try {
            RequestDoubleBet request = new RequestDoubleBet(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseDoubleBet responseDoubleBet = gson.fromJson(responseString, ResponseDoubleBet.class);
            if (responseDoubleBet.getRequestSuccess() == 1){
                System.out.println("Double bet!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void requestHit(){
        try {
            RequestHit request = new RequestHit(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseHit responseHit = gson.fromJson(responseString, ResponseHit.class);
            if (responseHit.getRequestSuccess() == 1){
                System.out.println("Hit!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestStand(){
        try {
            RequestStand request = new RequestStand(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseStand responseStand = gson.fromJson(responseString, ResponseStand.class);
            if (responseStand.getRequestSuccess() == 1){
                System.out.println("Stand!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestFold(){
        try {
            RequestFold request = new RequestFold(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseFold responseFold = gson.fromJson(responseString, ResponseFold.class);
            if (responseFold.getRequestSuccess() == 1){
                System.out.println("Fold!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestGetMessages(int offset){
        try {
            RequestGetMessages request = new RequestGetMessages(offset);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseGetMessages responseGetMessages = gson.fromJson(responseString, ResponseGetMessages.class);
            if (responseGetMessages.getRequestSuccess() == 1){
                System.out.println("Got messages from the server");
                System.out.println(responseString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestSendMessages(String message){
        try {
            RequestSendMessage request = new RequestSendMessage(user.getUserName(),message);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseSendMessage responseSendMessage = gson.fromJson(responseString, ResponseSendMessage.class);
            if (responseSendMessage.getRequestSuccess() == 1){
                System.out.println("sent a messages to the server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void requestQuitGame(String gameToQuit){
        try {
            RequestQuitGame request = new RequestQuitGame(gameToQuit,user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            ResponseQuitGame responseQuitGame = gson.fromJson(responseString, ResponseQuitGame.class);
            if (responseQuitGame.getRequestSuccess() == 1){
                System.out.println("quit the game");
                setCurrentScreen(LOBBYSCREEN);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * getter for cardgameclient object.
     *
     * @return
     */
    public CardGameClient getCardGameClient() {
        return cardGameClient;
    }

    /**
     * getter for connected
     *
     * @return
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * getter for boolean logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * getter for user
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * getter for current screen
     *
     * @return
     */
    public int getCurrentScreen() {
        return currentScreen;
    }

    /**
     * getter for user list
     *
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * getter for list of games
     *
     * @return
     */
    public ArrayList<String> getListOfGames() {
        return listOfGames;
    }

    /**
     * Setter for current screen.
     *
     * @param currentScreen
     */
    public void setCurrentScreen(int currentScreen) {
        this.currentScreen = currentScreen;
        setChanged();
        notifyObservers();
    }

    /**
     * Setter for list of games.
     *
     * @param listOfGames
     */
    public void setListOfGames(ArrayList<String> listOfGames) {
        this.listOfGames = listOfGames;
    }

    /**
     * getter for player names
     *
     * @return
     */
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * getter for dealer hand
     *
     * @return
     */
    public Hand getDealerHand() {
        return dealerHand;
    }

    /**
     * getter for player hands
     *
     * @return
     */
    public Map<String, Hand> getPlayerHands() {
        return playerHands;
    }

    /**
     * getter for player bets
     *
     * @return
     */
    public Map<String, Integer> getPlayerBets() {
        return playerBets;
    }

    /**
     * getter for player budgets
     *
     * @return
     */
    public Map<String, Integer> getPlayerBudgets() {
        return playerBudgets;
    }

    /**
     * getter for players finished
     *
     * @return
     */
    public Map<String, Boolean> getPlayersFinished() {
        return playersFinished;
    }

    /**
     * getter for players won
     *
     * @return
     */
    public Map<String, Boolean> getPlayersWon() {
        return playersWon;
    }

    /**
     * getter for players bust
     *
     * @return
     */
    public Map<String, Boolean> getPlayersBust() {
        return playersBust;
    }

    /**
     * setter for player names
     *
     * @param playerNames
     */
    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    /**
     * setter for dealerHand
     *
     * @param dealerHand
     */
    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    /**
     * setter for playerhands
     *
     * @param playerHands
     */
    public void setPlayerHands(Map<String, Hand> playerHands) {
        this.playerHands = playerHands;
    }

    /**
     * setter for player bets
     *
     * @param playerBets
     */
    public void setPlayerBets(Map<String, Integer> playerBets) {
        this.playerBets = playerBets;
    }

    /**
     * setter for player budgets
     *
     * @param playerBudgets
     */
    public void setPlayerBudgets(Map<String, Integer> playerBudgets) {
        this.playerBudgets = playerBudgets;
    }

    /**
     * setter for players finished
     *
     * @param playersFinished
     */
    public void setPlayersFinished(Map<String, Boolean> playersFinished) {
        this.playersFinished = playersFinished;
    }

    /**
     * setter for players won
     *
     * @param playersWon
     */
    public void setPlayersWon(Map<String, Boolean> playersWon) {
        this.playersWon = playersWon;
    }

    /**
     * setter for players bust
     *
     * @param playersBust
     */
    public void setPlayersBust(Map<String, Boolean> playersBust) {
        this.playersBust = playersBust;
    }

    public PipedOutputStream getPout() {
        return pout;
    }

    public LinkedBlockingQueue<PushProtocol> getPushRequestQueue() {
        return pushRequestQueue;
    }
}
