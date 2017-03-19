package CardGame.GameEngine;

import CardGame.MessageObject;
import CardGame.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by tom on 09/03/17.
 */
public class GameLobby {
    private String lobbyName;
    private ArrayList<Player> players;
    private Map<String, Socket> playerSockets;
    private Deck deck;

    // boolean flags
    private boolean allPlayersStand;
    private boolean allPlayersBetPlaced;
    private boolean dealerCardLeft;
    private boolean allPlayersFinished;

    // variables that will be sent to client
    private Map<String, Integer> playerBudgets;
    private Map<String, Boolean> playersBust;
    private Map<String, Boolean> playersWon;
    private Map<String, Boolean> playersStand;
    private BlackjackHand dealerHand;

    // chat variables
    private volatile ConcurrentLinkedDeque<MessageObject> messageQueue;


    /**
     * creates gamelobby with lobbyname set  : user1's lobby
     *
     * @param user user1's name
     */
    public GameLobby(User user, Socket socket) {
        this.lobbyName = user.getUserName();
        this.players = new ArrayList<>();

        this.players.add(new Player(user));

        this.playerSockets = new HashMap<>();
        this.playerSockets.put(user.getUserName(), socket);

        // boolean flags
        this.allPlayersBetPlaced = false;
        this.dealerCardLeft = false;
        this.allPlayersFinished = false;

        // Create a deck
        this.deck = new Deck();

        // variables that will be sent to client
        this.playerBudgets = new HashMap<>();
        this.playersBust = new HashMap<>();
        this.playersWon = new HashMap<>();
        this.playersStand = new HashMap<>();
        this.dealerHand = new BlackjackHand();
        setAllPlayersBustToFalse();
        setAllPlayersWonToFalse();
        setAllPlayersStandToFalse();

        // chat variables
        this.messageQueue = new ConcurrentLinkedDeque<>();


    }

    private void setAllPlayersStandToFalse() {
        for (Player p: players){
            getPlayersStand().put(p.getUsername(), false);
        }
    }

    private void setAllPlayersWonToFalse() {
        for (Player p : players) {
            getPlayersWon().put(p.getUsername(), false);
        }
    }

    private void setAllPlayersBustToFalse() {
        for (Player p : players) {
            getPlayersBust().put(p.getUsername(), false);
        }
    }

    /**
     * @return true if bust, false if not
     */
    public synchronized boolean isDealerBust() {
        return dealerHand.getBlackjackValue() > 21;

    }

    /**
     * return true if player has hand greater than dealer but player is not bust
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

    public synchronized void setPlayerFold(String username) {
        getPlayersBust().put(username, true);
        setPlayerStand(username);
        setPlayersWon();
    }


    public synchronized boolean wonAgainstDealer(Player player) {
        int playerValue = player.getPlayerHand().getBlackjackValue();


        if (playerValue > 21) {
            // lost - set bet to zero
            playersBust.put(player.getUsername(), true);
            playersStand.put(player.getUsername(), true);
            setBetToZero(player);
            return false;
        }

        // check if all players have finished or stand
        setAllPlayersStand();

        if (allPlayersStand) {

            // deal cards to dealer while hand value is less than 17
            setDealerHandFaceUp();


            while (getDealerHand().getBlackjackValue() < 17) {
                dealToDealer();
            }


            if (dealerHand.getBlackjackValue() == 21) {
                // lost - set bet to zero
                setBetToZero(player);
                return false;
            } else if (isPlayerBust(player)) {
                // lost - set bet to zero
                setBetToZero(player);
                return false;
            } else if (dealerHand.getBlackjackValue() < 21 && playerValue == 21) {
                // won - add bet to budget
                addBetToBudget(player);
                return true;
            } else if (isDealerBust() && !isPlayerBust(player)) {
                // won - add bet to budget
                addBetToBudget(player);
                return true;
            } else if (!isDealerBust() && playerValue > dealerHand.getBlackjackValue()) {
                // won - add bet to budget
                addBetToBudget(player);
                return true;
            } else {
                // lost - set bet to zero
                setBetToZero(player);
                return false;
            }

        }

        return false;
    }

    private boolean isPlayerBust(Player player) {
        return getPlayer(player.getUsername()).isBust();
    }

    private void setBetToZero(Player player) {
        getPlayer(player.getUsername()).setBet(0);
    }

    private void addBetToBudget(Player player) {
        int amountWon = getPlayer(player.getUsername()).getBet() * 2;
        int currentBudget = getPlayer(player.getUsername()).getBudget();
        getPlayer(player.getUsername()).setBudget(currentBudget + amountWon);
        getPlayer(player.getUsername()).setBet(0);
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

    public synchronized boolean removePlayer(String username) {
        int removeID = -1;
        int index = 0;
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                removeID = index;
                break;
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

    /**
     * This method will check whether all cards are left on this deck
     *
     * @return If the player still has the cards, return false, else true
     */
    public synchronized boolean allPlayersNoCards() {
        for (Player p : players) {
            // if the player still have cards
            // return false
            if (p.isCardLeft()) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean setAllPlayersFinished() {
        // change to check player.isfinishedround

        for (Player p : players) {
            if (!getPlayersWon().get(p.getUsername()) && !getPlayersStand().get(p.getUsername())) {
                setAllPlayersFinished(false);
                return false;
            }
        }
        setAllPlayersFinished(true);
        return true;
    }


    public synchronized boolean allPlayersBetPlaced() {
        for (Player p : players) {
            if (!p.isBetPlaced()) {
                setAllPlayersBetPlaced(false);
                return false;
            }
        }
        setAllPlayersBetPlaced(true);
        return true;
    }

    /**
     * This method returns a map of all players and their budgets.
     *
     * @return
     */
    public synchronized Map<String, Integer> getPlayerBets() {
        Map<String, Integer> playerBets = new HashMap<>();

        for (Player player : players) {
            playerBets.put(player.getUsername(), player.getBet());
        }

        return playerBets;
    }

    /**
     * This method returns a map of all players and their isFinishedRound status.
     *
     * @return
     */
    public synchronized Map<String, Boolean> getPlayersFinished() {
        Map<String, Boolean> playersFinished = new HashMap<>();

        for (Player player : players) {
            playersFinished.put(player.getUsername(), player.isFinishedRound());
        }

        return playersFinished;
    }

    /**
     * deals 2 cards to everyone, all cards face up
     * deals 2 card to dealer, 1 card face down, 1 card face up
     */
    public synchronized void startGame() {
        // start the game
        deck.shuffle();
        // shuffle the deck
        nextGame();
    }

    public synchronized void nextGame() {
        if (deck.cardsLeft() == 0) {
            deck = new Deck();
            deck.shuffle();
        }

        // remove the cards from each players
        for (Player p : players) {
            p.removeAllCards();
        }
        // remove the cards from dealer
        removeDealerCards();

        // For players:
        for (Player p : players) {

            // deal cards to all players and make them face up

            // first card
            Card firstCard = deck.dealCard();
            firstCard.setFaceUp(true);
            p.addCardToPlayerHand(firstCard);

            // second card
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

    private void removeDealerCards() {
        Iterator<Card> iterator = dealerHand.getHand().iterator();

        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
    }

    public synchronized void setDealerHandFaceUp() {
        for (Card card : dealerHand.getHand()) {
            card.setFaceUp(true);
        }
    }

    public synchronized void setPlayersWon() {
        for (Player player : players) {
            if (wonAgainstDealer(player)) {
                playersWon.put(player.getUsername(), true);
            }
        }

        setAllPlayersFinished();

        if (isAllPlayersFinished()){
            setAllPlayersBustToFalse();
            setAllPlayersWonToFalse();
            setAllPlayersStandToFalse();
            setAllPlayersBetPlaced(false);

            resetPlayers();


        }
    }

    private void resetPlayers() {
        for (Player player : players){
            player.setBetPlaced(false);
            player.setPlayerStand(false);
        }
    }

    private void setDealerCardsFaceUp() {
        for(Card card: getDealerHand().getHand()){
            card.setFaceUp(true);
        }
    }

    /**
     * adds card to player hand
     * returns true if below or equal 21
     * sets player to finished round
     *
     * @param user //     * @return if the user is bet and than hit ,or return false
     */
    public synchronized boolean hit(User user) {
        Player player = getPlayer(user);

        Card newCard = deck.dealCard();
        player.addCardToPlayerHand(newCard);

        player.setBetPlaced(false);

        if(player.isBust()){
            playersBust.put(player.getUsername(),true);
        }

        if(isAllPlayersStand()){
            setDealerCardsFaceUp();
            setPlayersWon();
        }

        return true;
    }


    /**
     * adds card to player hand
     * returns true if below or equal 21
     * sets player to finished round
     *
     * @param username //     * @return if the user is bet and than hit ,or return false
     */
    public synchronized boolean hit(String username) {
        Player player = getPlayer(username);
        Card newCard = deck.dealCard();
        player.addCardToPlayerHand(newCard);

        player.setBetPlaced(false);

        if(player.isBust()){
            playersBust.put(player.getUsername(),true);
        }

        if(allPlayersStand){
            setDealerCardsFaceUp();
            setPlayersWon();
        }


        return player.isBust();
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

    public Map<String, Hand> getPlayerHands() {
        Map<String, Hand> playerHands = new HashMap<>();

        for (Player player : this.getPlayers()) {
            playerHands.put(player.getUsername(), player.getPlayerHand());
        }
        return playerHands;
    }

    public Map<String, Integer> getPlayerBudgets() {
        Map<String, Integer> playerBudgets = new HashMap<>();

        for (Player player : getPlayers()) {
            playerBudgets.put(player.getUsername(), player.getBudget());
        }
        return playerBudgets;
    }

    public ArrayList<String> getPlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();

        for (Player player : getPlayers()) {
            playerNames.add(player.getUsername());
        }

        return playerNames;
    }

    public Map<String, Socket> getPlayerSockets() {
        return playerSockets;
    }

    public Map<String, Boolean> getPlayersBust() {
        return playersBust;
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

    public synchronized void setPlayerStand(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                player.setPlayerStand(true);
                playersStand.put(username, true);
            }
        }

        setAllPlayersStand();
        setPlayersWon();
    }

    public synchronized void setAllPlayersStand() {
        for (Map.Entry<String, Boolean> playerStand : playersStand.entrySet()) {
            if (!playerStand.getValue()) {
                allPlayersStand = false;
                return;
            }
        }

        allPlayersStand = true;
    }

    public ConcurrentLinkedDeque<MessageObject> getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(ConcurrentLinkedDeque<MessageObject> messageQueue) {
        this.messageQueue = messageQueue;
    }

    public Map<String, Boolean> getPlayersWon() {
        return playersWon;
    }

    public Map<String, Boolean> getPlayersStand() {
        return playersStand;
    }

    public boolean isAllPlayersStand() {
        return allPlayersStand;
    }

    public boolean isDealerCardLeft() {
        return dealerCardLeft;
    }

    public void setDealerCardLeft(boolean dealerCardLeft) {
        this.dealerCardLeft = dealerCardLeft;
    }

    public boolean isAllPlayersBetPlaced() {
        return allPlayersBetPlaced;
    }

    public void setAllPlayersBetPlaced(boolean allPlayersBetPlaced) {
        this.allPlayersBetPlaced = allPlayersBetPlaced;
    }

    public boolean isAllPlayersFinished() {
        return allPlayersFinished;
    }

    public void setAllPlayersFinished(boolean allPlayersFinished) {
        this.allPlayersFinished = allPlayersFinished;
    }
}
