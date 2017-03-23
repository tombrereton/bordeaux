package CardGame;

import CardGame.GameEngine.Card;
import CardGame.GameEngine.Deck;
import CardGame.GameEngine.GameLobby;
import CardGame.GameEngine.Player;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the GameEngine.
 * Created by Yifan on 11/03/17.
 *
 * This part is include the all classes in the GameEngine package
 *
 */
public class GameEngineTest {
    GameServer server;

    Socket s = new Socket();
    // SET UP
    User user = new User("TestUser1");
    User user2 = new User("TestUser2");



    @Test
    public void getLobbyNameTest(){

        GameLobby gameLobby = new GameLobby(user);
        assertEquals(user.getUserName(),gameLobby.getLobbyName());
    }
    /**
     * Test get user part
     */
    @Test
    public void getPlayerTest1(){
        GameLobby gameLobby = new GameLobby(user);
        gameLobby.addPlayer(user);
        assertEquals("TestUser1",gameLobby.getPlayer(user).getUsername());
    }

    /**
     * Edge case: user does not exist
     */
    @Test
    public void getPlayerTest2(){
        GameLobby gameLobby = new GameLobby(user);

        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user);
        assertEquals(null,gameLobby.getPlayer(user2));
    }

    /**
     * Test add player part
     */
    @Test
    public void addPlayerTest(){
        GameLobby gameLobby = new GameLobby(user);

        gameLobby.addPlayer(user);
        assertEquals(user.getUserName(),gameLobby.getPlayer(user).getUsername());
    }

    /**
     * Test remove player part
     */
    @Test
    public void removePlayerTest1(){
        GameLobby gameLobby = new GameLobby(user);

        User user2 = new User("TestUser2");
        gameLobby.addPlayer(user2);
        assertEquals(true,gameLobby.removePlayer(user2));
    }

    /**
     * Edge case: the palyer want to remove doesn't exist
     */
    @Test
    public void removePlayerTest2(){
        GameLobby gameLobby = new GameLobby(user);

        User user2 = new User("TestUser2");
        assertEquals(false,gameLobby.removePlayer(user2));
    }

    /**
     * Test if all player is finished round
     */
    @Test
    public void allPlayerFinishedTest1(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user2);

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
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user);
        gameLobby.addPlayer(user2);

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
        GameLobby gameLobby = new GameLobby(user);

        assertEquals(false,gameLobby.isAllPlayersFinished());
    }



    /**
     * Test if all player bet is placed
     */
    @Test
    public void allPlayersBetPlacedTest1(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user2);


//        gameLobby.getPlayer(user.getUserName()).setBetPlaced(true);
//        gameLobby.getPlayer(user2.getUserName()).setBetPlaced(true);

//        gameLobby.setAllPlayersBetPlaced(true);
        assertEquals(true,gameLobby.isAllPlayersBetPlaced());

    }
    /**
     * Test if all player is finished round
     */
    @Test
    public void allPlayersBetPlacedTest2(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user);
        gameLobby.addPlayer(user2);

        gameLobby.getPlayer(user.getUserName()).setBetPlaced(true);
        gameLobby.getPlayer(user2.getUserName()).setBetPlaced(false);

        assertEquals(false,gameLobby.isAllPlayersBetPlaced());

    }

    /**
     * Edge cases
     */
    @Test
    public void allPlayersBetPlacedTest3(){
        GameLobby gameLobby = new GameLobby(user);

        assertEquals(false,gameLobby.isAllPlayersBetPlaced());
    }



    /**
     * Test the bet part
     */
    @Test
    public void takeBetsTest1(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user);
        gameLobby.addPlayer(user2);

        gameLobby.getPlayer("TestUser1").setBet(10);
        gameLobby.getPlayer("TestUser2").setBet(20);

        assertEquals(20,gameLobby.getPlayer("TestUser2").getBet());
    }

    /**
     * Test the bet part
     */
    @Test
    public void takeBetsTest2(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user);
        gameLobby.addPlayer(user2);

        gameLobby.getPlayer("TestUser1").setBet(10);
        assertEquals(10,gameLobby.getPlayer("TestUser1").getBet());


    }

    /**
     * Test the bet part
     */
    @Test
    public void takeBetsTest3(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user);

        assertEquals(0,gameLobby.getPlayer("TestUser1").getBet());


    }


    /**
     * Test the double bet part
     */
    @Test
    public void takeDoubleBetsTest1(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        User user2 = new User("TestUser2");
        Player player2 = new Player(user2);

        gameLobby.addPlayer(user);

        gameLobby.getPlayer("TestUser1").setBet(10);
        gameLobby.getPlayer("TestUser1").doubleBet();

        assertEquals(20,gameLobby.getPlayer("TestUser1").getBet());
    }

    /**
     * Test the double bet part
     */
    @Test
    public void takeDoubleBetsTest2(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);

        gameLobby.addPlayer(user);

        gameLobby.getPlayer("TestUser1").doubleBet();

        assertEquals(0,gameLobby.getPlayer("TestUser1").getBet());


    }


    /**
     * Test the game part
     */
    @Test
    public void dealtoDealerTest1(){
        GameLobby gameLobby = new GameLobby(user);

        gameLobby.startGame();

        System.out.println("Dealer: " + gameLobby.getDealerHand().getCard(0).getValue());
        System.out.println("Dealer: " + gameLobby.getDealerHand().getCard(1).getValue());


    }
    /**
     * Test the game part
     */
    @Test
    public void playerTest(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        Deck deck = new Deck();
        deck.shuffle();
        player1.addCardToPlayerHand(deck.dealCard());
        player1.addCardToPlayerHand(deck.dealCard());

        System.out.println("Player 1: " +player1.getPlayerHand().getHand().get(0).getValue());
        System.out.println("Player 1: " +player1.getPlayerHand().getHand().get(1).getValue());

    }

    /**
     * Test the budget part
     */
    @Test
    public void playerBudgetTest1(){
        GameLobby gameLobby = new GameLobby(user);

        Player player1 = new Player(user);
        gameLobby.addPlayer(user);
        gameLobby.getPlayers().get(0).setBudget(200);

        assertEquals(200,gameLobby.getPlayers().get(0).getBudget());
    }

    /**
     * Test the budget part
     */
    @Test
    public void playerBudgetTest2(){
        GameLobby gameLobby = new GameLobby(user);
        Player player1 = new Player(user);
        gameLobby.addPlayer(user);

        assertEquals(100,gameLobby.getPlayers().get(0).getBudget());
    }

    /**
     * Test the bust part
     */
    @Test
    public void playerBustTest1(){
        GameLobby gameLobby = new GameLobby(user);
        Player player1 = new Player(user);

        Deck deck = new Deck();
        deck.shuffle();

        Card card = deck.dealCard();

        while(gameLobby.getPlayers().get(0).getPlayerHandValue() < 8){
            deck.shuffle();
            card = deck.dealCard();
            gameLobby.getPlayers().get(0).addCardToPlayerHand(card);
        }

        System.out.println("Now the value of player's hand card :" + gameLobby.getPlayers().get(0).getPlayerHandValue());
        assertEquals(false,gameLobby.getPlayers().get(0).isBust());

    }

    /**
     * Test the bust part
     */
    @Test
    public void playerBustTest2(){
        GameLobby gameLobby = new GameLobby(user);
        Player player1 = new Player(user);

        Deck deck = new Deck();
        deck.shuffle();

        Card card = deck.dealCard();

        while(gameLobby.getPlayers().get(0).getPlayerHandValue() <= 21){
            deck.shuffle();
            card = deck.dealCard();
            gameLobby.getPlayers().get(0).addCardToPlayerHand(card);
        }

        System.out.println("Now the value of player's hand card :" + gameLobby.getPlayers().get(0).getPlayerHandValue());
        assertEquals(true,gameLobby.getPlayers().get(0).isBust());

    }

    /**
     * Test the bust part
     */
    @Test
    public void playerBustTest3(){
        GameLobby gameLobby = new GameLobby(user);
        Player player1 = new Player(user);

        assertEquals(false,gameLobby.getPlayers().get(0).isBust());

    }



}