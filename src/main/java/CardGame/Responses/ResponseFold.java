package CardGame.Responses;

import static CardGame.ProtocolTypes.FOLD;

/**
 * This class is a response to the Fold request. If the Fold
 * was performed successfully in the gameLobby, we send a successful
 * response, otherwise not.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseFold extends ResponseProtocol {
    public ResponseFold(int protocolId, int requestSuccess) {
        super(protocolId, FOLD, requestSuccess);
    }

    public ResponseFold(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, FOLD, requestSuccess, errorMsg);
    }
}
