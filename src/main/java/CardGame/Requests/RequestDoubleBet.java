package CardGame.Requests;

import static CardGame.ProtocolTypes.DOUBLE;

/**
 * This class is a request to double the bet in the
 * game of blackjack.
 *
 * Created by tom on 12/03/17.
 */
public class RequestDoubleBet extends RequestProtocol {

    public RequestDoubleBet(int protocolId) {
        super(protocolId, DOUBLE);
    }

    public RequestDoubleBet() {
        super(DOUBLE);
    }
}
