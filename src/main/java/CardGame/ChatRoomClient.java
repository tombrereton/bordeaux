package CardGame;

import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestSendMessage;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatRoomClient {
    private Socket socket;
    private String host = "localhost";
    private int port = 7655;

    public ChatRoomClient() {

        try {
            this.socket = new Socket(this.host, this.port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatRoomClient chatroomClient = new ChatRoomClient();

        User userObjectTest = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");

        RequestProtocol request = new RequestSendMessage(userObjectTest.getUserName(),"This is a test message");

        Socket sock = chatroomClient.getSocket();
        try {
            DataOutputStream toServer = new DataOutputStream(sock.getOutputStream());
            DataInputStream fromServer = new DataInputStream(sock.getInputStream());

            Gson gson = new Gson();

            String jsonOutString = gson.toJson(request);

            toServer.writeUTF(jsonOutString);
            toServer.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

        chatroomClient.closeConnection();
    }

}
