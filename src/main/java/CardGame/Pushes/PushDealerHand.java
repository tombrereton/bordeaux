package CardGame.Pushes;

import CardGame.GameEngine.Hand;

/**
 * Created by tom on 11/03/17.
 */
public class PushDealerHand extends PushProtocol {
    private Hand dealerHand;

    public PushDealerHand(int type, Hand dealerHand) {
        super(type);
        this.dealerHand = dealerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }
}
