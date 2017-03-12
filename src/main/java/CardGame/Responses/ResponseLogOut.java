package CardGame.Responses;

import static CardGame.ProtocolTypes.LOG_OUT_USER;

/**
 * This class is a reponse to a log out request. We send a successful
 * response if the user is successfully logged out from the clientThread.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseLogOut extends ResponseProtocol {
    public ResponseLogOut(int protocolId, int requestSuccess) {
        super(protocolId, LOG_OUT_USER, requestSuccess);
    }

    public ResponseLogOut(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, LOG_OUT_USER, requestSuccess, errorMsg);
    }
}
