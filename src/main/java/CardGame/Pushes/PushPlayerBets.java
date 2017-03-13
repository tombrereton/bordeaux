package CardGame.Pushes;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYER_BETS;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBets extends PushProtocol {
    private Map<String, Integer> playerBets;

    public PushPlayerBets(Map<String, Integer> playerBets) {
        super(PUSH_PLAYER_BETS);
        this.playerBets = playerBets;
    }

    public Map<String, Integer> getPlayerBets() {
        return playerBets;
    }

    @Override
    public String toString() {
        return "PushPlayerBets{" +
                "playerBets=" + playerBets +
                "} " + super.toString();
    }
}
