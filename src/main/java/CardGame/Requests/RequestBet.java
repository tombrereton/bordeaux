package CardGame.Requests;

import static CardGame.ProtocolTypes.BET;

/**
 * This request send the betAmount to the game lobby the user has joined.
 *
 * Created by tom on 12/03/17.
 */
public class RequestBet extends RequestProtocol {
    private int betAmount;

    public RequestBet(int protocolId, int betAmount) {
        super(protocolId, BET);
        this.betAmount = betAmount;
    }

    public RequestBet(int betAmount) {
        super(BET);
        this.betAmount = betAmount;
    }

    public int getBetAmount() {
        return betAmount;
    }

    @Override
    public String toString() {
        return "RequestBet{" +
                "betAmount=" + betAmount +
                "} " + super.toString();
    }
}
