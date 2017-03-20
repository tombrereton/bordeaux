package CardGame.Responses;

import static CardGame.ProtocolTypes.HIT;

/**
 * This class is a response to the Hit request. If the Hit
 * was performed successfully in the gameLobby, we send a successful
 * response, otherwise not.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseHit extends ResponseProtocol {
    public ResponseHit(int protocolId, int requestSuccess) {
        super(protocolId, HIT, requestSuccess);
    }

    public ResponseHit(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, HIT, requestSuccess, errorMsg);
    }
}
