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

    private int cardsUsed; // How many cards have been dealt from the deck.
    private ArrayList<Card> deckOfCards;
    private int testSeed = 1;

    public Deck() {
        deckOfCards = new ArrayList<Card>();

        for (int suit = 0; suit <= 3; suit++) {
            for (int value = 1; value <= 13; value++) {
                deckOfCards.add(new Card(value, suit));
            }

        }
        cardsUsed = 0;
    }


    /**
     * Randomises the cards with a set seed for testing.
     */
    public void shuffleForTests() {
        Collections.shuffle(deckOfCards, new Random(testSeed));
        cardsUsed = 0;
    }

    /**
     * Randomises the cards
     */
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deckOfCards, new Random(seed));
        cardsUsed = 0;
    }

    public int cardsLeft() {
        return 52 - cardsUsed;
    }

    public Card dealCard() {
        // Deals one card from the deck and returns it.
//        if (cardsUsed == 52)
//            shuffle();
        cardsUsed++;
        return deckOfCards.get(cardsUsed - 1);
    }

} // end class Deck
