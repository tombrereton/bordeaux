package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_BUST;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersBust extends ResponseProtocol {
    private Map<String, Boolean> playersBust;

    public PushPlayersBust(int protocolID, int success, Map<String, Boolean> playerBust) {
        super(protocolID, PUSH_PLAYERS_BUST, success);
        this.playersBust = playersBust;
    }

    public Map<String, Boolean> getPlayersBust() {
        return playersBust;
    }

    @Override
    public String toString() {
        return "PushPlayersWon{" +
                "playersBust=" + playersBust +
                "} " + super.toString();
    }
}
