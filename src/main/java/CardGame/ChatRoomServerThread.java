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
    private DataOutputStream outputStream;

    public ChatRoomServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.clientID = (int) Thread.currentThread().getId();

    }

    /**
     * Send messages to all clients
     * @param msgContent
     * @throws IOException
     */
    private void sendMessage(String msgContent) throws IOException {
        for (Socket client :  ChatRoomServer.socketlist) {
            if(!client.isClosed()){
                outputStream = new DataOutputStream(client.getOutputStream());
                outputStream.writeUTF(msgContent);
            }
        }
    }
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        String msg = null;
        if(!socket.isClosed()){
                msg = inputStream.readUTF();
                System.out.println("Received msg: "+ msg);
            sendMessage(msg);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
