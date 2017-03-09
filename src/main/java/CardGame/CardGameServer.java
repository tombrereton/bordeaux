package CardGame;

import com.google.gson.Gson;

import java.io.DataOutputStream;
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
    static ArrayList<User> users = new ArrayList<User>();
    private final int port = 7654;
    private ServerSocket serverSocket;
    private final int numberOfThreads = 10;
    static ArrayList<Socket> socketlist = new ArrayList<Socket>();


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
                socketlist.add(socket);
                System.out.println("Accepted connection form client");

                threadPool.execute(new ClientThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Send messages to all clients
     * @param
     * @throws IOException
     */
    public static synchronized void sendMessage(MessageObject msg) throws IOException {
        Gson gson = new Gson();
        DataOutputStream outputStream;
        for (Socket client :  CardGameServer.socketlist) {
            if(!client.isClosed()){
                outputStream = new DataOutputStream(client.getOutputStream());
                String jsonOutString = gson.toJson(msg);
                outputStream.writeUTF(jsonOutString);
                outputStream.flush();
            }
        }
    }

    public static synchronized void addUsertoUsers(User user) {
        users.add(user);
    }

    public static synchronized int getSizeOfUsers(){
        return users.size();
    }

    public static synchronized User getUsers(int i){
        return users.get(i);
    }

    public static void main(String[] args) throws IOException {

        CardGameServer server = new CardGameServer();

    }
}
