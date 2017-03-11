package CardGame;

import CardGame.GameEngine.GameLobby;
import CardGame.Requests.*;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseRegisterUser;
import CardGame.Responses.ResponseSendMessage;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.ProtocolTypes.CREATE_GAME;
import static org.junit.Assert.assertEquals;

/**
 * This class tests the CardGameServer.
 * <p>
 * This inlcudes handling json objects and retrieving
 * and querying from the database.
 * <p>
 * Created by tom on 25/02/17.
 */
public class CardGameServerTest {

    // SET UP

    CardGameServer server;
    FunctionDB functionDB;
    ClientThread clientThread;
    CopyOnWriteArrayList<GameLobby> games = new CopyOnWriteArrayList<>();
    Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {
        functionDB = new FunctionDB();
        clientThread = new ClientThread(null, new ConcurrentLinkedDeque<MessageObject>(),
                new ConcurrentLinkedDeque<Socket>(), new CopyOnWriteArrayList<User>(), functionDB, games);
    }

    // TESTS


    /**
     * We test for registering an already registered user.
     * We expect a failed reponse with an error message
     * for duplicate username.
     */
    @Test
    public void registerUser01_test() {

        // We expect the user in the database
        String expected = "duplicate username in database.";
        int expectedSuccess = ProtocolMessages.FAIL;

        // Create user and requset object
        User user = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");
        RequestProtocol request = new RequestRegisterUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clienThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is register user
        int type = responseProtocol.getType();
        assertEquals("Type should match register user", ProtocolTypes.REGISTER_USER, type);

        // We check we tried to insert and got a duplicate entry exception
        ResponseRegisterUser responseRegisterUser = (ResponseRegisterUser) responseProtocol;
        String actual = responseRegisterUser.getErrorMsg();
        assertEquals("Should return error message matching expected ", expected, actual);

        // We check for failed response
        int actualSuccess = responseRegisterUser.getRequestSuccess();
        assertEquals("Should return failed response matching expected success ", expectedSuccess, actualSuccess);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }

    /**
     * We test for an empty user object
     */
    @Test
    public void registerUser02_test() {

        // We expect the user in the database
        String expected = ProtocolMessages.EMPTY_INSERT;
        int expectedSuccess = ProtocolMessages.FAIL;

        // Create user and requset object
        User user = new User("", "", "", "");
        RequestProtocol request = new RequestRegisterUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clienThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is register user
        int type = responseProtocol.getType();
        assertEquals("Type should match register user", ProtocolTypes.REGISTER_USER, type);

        // We check we tried to insert and got a duplicate entry exception
        ResponseRegisterUser responseRegisterUser = (ResponseRegisterUser) responseProtocol;
        String actual = responseRegisterUser.getErrorMsg();
        assertEquals("Should return error message matching expected ", expected, actual);

        // We check for failed response
        int actualSuccess = responseRegisterUser.getRequestSuccess();
        assertEquals("Should return failed response matching expected success ", expectedSuccess, actualSuccess);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }

    /**
     * We test for a null user object
     */
    @Test
    public void registerUser03_test() {

        // We expect the user in the database
        String expected = ProtocolMessages.EMPTY_INSERT;
        int expectedSuccess = ProtocolMessages.FAIL;

        // Create user and requset object
        User user = new User();
        RequestProtocol request = new RequestRegisterUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clienThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is register user
        int type = responseProtocol.getType();
        assertEquals("Type should match register user", ProtocolTypes.REGISTER_USER, type);

        // We check we tried to insert and got a duplicate entry exception
        ResponseRegisterUser responseRegisterUser = (ResponseRegisterUser) responseProtocol;
        String actual = responseRegisterUser.getErrorMsg();
        assertEquals("Should return error message matching expected ", expected, actual);

        // We check for failed response
        int actualSuccess = responseRegisterUser.getRequestSuccess();
        assertEquals("Should return failed response matching expected success ", expectedSuccess, actualSuccess);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }


    /**
     * We test for logging in a user with
     * a correct username and password.
     */
    @Test
    public void loginUser01_test() {
        int expected = 1; // 1 equals success

        // Create user and requset object
        User user = new User("N00b_D3STROYER", "password");
        RequestProtocol request = new RequestLoginUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actual);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);

    }

    /**
     * We test for an incorrect password of existing user
     */
    @Test
    public void loginUser02_test() {
        int expected = 0; // 0 equals fail

        // Create user and requset object
        User user = new User("N00b_D3STROYER", "wrongPassword");
        RequestProtocol request = new RequestLoginUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords do not match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 0 (signalling mismatch), matching expected ", expected, actual);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }

    /**
     * We test for incorrect username, with correct password
     */
    @Test
    public void loginUser03_test() {
        int expected = 0; // 0 equals fail

        // Create user and requset object
        User user = new User("N00b_D3STROYER_WRONG", "password");
        RequestProtocol request = new RequestLoginUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check we got a failed response back
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return a failed response, matching expected ", expected, actual);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }

    /**
     * We test for empty username and password
     */
    @Test
    public void loginUser04_test() {
        int expected = 0; // 0 equals fail

        // Create user and requset object
        User user = new User("", "");
        RequestProtocol request = new RequestLoginUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check we got a failed response back
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return a failed response, matching expected ", expected, actual);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }

/**
     * We test for empty username and password
     */
    @Test
    public void sendMessage01_test() {
        int expected = 0; // 0 equals fail

        // Create message object and request
        User user = new User("Test user", "pword");
        MessageObject messageObject = new MessageObject(user.getUserName(), "Hello this is a test.");
        RequestProtocol request = new RequestSendMessage(messageObject);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match send message", ProtocolTypes.SEND_MESSAGE, type);

        // We check we got a failed response back
        ResponseSendMessage responseSendMessage = (ResponseSendMessage) responseProtocol;
        int actual = responseSendMessage.getRequestSuccess();
        assertEquals("Should return a failed response, matching expected ", expected, actual);

        // We check the protocolId is the same.
        int expectedID = request.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);
    }

    @Test
    public void CreateGameRequest01_test() {
        int expected = 1;

        RequestProtocol requestProtocol = new RequestCreateGame(CREATE_GAME);




    }
}