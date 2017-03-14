package CardGame.Pushes;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_BUST;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersBust extends PushProtocol {
    private Map<String, Boolean> playerBust;

    public PushPlayersBust(Map<String, Boolean> playerBust) {
        super(PUSH_PLAYERS_BUST);
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
