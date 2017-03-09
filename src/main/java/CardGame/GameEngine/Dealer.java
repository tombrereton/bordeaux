package CardGame.GameEngine;

import CardGame.CardGameServer;
import CardGame.User;

import java.util.ArrayList;

/**
 * Created by tom on 08/03/17.
 */
public class Dealer {
    Deck dealerDeck;
    ArrayList<User> players;

    public Dealer() {
        this.players = new ArrayList<>();

        for (int i = 0; i < CardGameServer.getSizeOfUsers(); i++) {
            players.add(CardGameServer.getUsers(i));
        }

        this.dealerDeck = new Deck();
    }
}
