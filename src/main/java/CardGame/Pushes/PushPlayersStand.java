package CardGame.Pushes;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_STAND;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersStand extends PushProtocol {
    private Map<String, Boolean> playersStand;

    public PushPlayersStand(Map<String, Boolean> playersStand) {
        super(PUSH_PLAYERS_STAND);
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
