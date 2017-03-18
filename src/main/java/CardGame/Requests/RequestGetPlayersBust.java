package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayersBust extends RequestProtocol {
    public RequestGetPlayersBust(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_PLAYERS_BUST);
    }

    public RequestGetPlayersBust() {
        super(ProtocolTypes.PUSH_PLAYERS_BUST);
    }
}
