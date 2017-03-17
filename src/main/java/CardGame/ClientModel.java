package CardGame;

import CardGame.GameEngine.BlackjackHand;
import CardGame.GameEngine.Hand;
import CardGame.Gui.Screens;
import CardGame.Pushes.PushGameNames;
import CardGame.Pushes.PushProtocol;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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

    // Game variables
    private volatile LinkedBlockingQueue<PushProtocol> pushProtocolQueue;
    private volatile CopyOnWriteArrayList<String> playerNames;
    private volatile Hand dealerHand;
    private volatile ConcurrentHashMap<String, Hand> playerHands;
    private volatile ConcurrentHashMap<String, Integer> playerBets;
    private volatile ConcurrentHashMap<String, Integer> playerBudgets;
    private volatile ConcurrentHashMap<String, Boolean> playersFinished;
    private volatile ConcurrentHashMap<String, Boolean> playersWon;
    private volatile ConcurrentHashMap<String, Boolean> playersBust;
    private volatile ConcurrentHashMap<String, Boolean> playersStand;
    private volatile CopyOnWriteArrayList<String> listOfGames;

    // chat variables
    private int chatOffset;

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
        this.pushProtocolQueue = new LinkedBlockingQueue<PushProtocol>();
        this.currentScreen = Screens.LOGINSCREEN;

        // instantiate game variables
        this.playerNames = new CopyOnWriteArrayList<>();
        this.playerHands = new ConcurrentHashMap<>();
        this.playerBudgets = new ConcurrentHashMap<>();
        this.playersBust = new ConcurrentHashMap<>();
        this.playersWon = new ConcurrentHashMap<>();
        this.playersStand = new ConcurrentHashMap<>();
        this.dealerHand = new BlackjackHand();
        this.listOfGames = new CopyOnWriteArrayList<>();

        this.chatOffset = -1;

        // method which starts a thread to listen for pushes
//        getPushFromQueue();
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
//                String gameName = responseCreateGame.getGameName();
//                listOfGames.add(gameName);
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

    // PUSHES

    /**
     * This method gets pushes from the queue
     */
    public void getPushFromQueue(){

        // We start a new thread to handle incoming pushes
        Thread pushThread = new Thread(new Runnable() {

            @Override
            public void run() {

                // loop while the queue is not empty
                while (true){

                    // get the push from the queue
                    try {
                        PushProtocol push = pushProtocolQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // make thread sleep
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void handlePush(String jsonString){
        ResponseProtocol push = PushProtocol.decodeResponse(jsonString);
        int pushType = push.getType();

        switch (pushType){
            case ProtocolTypes.PUSH_GAME_NAMES:
                PushGameNames pushGameNames = gson.fromJson(jsonString, PushGameNames.class);
                ArrayList<String> gamesNames =  pushGameNames.getGameNames();
                this.getListOfGames().remove(gamesNames);
                this.getListOfGames().addAll(gamesNames);
                notifyObservers();
                break;
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
    public CopyOnWriteArrayList<String> getListOfGames() {
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
     * Getter for chatoffset
     * @return
     */
    public int getChatOffset() {
        return chatOffset;
    }

    /**
     * setter for chat offset
     * @param chatOffset
     */
    public void setChatOffset(int chatOffset) {
        this.chatOffset = chatOffset;
    }

    /**
     * Setter for list of games.
     *
     * @param listOfGames
     */
    public void setListOfGames(CopyOnWriteArrayList<String> listOfGames) {
        this.listOfGames = listOfGames;
    }

    /**
     * getter for player names
     *
     * @return
     */
    public CopyOnWriteArrayList<String> getPlayerNames() {
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
    public void setPlayerNames(CopyOnWriteArrayList<String> playerNames) {
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
    public void setPlayerHands(ConcurrentHashMap<String, Hand> playerHands) {
        this.playerHands = playerHands;
    }

    /**
     * setter for player bets
     *
     * @param playerBets
     */
    public void setPlayerBets(ConcurrentHashMap<String, Integer> playerBets) {
        this.playerBets = playerBets;
    }

    /**
     * setter for player budgets
     *
     * @param playerBudgets
     */
    public void setPlayerBudgets(ConcurrentHashMap<String, Integer> playerBudgets) {
        this.playerBudgets = playerBudgets;
    }

    /**
     * setter for players finished
     *
     * @param playersFinished
     */
    public void setPlayersFinished(ConcurrentHashMap<String, Boolean> playersFinished) {
        this.playersFinished = playersFinished;
    }

    /**
     * setter for players won
     *
     * @param playersWon
     */
    public void setPlayersWon(ConcurrentHashMap<String, Boolean> playersWon) {
        this.playersWon = playersWon;
    }

    /**
     * setter for players bust
     *
     * @param playersBust
     */
    public void setPlayersBust(ConcurrentHashMap<String, Boolean> playersBust) {
        this.playersBust = playersBust;
    }


    /**
     * setter for players standing
     *
     * @param playersStand
     */
    public void setPlayersStand(ConcurrentHashMap<String, Boolean> playersStand) {
        this.playersStand = playersStand;
    }


    public PipedOutputStream getPout() {
        return pout;
    }

    /**
     * getter for the push protocol queue
     * @return
     */
    public LinkedBlockingQueue<PushProtocol> getPushProtocolQueue() {
        return pushProtocolQueue;
    }
}
