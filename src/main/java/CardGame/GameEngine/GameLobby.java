package CardGame.GameEngine;

import CardGame.User;

import java.util.ArrayList;

/**
 * Created by tom on 09/03/17.
 */
public class GameLobby {
    String lobbyName;
    ArrayList<Player> players;
    BlackjackHand dealerHand;

    public GameLobby(User user){
        // creates gamelobby with lobbyname set  : user1's lobby
    }

    public synchronized boolean isDealerBust(){
        // return true if bust, false if not
        return false;
    }

    public synchronized boolean compareHandWithDealer(Player player){
        // return true if player has values greater than dealer but player is not bust
        // return false if player has value less than dealer but dealer is not bust
        return  false;
    }

    public synchronized void dealToDealer(){
        // add card to dealerHand if value < 17 (check this!)
        // stand if > 17
    }

    public synchronized void addPlayer(Player player){
        // fill out
    }

    public synchronized Player getPlayer(User user){
        // check
        for(Player player : players){
            if (player.getUsername().equals(user.getUserName())){
                return player;
            } else {
                return null;
            }
        }
        return null;
    }

    public synchronized void removePlayer(User user){
       // fill out
    }

    public synchronized void updatePlayer(Player player){
        // fill out
    }

    public synchronized void takeBets(int bet){
        // fill out
        // tells everyone to bet
        // tells everyone who still has to bet
        // once everyone has bet, calls startgame method
    }

    public synchronized void startGame(){
        //fill out
        // deals 2 cards to everyone, all cards face up
        // deals 2 card to dealer, 1 card face down, 1 card face up
    }

    public synchronized boolean hit(User user){
        // adds card to player hand
        // returns true if below or equal 21
        // sets player to finished round
        return false;
    }

    public synchronized boolean stand(User user){
        // does nothing
        // sets player to finished round
        return false;
    }

    public synchronized boolean doubleBet(User user){
        // doubles bet
        // sets player to finished round
        return false;
    }


}
