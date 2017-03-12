package CardGame.Pushes;

import CardGame.GameEngine.Hand;

import java.util.Map;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerHands extends PushProtocol {
    private Map<String, Hand> playerHands;
    public PushPlayerHands(int type, Map<String, Hand> playerHands) {
        super(type);
        this.playerHands = playerHands;
    }

    public Map<String, Hand> getPlayerHands() {
        return playerHands;
    }
}
