package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_WON;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersWon extends ResponseProtocol {
    private Map<String, Boolean> playersWon;

    public PushPlayersWon(int protocolID, int success, Map<String, Boolean> playersWon) {
        super(protocolID, PUSH_PLAYERS_WON, success);
        this.playersWon = playersWon;
    }

    public Map<String, Boolean> getPlayersWon() {
        return playersWon;
    }

    @Override
    public String toString() {
        return "PushPlayersWon{" +
                "playersWon=" + playersWon +
                "} " + super.toString();
    }
}
