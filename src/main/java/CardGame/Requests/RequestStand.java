package CardGame.Requests;

import static CardGame.ProtocolTypes.STAND;

/**
 * This class is a request to stand in the game of blackjack.
 * i.e. deal no cards to the user who sent this request.
 *
 * Created by tom on 12/03/17.
 */
public class RequestStand extends RequestProtocol {

    public RequestStand(int protocolId) {
        super(protocolId, STAND);
    }

    public RequestStand() {
        super(STAND);
    }
}