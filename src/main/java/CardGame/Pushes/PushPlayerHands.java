package CardGame.Pushes;

import CardGame.GameEngine.Hand;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYER_HANDS;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerHands extends PushProtocol {
    private Map<String, Hand> playerHands;

    public PushPlayerHands(Map<String, Hand> playerHands) {
        super(PUSH_PLAYER_HANDS);
        this.playerHands = playerHands;
    }

    public Map<String, Hand> getPlayerHands() {
        return playerHands;
    }

    @Override
    public String toString() {
        return "PushPlayerHands{" +
                "playerHands=" + playerHands +
                "} " + super.toString();
    }
}
