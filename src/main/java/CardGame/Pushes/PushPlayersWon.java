package CardGame.Pushes;

import java.util.Map;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayersWon extends PushProtocol {
    private Map<String, Boolean> playersWon;

    public PushPlayersWon(int type, Map<String, Boolean> playersWon) {
        super(type);
        this.playersWon = playersWon;
    }

    public Map<String, Boolean> getPlayersWon() {
        return playersWon;
    }
}
