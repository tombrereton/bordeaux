package CardGame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by tom on 25/02/17.
 */
public class CardGameServerTest {

    // THIS WON'T WORK!! JUST LEAVING IT HERE AS I WILL CHANGE IT TO SOMETHING WORKING LATER.

    // SET UP

    CardGameServer server;
    CardGameClient client;


    @Before
    public void setUp() throws Exception {
        server = new CardGameServer();
        client = new CardGameClient();
    }

    // TESTS
    @Test
    public void sendingJSONObject1_test() {

        User expected = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");

        Socket clientSock = client.getSocket();
        try {
            client.sendUserObject("N00B_D3STROYER", "password",
                    "Gwenith", "Hazlenut", clientSock);
        } catch (IOException e) {
            e.printStackTrace();
        }

        User actual = server.getUsers(1);

        assertEquals("Should return the user Gwenith matching expected ", expected, actual);



    }

}