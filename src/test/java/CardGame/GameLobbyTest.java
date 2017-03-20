package CardGame;

import CardGame.GameEngine.Card;
import CardGame.GameEngine.Deck;
import CardGame.GameEngine.GameLobby;
import CardGame.GameEngine.Player;
import org.junit.Test;

import java.net.Socket;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the GameLobby.
 * Created by Yifan on 11/03/17.
 *
 */
public class GameLobbyTest {
    GameServer server;

    Socket s = new Socket();
    // SET UP
    User user = new User("TestUser1");
    User user2 = new User("TestUser2");



    @Test
    public void getLobbyNameTest(){

        GameLobby gameLobby = new GameLobby(user,s);
        assertEquals(user.getUserName(),gameLobby.getLobbyName());
    }
    /**
     * get user
     */
    @Test
    public void getPlayerTest1(){
        GameLobby gameLobby = new GameLobby(user,s);
        gameLobby.addPlayer(user,s);
        assertEquals("TestUser1",gameLobby.getPlayer(user).getUsername());
    }

    /**
     * Edge case: user does not exist
     */
    @Test
    public void getPlayerTest2(){
        GameLobby gameLobby = new GameLobby(user,s);

        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user,s);
        assertEquals(null,gameLobby.getPlayer(user2));
    }

    /**
     * add player
     */
    @Test
    public void addPlayerTest(){
        GameLobby gameLobby = new GameLobby(user,s);

        gameLobby.addPlayer(user,s);
        assertEquals(user.getUserName(),gameLobby.getPlayer(user).getUsername());
    }

    /**
     * remove player
     */
    @Test
    public void removePlayerTest1(){
        GameLobby gameLobby = new GameLobby(user,s);

        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user2,s);
        assertEquals(true,gameLobby.removePlayer(user2));
    }

    /**
     * Edge case: the palyer want to remove doesn't exist
     */
    @Test
    public void removePlayerTest2(){
        GameLobby gameLobby = new GameLobby(user,s);

        User user2 = new User("TestUser2");
        assertEquals(false,gameLobby.removePlayer(user2));
    }

    /**
     * Test if all player is finished round
     */
    @Test
    public void allPlayerFinishedTest1(){
        GameLobby gameLobby = new GameLobby(user,s);

        Player player1 = new Player(user);
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user2,s);

        gameLobby.getPlayer(user.getUserName()).setFinishedRound(true);
        gameLobby.getPlayer(user2.getUserName()).setFinishedRound(true);

        gameLobby.setAllPlayersFinished();
        gameLobby.setAllPlayersFinished(true);
        assertEquals(true,gameLobby.isAllPlayersFinished());

    }
    /**
     * Test if all player is finished round
     */
    @Test
    public void allPlayerFinishedTest2(){
        GameLobby gameLobby = new GameLobby(user,s);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);

        gameLobby.getPlayer(user.getUserName()).setFinishedRound(true);
        gameLobby.getPlayer(user2.getUserName()).setFinishedRound(false);

        gameLobby.setAllPlayersFinished();
        assertEquals(false,gameLobby.isAllPlayersFinished());

    }

    /**
     * Edge cases
     */
    @Test
    public void allPlayerFinishedTest3(){
        GameLobby gameLobby = new GameLobby(user,s);

        assertEquals(false,gameLobby.isAllPlayersFinished());
    }


    @Test
    public void takeBetsTest1(){
        GameLobby gameLobby = new GameLobby(user,s);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);

        gameLobby.getPlayer("TestUser1").setBet(10);
        gameLobby.getPlayer("TestUser2").setBet(20);

        assertEquals(20,gameLobby.getPlayer("TestUser2").getBet());


    }
    @Test
    public void takeBetsTest2(){
        GameLobby gameLobby = new GameLobby(user,s);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);

        gameLobby.getPlayer("TestUser1").setBet(10);
        assertEquals(10,gameLobby.getPlayer("TestUser1").getBet());


    }
    @Test
    public void takeBetsTest3(){
        GameLobby gameLobby = new GameLobby(user,s);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);

        assertEquals(0,gameLobby.getPlayer("TestUser1").getBet());


    }

    @Test
    public void dealtoDealerTest1(){
        GameLobby gameLobby = new GameLobby(user,s);

        gameLobby.startGame();

        System.out.println("Dealer: " + gameLobby.getDealerHand().getCard(0).getValue());
        System.out.println("Dealer: " + gameLobby.getDealerHand().getCard(1).getValue());


    }
    @Test
    public void playerTest(){
        GameLobby gameLobby = new GameLobby(user,s);

        Player player1 = new Player(user);
        Deck deck = new Deck();
        deck.shuffle();
        player1.addCardToPlayerHand(deck.dealCard());
        player1.addCardToPlayerHand(deck.dealCard());

        System.out.println("Player 1: " +player1.getPlayerHand().getHand().get(0).getValue());
        System.out.println("Player 1: " +player1.getPlayerHand().getHand().get(1).getValue());

    }


}