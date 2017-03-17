package CardGame;

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
import java.util.Observable;
import java.util.concurrent.ConcurrentSkipListSet;

import static CardGame.Gui.Screens.HOMESCREEN;
import static CardGame.Gui.Screens.LOGINSCREEN;

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

    // chat variables
    private int chatOffset;


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
    public <T extends RequestProtocol> void sendRequest(T request) {
        // convert to json string
        String jsonOutput = gson.toJson(request);

        try {
            // write request to server
            this.serverOutputStream.writeUTF(jsonOutput);
            this.serverOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
    public <T> T getResponse(Class<T> responseClass) {
        String jsonInput = null;
        try {
            // read response from server
            jsonInput = this.serverInputStream.readUTF();

            // print out response
            System.out.println(jsonInput);
        } catch (IOException e) {
            e.printStackTrace();
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
    public ResponseLoginUser requestLogin(String username, String password) {
        // hash password and create user
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);

        // create and send login request
        RequestLoginUser requestLoginUser = new RequestLoginUser(user);
        sendRequest(requestLoginUser);

        // get the response from the server
        ResponseLoginUser responseLoginUser = getResponse(ResponseLoginUser.class);
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
    public ResponseRegisterUser requestRegisterUser(String username, String password, String firstName, String lastName) {
        // hash password and create user
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword, firstName, lastName);

        // create request
        RequestRegisterUser requestRegisterUser = new RequestRegisterUser(user);
        sendRequest(requestRegisterUser);

        return getResponse(ResponseRegisterUser.class);
    }


    /**
     * log out request
     *
     * @return
     */
    public ResponseLogOut requestLogOut() {
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
        }

        return responseLogOut;
    }

    /**
     * send message request
     *
     * @param message
     * @return
     */
    public ResponseSendMessage requestSendMessage(String message) {
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

    public ResponseGetMessages requestGetMessages(int offset) {
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
    public ResponseCreateGame requestCreateGame() {
        // create request and send request
        RequestCreateGame requestCreateGame = new RequestCreateGame(getLoggedInUser().getUserName());
        sendRequest(requestCreateGame);

        // get response from server and returnit
        return getResponse(ResponseCreateGame.class);
    }

    /**
     * send join game request
     *
     * @param gameToJoin
     * @return
     */
    public ResponseJoinGame requestJoinGame(String gameToJoin) {
        // create request and send request
        RequestJoinGame requestJoinGame = new RequestJoinGame(gameToJoin, getLoggedInUser().getUserName());
        sendRequest(requestJoinGame);

        // get response from server and returnit
        return getResponse(ResponseJoinGame.class);
    }

    /**
     * send quit game request
     *
     * @param gameToQuit
     * @return
     */
    public ResponseQuitGame requestQuitGame(String gameToQuit) {
        // create request and send request
        RequestQuitGame requestQuitGame = new RequestQuitGame(gameToQuit, getLoggedInUser().getUserName());
        sendRequest(requestQuitGame);

        // get response from server and returnit
        return getResponse(ResponseQuitGame.class);
    }

    /**
     * send bet request
     *
     * @param betAmount
     * @return
     */
    public ResponseBet requestBet(int betAmount) {
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
    public ResponseHit requestHit() {
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
    public ResponseDoubleBet requestDoubleBet() {
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
    public ResponseFold requestFold() {
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
    public ResponseStand requestStand() {
        // create request and send request
        RequestStand requestStand = new RequestStand(getLoggedInUser().getUserName());
        sendRequest(requestStand);

        // get response from server and returnit
        return getResponse(ResponseStand.class);
    }

    public PushDealerHand requestGetDealerHand() {
        // create request and send request
        RequestGetDealerHand requestGetDealerHand = new RequestGetDealerHand();
        sendRequest(requestGetDealerHand);

        // get response from server and returnit
        return getResponse(PushDealerHand.class);
    }

    public PushGameNames requestGetGameNames() {
        // create request and send request
        RequestGetGameNames requestGetGameNames = new RequestGetGameNames();
        sendRequest(requestGetGameNames);

        // get response from server and returnit
        return getResponse(PushGameNames.class);
    }

    public PushPlayerBets requestGetPlayerBets() {
        // create request and send request
        RequestGetPlayerBets requestGetPlayerBets = new RequestGetPlayerBets();
        sendRequest(requestGetPlayerBets);

        // get response from server and returnit
        return getResponse(PushPlayerBets.class);
    }

    public PushPlayerBudgets requestGetPlayerBudgets() {
        // create request and send request
        RequestGetPlayerBudgets requestGetPlayerBudgets = new RequestGetPlayerBudgets();
        sendRequest(requestGetPlayerBudgets);

        // get response from server and returnit
        return getResponse(PushPlayerBudgets.class);
    }

    public PushPlayersBust requestGetPlayersBust() {
        // create request and send request
        RequestGetPlayersBust requestGetPlayersBust = new RequestGetPlayersBust();
        sendRequest(requestGetPlayersBust);

        // get response from server and returnit
        return getResponse(PushPlayersBust.class);
    }

    public PushPlayerHands requestGetPlayerHands() {
        // create request and send request
        RequestGetPlayerHands requestGetPlayerHands = new RequestGetPlayerHands();
        sendRequest(requestGetPlayerHands);

        // get response from server and returnit
        return getResponse(PushPlayerHands.class);
    }

    public PushPlayerNames requestGetPlayerNames() {
        // create request and send request
        RequestGetPlayerNames requestGetPlayerNames = new RequestGetPlayerNames();
        sendRequest(requestGetPlayerNames);

        // get response from server and returnit
        return getResponse(PushPlayerNames.class);
    }

    public PushPlayersStand requestGetPlayersStand() {
        // create request and send request
        RequestGetPlayersStand requestGetPlayersStand = new RequestGetPlayersStand();
        sendRequest(requestGetPlayersStand);

        // get response from server and returnit
        return getResponse(PushPlayersStand.class);
    }

    public PushPlayersWon requestGetPlayersWon() {
        // create request and send request
        RequestGetPlayersWon requestGetPlayersWon = new RequestGetPlayersWon();
        sendRequest(requestGetPlayersWon);

        // get response from server and returnit
        return getResponse(PushPlayersWon.class);
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

        // create the job
        Runnable gameNamesJob = () -> {

            while (true) {
                PushGameNames pushGameNames = requestGetGameNames();
                getListOfGames().addAll(pushGameNames.getGameNames());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // create the thread
        Thread gameNamesThread = new Thread(gameNamesJob);

        // start the thread
        gameNamesThread.start();
    }

    public static void main(String[] args) {
        // connect to server
        GameClient client = new GameClient("localhost", 7654);
        client.connectToServer();

        // send login request to server
        ResponseLoginUser responseLoginUser = client.requestLogin("user111", "password");
        System.out.println(responseLoginUser);

        // close connections
        client.closeConnections();
    }


}
