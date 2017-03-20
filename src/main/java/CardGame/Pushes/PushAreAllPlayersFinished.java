package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_FINISHED;

/**
 * Created by tom on 11/03/17.
 */
public class PushAreAllPlayersFinished extends ResponseProtocol {
    private Map<String, Boolean> playersFinished;

    public PushAreAllPlayersFinished(int protocolID, int success, Map<String, Boolean> playersFinished) {
        super(protocolID, PUSH_PLAYERS_FINISHED, success);
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
