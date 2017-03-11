package CardGame;

import CardGame.Requests.RequestLoginUser;
import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Requests.RequestSendMessage;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseRegisterUser;
import CardGame.Responses.ResponseSendMessage;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
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
    private FunctionDB functionDB;
    protected User user;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;


    public ClientThread(Socket toClientSocket, ConcurrentLinkedDeque<MessageObject> messageQueue,
                        ConcurrentLinkedDeque<Socket> socketList, CopyOnWriteArrayList<User> users) {
        this.toClientSocket = toClientSocket;
        this.clientID = Thread.currentThread().getId();
        this.messageQueue = messageQueue;
        this.socketList = socketList;
        this.users = users;

        // We connect to the database and create functionsDB object
        // TODO, move this to Server and make methods static
        this.functionDB = new FunctionDB();
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

                    Gson gson = new Gson();

                    String jsonOutString = gson.toJson(response);

                    outputStream.writeUTF(jsonOutString);
                    outputStream.flush();

                }
            }

            inputStream.close();
            outputStream.close();
        } catch (EOFException e) {
            System.out.println("Client likely disconnected.: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.toClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        // Deserialise request object
        Gson gson = new Gson();
        RequestProtocol request = gson.fromJson(JSONInput, RequestProtocol.class);

        // Get packet ID and its type
        int protocolId = request.getProtocolId();
        int requestType = request.getType();

        // Declare a response variable
        ResponseProtocol response = null;

        // We handle the request accordingly
        if (requestType == REGISTER_USER) {
            return handleRegisterUser(JSONInput, gson, protocolId, response);

        } else if (requestType == LOGIN_USER) {
            return handleLoginUser(JSONInput, gson, protocolId, response);

        } else if (requestType == SEND_MESSAGE) {
            return handleSendMessage(JSONInput, gson, protocolId, response);
        } else {
            return new ResponseProtocol(protocolId, UNKNOWN_TYPE, FAIL, UNKNOWN_ERROR);
        }
    }

    private ResponseProtocol handleSendMessage(String JSONInput, Gson gson, int protocolId, ResponseProtocol response) {
        RequestSendMessage requestSendMessage = gson.fromJson(JSONInput, RequestSendMessage.class);
        MessageObject message = requestSendMessage.getMessageObject();
        try {
            addToMessageQueue(message);
            response = new ResponseSendMessage(protocolId, SUCCESS);
        } catch (IOException e) {
            if (e.getMessage().equals(NO_CLIENTS)){
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
     * @param gson
     * @param protocolId
     * @param response
     * @return
     */
    private ResponseProtocol handleLoginUser(String JSONInput, Gson gson, int protocolId, ResponseProtocol response) {
        // We deserialise it again but as a RequestRegisterUser object
        RequestLoginUser requestLoginUser = gson.fromJson(JSONInput, RequestLoginUser.class);
        this.user = requestLoginUser.getUser();
        
        addUsertoUsers(this.user);

        // retrieve user from database and check passwords match
        try {
            // retrieve user
            User existingUser = functionDB.retrieveUserFromDatabase(this.user.getUserName());

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
     * @param gson
     * @param protocolId
     * @param response
     * @return
     */
    private ResponseProtocol handleRegisterUser(String JSONInput, Gson gson, int protocolId, ResponseProtocol response) {

        // We deserialise it again but as a RequestRegisterUser object
        RequestRegisterUser requestRegisterUser = gson.fromJson(JSONInput, RequestRegisterUser.class);
        this.user = requestRegisterUser.getUser();

        // Try to insert into database
        try {
            if (this.user.getUserName() == null || this.user.isUserEmpty()) {
                response = new ResponseRegisterUser(protocolId, FAIL, EMPTY_INSERT);
            } else {
                boolean success = functionDB.insertUserIntoDatabase(this.user);
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
     * Send messages to all clients
     * @param
     * @throws IOException
     */
    public void addToMessageQueue(MessageObject msg) throws IOException {
        this.messageQueue.add(msg);


//        if (this.socketList.size() == 0){
//            throw new IOException(NO_CLIENTS);
//        }
//
//        Gson gson = new Gson();
//        DataOutputStream outputStream;
//        for (Socket client :  this.socketList) {
//            if(!client.isClosed()){
//                outputStream = new DataOutputStream(client.getOutputStream());
//                String jsonOutString = gson.toJson(msg);
//                outputStream.writeUTF(jsonOutString);
//                outputStream.flush();
//            }
//        }
    }


    public synchronized void addUsertoUsers(User user) {
        this.users.add(user);
    }

    public synchronized void removeUser(User user){
        this.users.remove(user);
    }

    public synchronized int getSizeOfUsers(){
        return users.size();
    }

    public synchronized User getUser(int i){
        return users.get(i);
    }

    public synchronized User getUser(User user){
        int index = users.indexOf(user);
        return users.get(index);

    }


}
