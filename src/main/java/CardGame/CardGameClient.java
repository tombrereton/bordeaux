package CardGame;

import CardGame.Requests.RequestLoginUser;
import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseRegisterUser;
import CardGame.Responses.ResponseSendMessage;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static CardGame.ProtocolMessages.FAIL;
import static CardGame.ProtocolMessages.UNKNOWN_ERROR;
import static CardGame.ProtocolTypes.*;

/**
 * TODO: fill this out.
 * Created by tom on 25/02/17.
 */
public class CardGameClient {

    private Socket socket;
    private DataInputStream serverInputStream, threadInputStream;
    private DataOutputStream serverOutputStream;
    private String host = "localhost";
    private int port = 7654;
    private Gson gson = new Gson();

    public CardGameClient() {

        try {
            this.socket = new Socket(this.host, this.port);
             serverInputStream = new DataInputStream(socket.getInputStream());
            serverOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void sendUserObject(User user, Socket socket) throws IOException {
//        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
//
//        Gson gson = new Gson();
//
//        // Java object to JSON, and assign to a String
//        String jsonInString = gson.toJson(user);
//
//        toServer.writeUTF(jsonInString);
//        toServer.flush();
//
//    }

    public Socket getSocket() {
        return socket;
    }

    public void closeConnection(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(RequestProtocol rp) throws IOException{
            String request =gson.toJson(rp);
            serverOutputStream.writeUTF(request);
            serverOutputStream.flush();
            
    }
    
    public String receiveResponse() throws IOException{
    	//read response
    	return serverInputStream.readUTF();
    }
    
    public ResponseProtocol handleResponse(String str){
    	Gson gson = new Gson();
    	ResponseProtocol rp = gson.fromJson(str, ResponseProtocol.class);
    	int protocolId = rp.getProtocolId();
int responseType = rp.getType();
    	if (responseType == REGISTER_USER) {
    		return gson.fromJson(str, ResponseRegisterUser.class);

        } else if (responseType == LOGIN_USER) {
        	return gson.fromJson(str, ResponseLoginUser.class);

        } else if (responseType == SEND_MESSAGE) {
        	return gson.fromJson(str, ResponseSendMessage.class);
        } else {
            return new ResponseProtocol(protocolId, UNKNOWN_TYPE, FAIL, UNKNOWN_ERROR);
        }
    }


}


