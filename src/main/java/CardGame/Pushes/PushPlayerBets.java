package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYER_BETS;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBets extends ResponseProtocol {
    private Map<String, Integer> playerBets;

    public PushPlayerBets(int protocolID, int success, Map<String, Integer> playerBets) {
        super(protocolID, PUSH_PLAYER_BETS, success);
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
