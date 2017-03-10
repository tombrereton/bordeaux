package CardGame;

import CardGame.Requests.*;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Responses.*;

import com.google.gson.Gson;

import static CardGame.ProtocolMessages.FAIL;
import static CardGame.ProtocolMessages.UNKNOWN_ERROR;
import static CardGame.ProtocolTypes.LOGIN_USER;
import static CardGame.ProtocolTypes.REGISTER_USER;
import static CardGame.ProtocolTypes.SEND_MESSAGE;
import static CardGame.ProtocolTypes.UNKNOWN_TYPE;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TODO: fill this out.
 * Created by tom on 25/02/17.
 */
public class CardGameClient {

    private Socket socket;
    private String host = "localhost";
    private int port = 7654;

    public CardGameClient() {

        try {
            this.socket = new Socket(this.host, this.port);
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
    
    public ResponseProtocol sendRequest(RequestProtocol rp) throws IOException{
    	
    		DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            Gson gson = new Gson();
            String request =gson.toJson(rp);
            outputStream.writeUTF(request);
            outputStream.flush();
            String jsonInString = "";
            //read response
            System.out.println("before read");
            	jsonInString = inputStream.readUTF();
            	System.out.println("after read");
            
            return handleResponse(jsonInString);
            
            
            
    	
    		
    	
    	
    	
    }
    public ResponseRegisterUser sendRequestRegisterUser(User user) throws IOException{
    	RequestRegisterUser request = new RequestRegisterUser(user);
    	ResponseProtocol response = sendRequest(request);
		System.out.println(response);
		
		Gson gson = new Gson();
		String s = gson.toJson(response);
		ResponseRegisterUser rru = gson.fromJson(s, ResponseRegisterUser.class);
		return rru;
    	
    }
    
    public ResponseLoginUser sendRequestLoginUser(User user) throws IOException{
    	RequestLoginUser request = new RequestLoginUser(user);
    	ResponseProtocol response = sendRequest(request);
		System.out.println(response);
		
		Gson gson = new Gson();
		String s = gson.toJson(response);
		ResponseLoginUser rlu = gson.fromJson(s, ResponseLoginUser.class);
		return rlu;
    	
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
    
    public static void main(String[] args){
    	CardGameClient cgc = new CardGameClient();
    	User user = new User("ILoveTerryWogan","terry","Barry","Moonpie");
    	
		try {
			ResponseRegisterUser rru = cgc.sendRequestRegisterUser(user);
			System.out.println(rru);
			ResponseLoginUser rlu = cgc.sendRequestLoginUser(user);
			System.out.println(rlu);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
    	
    	
    }


}


