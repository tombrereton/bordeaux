package CardGame;

import java.io.*;
import java.net.Socket;


/**
 * Created by ivan on 2017/3/8.
 */
public class ChatRoomServerThread implements Runnable  {
    private Socket socket;
    private BufferedReader br;
    private long clientID;
    private boolean clientAlive;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String msg;

    public ChatRoomServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.clientID = (int) Thread.currentThread().getId();
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.msg = inputStream.readUTF();

    }
    private void sendMessage(String msgContent) throws IOException {
        for (Socket client :  ChatRoomServer.socketlist) {
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(msgContent);
        }
    }
    public void run() {
        try {
            String tt = null;
            while ( tt != msg && msg !=null) {
                 sendMessage(msg);
                 tt = msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
