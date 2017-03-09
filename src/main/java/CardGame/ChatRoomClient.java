package CardGame;

import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestSendMessage;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private String messageContent;
    private User user;

    public ChatRoomClient(User user,String messageContent) throws IOException {
        this.user = user;
        this.messageContent = messageContent;
        this.socket = new Socket(this.host, this.port);
        sendMessagesToServer();
        retriveMessagesFromServer();
    }
    public void sendMessagesToServer(){


        ExecutorService threadPool = Executors.newFixedThreadPool(this.numberOfThreads);
        // 创建线程发送消息
        threadPool.execute(new ChatRoomClientThread(socket,user,messageContent));
    }
    public void retriveMessagesFromServer() throws IOException {
        input = new DataInputStream(socket.getInputStream());
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String msg = input.readUTF();
        String tt = null;
        while ( tt != msg && msg !=null) {
            MessageObject message = new Gson().fromJson(msg,MessageObject.class);
            // ---------------------------------------------------------
            // These codes will be replaced if the GUI part is done
            System.out.println(df.format(message.getTimeStamp()) + "\n" + message.getUserName() +": " + message.getMessage());
            // ---------------------------------------------------------
            tt = msg;
        }
    }
    public Socket getSocket() {
        return socket;
    }

    public static void main(String[] args) throws IOException {
        // ---------------------------------------------------------
        // These codes will be replaced if the GUI part is done
        String cc ="This is a test";
        User user = new User("Yifan");
        // ---------------------------------------------------------
        ChatRoomClient chatroomClient = new ChatRoomClient(user,cc);
        ChatRoomClient chatroomClnt = new ChatRoomClient(user,"Test 2");
    }

}
