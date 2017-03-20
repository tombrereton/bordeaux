package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetGameNames extends RequestProtocol {
    public RequestGetGameNames(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_GAME_NAMES);
    }

    public RequestGetGameNames() {
        super(ProtocolTypes.PUSH_GAME_NAMES);
    }
}
