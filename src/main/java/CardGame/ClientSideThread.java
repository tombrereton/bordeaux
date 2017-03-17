package CardGame;

import CardGame.Responses.ResponseProtocol;
import com.google.gson.Gson;

import java.io.*;

public class ClientSideThread implements Runnable {

    private ClientModel clientModel;
    private CardGameClient client;
    private DataOutputStream dataOutputStream;
    private Gson gson = new Gson();

    public ClientSideThread(ClientModel clientModel, CardGameClient client) {
        this.clientModel = clientModel;
        this.client = client;
        this.dataOutputStream = new DataOutputStream(clientModel.getPout());
    }

    @Override
    public void run() {
        while (true) {
            try {
                // we get the response from the server
                String response = client.receiveResponse();
                ResponseProtocol responseProtocol = gson.fromJson(response, ResponseProtocol.class);

                if (responseProtocol.getType() >= 50 && responseProtocol.getType() <= 60) {
                    // if the response is a push, handle it as a push
//                    clientModel.getPushProtocolQueue().put(pushProtocol);
                } else {
                    // else, handle it as a normal response
                    dataOutputStream.writeUTF(response);
                    System.out.println(response);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream();
        try {
            pin.connect(pout);
            DataInputStream din = new DataInputStream(pin);
            DataOutputStream dout = new DataOutputStream(pout);
            dout.writeUTF("hello");
            dout.writeUTF("moo");
            System.out.println(din.readUTF() + "world");
            System.out.println(din.readUTF());
            System.out.println(din.readUTF());
            System.out.println("afterread");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
