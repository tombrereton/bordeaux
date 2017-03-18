package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_BUST;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersBust extends ResponseProtocol {
    private Map<String, Boolean> playerBust;

    public PushPlayersBust(int protocolID, int success, Map<String, Boolean> playerBust) {
        super(protocolID, PUSH_PLAYERS_BUST, success);
        this.playerBust = playerBust;
    }

    public Map<String, Boolean> getPlayerBust() {
        return playerBust;
    }

    @Override
    public String toString() {
        return "PushPlayersWon{" +
                "playerBust=" + playerBust +
                "} " + super.toString();
    }
}
