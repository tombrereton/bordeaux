package CardGame;

import CardGame.Requests.RequestProtocol;
import CardGame.Requests.RequestRegisterUser;
import com.google.gson.Gson;
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


    @Before
    public void setUp() throws Exception {
        server = new CardGameServer();
    }

    // TESTS
    @Test
    public void registerUser01_test() {

        User expected = new User("N00b_D3STROYER", "password", "Gwenith", "Hazlenut");
        RequestProtocol request = new RequestRegisterUser(expected);


        Gson gson = new Gson();

        String expectedJSON = gson.toJson(expected);




    }

}