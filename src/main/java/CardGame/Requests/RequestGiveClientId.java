package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by tom on 07/03/17.
 */
public class RequestGiveClientId extends RequestProtocol{

    public RequestGiveClientId(int protocolId, int type) {
        super(protocolId, ProtocolTypes.CLIENT_ID);
    }

    public RequestGiveClientId() {
        super(ProtocolTypes.CLIENT_ID);
    }
}
