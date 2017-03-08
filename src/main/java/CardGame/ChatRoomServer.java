package CardGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The chatroom server
 * @author Yifan Wu
 * @version 2017-03-08
 */
public class ChatRoomServer {
    static List<String> messagelist = new ArrayList<String>();
    private final int port = 7655;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 10;
	public ChatRoomServer(){
		 ExecutorService threadPool = Executors.newFixedThreadPool(this.numberOfThreads);
	        try {
	            this.serverSocket = new ServerSocket(this.port);

	            while (true) {
	                System.out.println("Waiting for connection from Client");

	                Socket socket = this.serverSocket.accept();

	                System.out.println("Accepted connection form client");

	                threadPool.execute(new ChatRoomClientThread(socket));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
    public static synchronized void addMessage(String message) {
        messagelist.add(message);
    }

    public static synchronized String getMessage(int i){
        return messagelist.get(i);
    }

    public static void main(String[] args) throws IOException {
    	ChatRoomServer server = new ChatRoomServer();

    }
}
