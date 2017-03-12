package CardGame;

import CardGame.GameEngine.GameLobby;
import CardGame.Pushes.PushGameNames;
import com.google.gson.Gson;

import java.io.DataOutputStream;
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
public class CardGameServer {
    private final int port = 7654;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 10;
    protected FunctionDB functionDB;
    private ArrayList<String> gameNames;
    private Gson gson;

    // Shared data structures
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;
    private volatile ConcurrentLinkedDeque<Socket> socketList;
    private volatile CopyOnWriteArrayList<User> users;
    private volatile CopyOnWriteArrayList<GameLobby> games;


    public CardGameServer() {
        this.gameNames = new ArrayList<>();
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
                System.out.println("Waiting for connection from Client");
                Socket socket = this.serverSocket.accept();
                // add socket to socket list
                socketList.add(socket);
                System.out.println("Accepted connection form client");


                threadPool.execute(new ClientThread(this,socket, this.messageQueue,
                        this.socketList, this.users, this.functionDB, this.games));
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
        return gameNames;
    }

    /**
     * This method updates the gameNames list with the
     * current games in games.
     */
    public synchronized void updateGameNames(){

        if (this.gameNames.size() != 0){
            for (GameLobby game : games){
                this.gameNames.add(game.getLobbyName());
            }
        }
    }

    public synchronized void pushGameListToClient(){
        DataOutputStream outputStream;

        PushGameNames pushGameNames = new PushGameNames(getGameNames());
        String jsonOutString = this.gson.toJson(pushGameNames);

        if (!this.socketList.isEmpty()){
            for (Socket sock : this.socketList){
                try {
                    outputStream = new DataOutputStream(sock.getOutputStream());
                    outputStream.writeUTF(jsonOutString);
                } catch (IOException e) {
                    System.out.println("Failed to send out list of game names");
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {

        CardGameServer server = new CardGameServer();
        server.connectToDatabase();
        server.connectToClients();
    }
}
