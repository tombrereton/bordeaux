package CardGame;

import CardGame.GameEngine.GameLobby;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
public class GameServer {
    private final int port = 7654;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 20;
    protected FunctionDB functionDB;
    private Gson gson;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;
    private volatile CopyOnWriteArrayList<GameLobby> games;
    private volatile CopyOnWriteArrayList<String> gameNames;

    public GameServer() {
        this.gameNames = new CopyOnWriteArrayList<>();
        this.gson =  new Gson();
        this.messageQueue = new ConcurrentLinkedDeque<>();
        this.socketList = new ConcurrentLinkedDeque<>();
        this.users = new CopyOnWriteArrayList<>();
        this.games = new CopyOnWriteArrayList<>();
    }

    public void connectToDatabase() {
        this.functionDB = new FunctionDB();
    }

    public void connectToClients() {
        ExecutorService threadPool = Executors.newFixedThreadPool(this.numberOfThreads);

        try {
            this.serverSocket = new ServerSocket(this.port);

            while (true) {
                // Wait for a client to connect
                System.out.println("Waiting for connection from Client");
                Socket socket = this.serverSocket.accept();


                // Add client socket to socketList
                socketList.add(socket);
                System.out.println("Accepted connection form client");


                // pass the socket to a new clientSideThread
                threadPool.execute(new GameServerThread(this,socket, this.messageQueue,
                        this.socketList, this.users, this.functionDB, this.games, this.gameNames));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets a list of gameNames
     * @return
     */
    public synchronized ArrayList<String> getGameNames() {
        return new ArrayList<>(this.gameNames);
    }



    public static void main(String[] args) throws IOException {

        GameServer server = new GameServer();
        server.connectToDatabase();
        server.connectToClients();
    }
}
