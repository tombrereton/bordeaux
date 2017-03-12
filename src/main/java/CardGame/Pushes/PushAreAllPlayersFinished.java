package CardGame.Pushes;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_FINISHED;

/**
 * Created by tom on 11/03/17.
 */
public class PushAreAllPlayersFinished extends PushProtocol {
    private Map<String, Boolean> playersFinished;

    public PushAreAllPlayersFinished(Map<String, Boolean> playersFinished) {
        super(PUSH_PLAYERS_FINISHED);
        this.playersFinished = playersFinished;
    }

    public Map<String, Boolean> getPlayersFinished() {
        return playersFinished;
    }

    @Override
    public String toString() {
        return "PushAreAllPlayersFinished{" +
                "playersFinished=" + playersFinished +
                "} " + super.toString();
    }
}
