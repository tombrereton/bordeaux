package CardGame;

import CardGame.GameEngine.GameLobby;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
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

    // connection variables
    private final int PORT;
    private final String HOST;
    private final int NUMBER_OF_THREADS;
    private ServerSocket serverSocket;
    protected FunctionDB functionDB;
    private Gson gson;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;
    private volatile CopyOnWriteArrayList<GameLobby> games;
    private volatile ConcurrentLinkedDeque<String> gameNames;

    /**
     * Constructor for the GameServer class.
     *
     * @param port               the port the server socket listens for connections.
     * @param host               the host (ip address) where the server socket listens for connections.
     * @param maxNumberOfClients the maximum number of clients which can connect to the server.
     */
    public GameServer(int port, String host, int maxNumberOfClients) {
        this.HOST = host;
        this.gameNames = new ConcurrentLinkedDeque<>();
        this.gson = new Gson();
        this.messageQueue = new ConcurrentLinkedDeque<>();
        this.socketList = new ConcurrentLinkedDeque<>();
        this.users = new CopyOnWriteArrayList<>();
        this.games = new CopyOnWriteArrayList<>();
        this.PORT = port;
        this.NUMBER_OF_THREADS = maxNumberOfClients;
    }

    /**
     * This method connects the server to the database.
     */
    public void connectToDatabase() {
        this.functionDB = new FunctionDB();
    }

    /**
     * This method starts and thread pool of size as denoted by NUMBER_OF_THREADS.
     * It then opens a server socket and waits for clients to connect. Upon
     * connection, the socket is passed into a thread, which now handles the
     * communication for that client.
     */
    public void connectToClients() {
        ExecutorService threadPool = Executors.newFixedThreadPool(this.NUMBER_OF_THREADS);

        try {
            InetAddress inetAddress = InetAddress.getByName(HOST);
            this.serverSocket = new ServerSocket(this.PORT, this.NUMBER_OF_THREADS, inetAddress);

            while (true) {
                // Wait for a client to connect
                System.out.println("Waiting for connection from GameClient");
                Socket socket = this.serverSocket.accept();


                // Add client socket to socketList
                socketList.add(socket);
                System.out.println("Accepted connection form client");


                // pass the socket to a new clientSideThread
                threadPool.execute(new GameServerThread(socket,
                        this.users, this.functionDB, this.games, this.gameNames));
            }
        } catch (IOException e) {
            System.out.println("Cannot open server socket, host likely already in use.");
        }
    }

    /**
     * This method gets a list of gameNames
     *
     * @return
     */
    public synchronized ArrayList<String> getGameNames() {
        return new ArrayList<>(this.gameNames);
    }


    /**
     * This method is run first when running the server through the command line.
     *
     * @param args first argument is the port, second is the host, 3rd is maxNumber of clients. All optional.
     */
    public static void main(String[] args) {

        // parse command line arguments
        int port = 0;
        String host = "";
        int maxNumberOfClients = 0;

        if (args.length == 1 && args[0].equals("-h")) {
            System.out.println("Enter: \'[port]\' or \'[port] [host]\' " +
                    "\nOr \'[port] [host] [max number of clients]\'" +
                    "\nOr default is \'[7654] [0.0.0.0] [20]\'");
            return;
        } else if (args.length == 0) {
            port = 7654;
            host = "0.0.0.0";
            maxNumberOfClients = 20;
        } else if (args.length == 1) {
            port = Integer.parseInt(args[0]);
            host = "0.0.0.0";
            maxNumberOfClients = 20;
        } else if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            host = args[1];
            maxNumberOfClients = 20;
        } else if (args.length == 3) {
            port = Integer.parseInt(args[0]);
            host = args[1];
            maxNumberOfClients = Integer.parseInt(args[1]);
        } else {
            System.out.println("Enter: \'[port]\' or \'[port] [host]\' " +
                    "\nOr \'[port] [host] [max number of clients]\'" +
                    "\nOr default is \'[7654] [0.0.0.0] [20]\'");
        }

        System.out.println("Host: " + host + ", Port: " + port + ", Max number of client: " + maxNumberOfClients);


        // start server and connect to database
        GameServer server = new GameServer(port, host, maxNumberOfClients);
        server.connectToDatabase();

        // wait for client connections
        server.connectToClients();
    }
}
