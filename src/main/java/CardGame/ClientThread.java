package CardGame;

import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestLoginUser;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Requests.RequestSendMessage;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseRegisterUser;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

import static CardGame.ProtocolMessages.*;

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
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public ClientThread(Socket toClientSocket) {
        this.toClientSocket = toClientSocket;
        this.clientID = Thread.currentThread().getId();

        // We connect to the database and create functionsDB object
        this.functionDB = new FunctionDB();
    }

    public ResponseProtocol handleInput(String JSONInput) {

        // Deserialise request object
        Gson gson = new Gson();
        RequestProtocol request = gson.fromJson(JSONInput, RequestProtocol.class);

        // Get packet ID and its type
        int protocolId = request.getProtocolId();
        int requestType = request.getType();

        // Declare a response variable
        ResponseProtocol response = null;

        // If type is register we try to register user in database
        if (requestType == ProtocolTypes.REGISTER_USER) {
            return handleRegisterUser(JSONInput, gson, protocolId, response);

        } else if (requestType == ProtocolTypes.LOGIN_USER) {
            return handleLoginUser(JSONInput, gson, protocolId, response);

        } else if (requestType == ProtocolTypes.SEND_MESSAGE) {
            RequestSendMessage rsm = (RequestSendMessage) request;

            // send message stuff
            return response;
        } else {
            return response;
        }
    }

    private ResponseProtocol handleLoginUser(String JSONInput, Gson gson, int protocolId, ResponseProtocol response) {
        // We deserialise it again but as a RequestRegisterUser object
        RequestLoginUser requestLoginUser = gson.fromJson(JSONInput, RequestLoginUser.class);
        this.user = requestLoginUser.getUser();

        // retrive user from database and check passwords match
        try {
            // retrieve user
            User existingUser = functionDB.retrieveUserFromDatabase(this.user.getUserName());

            // check if passwords match
            if (existingUser.getPassword() == null) {
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

    private ResponseProtocol handleRegisterUser(String JSONInput, Gson gson, int protocolId, ResponseProtocol response) {

        // We deserialise it again but as a RequestRegisterUser object
        RequestRegisterUser rru = gson.fromJson(JSONInput, RequestRegisterUser.class);
        this.user = rru.getUser();

        // Try to insert into database
        try {
            boolean success = functionDB.insertUserIntoDatabase(this.user);
            response = new ResponseRegisterUser(protocolId, SUCCESS);
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

    @Override
    public void run() {

        try {

            DataInputStream inputStream = new DataInputStream(toClientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(toClientSocket.getOutputStream());
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
}
