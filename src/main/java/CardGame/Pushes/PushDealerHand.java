package CardGame.Pushes;

import CardGame.GameEngine.Hand;
import CardGame.Responses.ResponseProtocol;

import static CardGame.ProtocolTypes.PUSH_DEALER_HAND;

/**
 * Created by tom on 11/03/17.
 */
public class PushDealerHand extends ResponseProtocol {
    private Hand dealerHand;

    public PushDealerHand(int protocolID, int success, Hand dealerHand) {
        super(protocolID, PUSH_DEALER_HAND, success);
        this.dealerHand = dealerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    @Override
    public String toString() {
        return "PushDealerHand{" +
                "dealerHand=" + dealerHand +
                "} " + super.toString();
    }
}
