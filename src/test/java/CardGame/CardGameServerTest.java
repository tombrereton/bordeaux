package CardGame;

import CardGame.GameEngine.GameLobby;
import CardGame.Pushes.PushGameNames;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.ProtocolMessages.*;
import static CardGame.Requests.RequestProtocol.encodeRequest;
import static org.junit.Assert.*;

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
    ClientThread clientThreadTwo;
    CopyOnWriteArrayList<GameLobby> games = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<String> gameNames = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    User userTest = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");
    User userTestTwo = new User("boris99", "dog", "Thomas", "Brereton");
    User userNotReg = new User("something", "orange", "Sam", "Smith");
    Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {
        functionDB = new FunctionDB();
        server = new CardGameServer();
        clientThread = new ClientThread(server, new Socket(), new ConcurrentLinkedDeque<MessageObject>(),
                new ConcurrentLinkedDeque<Socket>(), users, functionDB, games, gameNames);
        clientThreadTwo = new ClientThread(server, new Socket(), new ConcurrentLinkedDeque<MessageObject>(),
                new ConcurrentLinkedDeque<Socket>(), users, functionDB, games, gameNames);
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
        int expectedSuccess = FAIL;

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
        String expected = EMPTY_INSERT;
        int expectedSuccess = FAIL;

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
        String expected = EMPTY_INSERT;
        int expectedSuccess = FAIL;

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

        // We check the user from the database is the same
        User fromDB = responseLoginUser.getUser();
        assertEquals("Should return user from database matching usertest ", this.userTest, fromDB);

        // We check user is added to users on thread
        int userSize = this.clientThread.getUsers().size();
        assertEquals("Should return size of 1 ", 1, userSize);

        // We check correct user is added to users on thread
        User userOnThread = this.clientThread.getLoggedInUser();
        assertEquals("Should return user matching userTest ", this.userTest, userOnThread);
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

        // We check we get password mismatch error
        String errorMsg = responseLoginUser.getErrorMsg();
        assertEquals("Should return password mismatch error ", PASSWORD_MISMATCH, errorMsg);

        // We check no user was logged in
        User userOnThread = this.clientThread.getLoggedInUser();
        assertEquals("Should return user matching userTest ", null, userOnThread);
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

        RequestProtocol requestCreateGame = new RequestCreateGame(this.userTest.getUserName());
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest);

        // LOGIN

        // Convert to json
        String userJson = gson.toJson(requestLoginUser);

        // handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actual);

        // We check the log in protocolId is the same.
        int expectedID = requestLoginUser.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);

        // We check the user from the database is the same
        User fromDB = responseLoginUser.getUser();
        assertEquals("Should return user from database matching usertest ", this.userTest, fromDB);

        User loggedInUser = this.clientThread.getLoggedInUser();

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.clientThread.handleInput(createGameJson);

        // We check the type is create game
        int typeCreateGame = responseCreateGame.getType();
        assertEquals("Type should match login user", ProtocolTypes.CREATE_GAME, typeCreateGame);

        // We check we got a successful response
        int actualCreateGame = responseCreateGame.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actualCreateGame);

        // We check the protocolId is the same.
        int expectedIDCreateGame = requestCreateGame.getProtocolId();
        int actualIDCreateGame = responseCreateGame.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ",
                expectedIDCreateGame, actualIDCreateGame);

        // We check we created the correct game
        GameLobby game = this.clientThread.getGame(this.userTest);
        assertNotNull(game);
    }

    /**
     * We test creating a game when not logged in.
     */
    @Test
    public void CreateGameRequest02_test() {
        int expected = 0;

        ClientThread clientThreadEmpty = new ClientThread(
                new CardGameServer(),
                new Socket(),
                new ConcurrentLinkedDeque<MessageObject>(),
                new ConcurrentLinkedDeque<Socket>(),
                new CopyOnWriteArrayList<User>(),
                new FunctionDB(),
                new CopyOnWriteArrayList<GameLobby>(),
                new CopyOnWriteArrayList<String>());

        RequestProtocol requestCreateGame = new RequestCreateGame(userTest.getUserName());

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = clientThreadEmpty.handleInput(createGameJson);

        // We check the type is create game
        int typeCreateGame = responseCreateGame.getType();
        assertEquals("Type should match login user", ProtocolTypes.CREATE_GAME, typeCreateGame);

        // We check we got a successful response
        int actualCreateGame = responseCreateGame.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actualCreateGame);

        // We check the error message is user not logged in
        String errorMsg = responseCreateGame.getErrorMsg();
        assertEquals("Should return error message of not logged in matching errorMsg ", NOT_LOGGED_IN, errorMsg);
        // We check the protocolId is the same.
        int expectedIDCreateGame = requestCreateGame.getProtocolId();
        int actualIDCreateGame = responseCreateGame.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ",
                expectedIDCreateGame, actualIDCreateGame);

        // We check we created the correct game
        GameLobby game = this.clientThread.getGame(this.userTest);
        assertNull(game);
    }

    @Test
    public void CreateGameRequestPush03_test() {
        int expected = 1;

        RequestProtocol requestCreateGame = new RequestCreateGame(userTest.getUserName());
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest);

        // LOGIN

        // Convert to json
        String userJson = gson.toJson(requestLoginUser);

        // handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actual);

        // We check the log in protocolId is the same.
        int expectedID = requestLoginUser.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);

        // We check the user from the database is the same
        User fromDB = responseLoginUser.getUser();
        assertEquals("Should return user from database matching usertest ", this.userTest, fromDB);

        User loggedInUser = this.clientThread.getLoggedInUser();

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.clientThread.handleInput(createGameJson);

        // We check the type is create game
        int typeCreateGame = responseCreateGame.getType();
        assertEquals("Type should match login user", ProtocolTypes.CREATE_GAME, typeCreateGame);

        // We check we got a successful response
        int actualCreateGame = responseCreateGame.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actualCreateGame);

        // We check the protocolId is the same.
        int expectedIDCreateGame = requestCreateGame.getProtocolId();
        int actualIDCreateGame = responseCreateGame.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ",
                expectedIDCreateGame, actualIDCreateGame);

        // We check we created the correct game
        GameLobby game = this.clientThread.getGame(this.userTest);
        assertNotNull(game);

        // We check the game list is pushed
        PushGameNames push = this.clientThread.pushGameListToClient();
        ArrayList<String> gameNames = push.getGameNames();

        ArrayList<String> expectedGames = new ArrayList<>();
        expectedGames.add(this.userTest.getUserName());

        assertEquals("Should return list of gamenames matching expected gamesList ", expectedGames, gameNames);
    }

    /**
     * We test joining a game again on the same thread.
     */
    @Test
    public void joinGame01_test() {
        int expected = 1;

        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest.getUserName());

        // LOGIN

        // Convert to json
        String userJson = gson.toJson(requestLoginUser);

        // handle the json object
        ResponseProtocol responseProtocol = clientThread.handleInput(userJson);

        // We check the type is login user
        int type = responseProtocol.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseProtocol;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actual);

        // We check the log in protocolId is the same.
        int expectedID = requestLoginUser.getProtocolId();
        int actualID = responseProtocol.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);

        // We check the user from the database is the same
        User fromDB = responseLoginUser.getUser();
        assertEquals("Should return user from database matching usertest ", this.userTest, fromDB);

        User loggedInUser = this.clientThread.getLoggedInUser();

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.clientThread.handleInput(createGameJson);

        // We check the type is create game
        int typeCreateGame = responseCreateGame.getType();
        assertEquals("Type should match login user", ProtocolTypes.CREATE_GAME, typeCreateGame);

        // We check we got a successful response
        int actualCreateGame = responseCreateGame.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actualCreateGame);

        // We check the protocolId is the same.
        int expectedIDCreateGame = requestCreateGame.getProtocolId();
        int actualIDCreateGame = responseCreateGame.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ",
                expectedIDCreateGame, actualIDCreateGame);

        // We check we created the correct game
        GameLobby game = this.clientThread.getGame(this.userTest);
        assertNotNull(game);

        // We check the game list is pushed
        PushGameNames push = this.clientThread.pushGameListToClient();
        ArrayList<String> gameNames = push.getGameNames();

        ArrayList<String> expectedGames = new ArrayList<>();
        expectedGames.add(this.userTest.getUserName());

        assertEquals("Should return list of gamenames matching expected gamesList ", expectedGames, gameNames);

        // JOIN GAME
        RequestJoinGame requestJoinGame = new RequestJoinGame(userTest.getUserName(), userTestTwo.getUserName());
        ResponseProtocol responseJoin = this.clientThread.handleInput(encodeRequest(requestJoinGame));

        // we check for unsuccessful response
        int successJoin = responseJoin.getRequestSuccess();
        assertEquals("Should return success join response  ", FAIL, successJoin);

        // we check for user not logged in error msg
        String errorJoin = responseJoin.getErrorMsg();
        assertEquals("Should return error message matching user not logged in ", USERNAME_MISMATCH, errorJoin);
    }

    /**
     * We test for 1 thread creating a game, another thread joining that game.
     */
    @Test
    public void joinGame02_test() {
        int expected = 1;

        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest.getUserName());
        RequestLoginUser requestLoginUser1 = new RequestLoginUser(this.userTestTwo);

        // LOGIN

        // handle the json object
        ResponseProtocol responseLogin = clientThread.handleInput(encodeRequest(requestLoginUser));
        ResponseProtocol responseLogin2 = clientThreadTwo.handleInput(encodeRequest(requestLoginUser1));

        // We check the type is login user
        int type = responseLogin.getType();
        assertEquals("Type should match login user", ProtocolTypes.LOGIN_USER, type);

        // We check the passwords match as per the response
        ResponseLoginUser responseLoginUser = (ResponseLoginUser) responseLogin;
        int actual = responseLoginUser.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actual);

        // We check the log in protocolId is the same.
        int expectedID = requestLoginUser.getProtocolId();
        int actualID = responseLogin.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ", expectedID, actualID);

        // We check the user from the database is the same
        User fromDB = responseLoginUser.getUser();
        assertEquals("Should return user from database matching usertest ", this.userTest, fromDB);

        // SECOND USER

        // We check the second user, userTestTwo
        User loggedInUser = this.clientThreadTwo.getLoggedInUser();
        assertEquals("Should return logged in user matching usertesttwo ", userTestTwo, loggedInUser);

        // we check second user logged in
        int successLogin2 = responseLogin2.getRequestSuccess();
        assertEquals("Should return successful login matching success ", SUCCESS, successLogin2);

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.clientThread.handleInput(createGameJson);

        // We check the type is create game
        int typeCreateGame = responseCreateGame.getType();
        assertEquals("Type should match login user", ProtocolTypes.CREATE_GAME, typeCreateGame);

        // We check we got a successful response
        int actualCreateGame = responseCreateGame.getRequestSuccess();
        assertEquals("Should return success of value 1, matching expected ", expected, actualCreateGame);

        // We check the protocolId is the same.
        int expectedIDCreateGame = requestCreateGame.getProtocolId();
        int actualIDCreateGame = responseCreateGame.getProtocolId();
        assertEquals("Should return the same protocol ID matching expectedID ",
                expectedIDCreateGame, actualIDCreateGame);

        // We check we created the correct game
        GameLobby game = this.clientThread.getGame(this.userTest);
        assertNotNull(game);

        // We check the game list is pushed
        PushGameNames push = this.clientThread.pushGameListToClient();
        ArrayList<String> gameNames = push.getGameNames();

        ArrayList<String> expectedGames = new ArrayList<>();
        expectedGames.add(this.userTest.getUserName());

        assertEquals("Should return list of gamenames matching expected gamesList ", expectedGames, gameNames);

        // SECOND USER JOIN GAME
        String gameName = userTest.getUserName();
        RequestJoinGame requestJoinGame = new RequestJoinGame(gameName, userTestTwo.getUserName());
        ResponseProtocol responseJoin = this.clientThreadTwo.handleInput(encodeRequest(requestJoinGame));

        // we check for successful response
        int successJoin = responseJoin.getRequestSuccess();
        assertEquals("Should return success join response  ", SUCCESS, successJoin);
    }

    /**
     * We test a bet request for the blackjack game
     */
    @Test
    public void bet01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // BET
        RequestBet requestBet = new RequestBet(10, userTest.getUserName());
        ResponseProtocol responseBet = this.clientThread.handleInput(encodeRequest(requestBet));

        // We check the bet was successful
        int successBet = responseBet.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, successBet);
    }

    /**
     * We test a hit request for the blackjack game
     */
    @Test
    public void hit01_test() {
        RequestHit requestHit = new RequestHit(userTest.getUserName());

        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestHit));

        // we check the hit was successful
        int successHit = responseProtocol.getRequestSuccess();
        assertEquals("Should return successful hit response matching success ", SUCCESS, successHit);
    }

    /**
     * We test a double bet request for the blackjack game
     */
    @Test
    public void double01_test() {
        RequestDoubleBet requestDoubleBet = new RequestDoubleBet(userTest.getUserName());

        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestDoubleBet));

        // we check the double bet was successful
        int successDouble = responseProtocol.getRequestSuccess();
        assertEquals("Should return successful double response matching success ", SUCCESS, successDouble);

    }

    /**
     * We test a stand request for the blackjack game
     */
    @Test
    public void stand01_test() {
        RequestStand requestStand = new RequestStand();

        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestStand));

        // we check stand was successful
        int successStand = responseProtocol.getRequestSuccess();

        assertEquals("Should return successful stand response matching success ", SUCCESS, successStand);
    }

    /**
     * We test a quit game request for a user leaving a blackjack game
     */
    @Test
    public void quitGame01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // QUIT GAME
        RequestQuitGame requestQuitGame = new RequestQuitGame(userTest.getUserName(), userTest.getUserName());
        ResponseProtocol responseProtocol2 = this.clientThread.handleInput(encodeRequest(requestQuitGame));

        // we check the user quit the game successfully
        int successQuit = responseProtocol.getRequestSuccess();

        assertEquals("Should return successful quitGame response matching success ", SUCCESS, successQuit);
    }

    /**
     * We test the user can log out of the client
     */
    @Test
    public void logOut01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseLogin = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // LOG OUT
        RequestLogOut requestLogOut = new RequestLogOut(this.userTest.getUserName());
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLogOut));

        // we check the user logged out successfully
        int successLogout = responseProtocol.getRequestSuccess();

        assertEquals("Should return successful logOut response matching success ", SUCCESS, successLogout);
    }


    /**
     * We test a fold game request for a user in a blackjack game.
     */
    @Test
    public void fold01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // QUIT GAME
        RequestFold requestFold = new RequestFold(userTest.getUserName());
        ResponseProtocol responseProtocol2 = this.clientThread.handleInput(encodeRequest(requestFold));

        // we check the user quit the game successfully
        int successFold = responseProtocol2.getRequestSuccess();

        assertEquals("Should return successful quitGame response matching success ", SUCCESS, successFold);
    }

    /**
     * We test getting a message while in a game.
     */
    @Test
    public void getMessages01_test() {

        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.clientThread.handleInput(encodeRequest(requestSendMessage));
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();
        expectedMsg.add(messageObject);

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(-1);
        ResponseProtocol responseProtocol3 = this.clientThread.handleInput(encodeRequest(requestGetMessages));
        ResponseGetMessages responseGetMessages = (ResponseGetMessages) responseProtocol3;


        int successGetMsg = responseGetMessages.getRequestSuccess();
        // We check we got success
        assertEquals("Should return successful quitGame response matching success ", SUCCESS, successGetMsg);

        // We check we got the correct message back
        ArrayList<MessageObject> messages = responseGetMessages.getMessages();
        assertEquals("Should return arraylist containing messages matching those sent ",
                expectedMsg.toString(), messages.toString());
    }


    /**
     * We test getting a message when no message has been sent.
     */
    @Test
    public void getMessages02_test() {

        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(-1);
        ResponseProtocol responseProtocol3 = this.clientThread.handleInput(encodeRequest(requestGetMessages));
        ResponseGetMessages responseGetMessages = (ResponseGetMessages) responseProtocol3;


        int successGetMsg = responseGetMessages.getRequestSuccess();
        // We check we got success
        assertEquals("Should return successful quitGame response matching success ", SUCCESS, successGetMsg);

        // We check we got the correct message back
        ArrayList<MessageObject> messages = responseGetMessages.getMessages();
        assertEquals("Should return arraylist containing messages matching those sent ",
                expectedMsg.toString(), messages.toString());
    }


    /**
     * We test getting a message when the offset is too high.
     */
    @Test
    public void getMessages03_test() {

        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.clientThread.handleInput(encodeRequest(requestSendMessage));
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(2);
        ResponseProtocol responseProtocol3 = this.clientThread.handleInput(encodeRequest(requestGetMessages));
        ResponseGetMessages responseGetMessages = (ResponseGetMessages) responseProtocol3;


        // We check we got success
        int successGetMsg = responseGetMessages.getRequestSuccess();
        assertEquals("Should return successful quitGame response matching success ", SUCCESS, successGetMsg);

        // We check we got the correct message back
        ArrayList<MessageObject> messages = responseGetMessages.getMessages();
        assertEquals("Should return arraylist containing messages matching those sent ",
                expectedMsg.toString(), messages.toString());

        // We check the arraylist is empty
        assertTrue(messages.isEmpty());
    }


    /**
     * We test getting only the last message while in a game
     */
    @Test
    public void getMessages04_test() {

        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest.getUserName());
        ResponseProtocol responseProtocol1 = this.clientThread.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.clientThread.handleInput(encodeRequest(requestSendMessage));
        MessageObject messageObject1 = new MessageObject(userTest.getUserName(), "How are you?");
        RequestSendMessage requestSendMessage1 = new RequestSendMessage(messageObject1);
        ResponseProtocol responseProtocol3 = this.clientThread.handleInput(encodeRequest(requestSendMessage1));
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();
        expectedMsg.add(messageObject1);

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(0);
        ResponseProtocol responseProtocol4 = this.clientThread.handleInput(encodeRequest(requestGetMessages));
        ResponseGetMessages responseGetMessages = (ResponseGetMessages) responseProtocol4;


        int successGetMsg = responseGetMessages.getRequestSuccess();
        // We check we got success
        assertEquals("Should return successful quitGame response matching success ", SUCCESS, successGetMsg);

        // We check we got the correct message back
        ArrayList<MessageObject> messages = responseGetMessages.getMessages();
        assertEquals("Should return arraylist containing messages matching those sent ",
                expectedMsg.toString(), messages.toString());
    }


    /**
     * We test getting messages while not in a game, should be fail
     */
    @Test
    public void getMessages05_test() {

        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest);
        ResponseProtocol responseProtocol = this.clientThread.handleInput(encodeRequest(requestLoginUser));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.clientThread.handleInput(encodeRequest(requestSendMessage));

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(-1);
        ResponseProtocol responseProtocol3 = this.clientThread.handleInput(encodeRequest(requestGetMessages));
        ResponseGetMessages responseGetMessages = (ResponseGetMessages) responseProtocol3;

        int successGetMsg = responseGetMessages.getRequestSuccess();
        // We check we got success
        assertEquals("Should return fail get message response matching fail ", FAIL, successGetMsg);
    }

}