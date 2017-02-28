package CardGame;

import com.google.gson.Gson;

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

    public void sendUserObject(User user, Socket socket) throws IOException {
        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

        Gson gson = new Gson();

        // Java object to JSON, and assign to a String
        String jsonInString = gson.toJson(user);

        toServer.writeUTF(jsonInString);
        toServer.flush();

    }

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

        Socket clientSock = cardGameClient.getSocket();
        try {
            cardGameClient.sendUserObject(userObjectTest, clientSock);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardGameClient.closeConnection();
    }

}
