package CardGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class runs a server for a
 * blackjack card game and chat client.
 *
 * @Author Tom Brereton
 */
public class CardGameServer {
    private final int port = 7654;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 10;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;


    public CardGameServer() {
        connectToClients();
    }

    public void connectToClients(){
        ExecutorService threadPool = Executors.newFixedThreadPool(this.numberOfThreads);

        try {
            this.serverSocket = new ServerSocket(this.port);

            while (true) {
                System.out.println("Waiting for connection from Client");
                Socket socket = this.serverSocket.accept();
                // add socket to socket list
                socketList.add(socket);
                System.out.println("Accepted connection form client");


                threadPool.execute(new ClientThread(socket, messageQueue, socketList, users));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {

        CardGameServer server = new CardGameServer();

    }
}
