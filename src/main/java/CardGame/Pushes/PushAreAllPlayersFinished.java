package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_FINISHED;

/**
 * Created by tom on 11/03/17.
 */
public class PushAreAllPlayersFinished extends ResponseProtocol {
    private boolean allPlayersFinished;

    public PushAreAllPlayersFinished(int protocolID, int success, boolean playersFinished) {
        super(protocolID, PUSH_PLAYERS_FINISHED, success);
        this.allPlayersFinished = playersFinished;
    }

    public boolean isAllPlayersFinished() {
        return allPlayersFinished;
    }

    @Override
    public String toString() {
        return "PushAreAllPlayersFinished{" +
                "allPlayersFinished=" + allPlayersFinished +
                "} " + super.toString();
    }
}
