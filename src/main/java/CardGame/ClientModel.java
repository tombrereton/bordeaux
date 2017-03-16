package CardGame;

import CardGame.GameEngine.BlackjackHand;
import CardGame.GameEngine.Hand;
import CardGame.Gui.Screens;
import CardGame.Pushes.PushProtocol;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    CardGameClient cardGameClient;
    ClientSideThread clientSideThread;
    Thread runningThread;
    PipedInputStream pin; // for listener clientSideThread
    PipedOutputStream pout; // for listener clientSideThread
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

    // Game variables
    private ArrayList<String> playerNames;
    private Hand dealerHand;
    private Map<String, Hand> playerHands;
    private Map<String, Integer> playerBets;
    private Map<String, Integer> playerBudgets;
    private Map<String, Boolean> playersFinished;
    private Map<String, Boolean> playersWon;
    private Map<String, Boolean> playersBust;
    private Map<String, Boolean> playersStand;
    private ArrayList<String> listOfGames;

    /**
     * Constructor.
     *
     * @throws IOException
     */
    public ClientModel() throws IOException {
        // set up pipes
        pout = new PipedOutputStream();
        pin = new PipedInputStream();
        pin.connect(pout);

        // get input stream from client side thread
        threadDataIn = new DataInputStream(pin);
        cardGameClient = new CardGameClient();
        clientSideThread = new ClientSideThread(this, cardGameClient);
        runningThread = new Thread(clientSideThread);
        runningThread.start();

        this.connected = false;
        this.loggedIn = false;
        this.pushRequestQueue = new LinkedBlockingQueue<PushProtocol>();
        this.currentScreen = Screens.LOGINSCREEN;

        // instantiate game variables
        this.playerNames = new ArrayList<>();
        this.playerHands = new HashMap<>();
        this.playerBudgets = new HashMap<>();
        this.playersBust = new HashMap<>();
        this.playersWon = new HashMap<>();
        this.playersStand = new HashMap<>();
        this.dealerHand = new BlackjackHand();
        this.listOfGames = new ArrayList<>();
    }

    /**
     * method that sends login request to server and updates loggedin and user
     * fields.
     *
     * @param username
     * @param password
     */
    public ResponseProtocol requestLogin(String username, String password) {
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);
        ResponseLoginUser responseLoginUser = null;
        try {
            RequestLoginUser request = new RequestLoginUser(user);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseLoginUser = gson.fromJson(responseString, ResponseLoginUser.class);

            // set logged in user on successful response
            if (responseLoginUser.getRequestSuccess() == SUCCESS) {
                setLoggedIn(true, responseLoginUser.getUser());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseLoginUser;
    }

    public ResponseProtocol requestLogOut() {
        ResponseLogOut responseLogOut = null;
        try {
            RequestLogOut request = new RequestLogOut(this.user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseLogOut = gson.fromJson(responseString, ResponseLogOut.class);
            if (responseLogOut.getRequestSuccess() == SUCCESS) {
                setLoggedIn(false, null);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseLogOut;
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
    public ResponseProtocol requestRegisterUser(String username, String password, String first, String last) {
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword, first, last);
        ResponseRegisterUser responseRegisterUser = null;
        try {
            RequestRegisterUser request = new RequestRegisterUser(user);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseRegisterUser = gson.fromJson(responseString, ResponseRegisterUser.class);
            if (responseRegisterUser.getRequestSuccess() == 1) {
                System.out.println("registration succesful");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseRegisterUser;

    }

    public String hashPassword(String password) {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return sha256hex;
    }

    public ResponseProtocol requestCreateGame() {
        ResponseCreateGame responseCreateGame = null;
        try {
            RequestCreateGame request = new RequestCreateGame(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseCreateGame = gson.fromJson(responseString, ResponseCreateGame.class);
            if (responseCreateGame.getRequestSuccess() == 1) {
                System.out.println("Created Game");
                String gameName = responseCreateGame.getGameName();
                listOfGames.add(gameName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCreateGame;
    }

    public ResponseProtocol requestJoinGame(String gamename) {
        ResponseJoinGame responseJoinGame = null;
        try {
            RequestJoinGame request = new RequestJoinGame(gamename, user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseJoinGame = gson.fromJson(responseString, ResponseJoinGame.class);
            if (responseJoinGame.getRequestSuccess() == 1) {
                setCurrentScreen(GAMESCREEN);
                System.out.println("Joined the Game");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseJoinGame;
    }

    public ResponseProtocol requestBet(int betAmount) {
        ResponseBet responseBet = null;
        try {
            RequestBet request = new RequestBet(betAmount, user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseBet = gson.fromJson(responseString, ResponseBet.class);
            if (responseBet.getRequestSuccess() == 1) {
                System.out.println("Bet!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBet;
    }


    public ResponseProtocol requestDoubleBet() {
        ResponseDoubleBet responseDoubleBet = null;
        try {
            RequestDoubleBet request = new RequestDoubleBet(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseDoubleBet = gson.fromJson(responseString, ResponseDoubleBet.class);
            if (responseDoubleBet.getRequestSuccess() == 1) {
                System.out.println("Double bet!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDoubleBet;
    }


    public ResponseProtocol requestHit() {
        ResponseHit responseHit = null;
        try {
            RequestHit request = new RequestHit(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseHit = gson.fromJson(responseString, ResponseHit.class);
            if (responseHit.getRequestSuccess() == 1) {
                System.out.println("Hit!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseHit;
    }

    public ResponseProtocol requestStand() {
        ResponseStand responseStand = null;
        try {
            RequestStand request = new RequestStand(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseStand = gson.fromJson(responseString, ResponseStand.class);
            if (responseStand.getRequestSuccess() == 1) {
                System.out.println("Stand!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStand;
    }

    public ResponseProtocol requestFold() {
        ResponseFold responseFold = null;
        try {
            RequestFold request = new RequestFold(user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseFold = gson.fromJson(responseString, ResponseFold.class);
            if (responseFold.getRequestSuccess() == 1) {
                System.out.println("Fold!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseFold;
    }

    public ResponseProtocol requestGetMessages(int offset) {
        ResponseGetMessages responseGetMessages = null;
        try {
            RequestGetMessages request = new RequestGetMessages(offset);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseGetMessages = gson.fromJson(responseString, ResponseGetMessages.class);
            if (responseGetMessages.getRequestSuccess() == 1) {
                System.out.println("Got messages from the server");
                System.out.println(responseGetMessages.getMessages());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseGetMessages;
    }

    public ResponseProtocol requestSendMessages(String message) {
        ResponseSendMessage responseSendMessage = null;
        try {
            RequestSendMessage request = new RequestSendMessage(user.getUserName(), message);
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseSendMessage = gson.fromJson(responseString, ResponseSendMessage.class);
            if (responseSendMessage.getRequestSuccess() == 1) {
                System.out.println("sent a messages to the server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseSendMessage;
    }


    public ResponseProtocol requestQuitGame(String gameToQuit) {
        ResponseQuitGame responseQuitGame = null;
        try {
            RequestQuitGame request = new RequestQuitGame(gameToQuit, user.getUserName());
            cardGameClient.sendRequest(request);
            String responseString = threadDataIn.readUTF();
            responseQuitGame = gson.fromJson(responseString, ResponseQuitGame.class);
            if (responseQuitGame.getRequestSuccess() == 1) {
                System.out.println("quit the game");
                setCurrentScreen(LOBBYSCREEN);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseQuitGame;
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
     * getter for players standing
     *
     * @return
     */
    public Map<String, Boolean> getPlayersStand() {
        return playersStand;
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


    /**
     * setter for players standing
     *
     * @param playersStand
     */
    public void setPlayersStand(Map<String, Boolean> playersStand) {
        this.playersStand = playersStand;
    }

    public PipedOutputStream getPout() {
        return pout;
    }

    public LinkedBlockingQueue<PushProtocol> getPushRequestQueue() {
        return pushRequestQueue;
    }
}
