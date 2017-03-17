package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayerNames extends RequestProtocol {
    public RequestGetPlayerNames(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_PLAYER_NAMES);
    }

    public RequestGetPlayerNames() {
        super(ProtocolTypes.PUSH_PLAYER_NAMES);
    }
}
