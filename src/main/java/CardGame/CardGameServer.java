package CardGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class runs a server for a
 * blackjack card game and chat client.
 *
 * @Author Tom Brereton
 */
public class CardGameServer {
    static List<User> users = new ArrayList<User>();
    private final int port = 7654;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 10;

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

                System.out.println("Accepted connection form client");

                threadPool.execute(new ClientThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addUsertoUsers(User user) {
        users.add(user);
    }

    public static synchronized User getUsers(int i){
        return users.get(i);
    }

    public static void main(String[] args) throws IOException {

        CardGameServer server = new CardGameServer();

    }
}
