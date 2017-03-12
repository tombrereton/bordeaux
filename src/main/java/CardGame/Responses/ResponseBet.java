package CardGame.Responses;

/**
 * This class is a response for a BET. It tells the client
 * of the success of the BET request.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseBet extends ResponseProtocol {

    public ResponseBet(int protocolId, int type, int requestSuccess) {
        super(protocolId, type, requestSuccess);
    }

    public ResponseBet(int protocolId, int type, int requestSuccess, String errorMsg) {
        super(protocolId, type, requestSuccess, errorMsg);
    }
}
