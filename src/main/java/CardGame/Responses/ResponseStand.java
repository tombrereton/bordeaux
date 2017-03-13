package CardGame.Responses;

import static CardGame.ProtocolTypes.STAND;

/**
 * This class is a response to a stand request in blackjack.
 * If the player is successfully dealt no cards, we send a successful
 * resposne, otherwise not.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseStand extends ResponseProtocol {
    public ResponseStand(int protocolId, int requestSuccess) {
        super(protocolId, STAND, requestSuccess);
    }

    public ResponseStand(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, STAND, requestSuccess, errorMsg);
    }
}
