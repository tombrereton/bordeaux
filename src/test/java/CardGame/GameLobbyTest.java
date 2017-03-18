package CardGame;

import CardGame.GameEngine.Card;
import CardGame.GameEngine.Deck;
import CardGame.GameEngine.GameLobby;
import CardGame.GameEngine.Player;

import org.junit.Test;

import java.net.Socket;

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

    GameLobby gameLobby = new GameLobby(user,s);


    @Test
    public void getLobbyNameTest(){
        assertEquals("TestUser1",gameLobby.getLobbyName());
    }
    /**
     * get user
     */
    @Test
    public void getPlayerTest1(){
        gameLobby.addPlayer(user,s);
        assertEquals("TestUser1",gameLobby.getPlayer(user).getUsername());
    }

    /**
     * Edge case: user does not exist
     */
    @Test
    public void getPlayerTest2(){
        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user,s);
        assertEquals(null,gameLobby.getPlayer(user2));
    }

    /**
     * add player
     */
    @Test
    public void addPlayerTest(){
        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user2,s);
        assertEquals("TestUser2",gameLobby.getPlayer(user2).getUsername());
    }

    /**
     * remove player
     */
    @Test
    public void removePlayerTest1(){
        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user2,s);
        assertEquals(true,gameLobby.removePlayer(user2));
    }

    /**
     * Edge case: the palyer want to remove doesn't exist
     */
    @Test
    public void removePlayerTest2(){
        User user2 = new User("TestUser2");
        assertEquals(false,gameLobby.removePlayer(user2));
    }

    /**
     * Test if all player is finished round
     */
    @Test
    public void allPlayerFinishedTest1(){
        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);

        player1.setFinishedRound(true);
        player2.setFinishedRound(true);
        assertEquals(true,gameLobby.allPlayersFinished());

    }
    /**
     * Test if all player is finished round
     */
    @Test
    public void allPlayerFinishedTest2(){
        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);

        player1.setFinishedRound(true);
        player2.setFinishedRound(false);
        assertEquals(false,gameLobby.allPlayersFinished());

    }

    /**
     * Edge cases
     */
    @Test
    public void allPlayerFinishedTest3(){
        assertEquals(true,gameLobby.allPlayersFinished());
    }


    @Test
    public void takeBetsTest1(){
        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);
        player1.setBet(10);
        player2.setBet(10);
        System.out.println("takeBetsTest1");

        gameLobby.takeBets();
    }
    @Test
    public void takeBetsTest2(){
        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);

        player1.setBet(10);

        System.out.println("takeBetsTest2");
        gameLobby.takeBets();
    }

    @Test
    public void takeBetsTest3(){
        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user,s);
        gameLobby.addPlayer(user2,s);
        System.out.println("takeBetsTest3");
        gameLobby.takeBets();
    }

    @Test
    public void dealtoDealerTest1(){
        gameLobby.startGame();

        Card dealerFirstCard = new Deck().dealCard();
        gameLobby.getDealerHand().addCard(dealerFirstCard);
        gameLobby.getDealerHand().getCard(0).setFaceUp(false);
        Card dealerSecondCard = new Deck().dealCard();
        gameLobby.getDealerHand().addCard(dealerSecondCard);

        System.out.println("The dealer now has " +gameLobby.getDealerHand().getBlackjackValue());
        System.out.println("Dealer: " + gameLobby.getDealerHand().getCard(0).getValue());
        gameLobby.dealToDealer();
        System.out.println("Dealer: " + gameLobby.getDealerHand().getCard(0).getValue());

    }
    @Test
    public void playerTest(){
        Player player1 = new Player(user);
        Deck deck =  new Deck();
        deck.shuffle();
        gameLobby.addPlayer(user,s);
        System.out.println("Player 1: " +player1.getPlayerHandValue());
        player1.addCardToPlayerHand(deck.dealCard());
        System.out.println("Player 1: " +player1.getPlayerHandValue());
        player1.addCardToPlayerHand(deck.dealCard());
        System.out.println("Player 1: " +player1.getPlayerHandValue());
        player1.addCardToPlayerHand(deck.dealCard());
        System.out.println("Player 1: " +player1.getPlayerHandValue());
        player1.addCardToPlayerHand(deck.dealCard());
        System.out.println("Player 1: " +player1.getPlayerHandValue());
    }


}