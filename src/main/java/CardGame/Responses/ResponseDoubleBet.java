package CardGame.Responses;

import static CardGame.ProtocolTypes.DOUBLE;

/**
 * This class is a response to a double bet request. If the double bet
 * was completed in the gameLobby we send a successful response, otherwise
 * not.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseDoubleBet extends ResponseProtocol {

    public ResponseDoubleBet(int protocolId, int requestSuccess) {
        super(protocolId, DOUBLE, requestSuccess);
    }

    public ResponseDoubleBet(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, DOUBLE, requestSuccess, errorMsg);
    }
}
