package CardGame.GameEngine;

import CardGame.User;

import java.util.Iterator;

/**
 * Created by tom on 09/03/17.
 */
public class Player {
    private String username;
    private BlackjackHand playerHand;
    private int budget;
    private int bet;
    private boolean isBetPlaced;
    private boolean isFinishedRound;
    private boolean isCardLeft;
    private boolean isPlayerStand;

    public Player(User user) {
        this.username = user.getUserName();
        this.playerHand = new BlackjackHand();
        this.budget = 100;
        this.isFinishedRound = false;
        this.isBetPlaced = false;
        this.isCardLeft = false;
    }

    public String getUsername() {
        return username;
    }

    public BlackjackHand getPlayerHand() {
        return playerHand;
    }

    public int getBudget() {
        return budget;
    }

    public int getBet() {
        return bet;
    }

    public boolean isFinishedRound() {
        return isFinishedRound;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setBet(int bet) {
        if (isBetWithinBudget(bet)) {
            setBudget(this.budget - bet);
            this.bet = bet;
        }

        this.bet = bet;
    }

    public void doubleBet() {
        if (isBetWithinBudget(bet)) {
            setBudget(this.budget - bet);
            this.bet = bet * 2;
        }
    }

    public void setFinishedRound(boolean finishedRound) {
        isFinishedRound = finishedRound;
    }

    public int getPlayerHandValue() {
        return this.playerHand.getBlackjackValue();
    }

    public void addCardToPlayerHand(Card card) {
        this.playerHand.addCard(card);
        // when add a card to the player
        setCardLeft(true);
    }

    /**
     * This method will remove all cards from the player hand
     */
    public synchronized void removeAllCards() {

        Iterator<Card> iterator = playerHand.getHand().iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public boolean isBetWithinBudget(int bet) {
        return bet >= 0 && bet <= this.budget;
    }

    public boolean isBust() {
        return this.getPlayerHandValue() > 21;
    }

    public boolean isBetPlaced() {
        return isBetPlaced;
    }

    public void setBetPlaced(boolean betPlaced) {
        isBetPlaced = betPlaced;
    }

    public boolean isCardLeft() {
        return isCardLeft;
    }

    public void setCardLeft(boolean cardLeft) {
        isCardLeft = cardLeft;
    }

    public boolean isPlayerStand() {
        return isPlayerStand;
    }

    public void setPlayerStand(boolean playerStand) {
        isPlayerStand = playerStand;
    }
}
