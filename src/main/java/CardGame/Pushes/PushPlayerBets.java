package CardGame.Pushes;

import java.util.Map;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBets extends PushProtocol {
    private Map<String, Integer> playerBets;

    public PushPlayerBets(int type, Map<String, Integer> playerBets) {
        super(type);
        this.playerBets = playerBets;
    }

    public Map<String, Integer> getPlayerBets() {
        return playerBets;
    }
}
