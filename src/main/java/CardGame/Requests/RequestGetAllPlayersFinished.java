package CardGame.Requests;

import static CardGame.ProtocolTypes.PUSH_ARE_PLAYERS_FINISHED;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetAllPlayersFinished extends RequestProtocol {

    public RequestGetAllPlayersFinished(int protocolId) {
        super(protocolId, PUSH_ARE_PLAYERS_FINISHED);
    }

    public RequestGetAllPlayersFinished() {
        super(PUSH_ARE_PLAYERS_FINISHED);
    }
}
