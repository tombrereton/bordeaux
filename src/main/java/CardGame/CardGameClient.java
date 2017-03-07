package CardGame;

import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Responses.ResponseProtocol;
import com.google.gson.Gson;

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

    public static void main(String[] args) {
        CardGameClient cardGameClient = new CardGameClient();

        User userObjectTest = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");

        RequestProtocol request = new RequestRegisterUser(2, userObjectTest);

        Socket sock = cardGameClient.getSocket();
        try {
            DataOutputStream toServer = new DataOutputStream(sock.getOutputStream());
            DataInputStream fromServer = new DataInputStream(sock.getInputStream());

            Gson gson = new Gson();

            String jsonOutString = gson.toJson(request);

            toServer.writeUTF(jsonOutString);
            toServer.flush();

//            String jsonInString = fromServer.readUTF();
//
//
//            AbstractResponseProtocol response = gson.fromJson(jsonInString, AbstractResponseProtocol.class);
//
//            int success = response.getRequestSuccess();
//
//            System.out.println("Success (1: yes, 0: no) : " + success);


        } catch (IOException e) {
            e.printStackTrace();
        }

        cardGameClient.closeConnection();
    }

}

