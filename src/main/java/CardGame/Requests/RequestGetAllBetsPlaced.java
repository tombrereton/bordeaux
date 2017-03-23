package CardGame.Requests;

import static CardGame.ProtocolTypes.PUSH_ARE_ALL_BETS_PLACED;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetAllBetsPlaced extends RequestProtocol {
    public RequestGetAllBetsPlaced(int protocolId) {
        super(protocolId, PUSH_ARE_ALL_BETS_PLACED);
    }

    public RequestGetAllBetsPlaced() {
        super(PUSH_ARE_ALL_BETS_PLACED);
    }
}
