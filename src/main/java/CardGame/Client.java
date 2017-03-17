package CardGame;

import CardGame.Requests.RequestLogOut;
import CardGame.Requests.RequestLoginUser;
import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestRegisterUser;
import CardGame.Responses.ResponseLogOut;
import CardGame.Responses.ResponseLoginUser;
import CardGame.Responses.ResponseProtocol;
import CardGame.Responses.ResponseRegisterUser;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import static CardGame.ProtocolTypes.LOGIN_USER;
import static CardGame.ProtocolTypes.REGISTER_USER;

/**
 * This client connects to the servers.
 * Sends requests to the server.
 * Handles the response from the server.
 * Contains and updates client side data.
 * Notifies observers when there is a change (response).
 * <p>
 * Created by tom on 17/03/17.
 */
public class Client extends Observable {

    // connection variables
    private final String HOST;
    private final int PORT;
    private Socket socket;
    private DataInputStream serverInputStream;
    private DataOutputStream serverOutputStream;
    private Gson gson;
    private String username;

    public Client(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.gson = new Gson();
        connectToServer();
    }

    /**
     * This method connects to the server.
     */
    private void connectToServer() {
        try {
            // connect to server
            this.socket = new Socket(this.HOST, this.PORT);
            System.out.println("Connected to server.");
            connectDataStreams();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method connects the streams with the server
     *
     * @throws IOException
     */
    private void connectDataStreams() throws IOException {
        serverInputStream = new DataInputStream(socket.getInputStream());
        serverOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * This method closes all open connections.
     */
    private void closeConnections() {
        try {
            this.serverInputStream.close();
            this.serverOutputStream.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // OVERALL REQUEST METHOD

    /**
     * This method takes in the request and writes it to the server.
     *
     * @param request
     * @param <T>
     */
    public <T extends RequestProtocol> void sendRequest(T request) {
        // convert to json string
        String jsonOutput = gson.toJson(request);

        try {
            // write request to server
            this.serverOutputStream.writeUTF(jsonOutput);
            this.serverOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // RESPONSE METHODS

    /**
     * This method takes in the expected response class. It reads from the server and
     * returns the response.
     *
     * @param responseClass
     * @param <T>
     * @return
     */
    public <T> T getResponse(Class<T> responseClass) {
        String jsonInput = null;
        try {
            // read response from server
            jsonInput = this.serverInputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // return the correct response
        return handleResponse(responseClass, jsonInput);
    }

    /**
     * This method takes in the expected response class and the jsonInput read from the server.
     * It then returns the expected response.
     *
     * @param responseClass
     * @param jsonInput
     * @param <T>
     * @return
     */
    private <T> T handleResponse(Class<T> responseClass, String jsonInput) {

        // Get the response
        ResponseProtocol response = this.gson.fromJson(jsonInput, ResponseProtocol.class);

        // get the response type
        int responseType = response.getType();

        // return the correct response
        switch (responseType) {
            case REGISTER_USER:
                ResponseRegisterUser responseToCast = gson.fromJson(jsonInput, ResponseRegisterUser.class);
                return responseClass.cast(responseToCast);
            case LOGIN_USER:
                ResponseLoginUser responseLoginUser = gson.fromJson(jsonInput, ResponseLoginUser.class);
                return responseClass.cast(responseLoginUser);
            default:
                ResponseProtocol responseProtocol = gson.fromJson(jsonInput, ResponseRegisterUser.class);
                return responseClass.cast(responseProtocol);
        }
    }

    /**
     * This method hashes the password.
     *
     * @param password
     * @return
     */
    public String hashPassword(String password) {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return sha256hex;
    }

    // SPECIFIC REQUEST METHODS

    /**
     * method that sends login request to server and updates loggedin and user
     * fields.
     *
     * @param username
     * @param password
     */
    public ResponseLoginUser requestLogin(String username, String password) {
        // hash password and create user
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);

        // create and send login request
        RequestLoginUser requestLoginUser = new RequestLoginUser(user);
        sendRequest(requestLoginUser);

        // read response from server
        return getResponse(ResponseLoginUser.class);
    }

    /**
     * This method send a request and returns a response for register user.
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return
     */
    public ResponseRegisterUser requestRegisterUser(String username, String password, String firstName, String lastName){
        // hash password and create user
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword, firstName, lastName);

        // create request
        RequestRegisterUser requestRegisterUser = new RequestRegisterUser(user);
        sendRequest(requestRegisterUser);

        return getResponse(ResponseRegisterUser.class);
    }


    public ResponseLogOut requestLogOut() {


        // create request and send request
        RequestLogOut requestLogOut = new RequestLogOut(this.username);
        sendRequest(requestLogOut);

        // get response from server and returnit
        return getResponse(ResponseLogOut.class);
    }

    public static void main(String[] args) {
        // connect to server
        Client client = new Client("localhost", 7654);
        client.connectToServer();

        // send login request to server
        ResponseLoginUser responseLoginUser = client.requestLogin("user111", "password");
        System.out.println(responseLoginUser);

        // close connections
        client.closeConnections();
    }

}
