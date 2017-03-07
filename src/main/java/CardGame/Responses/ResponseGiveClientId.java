package CardGame.Responses;

import CardGame.ProtocolTypes;

/**
 * Created by tom on 07/03/17.
 */
public class ResponseGiveClientId extends ResponseProtocol {
    public ResponseGiveClientId(int protocolId, int type, int requestSuccess) {
        super(protocolId, ProtocolTypes.CLIENT_ID, requestSuccess);
    }

    public ResponseGiveClientId(int protocolId, int type, int requestSuccess, String errorMsg) {
        super(protocolId, ProtocolTypes.CLIENT_ID, requestSuccess, errorMsg);
    }
}
