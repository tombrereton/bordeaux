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
    private String clientID;
    private FunctionDB functionDB;// TODO: make functionDB methods synchronised
    protected User user;

    public ClientThread(Socket toClientSocket) {
        this.toClientSocket = toClientSocket;
        this.functionDB = new FunctionDB();
    }

    public ResponseProtocol handleInput(String JSONInput){

        ResponseProtocol response = null;

        Gson gson = new Gson();

        RequestProtocol request = gson.fromJson(JSONInput, RequestProtocol.class);

        int requestType = request.getType();

        if(requestType == 0){
            RequestRegisterUser rru = (RequestRegisterUser) request;
            this.user = rru.getUser();

            try {

                functionDB.insertUserIntoDatabase(this.user);
                int protocolId = rru.getProtocolId();
                response = new ResponseRegisterUser(protocolId, 1);

            } catch (SQLException e) {

                System.out.println("Failed to insert user into database");
                e.printStackTrace();

                int protocolId = rru.getProtocolId();
                response = new ResponseRegisterUser(protocolId, 0);

            } finally {
                return response;
            }

        } else if (requestType == 1) {
            RequestLoginUser rlu = (RequestLoginUser) request;
            this.user = rlu.getUser();
            String userName = this.user.getUserName();

            try {
                functionDB.isUserRegistered(userName);
                int protocolId = rlu.getProtocolId();
                response = new ResponseLoginUser(protocolId, 1);
            } catch (SQLException e) {
                System.out.println("Failed to check if user is registered");
                e.printStackTrace();
                int protocolId = rlu.getProtocolId();
                response = new ResponseLoginUser(protocolId, 0);
            } finally {
                return response;
            }

            // login user stuff
        } else if (requestType == 2 ) {
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

            while(clientAlive) {

                String jsonInString = inputStream.readUTF();

                if (!jsonInString.isEmpty()){
                    ResponseProtocol response = handleInput(jsonInString);

                    Gson gson = new Gson();

                    String jsonOutString = gson.toJson(response);

                    outputStream.writeUTF(jsonOutString);
                    outputStream.flush();

                }
            }



            inputStream.close();
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
