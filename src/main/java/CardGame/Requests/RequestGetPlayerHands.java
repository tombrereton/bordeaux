package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayerHands extends RequestProtocol {
    public RequestGetPlayerHands(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_PLAYER_HANDS);
    }

    public RequestGetPlayerHands() {
        super(ProtocolTypes.PUSH_PLAYER_HANDS);
    }
}
