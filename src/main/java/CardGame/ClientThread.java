package CardGame;

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
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.ProtocolMessages.*;
import static CardGame.ProtocolTypes.*;

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

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;


    public ClientThread(Socket toClientSocket, ConcurrentLinkedDeque<MessageObject> messageQueue,
                        ConcurrentLinkedDeque<Socket> socketList, CopyOnWriteArrayList<User> users,
                        FunctionDB functionsDB) {
        this.toClientSocket = toClientSocket;
        this.clientID = Thread.currentThread().getId();
        this.messageQueue = messageQueue;
        this.socketList = socketList;
        this.users = users;
        this.functionDB = functionsDB;
        this.gson = new Gson();
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
            default:
                return new ResponseProtocol(protocolId, UNKNOWN_TYPE, FAIL, UNKNOWN_ERROR);
        }
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

        return new ResponseGetMessages(protocolId, GET_MESSAGE, SUCCESS, messagesToClient);
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

        // We add the user to the current thread and the list of current users
        this.user = requestLoginUser.getUser();
        addUsertoUsers(this.user);

        // retrieve user from database and check passwords match
        try {
            // retrieve user
            User existingUser = this.functionDB.retrieveUserFromDatabase(this.user.getUserName());

            // check if passwords match
            if (existingUser.getPassword() == null || existingUser.getUserName() == null) {
                response = new ResponseLoginUser(protocolId, FAIL, NON_EXIST);
            } else if (existingUser.getPassword().equals(this.user.getPassword())) {
                response = new ResponseLoginUser(protocolId, SUCCESS);
            } else {
                response = new ResponseLoginUser(protocolId, FAIL);
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

    public synchronized void removeUser(User user) {
        this.users.remove(user);
    }

    public synchronized int getSizeOfUsers() {
        return users.size();
    }

    public synchronized User getUser(int i) {
        return users.get(i);
    }

    public synchronized User getUser(User user) {
        int index = users.indexOf(user);
        return users.get(index);

    }


}
