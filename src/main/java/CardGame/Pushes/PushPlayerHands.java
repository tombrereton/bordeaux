package CardGame.Pushes;

import CardGame.GameEngine.Hand;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerHands extends PushProtocol {
    ArrayList<Hand> hands;
    public PushPlayerHands(int type, ArrayList<Hand> hands) {
        super(type);
        this.hands = hands;
    }

    public ArrayList<Hand> getHands() {
        return hands;
    }
}
