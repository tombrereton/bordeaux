package CardGame;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

/**
 * Created by ivan on 2017/3/8.
 */
public class ChatRoomClientThread implements Runnable {
    private Socket socket;
    private long clientID;
    private DataOutputStream out;
    private User user;
    private String msg;

    public ChatRoomClientThread(Socket socket) {
        this.socket = socket;
        this.clientID = (int) Thread.currentThread().getId();
        this.user = user;
        this.msg = msg;

    }
    @Override
    public void run() {
        // Send messages
        try {
            // ---------------------------------------------------------
            // These codes will be replaced if the GUI part is done
            String msg ="Good!";
            User user = new User("User 3");
            // ---------------------------------------------------------
                MessageObject mo = new MessageObject(user.getUserName(),msg);
                Gson gson = new Gson();
                // Send the message to the server

                out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(gson.toJson(mo));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
