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
        this.functionDB = new FunctionDB();
        this.clientID = Thread.currentThread().getId();
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

            // We deserialise it again but as a RequestRegisterUser object
            RequestRegisterUser rru = gson.fromJson(JSONInput, RequestRegisterUser.class);
            this.user = rru.getUser();

            // Try to insert into database
            try {
                boolean success = functionDB.insertUserIntoDatabase(this.user);
                response = new ResponseRegisterUser(protocolId, 1);
            } catch (SQLException e) {
                String sqlState = e.getSQLState();
                if (sqlState.equalsIgnoreCase("23505")) {
                    response = new ResponseRegisterUser(protocolId, 0, ProtocolMessages.DUPE_USERNAME);
                } else {
                    response = new ResponseRegisterUser(protocolId, 0);
                }
            } finally {
                return response;
            }


        } else if (requestType == ProtocolTypes.LOGIN_USER) {
            RequestLoginUser rlu = (RequestLoginUser) request;
            this.user = rlu.getUser();
            String userName = this.user.getUserName();

            try {
                functionDB.isUserRegistered(userName);
                response = new ResponseLoginUser(protocolId, 1);
            } catch (SQLException e) {
                System.out.println("Failed to check if user is registered");
                e.printStackTrace();
                response = new ResponseLoginUser(protocolId, 0);
            } finally {
                return response;
            }

        } else if (requestType == ProtocolTypes.SEND_MESSAGE) {
            RequestSendMessage rsm = (RequestSendMessage) request;

            // send message stuff
            return response;
        } else {
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
