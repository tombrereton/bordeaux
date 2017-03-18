package CardGame.GameEngine;

/**
 * An object of class card represents one of the 52 cards in a
 * standard deck of playing cards.  Each card has a suit and
 * a value.
 *
 * Created by tom on 08/03/17.
 */

public class Card {

    public final static int SPADES = 0,       // Codes for the 4 suits.
            HEARTS = 1,
            DIAMONDS = 2,
            CLUBS = 3;

    public final static int ACE = 1,          // Codes for the non-numeric cards.
            JACK = 11,        //   Cards 2 through 10 have their
            QUEEN = 12,       //   numerical listOfGames for their codes.
            KING = 13;

    private final int suit;   // The suit of this card, one of the constants
    //    SPADES, HEARTS, DIAMONDS, CLUBS.

    private final int value;  // The value of this card, from 1 to 11.
    private boolean isFaceUp;

    public Card(int theValue, int theSuit) {
        // Construct a card with the specified value and suit.
        // Value must be between 1 and 13.  Suit must be between
        // 0 and 3.  If the parameters are outside these ranges,
        // the constructed card object will be invalid.
        this.value = theValue;
        this.suit = theSuit;
        this.isFaceUp = true;
    }

    public boolean validateValue(int value){
        return value >= 1 && value <= 13;
    }

    public boolean validateSuit(int suit){
        return suit >= 0 && suit <= 3;
    }

    public int getSuit() {
        // Return the int that codes for this card's suit.
        return suit;
    }

    public int getValue() {
        // Return the int that codes for this card's value.
        return value;
    }

    public String getSuitAsString() {
        // Return a String representing the card's suit.
        // (If the card's suit is invalid, "??" is returned.)
        switch (suit) {
            case SPADES:
                return "Spades";
            case HEARTS:
                return "Hearts";
            case DIAMONDS:
                return "Diamonds";
            case CLUBS:
                return "Clubs";
            default:
                return "??";
        }
    }

    public String getValueAsString() {
        // Return a String representing the card's value.
        // If the card's value is invalid, "??" is returned.
        switch (value) {
            case 1:
                return "Ace";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "10";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return "??";
        }
    }

    public String toString() {
        // Return a String representation of this card, such as
        // "10 of Hearts" or "Queen of Spades".
        return getValueAsString() + " of " + getSuitAsString();
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     * This method creates a 3 digit string ID to reference the card image
     * For example the Ace of Spaces would be "001"
     * @return The imageID as a 3 digit String
     */
    public String getImageID(){
        // default image
        String imageID = "000";

        if (isFaceUp()) {
            // if face up, get image string
            imageID = Integer.toString(suit);
            if (value < 10) {
                imageID = imageID + "0";
            }

            imageID = imageID + Integer.toString(value);
        }

        return imageID;
    }
} // end class Card
