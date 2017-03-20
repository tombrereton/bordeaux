package CardGame.Pushes;

import CardGame.GameEngine.Hand;
import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYER_HANDS;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerHands extends ResponseProtocol {
    private Map<String, Hand> playerHands;

    public PushPlayerHands(int protocolID, int success, Map<String, Hand> playerHands) {
        super(protocolID, PUSH_PLAYER_HANDS, success);
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
