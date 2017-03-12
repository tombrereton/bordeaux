package CardGame.Pushes;

import java.util.Map;

/**
 * Created by tom on 11/03/17.
 */
public class PushAreAllPlayersFinished extends PushProtocol {
    private Map<String, Boolean> playersFinished;

    public PushAreAllPlayersFinished(int type, Map<String, Boolean> playersFinished) {
        super(type);
        this.playersFinished = playersFinished;
    }

    public Map<String, Boolean> getPlayersFinished() {
        return playersFinished;
    }
}
