package CardGame;

import CardGame.GameEngine.GameLobby;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.Hasher.hashPassword;
import static CardGame.ProtocolMessages.*;
import static CardGame.Requests.RequestProtocol.encodeRequest;
import static org.junit.Assert.*;

/**
 * This class tests the GameServer.
 * <p>
 * This inlcudes handling json objects and retrieving
 * and querying from the database.
 * <p>
 * Created by tom on 25/02/17.
 */
public class GameServerTest {

    // SET UP

    GameServer server;
    FunctionDB functionDB;
    CopyOnWriteArrayList<GameLobby> games = new CopyOnWriteArrayList<>();
    ConcurrentLinkedDeque<String> gameNames = new ConcurrentLinkedDeque<>();
    CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

    // THREADS
    GameServerThread serverThread1;
    GameServerThread serverThread2;
    GameServerThread serverThread3;
    GameServerThread serverThread4;
    GameServerThread serverThread5;

    // TEST USERS
    User userTest1 = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");
    User userBoris = new User("boris99", hashPassword("dog"), "Thomas", "Brereton");
    User userTest2 = new User("user2", hashPassword("password"));
    User userTest3 = new User("user3", hashPassword("password"));
    User userTest4 = new User("user4", hashPassword("password"));
    User userTest5 = new User("user5", hashPassword("password"));
    User userNotReg = new User("something", "orange", "Sam", "Smith");
    Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {

        // connect to database
        functionDB = new FunctionDB();

        // start server
        server = new GameServer(7654, "localhost", 20);

        // threads
        serverThread1 = new GameServerThread(new Socket(),
                users, functionDB, games, gameNames);
        serverThread2 = new GameServerThread(new Socket(),
                users, functionDB, games, gameNames);
        serverThread3 = new GameServerThread(new Socket(),
                users, functionDB, games, gameNames);
        serverThread4 = new GameServerThread(new Socket(),
                users, functionDB, games, gameNames);
        serverThread5 = new GameServerThread(new Socket(),
                users, functionDB, games, gameNames);

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
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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

        // Create serverThread1 object and handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        assertEquals("Should return user from database matching usertest ", this.userTest1, fromDB);

        // We check user is added to users on clientSideThread
        int userSize = this.serverThread1.getUsers().size();
        assertEquals("Should return size of 1 ", 1, userSize);

        // We check correct user is added to users on clientSideThread
        User userOnThread = this.serverThread1.getLoggedInUser();
        assertEquals("Should return user matching userTest1 ", this.userTest1, userOnThread);
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

        // Create serverThread1 object and handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        User userOnThread = this.serverThread1.getLoggedInUser();
        assertEquals("Should return user matching userTest1 ", null, userOnThread);
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

        // Create serverThread1 object and handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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

        // Create serverThread1 object and handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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

        // Create serverThread1 object and handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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

        RequestProtocol requestCreateGame = new RequestCreateGame(this.userTest1.getUserName());
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);

        // LOGIN

        // Convert to json
        String userJson = gson.toJson(requestLoginUser);

        // handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        assertEquals("Should return user from database matching usertest ", this.userTest1, fromDB);

        User loggedInUser = this.serverThread1.getLoggedInUser();

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.serverThread1.handleInput(createGameJson);

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
        GameLobby game = this.serverThread1.getGame(this.userTest1);
        assertNotNull(game);
    }

    /**
     * We test creating a game when not logged in.
     */
    @Test
    public void CreateGameRequest02_test() {
        int expected = 0;

        GameServerThread cardGameServerThreadEmpty = new GameServerThread(
                new Socket(),
                new CopyOnWriteArrayList<User>(),
                new FunctionDB(),
                new CopyOnWriteArrayList<GameLobby>(),
                new ConcurrentLinkedDeque<>());

        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = cardGameServerThreadEmpty.handleInput(createGameJson);

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
        GameLobby game = this.serverThread1.getGame(this.userTest1);
        assertNull(game);
    }

    @Test
    public void CreateGameRequestPush03_test() {
        int expected = 1;

        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);

        // LOGIN

        // Convert to json
        String userJson = gson.toJson(requestLoginUser);

        // handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        assertEquals("Should return user from database matching usertest ", this.userTest1, fromDB);

        User loggedInUser = this.serverThread1.getLoggedInUser();

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.serverThread1.handleInput(createGameJson);

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
        GameLobby game = this.serverThread1.getGame(this.userTest1);
        assertNotNull(game);

        // We check the game list is pushed


        ConcurrentLinkedDeque<String> expectedGames = new ConcurrentLinkedDeque<>();
        expectedGames.add(this.userTest1.getUserName());

        assertEquals("Should return list of gamenames matching expected gamesList ", expectedGames.toString(), gameNames.toString());
    }

    /**
     * We test joining a game again on the same clientSideThread.
     */
    @Test
    public void joinGame01_test() {
        int expected = 1;

        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());

        // LOGIN

        // Convert to json
        String userJson = gson.toJson(requestLoginUser);

        // handle the json object
        ResponseProtocol responseProtocol = serverThread1.handleInput(userJson);

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
        assertEquals("Should return user from database matching usertest ", this.userTest1, fromDB);

        User loggedInUser = this.serverThread1.getLoggedInUser();

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.serverThread1.handleInput(createGameJson);

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
        GameLobby game = this.serverThread1.getGame(this.userTest1);
        assertNotNull(game);

        // We check the game list is pushed

        ConcurrentLinkedDeque<String> expectedGames = new ConcurrentLinkedDeque<>();
        expectedGames.add(this.userTest1.getUserName());

        assertEquals("Should return list of gamenames matching expected gamesList ", expectedGames.toString(), gameNames.toString());

        // JOIN GAME
        RequestJoinGame requestJoinGame = new RequestJoinGame(userTest1.getUserName(), userBoris.getUserName());
        ResponseProtocol responseJoin = this.serverThread1.handleInput(encodeRequest(requestJoinGame));

        // we check for unsuccessful response
        int successJoin = responseJoin.getRequestSuccess();
        assertEquals("Should return success join response  ", FAIL, successJoin);

        // we check for user not logged in error msg
        String errorJoin = responseJoin.getErrorMsg();
        assertEquals("Should return error message matching user not logged in ", USERNAME_MISMATCH, errorJoin);
    }


    /**
     * We test for 1 clientSideThread creating a game, another clientSideThread joining that game.
     */
    @Test
    public void joinGame02_test() {
        int expected = 1;

        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());

        RequestLoginUser requestLoginUser1 = new RequestLoginUser(this.userTest2);

        // LOGIN

        // handle the json object
        ResponseProtocol responseLogin = serverThread1.handleInput(encodeRequest(requestLoginUser));
        ResponseProtocol responseLogin2 = serverThread2.handleInput(encodeRequest(requestLoginUser1));

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
        assertEquals("Should return user from database matching usertest ", this.userTest1, fromDB);

        // SECOND USER

        // We check the second user, userBoris
        User loggedInUser = this.serverThread2.getLoggedInUser();
        assertEquals("Should return logged in user matching usertesttwo ", userTest2, loggedInUser);

        // we check second user logged in
        int successLogin2 = responseLogin2.getRequestSuccess();
        assertEquals("Should return successful login matching success ", SUCCESS, successLogin2);

        //CREATE GAME
        String createGameJson = gson.toJson(requestCreateGame);

        // Calling handle input will create game and add it to gameLobby list
        ResponseProtocol responseCreateGame = this.serverThread1.handleInput(createGameJson);

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
        GameLobby game = this.serverThread1.getGame(this.userTest1);
        assertNotNull(game);

        // We check the game list is pushed

        ConcurrentLinkedDeque<String> expectedGames = new ConcurrentLinkedDeque<>();
        expectedGames.add(this.userTest1.getUserName());

        assertEquals("Should return list of gamenames matching expected gamesList ", expectedGames.toString(),
                gameNames.toString());

        // SECOND USER JOIN GAME
        String gameName = userTest1.getUserName();
        RequestJoinGame requestJoinGame = new RequestJoinGame(gameName, userTest2.getUserName());
        ResponseProtocol responseJoin = this.serverThread2.handleInput(encodeRequest(requestJoinGame));

        // we check for successful response
        int successJoin = responseJoin.getRequestSuccess();
        assertEquals("Should return success join response  ", SUCCESS, successJoin);
    }


    /**
     * We test for 5 people joining a game, should return full game error for 5th person.
     * If we reach this point we also know the database and database methods are working correctly
     * for logging in.
     */
    @Test
    public void joinGame03_test() {

        // CREATE GAME (1st player)
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol = serverThread1.handleInput(encodeRequest(requestLoginUser));
        ResponseProtocol respCreate = serverThread1.handleInput(encodeRequest(requestCreateGame));
        // check success
        int success1st = respCreate.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success1st);

        // JOIN 2ND PLAYER
        RequestLoginUser requestLoginUser2 = new RequestLoginUser(this.userTest2);
        RequestJoinGame requestJoinGame2 = new RequestJoinGame(userTest1.getUserName(), userTest2.getUserName());
        ResponseProtocol responseLogin2 = serverThread2.handleInput(encodeRequest(requestLoginUser2));
        ResponseProtocol responseJoin2 = serverThread2.handleInput(encodeRequest(requestJoinGame2));
        // check success
        int success2nd = responseJoin2.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success2nd);

        // JOIN 3rd PLAYER
        RequestLoginUser requestLoginUser3 = new RequestLoginUser(this.userTest3);
        RequestJoinGame requestJoinGame3 = new RequestJoinGame(userTest1.getUserName(), userTest3.getUserName());
        ResponseProtocol responseLogin3 = serverThread3.handleInput(encodeRequest(requestLoginUser3));
        ResponseProtocol responseJoin3 = serverThread3.handleInput(encodeRequest(requestJoinGame3));
        // check success
        int success3rd = responseJoin3.getRequestSuccess();
        assertEquals("Should return failed response matching success", SUCCESS, success3rd);

        // JOIN 4th PLAYER
        RequestLoginUser requestLoginUser4 = new RequestLoginUser(this.userTest4);
        RequestJoinGame requestJoinGame4 = new RequestJoinGame(userTest1.getUserName(), userTest4.getUserName());
        ResponseProtocol responseLogin4 = serverThread4.handleInput(encodeRequest(requestLoginUser4));
        ResponseProtocol responseJoin4 = serverThread4.handleInput(encodeRequest(requestJoinGame4));
        // check success
        int success4th = responseJoin4.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success4th);

        // JOIN 5th PLAYER
        RequestLoginUser requestLoginUser5 = new RequestLoginUser(this.userTest5);
        RequestJoinGame requestJoinGame5 = new RequestJoinGame(userTest1.getUserName(), userTest5.getUserName());
        ResponseProtocol responseLogin5 = serverThread5.handleInput(encodeRequest(requestLoginUser5));
        ResponseProtocol responseJoin5 = serverThread5.handleInput(encodeRequest(requestJoinGame5));

        // 5th player should return failed response
        int success5th = responseJoin5.getRequestSuccess();
        String errorMsg5th = responseJoin5.getErrorMsg();

        assertEquals("Should return failed response matching fail ", FAIL, success5th);

        assertEquals("Should return error message saying game is full matching GAME_FULL ", GAME_FULL, errorMsg5th);
    }


    /**
     * We test a bet request for the blackjack game
     */
    @Test
    public void bet01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // BET
        RequestBet requestBet = new RequestBet(10, userTest1.getUserName());
        ResponseProtocol responseBet = this.serverThread1.handleInput(encodeRequest(requestBet));

        // We check the bet was successful
        int successBet = responseBet.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, successBet);
    }


    /**
     * We test a bet request for 2 players in the blackjack game
     */
    @Test
    public void bet02_test() {

        // CREATE GAME (1st player)
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol = serverThread1.handleInput(encodeRequest(requestLoginUser));
        ResponseProtocol respCreate = serverThread1.handleInput(encodeRequest(requestCreateGame));
        // check success
        int success1st = respCreate.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success1st);

        // JOIN 2ND PLAYER
        RequestLoginUser requestLoginUser2 = new RequestLoginUser(this.userTest2);
        RequestJoinGame requestJoinGame2 = new RequestJoinGame(userTest1.getUserName(), userTest2.getUserName());
        ResponseProtocol responseLogin2 = serverThread2.handleInput(encodeRequest(requestLoginUser2));
        ResponseProtocol responseJoin2 = serverThread2.handleInput(encodeRequest(requestJoinGame2));
        // check success
        int success2nd = responseJoin2.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success2nd);

        // BET PLAYER 1
        RequestBet requestBet1 = new RequestBet(10, userTest1.getUserName());
        ResponseProtocol responsBet1 = this.serverThread1.handleInput(encodeRequest(requestBet1));

        // We check the bet 1 was successful
        int successBet1 = responsBet1.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, successBet1);

        // BET PLAYER 1
        RequestBet requestBet2 = new RequestBet(10, userTest2.getUserName());
        ResponseProtocol responsBet2 = this.serverThread2.handleInput(encodeRequest(requestBet2));

        // We check the bet 2 was successful
        int successBet2 = responsBet2.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, successBet2);

        // We check error msg is empty
        String errorMsg2 = responsBet2.getErrorMsg();
        assertEquals("Should return empty error message matching empty ", "", errorMsg2);


    }


    /**
     * We test a hit request while not in a game
     */
    @Test
    public void hit01_test() {
        RequestHit requestHit = new RequestHit(userTest1.getUserName());

        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestHit));

        // we check the hit was successful
        int successHit = responseProtocol.getRequestSuccess();
        assertEquals("Should return successful hit response matching success ", FAIL, successHit);
    }


    /**
     * We test a hit request while in a game
     */
    @Test
    public void hit02_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // BET
        RequestBet requestBet = new RequestBet(10, userTest1.getUserName());
        ResponseProtocol responseBet = this.serverThread1.handleInput(encodeRequest(requestBet));

        // HIT
        RequestHit requestHit = new RequestHit(userTest1.getUserName());
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestHit));

        // we check the hit was successful
        int successHit = responseProtocol2.getRequestSuccess();
        assertEquals("Should return successful hit response matching success ", SUCCESS, successHit);
    }


    /**
     * We test a hitting until bust for 2 players in the blackjack game
     * NOTE: this test only works for when the shuffle method in Deck, has
     * it's random seed = 1. This test will be disabled if it is not 1.
     */
    @Test
    public void hit03_test() {

        // CREATE GAME (1st player)
        RequestProtocol requestLoginUser = new RequestLoginUser(this.userTest1);
        RequestProtocol requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol = serverThread1.handleInput(encodeRequest(requestLoginUser));
        ResponseProtocol respCreate = serverThread1.handleInput(encodeRequest(requestCreateGame));

        // check success
        int success1st = respCreate.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success1st);

        // JOIN 2ND PLAYER
        RequestLoginUser requestLoginUser2 = new RequestLoginUser(this.userTest2);
        RequestJoinGame requestJoinGame2 = new RequestJoinGame(userTest1.getUserName(), userTest2.getUserName());
        ResponseProtocol responseLogin2 = serverThread2.handleInput(encodeRequest(requestLoginUser2));
        ResponseProtocol responseJoin2 = serverThread2.handleInput(encodeRequest(requestJoinGame2));
        // check success
        int success2nd = responseJoin2.getRequestSuccess();
        assertEquals("Should return failed response matching success ", SUCCESS, success2nd);

        // BET PLAYER 1
        RequestBet requestBet1 = new RequestBet(10, userTest1.getUserName());
        ResponseProtocol responsBet1 = this.serverThread1.handleInput(encodeRequest(requestBet1));

        // We check the bet 1 was successful
        int successBet1 = responsBet1.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, successBet1);

        // BET PLAYER 2
        RequestBet requestBet2 = new RequestBet(10, userTest2.getUserName());
        ResponseProtocol responsBet2 = this.serverThread2.handleInput(encodeRequest(requestBet2));

        // We check the bet 2 was successful
        int successBet2 = responsBet2.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, successBet2);


//        // start game again with random seed set to 1
//        serverThread1.getGame(userTest1.getUserName()).startGameForTesting();

        // HIT FOR PLAYER ONE
        RequestHit requestHit1_1 = new RequestHit(userTest1.getUserName());
        ResponseProtocol responseProtocol1_1 = serverThread1.handleInput(encodeRequest(requestHit1_1));
        // We check the hit was successful
        int success1_1 = responseProtocol1_1.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, success1_1);

        RequestHit requestHit1_2 = new RequestHit(userTest1.getUserName());
        ResponseProtocol responseProtocol1_2 = serverThread1.handleInput(encodeRequest(requestHit1_2));
        // We check the hit was successful
        int success1_2 = responseProtocol1_2.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, success1_2);

        RequestHit requestHit1_3 = new RequestHit(userTest1.getUserName());
        ResponseProtocol responseProtocol1_3 = serverThread1.handleInput(encodeRequest(requestHit1_3));
        // We check the hit was successful
        int success1_3 = responseProtocol1_3.getRequestSuccess();
        assertEquals("Should return failed hit response matching FAIL ", FAIL, success1_3);


        // HIT FOR PLAYER TWO
        RequestHit requestHit2_1 = new RequestHit(userTest2.getUserName());
        ResponseProtocol responseProtocol2_1 = serverThread2.handleInput(encodeRequest(requestHit2_1));
        // We check the hit was successful
        int success2_1 = responseProtocol2_1.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, success2_1);

        RequestHit requestHit2_2 = new RequestHit(userTest2.getUserName());
        ResponseProtocol responseProtocol2_2 = serverThread2.handleInput(encodeRequest(requestHit2_2));
        // We check the hit was successful
        int success2_2 = responseProtocol2_2.getRequestSuccess();
        assertEquals("Should return successful bet response matching success ", SUCCESS, success2_2);

        RequestHit requestHit2_3 = new RequestHit(userTest1.getUserName());
        ResponseProtocol responseProtocol2_3 = serverThread2.handleInput(encodeRequest(requestHit2_3));
        // We check the hit was successful
        int success2_3 = responseProtocol2_3.getRequestSuccess();
        assertEquals("Should return failed hit response matching FAIL ", FAIL, success2_3);

        // CHECK IF ALL FINISHED
        boolean allFinished1 = serverThread1.getGame(userTest1.getUserName()).isAllPlayersFinished();
        boolean allFinished2 = serverThread2.getGame(userTest1.getUserName()).isAllPlayersFinished();

        assertEquals("Should return all players finished matching true ", true, allFinished1);
        assertEquals("Should return all players finished matching true ", true, allFinished2);
    }

    /**
     * We test a stand request for the blackjack game
     */
    @Test
    public void stand01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // BET
        RequestBet requestBet = new RequestBet(10, userTest1.getUserName());
        ResponseProtocol responseBet = this.serverThread1.handleInput(encodeRequest(requestBet));

        // STAND
        RequestStand requestStand = new RequestStand(userTest1.getUserName());
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestStand));

        // we check stand was successful
        int successStand = responseProtocol2.getRequestSuccess();

        assertEquals("Should return successful stand response matching success ", SUCCESS, successStand);
    }

    /**
     * We test a quit game request for a user leaving a blackjack game
     */
    @Test
    public void quitGame01_test() {
        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // QUIT GAME
        RequestQuitGame requestQuitGame = new RequestQuitGame(userTest1.getUserName(), userTest1.getUserName());
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestQuitGame));

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
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseLogin = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // LOG OUT
        RequestLogOut requestLogOut = new RequestLogOut(this.userTest1.getUserName());
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLogOut));

        // we check the user logged out successfully
        int successLogout = responseProtocol.getRequestSuccess();

        assertEquals("Should return successful logOut response matching success ", SUCCESS, successLogout);
    }


    /**
     * We test getting a message while in a game.
     */
    @Test
    public void getMessages01_test() {

        // LOG IN
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest1.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestSendMessage));
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();
        expectedMsg.add(messageObject);

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(-1);
        ResponseProtocol responseProtocol3 = this.serverThread1.handleInput(encodeRequest(requestGetMessages));
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
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(-1);
        ResponseProtocol responseProtocol3 = this.serverThread1.handleInput(encodeRequest(requestGetMessages));
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
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest1.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestSendMessage));
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(2);
        ResponseProtocol responseProtocol3 = this.serverThread1.handleInput(encodeRequest(requestGetMessages));
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
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // CREATE GAME
        RequestCreateGame requestCreateGame = new RequestCreateGame(userTest1.getUserName());
        ResponseProtocol responseProtocol1 = this.serverThread1.handleInput(encodeRequest(requestCreateGame));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest1.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestSendMessage));
        MessageObject messageObject1 = new MessageObject(userTest1.getUserName(), "How are you?");
        RequestSendMessage requestSendMessage1 = new RequestSendMessage(messageObject1);
        ResponseProtocol responseProtocol3 = this.serverThread1.handleInput(encodeRequest(requestSendMessage1));
        ArrayList<MessageObject> expectedMsg = new ArrayList<>();
        expectedMsg.add(messageObject1);

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(0);
        ResponseProtocol responseProtocol4 = this.serverThread1.handleInput(encodeRequest(requestGetMessages));
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
        RequestLoginUser requestLoginUser = new RequestLoginUser(userTest1);
        ResponseProtocol responseProtocol = this.serverThread1.handleInput(encodeRequest(requestLoginUser));

        // SEND MESSAGE
        MessageObject messageObject = new MessageObject(userTest1.getUserName(), "Hello world!.");
        RequestSendMessage requestSendMessage = new RequestSendMessage(messageObject);
        ResponseProtocol responseProtocol2 = this.serverThread1.handleInput(encodeRequest(requestSendMessage));

        // GET MESSAGE
        RequestGetMessages requestGetMessages = new RequestGetMessages(-1);
        ResponseProtocol responseProtocol3 = this.serverThread1.handleInput(encodeRequest(requestGetMessages));
        ResponseGetMessages responseGetMessages = (ResponseGetMessages) responseProtocol3;

        int successGetMsg = responseGetMessages.getRequestSuccess();
        // We check we got success
        assertEquals("Should return fail get message response matching fail ", FAIL, successGetMsg);
    }

}