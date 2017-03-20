package CardGame.Responses;

import static CardGame.ProtocolTypes.BET;

/**
 * This class is a response for a BET. It tells the client
 * of the success of the BET request.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseBet extends ResponseProtocol {

    public ResponseBet(int protocolId, int requestSuccess) {
        super(protocolId, BET, requestSuccess);
    }

    public ResponseBet(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, BET, requestSuccess, errorMsg);
    }
}
