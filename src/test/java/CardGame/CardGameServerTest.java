package CardGame;

import CardGame.Requests.RequestLoginUser;
import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseRegisterUser;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * This class tests the CardGameServer.
 *
 * This inlcudes handling json objects and retrieving
 * and querying from the database.
 *
 * Created by tom on 25/02/17.
 */
public class CardGameServerTest {

    // SET UP

    CardGameServer server;

    @Before
    public void setUp() throws Exception {
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
        ClientThread clientThread = new ClientThread(null);
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
        ClientThread clientThread = new ClientThread(null);
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actual);

    }

    /**
     * We test for an incorrect password of existing user
     */
    @Test
    public void loginUser02_test() {
        int expected = 0; // 1 equals fail

        // Create user and requset object
        User user = new User("N00b_D3STROYER", "wrongPassword");
        RequestProtocol request = new RequestLoginUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ClientThread clientThread = new ClientThread(null);
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords do not match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 0 (signalling mismatch), matching expected ", expected, actual);
    }

    /**
     * We test for incorrect username, with correct password
     */
    @Test
    public void loginUser03_test() {
        int expected = 0; // 0 equals success

        // Create user and requset object
        User user = new User("N00b_D3STROYER_WRONG", "password");
        RequestProtocol request = new RequestLoginUser(user);

        // Convert to json
        Gson gson = new Gson();
        String userJson = gson.toJson(request);

        // Create clientThread object and handle the json object
        ClientThread clientThread = new ClientThread(null);
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check we got a failed response back
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return a failed response, matching expected ", expected, actual);
    }

}