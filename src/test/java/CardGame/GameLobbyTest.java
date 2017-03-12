//package CardGame;
//
//import CardGame.GameEngine.GameLobby;
//import CardGame.GameEngine.Player;
//import CardGame.Requests.RequestLoginUser;
//import CardGame.Requests.RequestProtocol;
//import CardGame.Requests.RequestRegisterUser;
//import CardGame.Responses.ResponseLoginUser;
//import CardGame.Responses.ResponseProtocol;
//import CardGame.Responses.ResponseRegisterUser;
//import com.google.gson.Gson;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * This class tests the GameLobby.
// * Created by Yifan on 11/03/17.
// */
//public class GameLobbyTest {
//
//    // SET UP
//    User user = new User("TestUser1");
//    GameLobby gameLobby = new GameLobby(user, null);
//
//    @Before
//    public void setUp() throws Exception {
//
//
//    }
//
//    // TESTS
//
//    @Test
//    public void addPlayerTest(){
//        User user2 = new User("TestUser2");
//        Player player = new Player(user2);
//        gameLobby.addPlayer(player);
//        assertEquals("TestUser2",gameLobby.getPlayer(user2).getUsername());
//    }
//
//    @Test
//    public void removePlayerTest1(){
//        User user2 = new User("TestUser2");
//        Player player = new Player(user2);
//        gameLobby.addPlayer(player);
//        assertEquals(true,gameLobby.removePlayer(user2));
//    }
//
//    @Test
//    public void removePlayerTest2(){
//        User user2 = new User("TestUser2");
//        assertEquals(false,gameLobby.removePlayer(user2));
//    }
//
//
//}