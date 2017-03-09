package CardGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The chatroom server
 * @author Yifan Wu
 * @version 2017-03-08
 */
public class ChatRoomServer {
    static ArrayList<Socket> socketlist = new ArrayList<Socket>();
    private final int port = 7655;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 10;
	public ChatRoomServer(){
        connectToClients();
	}
    public void connectToClients(){
        ExecutorService threadPool = Executors.newFixedThreadPool(this.numberOfThreads);
        try {
            this.serverSocket = new ServerSocket(this.port);

            while (true) {
                System.out.println("Waiting for connection from Client");

                Socket socket = this.serverSocket.accept();

                System.out.println("Accepted connection form client");
                socketlist.add(socket);

                threadPool.execute(new ChatRoomServerThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized ServerSocket getServerSocket(){
		return this.serverSocket;
	}

    public static synchronized Socket getSocket(int i){
        return socketlist.get(i);
    }

    public static void main(String[] args) throws IOException {
    	ChatRoomServer server = new ChatRoomServer();

    }
}
