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

    public ClientThread(Socket toClientSocket) {
        this.toClientSocket = toClientSocket;
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
            CardGameServer.addUsertoUsers(user);

            //print the object
            System.out.println("Object is " + user);

            //get user object from arraylist
            User addedUser = CardGameServer.getUsers(0);

            System.out.println("This user was just added: " + addedUser);

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
