package CardGame.Responses;

import static CardGame.ProtocolTypes.CLIENT_ID;

/**
 * Created by tom on 07/03/17.
 */
public class ResponseGiveClientId extends ResponseProtocol {

    public ResponseGiveClientId(int protocolId, int requestSuccess) {
        super(protocolId, CLIENT_ID, requestSuccess);
    }

    public ResponseGiveClientId(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, CLIENT_ID, requestSuccess, errorMsg);
    }
}
