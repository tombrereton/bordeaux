package CardGame;

import CardGame.GameEngine.GameLobby;
import CardGame.GameEngine.Hand;
import CardGame.Pushes.*;
import CardGame.Requests.*;
import CardGame.Responses.*;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.ProtocolMessages.*;
import static CardGame.ProtocolTypes.*;
import static CardGame.Requests.RequestProtocol.decodeRequest;
import static CardGame.Responses.ResponseProtocol.encodeResponse;

/**
 * This class implements the Runnable interface and
 * make a connection via a 'Socket.'
 *
 * @Author Tom Brereton
 */
public class GameServerThread implements Runnable {
    private Socket toClientSocket;
    private boolean clientAlive;
    private long clientID;
    private User user;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private FunctionDB functionDB;
    private Gson gson;
    private GameServer server;
    private String gameJoined;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;
    private volatile CopyOnWriteArrayList<GameLobby> games;
    private volatile ConcurrentLinkedDeque<String> gameNames;


    public GameServerThread(GameServer server,
                            Socket toClientSocket,
                            ConcurrentLinkedDeque<MessageObject> messageQueue,
                            ConcurrentLinkedDeque<Socket> socketList,
                            CopyOnWriteArrayList<User> users,
                            FunctionDB functionsDB,
                            CopyOnWriteArrayList<GameLobby> games,
                            ConcurrentLinkedDeque<String> gameNames) {
        this.server = server;
        this.toClientSocket = toClientSocket;
        this.clientID = Thread.currentThread().getId();
        this.messageQueue = messageQueue;
        this.socketList = socketList;
        this.users = users;
        this.functionDB = functionsDB;
        this.games = games;
        this.gameNames = gameNames;
        this.gson = new Gson();
        this.user = null;
        this.clientAlive = true;
        connectStreams();
    }

    /**
     * This method runs when the clientSideThread starts.
     */
    @Override
    public void run() {
        try {
            while (clientAlive) {
                // We read in the request from the client and handle it
                ResponseProtocol response = handleInput(inputStream.readUTF());

                // We write the response to the client
                outputStream.writeUTF(encodeResponse(response));
                outputStream.flush();
                System.out.println(response);

                // sleep thread
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (EOFException e) {
            System.out.println("GameClient disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // log user out of client and game on disconnect
            if (gameJoined != null) {
                quitGame(gameJoined, getLoggedInUser().getUserName());
            }
            logUserOut();
            System.out.println("Logged in user set to: " + getLoggedInUser());
            closeConnections();
        }
    }

    private void connectStreams() {
        try {
            this.inputStream = new DataInputStream(this.toClientSocket.getInputStream());
            this.outputStream = new DataOutputStream(this.toClientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Cannot get input and output streams from client socket.");
        }
    }

    /**
     * This method closes all open connections.
     *
     * @throws IOException
     */
    public void closeConnections() {
        try {
            logUserOut();
            this.inputStream.close();
            this.outputStream.close();
            this.toClientSocket.close();
        } catch (NullPointerException e) {
            System.out.println("Problem closing connection due to null pointer");
        } catch (IOException e) {
            System.out.println("Problem closing connections.");
        }
    }

    /**
     * This method handles the input from the client and
     * returns a response as per the request sent.
     *
     * @param JSONInput
     * @return
     */
    public ResponseProtocol handleInput(String JSONInput) {

        // Deserialize request object
        RequestProtocol request = decodeRequest(JSONInput);

        // Get packet ID and its type
        int protocolId = request.getProtocolId();
        int requestType = request.getType();

        switch (requestType) {
            case REGISTER_USER:
                return handleRegisterUser(JSONInput, protocolId);
            case LOGIN_USER:
                return handleLoginUser(JSONInput, protocolId);
            case LOG_OUT_USER:
                return handleLogoutUser(JSONInput, protocolId);
            case SEND_MESSAGE:
                return handleSendMessage(JSONInput, protocolId);
            case GET_MESSAGE:
                return handleGetMessages(JSONInput, protocolId);
            case CREATE_GAME:
                return handleCreateGame(JSONInput, protocolId);
            case JOIN_GAME:
                return handleJoinGame(JSONInput, protocolId);
            case QUIT_GAME:
                return handleQuitGame(JSONInput, protocolId);
            case BET:
                return handleBet(JSONInput, protocolId);
            case HIT:
                return handleHit(JSONInput, protocolId);
            case STAND:
                return handleStand(JSONInput, protocolId);
            case FOLD:
                return handleFold(JSONInput, protocolId);
            case DOUBLE:
                return handleDouble(JSONInput, protocolId);
            case PUSH_GAME_NAMES:
                return handleGetGameNames(protocolId);
            case PUSH_PLAYER_NAMES:
                return handleGetPlayerNames(protocolId);
            case PUSH_PLAYER_HANDS:
                return handleGetPlayerHands(protocolId);
            case PUSH_PLAYER_BETS:
                return handleGetPlayerBets(protocolId);
            case PUSH_PLAYER_BUDGETS:
                return handleGetPlayerBudgets(protocolId);
            case PUSH_PLAYERS_STAND:
                return handleGetPlayersStand(protocolId);
            case PUSH_PLAYERS_WON:
                return handleGetPlayersWon(protocolId);
            case PUSH_PLAYERS_BUST:
                return handleGetPlayersBust(protocolId);
            case PUSH_DEALER_HAND:
                return handleGetDealerhand(protocolId);
            default:
                return new ResponseProtocol(protocolId, UNKNOWN_TYPE, FAIL, UNKNOWN_ERROR);
        }
    }

    private ResponseProtocol handleGetDealerhand(int protocolId) {
        Hand dealerHand = this.getGame(gameJoined).getDealerHand();

        return new PushDealerHand(protocolId, SUCCESS, dealerHand);
    }

    private ResponseProtocol handleGetPlayersBust(int protocolId) {
        Map<String, Boolean> playersBust = this.getGame(gameJoined).getPlayersBust();

        return new PushPlayersBust(protocolId, SUCCESS, playersBust);
    }

    private ResponseProtocol handleGetPlayersWon(int protocolId) {
        Map<String, Boolean> playersWon = this.getGame(gameJoined).getPlayersWon();

        return new PushPlayersWon(protocolId, SUCCESS, playersWon);
    }

    private ResponseProtocol handleGetPlayersStand(int protocolId) {
        Map<String, Boolean> playersStand = this.getGame(gameJoined).getPlayersStand();

        return new PushPlayersStand(protocolId, SUCCESS, playersStand);
    }

    private ResponseProtocol handleGetPlayerBudgets(int protocolId) {
        Map<String, Integer> playerBudgets = this.getGame(gameJoined).getPlayerBudgets();

        return new PushPlayerBudgets(protocolId, SUCCESS, playerBudgets);
    }

    private ResponseProtocol handleGetPlayerBets(int protocolId) {
        Map<String, Integer> playerBets = this.getGame(gameJoined).getPlayerBets();

        return new PushPlayerBets(protocolId, SUCCESS, playerBets);
    }

    private ResponseProtocol handleGetPlayerHands(int protocolId) {
        Map<String, Hand> playerHands = this.getGame(gameJoined).getPlayerHands();

        return new PushPlayerHands(protocolId, SUCCESS, playerHands);
    }

    private ResponseProtocol handleGetGameNames(int protocolId) {
        return new PushGameNames(protocolId, SUCCESS, getGameNames());
    }

    private ResponseProtocol handleGetPlayerNames(int protocolId) {
        Set<String> playerNames = new TreeSet<>(this.getGame(gameJoined).getPlayerNames());

        return new PushPlayerNames(protocolId, SUCCESS, playerNames);
    }

    private ResponseProtocol handleDouble(String jsonInput, int protocolId) {
        RequestDoubleBet requestDoubleBet = gson.fromJson(jsonInput, RequestDoubleBet.class);
        String userFromRequest = requestDoubleBet.getUsername();
        int betAmountToAdd = getGame(gameJoined).getPlayer(getLoggedInUser()).getBet();

        if (isLoggedInUserNull()) {
            // return fail if logged in user is null
            return new ResponseDoubleBet(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (userFromRequest == null) {
            // return fail if request user is null
            return new ResponseDoubleBet(protocolId, FAIL, EMPTY);
        } else if (!getLoggedInUser().getUserName().equals(userFromRequest)) {
            // return fail if request user does not match logged in user
            return new ResponseDoubleBet(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (!isBetWithinBudget(betAmountToAdd)) {
            // return fail if bet amount is not within budget
            return new ResponseBet(protocolId, FAIL, BET_NOT_IN_BUDGET);
        } else if (isBetWithinBudget(betAmountToAdd)) {
            // make a double bet and deal a card to player
            doubleBet(betAmountToAdd);

            // return success if bet within budget
            return new ResponseBet(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseBet(protocolId, FAIL, UNKNOWN_ERROR);
        }
    }

    private ResponseProtocol handleFold(String jsonInput, int protocolId) {
        RequestFold requestFold = gson.fromJson(jsonInput, RequestFold.class);
        String userFromRequest = requestFold.getUsername();

        if (isLoggedInUserNull()) {
            // return fail if logged in user is null
            return new ResponseFold(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (userFromRequest == null) {
            // return fail if request user is null
            return new ResponseFold(protocolId, FAIL, EMPTY);
        } else if (!getLoggedInUser().getUserName().equals(userFromRequest)) {
            // return fail if request user does not match logged in user
            return new ResponseFold(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (getGame(gameJoined).getPlayersStand().get(getLoggedInUser().getUserName())) {
            // return fail if player is already standing
            return new ResponseFold(protocolId, FAIL, ALREADY_STANDING);
        } else if (!getGame(gameJoined).getPlayersStand().get(getLoggedInUser().getUserName())) {
            // if the player is not standing, make player fold
            getGame(gameJoined).setPlayerFold(getLoggedInUser().getUserName());

            // return success if player is now standing
            return new ResponseFold(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseFold(protocolId, FAIL, UNKNOWN_ERROR);
        }
    }

    private ResponseProtocol handleStand(String jsonInput, int protocolId) {
        RequestStand requestStand = gson.fromJson(jsonInput, RequestStand.class);
        String userFromRequest = requestStand.getUsername();


        if (isLoggedInUserNull()) {
            // return fail if logged in user is null
            return new ResponseStand(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (userFromRequest == null) {
            // return fail if request user is null
            return new ResponseStand(protocolId, FAIL, EMPTY);
        } else if (!getLoggedInUser().getUserName().equals(userFromRequest)) {
            // return fail if request user does not match logged in user
            return new ResponseStand(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (getGame(gameJoined).getPlayer(getLoggedInUser()).getBet() == 0) {
            // return fail if user has not places a bet
            return new ResponseStand(protocolId, FAIL, NO_BET);
        } else if (getGame(gameJoined).getPlayersStand().get(getLoggedInUser().getUserName())) {
            // return fail if player is already standing
            return new ResponseStand(protocolId, FAIL, ALREADY_STANDING);
        } else if (!getGame(gameJoined).getPlayersStand().get(getLoggedInUser().getUserName())) {
            // if the player is not standing, make player stand
            getGame(gameJoined).setPlayerStand(getLoggedInUser().getUserName());

            // return success if player is now standing
            return new ResponseStand(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseStand(protocolId, FAIL, UNKNOWN_ERROR);
        }
    }

    private ResponseProtocol handleHit(String jsonInput, int protocolId) {
        RequestHit requestHit = gson.fromJson(jsonInput, RequestHit.class);
        String userFromRequest = requestHit.getUsername();

        if (isLoggedInUserNull()) {
            // return fail if logged in user is null
            return new ResponseHit(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (userFromRequest == null) {
            // return fail if request user is null
            return new ResponseHit(protocolId, FAIL, EMPTY);
        } else if (!getLoggedInUser().getUserName().equals(userFromRequest)) {
            // return fail if request user does not match logged in user
            return new ResponseHit(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (getGame(gameJoined).getPlayer(getLoggedInUser()).isFinishedRound()) {
            // return fail if user has finished round
            return new ResponseHit(protocolId, FAIL, FINISHED_ROUND);
        } else if (getGame(gameJoined).getPlayer(getLoggedInUser()).getBet() == 0) {
            // return fail if user has not places a bet
            return new ResponseHit(protocolId, FAIL, NO_BET);
        } else if (!getGame(gameJoined).getPlayer(getLoggedInUser()).isFinishedRound()) {
            // if player has not finished the round, give the player a card
            getGame(gameJoined).hit(getLoggedInUser());

            // return success if bet within budget
            return new ResponseHit(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseHit(protocolId, FAIL, UNKNOWN_ERROR);
        }
    }

    private ResponseProtocol handleBet(String jsonInput, int protocolId) {
        RequestBet requestBet = gson.fromJson(jsonInput, RequestBet.class);
        String userFromRequest = requestBet.getUsername();
        int betAmount = requestBet.getBetAmount();

        if (isLoggedInUserNull()) {
            // return fail if logged in user is null
            return new ResponseBet(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (userFromRequest == null) {
            // return fail if request user is null
            return new ResponseBet(protocolId, FAIL, EMPTY);
        } else if (!getLoggedInUser().getUserName().equals(userFromRequest)) {
            // return fail if request user does not match logged in user
            return new ResponseBet(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (betAmount < 5) {
            // return fail if bet is less than 5 pounds
            return new ResponseBet(protocolId, FAIL, BET_TOO_SMALL);
        } else if (!isBetWithinBudget(betAmount)) {
            // return fail if bet amount is not within budget
            return new ResponseBet(protocolId, FAIL, BET_NOT_IN_BUDGET);
        } else if (isBetPlaced()) {
            // return fail
            return new ResponseBet(protocolId, FAIL, PLAYER_BET_PLACED);
        } else if (isBetWithinBudget(betAmount)) {
            // make bet and push it to all players
            makeBet(betAmount);
            // return success if bet within budget
            return new ResponseBet(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseBet(protocolId, FAIL, UNKNOWN_ERROR);
        }

    }

    /**
     * This method checks that the bet sent in the request is
     * within the player budget. Returns true if it is, false if not.
     *
     * @param betAmount
     * @return
     */
    private boolean isBetWithinBudget(int betAmount) {
        return getGame(gameJoined).getPlayer(getLoggedInUser()).isBetWithinBudget(betAmount);
    }

    /**
     * This method checks whether the players is bet
     * Returns true if it is, false if not.
     *
     * @return
     */
    private boolean isBetPlaced() {
        return getGame(gameJoined).getPlayer(getLoggedInUser()).isBetPlaced();
    }

    /**
     * This method doubles the current bet for the logged in user and sets
     * the player to isFinishedRound to true. The method
     * then pushes bets, budgets and playersFinished to all clients
     * in the same game.
     *
     * @param increaseAmountOfBet
     */
    private void doubleBet(int increaseAmountOfBet) {
        // set player bet
        getGame(gameJoined).getPlayer(getLoggedInUser()).setBet(increaseAmountOfBet);
        getGame(gameJoined).hit(getLoggedInUser().getUserName());
        getGame(gameJoined).getPlayer(getLoggedInUser()).setFinishedRound(true);

    }

    /**
     * This method sets the bet for the logged in user and sets
     * the player to isFinishedRound to true. The method
     * then pushes bets, budgets and playersFinished to all clients
     * in the same game.
     *
     * @param betAmount
     */
    private void makeBet(int betAmount) {
        if (getGame(gameJoined).isAllPlayersFinished()) {
            getGame(gameJoined).nextGame();
        }

        // set player bet
        getGame(gameJoined).getPlayer(getLoggedInUser()).setBet(betAmount);

        // set player bet placed
        getGame(gameJoined).getPlayer(getLoggedInUser()).setBetPlaced(true);

        //check all bets are placed and set allbetplaced to true if so
        getGame(gameJoined).allPlayersBetPlaced();


        if (getGame(gameJoined).isAllPlayersBetPlaced()) {
            // if all players finished deal cards to players and dealer i.e. start game
            getGame(gameJoined).startGame();
        }
    }


    /**
     * This method logs a user out of the client.
     *
     * @param jsonInput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleLogoutUser(String jsonInput, int protocolId) {
        RequestLogOut requestLogOut = gson.fromJson(jsonInput, RequestLogOut.class);
        String userFromRequest = requestLogOut.getUsername();

        if (isLoggedInUserNull()) {
            // return fail if logged in user is null
            return new ResponseLogOut(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (userFromRequest == null) {
            // return fail if request user is null
            return new ResponseLogOut(protocolId, FAIL, EMPTY);
        } else if (!getLoggedInUser().getUserName().equals(userFromRequest)) {
            // return fail if request user does not match logged in user
            return new ResponseLogOut(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (getLoggedInUser().getUserName().equals(userFromRequest)) {
            // log user out
            logUserOut();
            // return success if request user matches logged in user
            return new ResponseLogOut(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseLogOut(protocolId, FAIL, UNKNOWN_ERROR);
        }
    }

    /**
     * This method sets user to null and removes
     * user from users.
     */
    private void logUserOut() {
        // set logged in user to null
        User tempUser = getLoggedInUser();
        user = null;

        // remove user from users
        removeUserFromUsers(tempUser);
    }

    /**
     * This method quits the player from the games. This means
     * the user is no longer subscribed to the game pushes, and sets
     * gameJoined to null.
     *
     * @param JSONinput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleQuitGame(String JSONinput, int protocolId) {
        RequestQuitGame requestQuitGame = this.gson.fromJson(JSONinput, RequestQuitGame.class);

        String requestUsername = requestQuitGame.getUsername();
        String gameToQuit = requestQuitGame.getGameToQuit();

        if (this.getLoggedInUser() == null) {
            // return fail if no one logged in
            return new ResponseQuitGame(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (!getLoggedInUser().getUserName().equals(requestUsername)) {
            // return fail if log in user does not match request user
            return new ResponseQuitGame(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (this.getGames().size() == 0) {
            // return fail if no games exist
            return new ResponseQuitGame(protocolId, FAIL, NO_GAMES);
        } else if (getGame(gameToQuit) == null) {
            // return fail if game to quit does not exist
            return new ResponseQuitGame(protocolId, FAIL, NO_GAME);
        } else {
            quitGame(gameToQuit, requestUsername);
            return new ResponseQuitGame(protocolId, SUCCESS);
        }
    }

    /**
     * This method removes the user from the game and sets
     * game joined to null.
     *
     * @param gameToQuit
     * @param requestUsername
     * @return
     */
    private void quitGame(String gameToQuit, String requestUsername) {
        if (getGame(gameToQuit) != null && getGame(gameToQuit).getPlayer(requestUsername) != null) {
            getGame(gameToQuit).removePlayer(requestUsername);
            this.gameJoined = null;

            // if no players in the game, remove the game
            if (getGame(gameToQuit).getPlayers().size() == 0) {
                removeGame(gameToQuit);
                gameNames.remove(gameToQuit);
            }

            updateGameNames();

        }
    }

    public void removeGame(String gameToRemove) {
        int gameIndex = 0;
        for (GameLobby game : games) {
            if (game.getLobbyName().equals(gameToRemove)) {
                break;
            }
            gameIndex++;
        }

        games.remove(gameIndex);
    }

    /**
     * This method joins the user to the game sent in the request.
     *
     * @param JSONinput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleJoinGame(String JSONinput, int protocolId) {
        RequestJoinGame requestJoinGame = this.gson.fromJson(JSONinput, RequestJoinGame.class);

        String requestUsername = requestJoinGame.getUsername();
        String gameToJoin = requestJoinGame.getGameToJoin();

        if (this.getLoggedInUser() == null) {
            // return fail if no one logged in
            return new ResponseJoinGame(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (!getLoggedInUser().getUserName().equals(requestUsername)) {
            // return fail if log in user does not match request user
            return new ResponseJoinGame(protocolId, FAIL, USERNAME_MISMATCH);
        } else if (this.getGames().size() == 0) {
            // return fail if no games exist
            return new ResponseJoinGame(protocolId, FAIL, NO_GAMES);
        } else if (getGame(gameToJoin) == null) {
            // return fail if game to join does not exist
            return new ResponseJoinGame(protocolId, FAIL, NO_GAME);
        } else {
            this.gameJoined = gameToJoin;
            joinGame(gameToJoin);
            return new ResponseJoinGame(protocolId, SUCCESS);
        }
    }

    /**
     * We use a method to create a new game of blackjack and add it to the games list.
     * This method returns a response containing a list of the current games.
     *
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleCreateGame(String JSONInput, int protocolId) {
        RequestCreateGame request = gson.fromJson(JSONInput, RequestCreateGame.class);
        String requestUsername = request.getUsername();

        if (isLoggedInUserNull()) {
            // return fail if not logged in
            return new ResponseCreateGame(protocolId, FAIL, null, NOT_LOGGED_IN);
        } else if (requestUsername == null) {
            // return fail if request user is null
            return new ResponseCreateGame(protocolId, FAIL, null, EMPTY);
        } else if (!requestUsername.equals(getLoggedInUser().getUserName())) {
            // return fail if request user does not match logged in user
            return new ResponseCreateGame(protocolId, FAIL, null, NOT_LOGGED_IN);
        } else if (getGame(requestUsername) != null) {
            // return fail if game with request game name already exists
            return new ResponseCreateGame(protocolId, FAIL,
                    getGame(requestUsername).getLobbyName(), GAME_ALREADY_EXISTS);
        } else if (gameJoined != null) {
            // return fail if already in a game
            return new ResponseCreateGame(protocolId, FAIL,
                    getGame(requestUsername).getLobbyName(), GAME_ALREADY_EXISTS);
        } else if (getGame(requestUsername) == null) {
            // create game if game does not exist
            GameLobby newGame = createGame();
            String gameName = newGame.getLobbyName();

            // return success
            return new ResponseCreateGame(protocolId, SUCCESS, gameName);
        } else {
            // return fail for unknown error
            return new ResponseCreateGame(protocolId, FAIL, null);
        }
    }


    private void joinGame(String lobbyname) {
        getGame(lobbyname).addPlayer(this.getLoggedInUser(), this.toClientSocket);
    }

    /**
     * This method creates a game with the name of the logged in user.
     * It adds the new game to the list of games, updates the gamesNames, and pushes
     * the gamesList to all clients.
     *
     * @return
     */
    private GameLobby createGame() {
        GameLobby newGame = new GameLobby(getLoggedInUser(), this.toClientSocket);

        this.getGames().add(newGame);

        this.updateGameNames();

        return newGame;
    }


    /**
     * We use a method to handle the get message requests. This
     * method returns a responseGetMessages which contains all the messages
     * as specified by the offset in the request.
     *
     * @param JSONInput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleGetMessages(String JSONInput, int protocolId) {
        RequestGetMessages requestGetMessages = this.gson.fromJson(JSONInput, RequestGetMessages.class);
        int offset = requestGetMessages.getOffset();

        if (getLoggedInUser() == null) {
            // return fail if user not logged in
            return new ResponseGetMessages(protocolId, FAIL, null, NOT_LOGGED_IN);
        } else if (gameJoined == null) {
            // return fail if user has not joined a game
            return new ResponseGetMessages(protocolId, FAIL, null, NO_GAME_JOINED);
        } else if (offset < -1) {
            // return fail for wrong index
            return new ResponseGetMessages(protocolId, FAIL, null, WRONG_OFFSET);
        } else if (offset >= -1) {
            // get messages from queue as per the offset
            ArrayList<MessageObject> messagesToClient = getMessages(requestGetMessages);

            // get offset
            int offsetFromQueue = getGame(gameJoined).getMessageQueue().size();

            // return success if offset greater than -2
            return new ResponseGetMessages(protocolId, SUCCESS, messagesToClient, offsetFromQueue);
        } else {
            // return fail for unknown error
            return new ResponseGetMessages(protocolId, FAIL, null, UNKNOWN_ERROR);
        }


    }

    /**
     * We use a method to get the messages from the message queue. This method
     * return an arraylist of the messages as specified by the offset in the request.
     *
     * @param requestGetMessages
     * @return
     */
    private ArrayList<MessageObject> getMessages(RequestGetMessages requestGetMessages) {

        int offset = requestGetMessages.getOffset();

        ArrayList<MessageObject> messageArrayList = new ArrayList<>(getGame(gameJoined).getMessageQueue());
        ArrayList<MessageObject> messagesToClient = new ArrayList<>();

        for (int i = offset + 1; i < messageArrayList.size(); i++) {
            messagesToClient.add(messageArrayList.get(i));
        }

        return messagesToClient;
    }

    /**
     * We use a method to handle the send message requests. This method
     * adds the message the message queue and returns a response depending on
     * if it was successfully added.
     *
     * @param JSONInput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleSendMessage(String JSONInput, int protocolId) {
        RequestSendMessage requestSendMessage = this.gson.fromJson(JSONInput, RequestSendMessage.class);
        MessageObject messageFromRequest = requestSendMessage.getMessageObject();

        if (getLoggedInUser() == null) {
            // return fail if user not logged in
            return new ResponseSendMessage(protocolId, FAIL, NOT_LOGGED_IN);
        } else if (gameJoined == null) {
            // return fail if user has not joined a game
            return new ResponseSendMessage(protocolId, FAIL, NO_GAME_JOINED);
        } else if (messageFromRequest.isEmpty()) {
            // return fail for empty message
            return new ResponseSendMessage(protocolId, FAIL, EMPTY_MSG);
        } else if (!messageFromRequest.isEmpty()) {
            // add message from request to message queue
            addToMessageQueue(messageFromRequest);
            // return success if message is not empty
            return new ResponseSendMessage(protocolId, SUCCESS);
        } else {
            // return fail for unknown error
            return new ResponseSendMessage(protocolId, FAIL, UNKNOWN_ERROR);
        }


    }

    /**
     * A method to login a user. This method checks the password send from the client
     * with the password stored in the database, if they match, we return a successful response,
     * otherwise, we return a failed response.
     *
     * @param JSONInput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleLoginUser(String JSONInput, int protocolId) {
        // We deserialize it again but as a RequestRegisterUser object
        RequestLoginUser requestLoginUser = this.gson.fromJson(JSONInput, RequestLoginUser.class);

        User userFromDatabase = null;
        User userFromRequest = requestLoginUser.getUser();

        try {
            // retrieve user from database
            userFromDatabase = this.functionDB.retrieveUserFromDatabase(userFromRequest.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isUserLoggedIn(userFromRequest)) {
            // return fail if user logged in on any client
            return new ResponseLoginUser(protocolId, FAIL, null, ALREADY_LOGGED_IN);
        } else if (!isLoggedInUserNull()) {
            // return fail if already logged in
            return new ResponseLoginUser(protocolId, FAIL, null, ALREADY_LOGGED_IN);
        } else if (userFromRequest.getUserName().equals("") || userFromRequest.getPassword().equals("")) {
            // return fail if user from request is empty
            return new ResponseLoginUser(protocolId, FAIL, null, USERNAME_MISMATCH);
        } else if (userFromDatabase == null) {
            // return fail if user does not exist in database
            return new ResponseLoginUser(protocolId, FAIL, null, NON_EXIST);
        } else if (!userFromRequest.checkPassword(userFromDatabase)) {
            // return fail if passwords mismatch
            return new ResponseLoginUser(protocolId, FAIL, null, PASSWORD_MISMATCH);
        } else if (userFromRequest.checkPassword(userFromDatabase)) {
            // if username and password match, we set this.user to user
            this.user = userFromDatabase;
            this.addUsertoUsers(this.user);

            // return success if password and username match
            return new ResponseLoginUser(protocolId, SUCCESS, userFromDatabase);
        } else {
            // return fail for unknown error
            return new ResponseLoginUser(protocolId, FAIL, null, UNKNOWN_ERROR);
        }
    }

    private boolean isUserLoggedIn(User userFromRequest) {
        for (User user : users) {
            if (userFromRequest.getUserName().equals(user.getUserName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to handle a user Registering. This method will
     * insert a users details into the database.
     *
     * @param JSONInput
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleRegisterUser(String JSONInput, int protocolId) {
        boolean successRegister = false;
        String sqlState = "";

        // We deserialize the request again but as a RequestRegisterUser object
        RequestRegisterUser requestRegisterUser = this.gson.fromJson(JSONInput, RequestRegisterUser.class);
        User userFromRequest = requestRegisterUser.getUser();

        try {
            // we try to insert user into database
            successRegister = this.functionDB.insertUserIntoDatabase(userFromRequest);
        } catch (SQLException e) {
            sqlState = e.getSQLState();
            if (sqlState.equalsIgnoreCase("23505")) {
                System.out.println(DUPE_USERNAME);
            } else {
                System.out.println(e.getSQLState() + ": " + e.getMessage());
            }
        }

        if (userFromRequest.isUserNull()) {
            // return fail if request user is null
            return new ResponseRegisterUser(protocolId, FAIL, EMPTY_INSERT);
        } else if (userFromRequest.isEmpty()) {
            // return fail if request user is empty
            return new ResponseRegisterUser(protocolId, FAIL, EMPTY_INSERT);
        } else if (sqlState.equalsIgnoreCase("23505")) {
            // return fail if user already in database
            return new ResponseRegisterUser(protocolId, FAIL, DUPE_USERNAME);
        } else if (!isLoggedInUserNull()) {
            // return fail if user already logged in
            return new ResponseRegisterUser(protocolId, FAIL, ALREADY_LOGGED_IN);
        } else if (successRegister) {
            // return success if user inserted into database
            return new ResponseRegisterUser(protocolId, SUCCESS, REGISTERED);
        } else {
            // return fail for unknown error
            return new ResponseRegisterUser(protocolId, FAIL, UNKNOWN_ERROR);
        }
    }

    /**
     * This method updates the gameNames list with the
     * current games in games.
     */
    public synchronized void updateGameNames() {

        if (this.games.size() != 0) {
            for (GameLobby game : games) {
                if (!gameNames.contains(game.getLobbyName())) {
                    this.gameNames.add(game.getLobbyName());
                }
            }
        }

    }

    /**
     * Add a message to the message queue.
     *
     * @param
     * @throws IOException
     */
    public void addToMessageQueue(MessageObject msg) {
        getGame(gameJoined).getMessageQueue().add(msg);
    }


    public synchronized void addUsertoUsers(User user) {
        this.users.add(user);
    }

    public synchronized void removeUserFromUsers(User user) {
        this.users.remove(user);
    }

    public synchronized int getSizeOfUsers() {
        return users.size();
    }

    public synchronized User getUserFromUsers(int i) {
        return users.get(i);
    }

    public synchronized User getUserFromUsers(User user) {
        int index = users.indexOf(user);
        return users.get(index);

    }

    public User getLoggedInUser() {
        return user;
    }

    public boolean isLoggedInUserNull() {
        return user == null;
    }

    public boolean isLoggedInUserEmpty() {
        return user.isEmpty();
    }

    public User getUser() {
        return user;
    }

    public void setLoggedInUser(User user) {
        this.user = user;
    }

    public ConcurrentLinkedDeque<Socket> getSocketList() {
        return socketList;
    }

    public CopyOnWriteArrayList<User> getUsers() {
        return users;
    }

    public CopyOnWriteArrayList<GameLobby> getGames() {
        return games;
    }

    public Set<String> getGameNames() {
        return new TreeSet<>(this.gameNames);
    }

    public String getGameJoined() {
        return gameJoined;
    }

    public GameLobby getGame(User user) {
        for (GameLobby game : games) {
            if (game.getLobbyName().equals(user.getUserName())) {
                return game;
            }
        }
        return null;
    }

    public GameLobby getGame(String lobbyName) {
        for (GameLobby game : games) {
            if (game.getLobbyName().equals(lobbyName)) {
                return game;
            }
        }
        return null;
    }
}
