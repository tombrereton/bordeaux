package CardGame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.google.gson.Gson;

import CardGame.Responses.ResponseProtocol;

public class ClientSideThread implements Runnable {
	
	private ClientModel model;
	private CardGameClient client;
	private DataOutputStream dataOutputStream;
	private Gson gson = new Gson();
	
	public ClientSideThread(ClientModel model, CardGameClient client){
		this.model = model;
		this.client = client;
		this.dataOutputStream = new DataOutputStream(model.getPout());
	}

	@Override
	public void run() {
		while(true){
			try {
				String response = client.receiveResponse();
				dataOutputStream.writeUTF(response);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args){
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
			System.out.println("afterread");;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
