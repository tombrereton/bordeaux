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
    private int port = 6666;

    public CardGameClient() {

        try {
            this.socket = new Socket(this.host, this.port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUserObject(String userName, String password, String firstName, String lastName, Socket socket) throws IOException {
        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
        User user = new User(userName, password, firstName, lastName);

        Gson gson = new Gson();

        // Java object to JSON, and assign to a String
        String jsonInString = gson.toJson(user);

        toServer.writeUTF(jsonInString);
        toServer.flush();

    }

    public Socket getSocket() {
        return socket;
    }

    public static void main(String[] args) {
        CardGameClient cardGameClient = new CardGameClient();
    }

}
