package CardGame;

import CardGame.GameEngine.Hand;
import CardGame.Pushes.*;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;

import static CardGame.Gui.Screens.*;

/**
 * This client connects to the servers.
 * Sends requests to the server.
 * Handles the response from the server.
 * Contains and updates client side data.
 * Notifies observers when there is a change (response).
 * <p>
 * Created by tom on 17/03/17.
 */
public class GameClient extends Observable {

    // connection variables
    private final String HOST;
    private final int PORT;
    private Socket socket;
    private DataInputStream serverInputStream;
    private DataOutputStream serverOutputStream;
    private Gson gson;
    private User loggedInUser;

    // Screen state variable
    private int currentScreen;

    // game variables
    private volatile ConcurrentSkipListSet<String> listOfGames;
    private String gameJoined;

    // game data
    private boolean isGameDataUpdated;
    private Map<String, Boolean> playersFinished;
    private Hand dealerHand;
    private Map<String, Integer> playerBets;
    private Map<String, Integer> playerBudgets;
    private Map<String, Hand> playerHands;
    private Set<String> playerNames;
    private Map<String, Boolean> playersBust;
    private Map<String, Boolean> playersStand;
    private Map<String, Boolean> playersWon;


    // chat variables
    private int chatOffset;
    private volatile ConcurrentLinkedDeque<MessageObject> messages;

    // Threads
    private Thread gameNamesThread;
    private Thread getMessagesThread;
    private Thread getGameData;
    private volatile boolean isGettingGames;
    private volatile boolean isGettingMessages;
    private volatile boolean isGettingGameData;


    public GameClient(String HOST, int PORT) {
        // connection variables
        this.HOST = HOST;
        this.PORT = PORT;
        this.gson = new Gson();

        connectToServer();

        // set current screen to login
        setCurrentScreen(LOGINSCREEN);

        // instantiate game variables
        this.chatOffset = -1;
        this.listOfGames = new ConcurrentSkipListSet<>();
        playersFinished = new TreeMap<>();
        dealerHand  = new Hand();
        playerBets  = new TreeMap<>();
        playerBudgets = new TreeMap<>();
        playerHands = new TreeMap<>();
        playerNames = new TreeSet<>();
        playersBust = new TreeMap<>();
        playersStand = new TreeMap<>();
        playersWon = new TreeMap<>();

        // chat variables
        this.messages = new ConcurrentLinkedDeque<>();
    }

    /**
     * This method connects to the server.
     */
    private void connectToServer() {
        try {
            // connect to server
            this.socket = new Socket(this.HOST, this.PORT);
            System.out.println("Connected to server.");
            connectDataStreams();
        } catch (ConnectException e) {
            System.out.println("Cannot connect to server. Ensure server is up.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method connects the streams with the server
     *
     * @throws IOException
     */
    private void connectDataStreams() throws IOException {
        serverInputStream = new DataInputStream(socket.getInputStream());
        serverOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * This method closes all open connections.
     */
    private void closeConnections() {
        try {
            this.serverInputStream.close();
            this.serverOutputStream.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter for logged in user
     *
     * @return
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * setter for logged in user
     *
     * @param loggedInUser
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    // OVERALL REQUEST METHOD

    /**
     * This method takes in the request and writes it to the server.
     *
     * @param request
     * @param <T>
     */
    public synchronized <T extends RequestProtocol> void sendRequest(T request) {
        // convert to json string
        String jsonOutput = gson.toJson(request);

        try {
            // write request to server
            this.serverOutputStream.writeUTF(jsonOutput);
            this.serverOutputStream.flush();
        } catch (NullPointerException e) {
            System.out.println("Server down.");
        } catch (IOException e) {
            System.out.println("Server down.");
        }
    }

    // RESPONSE METHODS

    /**
     * This method takes in the expected response class. It reads from the server and
     * returns the response.
     *
     * @param responseClass
     * @param <T>
     * @return
     */
    public synchronized <T> T getResponse(Class<T> responseClass) {
        String jsonInput = null;
        try {
            // read response from server
            jsonInput = this.serverInputStream.readUTF();

            // print out response
            System.out.println(jsonInput);
        } catch (IOException e) {
            System.out.println("Server down. Please try restarting client");
        }

        setChanged();
        notifyObservers();

        // return the correct response
        return handleResponse(responseClass, jsonInput);
    }

    /**
     * This method takes in the expected response class and the jsonInput read from the server.
     * It then returns the expected response.
     *
     * @param responseClass
     * @param jsonInput
     * @param <T>
     * @return
     */
    private <T> T handleResponse(Class<T> responseClass, String jsonInput) {
        // return the correct response
        return responseClass.cast(gson.fromJson(jsonInput, responseClass));
    }

    /**
     * This method hashes the password.
     *
     * @param password
     * @return
     */
    public String hashPassword(String password) {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return sha256hex;
    }

    // SPECIFIC REQUEST METHODS

    /**
     * method that sends login request to server and updates loggedin and user
     * fields.
     *
     * @param username
     * @param password
     */
    public synchronized ResponseLoginUser requestLogin(String username, String password) {
        // hash password and create user
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);

        // create and send login request
        RequestLoginUser requestLoginUser = new RequestLoginUser(user);
        sendRequest(requestLoginUser);

        ResponseLoginUser responseLoginUser = null;
        try {
            // get the response from the server
            responseLoginUser = getResponse(ResponseLoginUser.class);
        } catch (NullPointerException e) {
            System.out.println("Cannot log in, trying to reconnect.");
            connectToServer();
        }
        int success = responseLoginUser.getRequestSuccess();

        // log user in if successful
        if (success == 1) {
            setLoggedInUser(responseLoginUser.getUser());
            setCurrentScreen(HOMESCREEN);
            startGettingGameNames();
        }

        // return response
        return responseLoginUser;
    }

    /**
     * This method send a request and returns a response for register user.
     *
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return
     */
    public synchronized ResponseRegisterUser requestRegisterUser(String username, String password, String firstName, String lastName) {
        // hash password and create user
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword, firstName, lastName);

        // create request
        RequestRegisterUser requestRegisterUser = new RequestRegisterUser(user);
        sendRequest(requestRegisterUser);

        // get the response from the server
        ResponseRegisterUser responseRegisterUser = getResponse(ResponseRegisterUser.class);
        int success = responseRegisterUser.getRequestSuccess();

        if (success == 1) {
            setCurrentScreen(LOGINSCREEN);
        }

        return responseRegisterUser;
    }


    /**
     * log out request
     *
     * @return
     */
    public synchronized ResponseLogOut requestLogOut() {
        // create request and send request
        RequestLogOut requestLogOut = new RequestLogOut(getLoggedInUser().getUserName());
        sendRequest(requestLogOut);

        // get response from server
        ResponseLogOut responseLogOut = getResponse(ResponseLogOut.class);
        int success = responseLogOut.getRequestSuccess();

        // log user out if successful
        if (success == 1) {
            setLoggedInUser(null);
            setCurrentScreen(LOGINSCREEN);
            stopGettingGameNames();
        }

        return responseLogOut;
    }

    /**
     * send message request
     *
     * @param message
     * @return
     */
    public synchronized ResponseSendMessage requestSendMessage(String message) {
        // create request and send request
        RequestSendMessage requestSendMessage = new RequestSendMessage(getLoggedInUser().getUserName(), message);
        sendRequest(requestSendMessage);

        // get response from server and return it
        return getResponse(ResponseSendMessage.class);
    }

    /**
     * send message request
     *
     * @param offset offset
     * @return
     */

    public synchronized ResponseGetMessages requestGetMessages(int offset) {
        // create request and send request
        RequestGetMessages requestGetMessages = new RequestGetMessages(offset);
        sendRequest(requestGetMessages);

        // get response from server and returnit
        return getResponse(ResponseGetMessages.class);
    }

    /**
     * send create game request
     *
     * @return
     */
    public synchronized ResponseCreateGame requestCreateGame() {
        // create request and send request
        RequestCreateGame requestCreateGame = new RequestCreateGame(getLoggedInUser().getUserName());
        sendRequest(requestCreateGame);

        // get response from server and return it
        return getResponse(ResponseCreateGame.class);
    }

    /**
     * send join game request
     *
     * @param gameToJoin
     * @return
     */
    public synchronized ResponseJoinGame requestJoinGame(String gameToJoin) {
        // create request and send request
        RequestJoinGame requestJoinGame = new RequestJoinGame(gameToJoin, getLoggedInUser().getUserName());
        sendRequest(requestJoinGame);


        // get response from server
        ResponseJoinGame responseJoinGame = getResponse(ResponseJoinGame.class);
        int success = responseJoinGame.getRequestSuccess();

        if (success == 1) {
            setCurrentScreen(GAMESCREEN);
            stopGettingGameNames();
            startGettingMessages();
            startGettingGameData();
            setGameJoined(gameToJoin);
        }

        return responseJoinGame;
    }

    /**
     * send quit game request
     *
     * @param gameToQuit
     * @return
     */
    public synchronized ResponseQuitGame requestQuitGame(String gameToQuit) {
        // create request and send request
        RequestQuitGame requestQuitGame = new RequestQuitGame(gameToQuit, getLoggedInUser().getUserName());
        sendRequest(requestQuitGame);

        // get response from server
        ResponseQuitGame responseQuitGame = getResponse(ResponseQuitGame.class);
        int success = responseQuitGame.getRequestSuccess();

        if (success == 1) {
            setCurrentScreen(LOBBYSCREEN);
            stopGettingMessages();
            stopGettingGameData();
            listOfGames.clear();
            startGettingGameNames();
            messages.clear();
            setChatOffset(-1);

//            setChanged();
//            notifyObservers();
        }

        return responseQuitGame;

    }

    /**
     * send bet request
     *
     * @param betAmount
     * @return
     */
    public synchronized ResponseBet requestBet(int betAmount) {
        // create request and send request
        RequestBet requestBet = new RequestBet(betAmount, getLoggedInUser().getUserName());
        sendRequest(requestBet);

        // get response from server and returnit
        return getResponse(ResponseBet.class);
    }

    /**
     * send hit request
     *
     * @return
     */
    public synchronized ResponseHit requestHit() {
        // create request and send request
        RequestHit requestHit = new RequestHit(getLoggedInUser().getUserName());
        sendRequest(requestHit);

        // get response from server and returnit
        return getResponse(ResponseHit.class);
    }

    /**
     * send double bet request
     *
     * @return
     */
    public synchronized ResponseDoubleBet requestDoubleBet() {
        // create request and send request
        RequestDoubleBet requestDoubleBet = new RequestDoubleBet(getLoggedInUser().getUserName());
        sendRequest(requestDoubleBet);

        // get response from server and returnit
        return getResponse(ResponseDoubleBet.class);
    }

    /**
     * send fold bet request
     *
     * @return
     */
    public synchronized ResponseFold requestFold() {
        // create request and send request
        RequestFold requestFold = new RequestFold(getLoggedInUser().getUserName());
        sendRequest(requestFold);

        // get response from server and returnit
        return getResponse(ResponseFold.class);
    }

    /**
     * send stand request
     *
     * @return
     */
    public synchronized ResponseStand requestStand() {
        // create request and send request
        RequestStand requestStand = new RequestStand(getLoggedInUser().getUserName());
        sendRequest(requestStand);

        // get response from server and returnit
        return getResponse(ResponseStand.class);
    }

    public synchronized PushDealerHand requestGetDealerHand() {
        // create request and send request
        RequestGetDealerHand requestGetDealerHand = new RequestGetDealerHand();
        sendRequest(requestGetDealerHand);

        // get response from server and returnit
        PushDealerHand pushDealerHand = getResponse(PushDealerHand.class);
        this.dealerHand = pushDealerHand.getDealerHand();

        return pushDealerHand;
    }

    public synchronized PushGameNames requestGetGameNames() {
        // create request and send request
        RequestGetGameNames requestGetGameNames = new RequestGetGameNames();
        sendRequest(requestGetGameNames);

        // get response from server and returnit
        return getResponse(PushGameNames.class);
    }

    public synchronized PushPlayerBets requestGetPlayerBets() {
        // create request and send request
        RequestGetPlayerBets requestGetPlayerBets = new RequestGetPlayerBets();
        sendRequest(requestGetPlayerBets);

        // get response from server and returnit
        PushPlayerBets pushPlayerBets = getResponse(PushPlayerBets.class);
        this.playerBets = pushPlayerBets.getPlayerBets();
        return pushPlayerBets;
    }

    public synchronized PushPlayerBudgets requestGetPlayerBudgets() {
        // create request and send request
        RequestGetPlayerBudgets requestGetPlayerBudgets = new RequestGetPlayerBudgets();
        sendRequest(requestGetPlayerBudgets);

        // get response from server and returnit
        PushPlayerBudgets pushPlayerBudgets = getResponse(PushPlayerBudgets.class);
        this.playerBudgets = pushPlayerBudgets.getPlayerBudgets();
        return pushPlayerBudgets;
    }

    public synchronized PushPlayersBust requestGetPlayersBust() {
        // create request and send request
        RequestGetPlayersBust requestGetPlayersBust = new RequestGetPlayersBust();
        sendRequest(requestGetPlayersBust);

        // get response from server and returnit
        PushPlayersBust pushPlayersBust = getResponse(PushPlayersBust.class);
        playersBust = pushPlayersBust.getPlayersBust();

        return pushPlayersBust;
    }

    public synchronized PushPlayerHands requestGetPlayerHands() {
        // create request and send request
        RequestGetPlayerHands requestGetPlayerHands = new RequestGetPlayerHands();
        sendRequest(requestGetPlayerHands);

        // get response from server and returnit
        PushPlayerHands pushPlayerHands = getResponse(PushPlayerHands.class);
        this.playerHands = pushPlayerHands.getPlayerHands();
        return pushPlayerHands;
    }

    public synchronized PushPlayerNames requestGetPlayerNames() {
        // create request and send request
        RequestGetPlayerNames requestGetPlayerNames = new RequestGetPlayerNames();
        sendRequest(requestGetPlayerNames);

        // get response from server and returnit
        PushPlayerNames pushPlayerNames = getResponse(PushPlayerNames.class);
        playerNames = pushPlayerNames.getPlayerNames();

        return pushPlayerNames;
    }

    public synchronized PushPlayersStand requestGetPlayersStand() {
        // create request and send request
        RequestGetPlayersStand requestGetPlayersStand = new RequestGetPlayersStand();
        sendRequest(requestGetPlayersStand);

        // get response from server and returnit
        PushPlayersStand pushPlayersStand = getResponse(PushPlayersStand.class);
        playersStand = pushPlayersStand.getPlayersStand();

        return pushPlayersStand;
    }

    public synchronized PushPlayersWon requestGetPlayersWon() {
        // create request and send request
        RequestGetPlayersWon requestGetPlayersWon = new RequestGetPlayersWon();
        sendRequest(requestGetPlayersWon);

        // get response from server and returnit
        PushPlayersWon pushPlayersWon = getResponse(PushPlayersWon.class);
        playersWon = pushPlayersWon.getPlayersWon();

        return pushPlayersWon;
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
     * Setter for current screen.
     *
     * @param currentScreen
     */
    public void setCurrentScreen(int currentScreen) {
        this.currentScreen = currentScreen;
        setChanged();
        notifyObservers();
    }


    public ConcurrentSkipListSet<String> getListOfGames() {
        return listOfGames;
    }

    public int getChatOffset() {
        return chatOffset;
    }

    public void setChatOffset(int chatOffset) {
        this.chatOffset = chatOffset;
    }

    // THREAD FOR GETTING GAME NAMES

    /**
     * This method starts a thread which polls for game names
     */
    public void startGettingGameNames() {

        // set flag to true
        isGettingGames = true;

        // create the job
        Runnable gameNamesJob = () -> {

            try {
                while (isGettingGames) {
                    PushGameNames pushGameNames = requestGetGameNames();

                    Set<String> responseGamesNames = pushGameNames.getGameNames();
                    getListOfGames().clear();
                    getListOfGames().addAll(responseGamesNames);

                    Thread.sleep(1000);
                }
            } catch (NullPointerException e) {
                System.out.println("Can't get game names. Server down.");
            } catch (InterruptedException e) {
                System.out.println("Polling for game list interrupted.");
            } //finally {
//                    while (socket.isClosed()){
//                        connectToServer();
//                        try {
//                            requestLogin(getLoggedInUser().getUserName(), getLoggedInUser().getPassword());
//                            Thread.sleep(2000);
//                        } catch (NullPointerException e){
//                            System.out.println("Still can't log in, server is down.");
//                        } catch (InterruptedException e) {
//                            System.out.println("Trying to reconnect");
//                        }
//                    }
//                }
        };

        gameNamesThread = new Thread(gameNamesJob);

        // start the thread
        gameNamesThread.start();
    }

    public void stopGettingGameNames() {
        isGettingGames = false;
    }

    // THREAD FOR GETTING MESSAGES

    /**
     * This method polls the server for messages
     */
    public void startGettingMessages() {

        this.isGettingMessages = true;

        // create the job
        Runnable getMessagesJob = () -> {

            while (isGettingMessages) {

                // get the client offset and send a request for the messages
                int clientOffset = getChatOffset();
                ResponseGetMessages responseGetMessages = requestGetMessages(clientOffset);

                // if the response is successful and client offset is less than response offset
                int success = responseGetMessages.getRequestSuccess();
                int responseOffset = responseGetMessages.getOffset();
                if (success == 1 && clientOffset < responseOffset) {

                    // get message arrayList
                    ArrayList<MessageObject> messages = responseGetMessages.getMessages();

                    // add messages to message queue
                    addMessages(messages);

                    // set client offset to response offset to avoid getting old messages
                    setChatOffset(responseOffset - 1);
                }

                try {
                    Thread.sleep(100);
                } catch (NullPointerException e) {
                    System.out.println("Server down.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // create the thread
        getMessagesThread = new Thread(getMessagesJob);

        // start the thread
        getMessagesThread.start();
    }

    public void stopGettingMessages() {
        this.isGettingMessages = false;
    }

    /**
     * This method sends requests for the game data
     */
    public void getGameData() {

        isGameDataUpdated = false;

        // Dealer hand
        requestGetDealerHand();

        // player bets
        requestGetPlayerBets();

        // player budgets
        requestGetPlayerBudgets();

        // player hands
        requestGetPlayerHands();

        // player names
        requestGetPlayerNames();

        // players bust
        requestGetPlayersBust();

        // players stand
        requestGetPlayersStand();

        // players won
        requestGetPlayersWon();

        isGameDataUpdated = true;

    }


    /**
     * This method starts a thread to poll for game data
     */
    public void startGettingGameData() {

        this.isGettingGameData = true;

        // create the job
        Runnable getGameDataJob = () -> {

            while (isGettingGameData) {

                // get game data
                getGameData();


                // sleep thread for 1000
                try {
                    Thread.sleep(1000);
                } catch (NullPointerException e) {
                    System.out.println("Server down.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // create the thread
        getGameData = new Thread(getGameDataJob);

        // start thread
        getGameData.start();

    }

    public void stopGettingGameData() {
        this.isGettingGameData = false;
    }

    public ConcurrentLinkedDeque<MessageObject> getMessages() {
        return messages;
    }

    public synchronized void addMessages(ArrayList<MessageObject> messages) {
        for (MessageObject mo : messages) {
            this.messages.add(mo);
        }
    }

    public String getGameJoined() {
        return gameJoined;
    }

    public void setGameJoined(String gameJoined) {
        this.gameJoined = gameJoined;
    }


    public Hand getDealerHand() {
        return dealerHand;
    }

    public Map<String, Integer> getPlayerBets() {
        return playerBets;
    }

    public Map<String, Integer> getPlayerBudgets() {
        return playerBudgets;
    }

    public Map<String, Hand> getPlayerHands() {
        return playerHands;
    }

    public Set<String> getPlayerNames() {
        return playerNames;
    }

    public Map<String, Boolean> getPlayersBust() {
        return playersBust;
    }

    public Map<String, Boolean> getPlayersStand() {
        return playersStand;
    }

}


