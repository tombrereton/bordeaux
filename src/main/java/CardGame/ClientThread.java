package CardGame;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

/**
 * This class implements the Runnable interface and
 * make a connection via a 'Socket.'
 *
 * @Author Tom Brereton
 */
public class ClientThread implements Runnable {
    private Socket toClientSocket;
    private CardGameServer cardGameServer;

    public ClientThread(Socket toClientSocket) {
        this.toClientSocket = toClientSocket;
        this.cardGameServer = new CardGameServer();
    }

    @Override
    public void run() {
        try {

            DataInputStream in = new DataInputStream(toClientSocket.getInputStream());

            String jsonInString = in.readUTF();

            Gson gson = new Gson();

            // JSON to java object
            User user = gson.fromJson(jsonInString, User.class);

            // Add user to users
            this.cardGameServer.addUsertoUsers(user);

            //print the object
            System.out.println("Object is " + user);

            in.close();
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
