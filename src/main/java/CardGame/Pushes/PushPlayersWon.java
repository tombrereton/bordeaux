package CardGame.Pushes;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_WON;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersWon extends PushProtocol {
    private Map<String, Boolean> playersWon;

    public PushPlayersWon(Map<String, Boolean> playersWon) {
        super(PUSH_PLAYERS_WON);
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
