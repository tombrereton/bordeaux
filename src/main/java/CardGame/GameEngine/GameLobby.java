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

    /**
     * creates gamelobby with lobbyname set  : user1's lobby
     *
     * @param user user1's name
     */
    public GameLobby(User user){
        this.lobbyName = user.getUserName();
        this.players = new ArrayList<>();
        this.dealerHand = new BlackjackHand();
    }

    /**
     *
     * @return true if bust, false if not
     */
    public synchronized boolean isDealerBust(){
        if(dealerHand.getBlackjackValue()>21){
            return false;
        }else{
            return true;
        }

    }

    /**
     * return true if player has values greater than dealer but player is not bust
     * return false if player has value less than dealer but dealer is not bust
     * @param player
     * @return
     */
    public synchronized boolean compareHandWithDealer(Player player){
        int playerValue = player.getPlayerHand().getBlackjackValue();
        if(playerValue >0 && playerValue <=21){
            if(playerValue > dealerHand.getBlackjackValue()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * add card to dealerHand if value < 17 (check this!)
     * stand if > 17
     */
    public synchronized void dealToDealer(){
       if(dealerHand.getBlackjackValue()<17){
           Card newCard = new Deck().dealCard();
           dealerHand.addCard(newCard);
       }
    }

    public synchronized void addPlayer(Player player){
        players.add(player);
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

    public synchronized boolean removePlayer(User user){
       // fill out
        int removeID = -1;
        int index =0;
        for(Player player: players){
            if(player.getUsername().equals(user.getUserName())){
                removeID = index;
            }
            index++;
        }
        if(removeID!=-1){
            players.remove(removeID);
            return true;
        }else {
            return false;
        }

    }

    public synchronized void updatePlayer(Player player){
//        for(Player p: players){
//            if(p.getUsername().equals(player.getUsername())){
//                p.setBet(play);
//            }
//        }
        // fill out
    }
    public synchronized boolean allPlayersFinished(){
        for (Player p : players) {
            if(!p.isFinishedRound()){
                return false;
            }
        }
        return true;
    }

    /**
     *  tells everyone to bet
     *  tells everyone who still has to bet
     *  once everyone has bet, calls startgame method
     * @param bet
     */
    public synchronized void takeBets(int bet){
        // fill out

            for (Player p : players) {
                if(!p.isFinishedRound()){
                    System.out.println(p.getUsername() + " needs to bet");
                }
            }
            if(allPlayersFinished()){
                startGame();
            }
    }

    /**
     * deals 2 cards to everyone, all cards face up
     * deals 2 card to dealer, 1 card face down, 1 card face up
     */
    public synchronized void startGame(){

        //fill out
        //
        for (Player p : players) {
            Card firstCard = new Deck().dealCard();
            p.getPlayerHand().addCard(firstCard);
            Card secondCard = new Deck().dealCard();
            p.getPlayerHand().addCard(secondCard);
        }
        Card dealerFirstCard = new Deck().dealCard();
        dealerHand.addCard(dealerFirstCard);
        dealerHand.getCard(0).setFaceUp(false);
        Card dealerSecondCard = new Deck().dealCard();
        dealerHand.addCard(dealerSecondCard);

    }

    /**
     * adds card to player hand
     * returns true if below or equal 21
     * sets player to finished round
     * @param user
     * @return
     */
    public synchronized boolean hit(User user){
        Player player = getPlayer(user);
        Card newCard = new Deck().dealCard();
        player.getPlayerHand().addCard(newCard);
        player.setFinishedRound(true);
        if(player.getPlayerHand().getBlackjackValue()<=21){
            return true;
        }
        return false;
    }

    public synchronized boolean stand(User user){
        // does nothing
        // sets player to finished round
        Player player = getPlayer(user);
        player.setFinishedRound(true);
        return true;
    }

    public synchronized boolean doubleBet(User user){
        // doubles bet
        // sets player to finished round
        Player player = getPlayer(user);
        player.setBet(player.getBet()*2);
        return true;
    }


}
