package CardGame.Pushes;

import CardGame.GameEngine.Hand;

import static CardGame.ProtocolTypes.PUSH_DEALER_HAND;

/**
 * Created by tom on 11/03/17.
 */
public class PushDealerHand extends PushProtocol {
    private Hand dealerHand;

    public PushDealerHand(Hand dealerHand) {
        super(PUSH_DEALER_HAND);
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
