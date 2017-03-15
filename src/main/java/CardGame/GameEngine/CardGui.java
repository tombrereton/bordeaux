package CardGame.GameEngine;

/**
 * Created by Alex on 15/03/2017.
 */
public class CardGui extends Card {

    public CardGui(int theValue, int theSuit) {
        super(theValue, theSuit);
    }

    /**
     * This method creates a 3 digit string ID to reference the card image
     * For example the Ace of Spaces would be "001"
     * @param suit The ID of the suit 0-3
     * @param value The ID of the card 1-13
     * @return The imageID as a 3 digit String
     */
    public String getImageID(int suit, int value){
        String imageID = "000";
        if (isFaceUp() == true) {
            imageID = Integer.toString(suit);
            if (value < 10) {
                imageID = imageID + "0";
            }
            imageID = imageID + Integer.toString(value);
        }
        return imageID;
    }
}
