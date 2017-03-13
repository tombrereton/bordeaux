package CardGame.GameEngine;

import CardGame.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tom on 09/03/17.
 */
public class GameLobby {
    private String lobbyName;
    private ArrayList<Player> players;
    private BlackjackHand dealerHand;
    private Map<String, Socket> playerSockets;
    private Deck deck;
    private Map<String, Integer> playerBudgets;

    /**
     * creates gamelobby with lobbyname set  : user1's lobby
     *
     * @param user user1's name
     */
    public GameLobby(User user, Socket socket) {
        this.lobbyName = user.getUserName();
        this.players = new ArrayList<>();
        this.dealerHand = new BlackjackHand();
        this.playerSockets = new HashMap<>();
        this.playerSockets.put(user.getUserName(), socket);
        this.playerBudgets = new HashMap<>();
        // Create a deck
        this.deck = new Deck();
    }

    /**
     * @return true if bust, false if not
     */
    public synchronized boolean isDealerBust() {
        return dealerHand.getBlackjackValue() <= 21;

    }

    /**
     * return true if player has values greater than dealer but player is not bust
     * return false if player has value less than dealer but dealer is not bust
     *
     * @param player
     * @return
     */
    public synchronized boolean compareHandWithDealer(Player player) {
        int playerValue = player.getPlayerHand().getBlackjackValue();
        if (playerValue > 0 && playerValue <= 21) {
            return playerValue > dealerHand.getBlackjackValue();
        }
        return false;
    }

    /**
     * add card to dealerHand if value < 17 (check this!)
     * stand if > 17
     */
    public synchronized void dealToDealer() {
        if (dealerHand.getBlackjackValue() < 17) {
            Card newCard = new Deck().dealCard();
            dealerHand.addCard(newCard);
        }
    }

    public synchronized void addPlayer(User user, Socket playerSocket) {
        this.playerSockets.put(user.getUserName(), playerSocket);
        players.add(new Player(user));
        // fill out
    }

    public synchronized Player getPlayer(User user) {
        // check
        for (Player player : players) {
            if (player.getUsername().equals(user.getUserName())) {
                return player;
            } else {
                return null;
            }
        }
        return null;
    }

    public synchronized Player getPlayer(String username) {
        // check
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            } else {
                return null;
            }
        }
        return null;
    }

    public synchronized boolean removePlayer(User user) {
        // fill out
        int removeID = -1;
        int index = 0;
        for (Player player : players) {
            if (player.getUsername().equals(user.getUserName())) {
                removeID = index;
            }
            index++;
        }
        if (removeID != -1) {
            players.remove(removeID);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean removePlayer(String username){
        int removeID = -1;
        int index = 0;
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                removeID = index;
            }
            index++;
        }
        if (removeID != -1) {
            players.remove(removeID);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void updatePlayer(Player player) {
//        for(Player p: players){
//            if(p.getUsername().equals(player.getUsername())){
//                p.setBet(play);
//            }
//        }
        // fill out
    }

    public synchronized boolean allPlayersFinished() {
        for (Player p : players) {
            if (!p.isFinishedRound()) {
                return false;
            }
        }
        return true;
    }

    /**
     * tells everyone to bet
     * tells everyone who still has to bet
     * once everyone has bet, calls startgame method
     *
     */
    public synchronized void takeBets() {
        // fill out

        for (Player p : players) {
            if (!p.isFinishedRound()) {
                System.out.println(p.getUsername() + " needs to bet");
            }
        }
        if (allPlayersFinished()) {
            startGame();
        }
    }

    /**
     * deals 2 cards to everyone, all cards face up
     * deals 2 card to dealer, 1 card face down, 1 card face up
     */
    public synchronized void startGame() {
        // start the game and shuffle the deck
        deck.shuffle();

        // For players:

        for (Player p : players) {
            Card firstCard = deck.dealCard();
            firstCard.setFaceUp(true);
            p.addCardToPlayerHand(firstCard);
            Card secondCard = deck.dealCard();
            secondCard.setFaceUp(true);
            p.addCardToPlayerHand(secondCard);
        }

        // For the dealer:

        Card dealerFirstCard = deck.dealCard();
        dealerFirstCard.setFaceUp(false);
        dealerHand.addCard(dealerFirstCard);

        Card dealerSecondCard = deck.dealCard();
        dealerSecondCard.setFaceUp(true);
        dealerHand.addCard(dealerSecondCard);

    }

    /**
     * adds card to player hand
     * returns true if below or equal 21
     * sets player to finished round
     *
     * @param user
     * @return
     */
    public synchronized boolean hit(User user) {
        Player player = getPlayer(user);
        Card newCard = deck.dealCard();
        player.addCardToPlayerHand(newCard);
        player.setFinishedRound(true);
        return player.getPlayerHand().getBlackjackValue() <= 21;
    }

    public synchronized boolean stand(User user) {
        // does nothing
        // sets player to finished round
        Player player = getPlayer(user);
        player.setFinishedRound(true);
        return true;
    }

    public synchronized boolean doubleBet(User user) {
        // doubles bet
        // sets player to finished round
        Player player = getPlayer(user);
        player.setBet(player.getBet() * 2);
        return hit(user);
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public BlackjackHand getDealerHand() {
        return dealerHand;
    }

    public Map<String, Hand> getPlayerHands(){
        Map<String, Hand> playerHands = new HashMap<>();

        for (Player player : this.getPlayers()){
            playerHands.put(player.getUsername(), player.getPlayerHand());
        }
        return playerHands;
    }

    public Map<String, Integer> getPlayerBudgets() {
        Map<String, Integer> playerBudgets = new HashMap<>();

        for (Player player: getPlayers()){
            playerBudgets.put(player.getUsername(), player.getBudget());
        }
        return playerBudgets;
    }

    public ArrayList<String> getPlayerNames(){
        ArrayList<String> playerNames = new ArrayList<>();

        for (Player player : getPlayers()){
            playerNames.add(player.getUsername());
        }

        return playerNames;
    }

    public Map<String, Socket> getPlayerSockets() {
        return playerSockets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        GameLobby gameLobby = (GameLobby) o;

        return this.getLobbyName().equals(gameLobby.getLobbyName());
    }

    @Override
    public int hashCode() {
        return getLobbyName() != null ? getLobbyName().hashCode() : 0;
    }
}
