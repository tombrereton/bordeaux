package CardGame;

import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestSendMessage;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseSendMessage;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by ivan on 2017/3/8.
 */
public class ChatRoomClientThread implements Runnable  {
    private Socket toClientSocket;
    private long clientID;
    private boolean clientAlive;
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public ChatRoomClientThread(Socket toClientSocket) {
        this.toClientSocket = toClientSocket;
        this.clientID = (int) Thread.currentThread().getId();
    }

    public ResponseProtocol handleInput(String JSONInput) {
        // Deserialise request object
        Gson gson = new Gson();
//        RequestProtocol request;
//        request = gson.fromJson(JSONInput, RequestProtocol.class);

        // Declare a response variable
        ResponseProtocol response = null;

        response = gson.fromJson(JSONInput, ResponseSendMessage.class);
        return response;
    }

    public RequestProtocol getMessages(String JSONInput){
        Gson gson = new Gson();
        return gson.fromJson(JSONInput, RequestSendMessage.class);
    }

    @Override
    public void run() {
        try {

            DataInputStream inputStream;
            inputStream = new DataInputStream(toClientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(toClientSocket.getOutputStream());
            this.clientAlive = true;

            while (clientAlive) {

                String jsonInString = inputStream.readUTF();

                if (!jsonInString.isEmpty()) {

                    System.out.println(getMessages(jsonInString));
                    System.out.println();


//                    ResponseProtocol response = handleInput(jsonInString);
//                    System.out.println(response);
//                    Gson gson = new Gson();
//
//                    String jsonOutString = gson.toJson(response);
//                    System.out.println(jsonOutString);
//                    outputStream.writeUTF(jsonOutString);
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
