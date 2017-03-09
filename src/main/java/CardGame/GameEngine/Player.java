package CardGame.GameEngine;

import CardGame.User;

/**
 * Created by tom on 09/03/17.
 */
public class Player {
    private String username;
    private BlackjackHand playerHand;
    private int budget;
    private int bet;
    private boolean isFinishedRound;

    public Player(User user) {
        this.username = user.getUserName();
        this.playerHand = new BlackjackHand();
        this.budget = 100;
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
        if (isBetWithinBudget(bet)){
            setBudget(-bet);
            this.bet = bet;
        }

        this.bet = bet;
    }

    public void setFinishedRound(boolean finishedRound) {
        isFinishedRound = finishedRound;
    }

    public int getPlayerHandValue(){
        return this.playerHand.getBlackjackValue();
    }

    public void addCardToPlayerHand(Card card){
        this.playerHand.addCard(card);
    }

    public boolean isBetWithinBudget(int bet){
        return bet >= 0 && bet <= this.budget;
    }
}
