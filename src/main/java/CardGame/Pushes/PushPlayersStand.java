package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_STAND;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersStand extends ResponseProtocol {
    private Map<String, Boolean> playersStand;

    public PushPlayersStand(int protocolID, int success, Map<String, Boolean> playersStand) {
        super(protocolID, PUSH_PLAYERS_STAND, success);
        this.playersStand = playersStand;
    }

    public Map<String, Boolean> getPlayersStand() {
        return playersStand;
    }

    @Override
    public String toString() {
        return "PushPlayersWon{" +
                "playersStand=" + playersStand +
                "} " + super.toString();
    }
}
