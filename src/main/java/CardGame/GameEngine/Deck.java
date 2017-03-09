package CardGame.GameEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * An object of type Deck represents an ordinary deck of 52 playing cards.
 * The deck can be shuffled, and cards can be dealt from the deck.
 *
 * Created by tom on 08/03/17.
 */
public class Deck {

    private Card[] deck;   // An array of 52 Cards, representing the deck.
    private int cardsUsed; // How many cards have been dealt from the deck.
    private ArrayList<Card> deck_two;

    public Deck() {
//        // Create an unshuffled deck of cards.
//        deck = new Card[52];
//        int cardCt = 0; // How many cards have been created so far.
//        for (int suit = 0; suit <= 3; suit++) {
//            for (int value = 1; value <= 13; value++) {
//                deck[cardCt] = new Card(value, suit);
//                cardCt++;
//            }
//        }

        deck_two = new ArrayList<Card>();

        for (int suit = 0; suit <= 3; suit++) {
            for (int value = 1; value <= 13; value++) {
                deck_two.add(new Card(value, suit));
            }

        }

        cardsUsed = 0;
    }


    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck_two, new Random(seed));
//        // Put all the used cards back into the deck, and shuffle it into
//        // a random order.
//        for (int i = 51; i > 0; i--) {
//            int rand = (int) (Math.random() * (i + 1));
//            Card temp = deck[i];
//            deck[i] = deck[rand];
//            deck[rand] = temp;
//        }
        cardsUsed = 0;
    }

    public int cardsLeft() {
        // As cards are dealt from the deck, the number of cards left
        // decreases.  This function returns the number of cards that
        // are still left in the deck.
        return 52 - cardsUsed;
    }

    public Card dealCard() {
        // Deals one card from the deck and returns it.
        if (cardsUsed == 52)
            shuffle();
        cardsUsed++;
        return deck_two.get(0);
//        return deck[cardsUsed - 1];
    }

} // end class Deck
