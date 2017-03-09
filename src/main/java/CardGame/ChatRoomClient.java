package CardGame;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRoomClient {
    private Socket socket;
    private String host = "localhost";
    private int port = 7655;
    private DataInputStream input;
    private final int numberOfThreads = 10;
    private DataOutputStream out;

    public ChatRoomClient() throws IOException {
        this.socket = new Socket(this.host, this.port);
        ExecutorService threadPool = Executors.newFixedThreadPool(this.numberOfThreads);
        // Create thread to send messages
        threadPool.execute(new ChatRoomClientThread(socket));

        retriveMessagesFromServer();

    }
    public void retriveMessagesFromServer() throws IOException {
        input = new DataInputStream(socket.getInputStream());
        while(true){
            String msg = null;
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            if(!socket.isClosed()){
                try {
                    msg = input.readUTF();
                    System.out.println(msg);
                    if(msg!=null){
                        MessageObject message = new Gson().fromJson(msg, MessageObject.class);
                        // ---------------------------------------------------------
                        // These codes will be replaced if the GUI part is done
                        System.out.println(df.format(message.getTimeStamp()) + "\n" + message.getUserName() + ": " + message.getMessage());
                        // ---------------------------------------------------------
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public Socket getSocket() {
        return socket;
    }

    public static void main(String[] args) throws IOException {

        ChatRoomClient chatroomClient = new ChatRoomClient();
    }

}
