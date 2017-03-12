package CardGame.Requests;

import static CardGame.ProtocolTypes.CLIENT_ID;

/**
 * Created by tom on 07/03/17.
 */
public class RequestGiveClientId extends RequestProtocol{

    public RequestGiveClientId(int protocolId) {
        super(protocolId, CLIENT_ID);
    }

    public RequestGiveClientId() {
        super(CLIENT_ID);
    }
}
