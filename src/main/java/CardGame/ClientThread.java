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
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.ProtocolMessages.*;
import static CardGame.ProtocolTypes.*;
import static CardGame.Pushes.PushProtocol.encodePush;

/**
 * This class implements the Runnable interface and
 * make a connection via a 'Socket.'
 *
 * @Author Tom Brereton
 */
public class ClientThread implements Runnable {
    private Socket toClientSocket;
    private boolean clientAlive;
    private long clientID;
    protected User user;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private FunctionDB functionDB;
    private Gson gson;
    private CardGameServer server;
    private String gameJoined;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;
    private volatile CopyOnWriteArrayList<GameLobby> games;
    private volatile CopyOnWriteArrayList<String> gameNames;


    public ClientThread(CardGameServer server, Socket toClientSocket, ConcurrentLinkedDeque<MessageObject> messageQueue,
                        ConcurrentLinkedDeque<Socket> socketList, CopyOnWriteArrayList<User> users,
                        FunctionDB functionsDB, CopyOnWriteArrayList<GameLobby> games,
                        CopyOnWriteArrayList<String> gameNames) {
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
    }

    /**
     * This method runs when the thread starts.
     */
    @Override
    public void run() {

        try {

            this.inputStream = new DataInputStream(toClientSocket.getInputStream());
            this.outputStream = new DataOutputStream(toClientSocket.getOutputStream());
            this.clientAlive = true;

            while (clientAlive) {
                String jsonInString = inputStream.readUTF();

                if (!jsonInString.isEmpty()) {

                    ResponseProtocol response = handleInput(jsonInString);
                    System.out.println(response);

                    String jsonOutString = this.gson.toJson(response);

                    outputStream.writeUTF(jsonOutString);
                    outputStream.flush();
                }
            }

        } catch (EOFException e) {
            System.out.println("Client likely disconnected.: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnections();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method closes all open connections.
     *
     * @throws IOException
     */
    public void closeConnections() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        this.toClientSocket.close();
    }

    /**
     * This method handles the input from the client and
     * returns a response as per the request sent.
     *
     * @param JSONInput
     * @return
     */
    public ResponseProtocol handleInput(String JSONInput) {

        // Deserialise request object
        RequestProtocol request = this.gson.fromJson(JSONInput, RequestProtocol.class);

        // Get packet ID and its type
        int protocolId = request.getProtocolId();
        int requestType = request.getType();

        switch (requestType) {
            case REGISTER_USER:
                return handleRegisterUser(JSONInput, protocolId);
            case LOGIN_USER:
                return handleLoginUser(JSONInput, protocolId);
            case SEND_MESSAGE:
                return handleSendMessage(JSONInput, protocolId);
            case GET_MESSAGE:
                return handleGetMessages(JSONInput, protocolId);
            case CREATE_GAME:
                return handleCreateGame(protocolId);
            default:
                return new ResponseProtocol(protocolId, UNKNOWN_TYPE, FAIL, UNKNOWN_ERROR);
        }
    }

    /**
     * We use a method to create a new game of blackjack and add it to the games list.
     * This method returns a response containing a list of the current games.
     *
     * @param protocolId
     * @return
     */
    private ResponseProtocol handleCreateGame(int protocolId) {
        if (this.getLoggedInUser() == null) {
            return new ResponseCreateGame(protocolId, FAIL, null, NOT_LOGGED_IN);
        } else if (getGame(getLoggedInUser()) != null) {
            return new ResponseCreateGame(protocolId, FAIL,
                    getGame(getLoggedInUser()).getLobbyName(), GAME_ALREADY_EXISTS);
        }

        GameLobby newGame = createGame();
        String gameName = newGame.getLobbyName();

        if (isNewGameExists(newGame)) {
            this.gameJoined = gameName;
            joinGame(this.gameJoined);
            return new ResponseCreateGame(protocolId, SUCCESS, gameName);
        } else {
            return new ResponseCreateGame(protocolId, FAIL, null);
        }
    }

    private boolean isNewGameExists(GameLobby newGame) {
        return this.getGame(this.getLoggedInUser()).equals(newGame);
    }

    /**
     * This method pushes all the player hands to
     * all the clients joined in the same game for this thread.
     *
     * @return true if pushed, false if not.
     */
    private boolean pushPlayerHands() {
        Map<String, Hand> playerHands = this.getGame(gameJoined).getPlayerHands();

        PushPlayerHands push = new PushPlayerHands(playerHands);

        return pushToPlayers(push);

    }

    /**
     * This method pushes the dealer hand to
     * all the clients joined in the same game for this thread.
     *
     * @return true if pushed, false if not.
     */
    private boolean pushDealerHand() {
        Hand dealerHand = this.getGame(gameJoined).getDealerHand();

        PushDealerHand push = new PushDealerHand(dealerHand);

        return pushToPlayers(push);
    }

    /**
     * This method pushes all the player budgets to
     * all the clients joined in the same game for this thread.
     *
     * @return true if pushed, false if not.
     */
    private boolean pushPlayerBudgets() {
        Map<String, Integer> playerBudgets = this.getGame(gameJoined).getPlayerBudgets();

        PushPlayerBudgets push = new PushPlayerBudgets(playerBudgets);

        return pushToPlayers(push);
    }

    /**
     * This method pushes all the player names to
     * all the clients joined in the same game for this thread.
     *
     * @return true if pushed, false if not.
     */
    private boolean pushPlayerNames() {
        ArrayList<String> playerNames = this.getGame(gameJoined).getPlayerNames();

        PushPlayerNames push = new PushPlayerNames(playerNames);

        return pushToPlayers(push);
    }

    /**
     * This method takes in any PushProtocol subtype and
     * pushes it to all the players in the game.
     *
     * @param push
     * @param <T>
     * @return True if push successful, false if not
     */
    private <T extends PushProtocol> boolean pushToPlayers(T push) {
        DataOutputStream outputStream = null;
        Map<String, Socket> playerSockets = this.getGame(gameJoined).getPlayerSockets();

        if (!this.socketList.isEmpty()) {
            try {
                for (Map.Entry<String, Socket> entry : playerSockets.entrySet()) {
                    outputStream = new DataOutputStream(entry.getValue().getOutputStream());
                    outputStream.writeUTF(encodePush(push));
                }
                return true;
            } catch (IOException e) {
                System.out.println("Failed to send out list of game names");
                return false;
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void joinGame(String lobbyname) {
        getGame(lobbyname).addPlayer(this.getLoggedInUser(), this.toClientSocket);
        pushDealerHand();
        pushPlayerBudgets();
        pushPlayerHands();
        pushPlayerNames();
    }

    /**
     * This method creates a game with the name of the logged in user.
     * It adds the new game to the list of games, updates the gamesNames, and pushes
     * the gamesList to all clients.
     *
     * @return
     */
    private GameLobby createGame() {
        GameLobby newGame = new GameLobby(this.getLoggedInUser(), this.toClientSocket);

        this.getGames().add(newGame);

        this.updateGameNames();
        this.pushGameListToClient();

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
        ArrayList<MessageObject> messagesToClient = getMessages(requestGetMessages);

        return new ResponseGetMessages(protocolId, SUCCESS, messagesToClient);
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

        ArrayList<MessageObject> messageArrayList = new ArrayList<>(messageQueue);
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
        ResponseProtocol response = null;

        RequestSendMessage requestSendMessage = this.gson.fromJson(JSONInput, RequestSendMessage.class);
        MessageObject message = requestSendMessage.getMessageObject();
        try {
            addToMessageQueue(message);
            response = new ResponseSendMessage(protocolId, SUCCESS);
        } catch (IOException e) {
            if (e.getMessage().equals(NO_CLIENTS)) {
                response = new ResponseSendMessage(protocolId, FAIL, NO_CLIENTS);
            } else {
                response = new ResponseSendMessage(protocolId, FAIL);
            }
        } finally {
            return response;
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
        ResponseProtocol response = null;

        // We deserialise it again but as a RequestRegisterUser object
        RequestLoginUser requestLoginUser = this.gson.fromJson(JSONInput, RequestLoginUser.class);

        // retrieve user from database and check passwords match
        try {
            // retrieve user
            User tempUser = requestLoginUser.getUser();
            User existingUser = this.functionDB.retrieveUserFromDatabase(tempUser.getUserName());

            // check if passwords match
            if (existingUser.getPassword() == null || existingUser.getUserName() == null) {
                response = new ResponseLoginUser(protocolId, FAIL, null, NON_EXIST);
            } else if (existingUser.checkPassword(tempUser)) {
                response = new ResponseLoginUser(protocolId, SUCCESS, existingUser);

                // We add the user to the current thread and the list of current users
                this.user = existingUser;
                this.addUsertoUsers(this.user);
            } else {
                response = new ResponseLoginUser(protocolId, FAIL, null, PASSWORD_MISMATCH);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return response;
        }
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
        ResponseProtocol response = null;

        // We deserialise it again but as a RequestRegisterUser object
        RequestRegisterUser requestRegisterUser = this.gson.fromJson(JSONInput, RequestRegisterUser.class);
        this.user = requestRegisterUser.getUser();

        // Try to insert into database
        try {
            if (this.user.getUserName() == null || this.user.isUserEmpty()) {
                response = new ResponseRegisterUser(protocolId, FAIL, EMPTY_INSERT);
            } else {
                boolean success = this.functionDB.insertUserIntoDatabase(this.user);
                response = new ResponseRegisterUser(protocolId, SUCCESS);
            }
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if (sqlState.equalsIgnoreCase("23505")) {
                response = new ResponseRegisterUser(protocolId, FAIL, DUPE_USERNAME);
            } else {
                response = new ResponseRegisterUser(protocolId, FAIL);
            }
        } finally {
            return response;
        }
    }


    /**
     * This method updates the gameNames list with the
     * current games in games.
     */
    public synchronized void updateGameNames() {

        if (this.games.size() != 0) {
            for (GameLobby game : games) {
                this.gameNames.add(game.getLobbyName());
            }
        }
    }

    public synchronized PushGameNames pushGameListToClient() {
        DataOutputStream outputStream;

        PushGameNames pushGameNames = new PushGameNames(getGameNames());

        if (!this.socketList.isEmpty()) {
            for (Socket sock : this.socketList) {
                try {
                    outputStream = new DataOutputStream(sock.getOutputStream());
                    outputStream.writeUTF(encodePush(pushGameNames));
                } catch (IOException e) {
                    System.out.println("Failed to send out list of game names");
                } finally {
                    return pushGameNames;
                }
            }
        }
        return pushGameNames;
    }


    /**
     * Add a message to the message queue.
     *
     * @param
     * @throws IOException
     */
    public void addToMessageQueue(MessageObject msg) throws IOException {
        this.messageQueue.add(msg);
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

    public ArrayList<String> getGameNames() {
        return new ArrayList<>(this.gameNames);
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
