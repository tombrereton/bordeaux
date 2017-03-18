package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetDealerHand extends RequestProtocol {
    public RequestGetDealerHand(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_DEALER_HAND);
    }
    public RequestGetDealerHand() {
        super(ProtocolTypes.PUSH_DEALER_HAND);
    }
}
