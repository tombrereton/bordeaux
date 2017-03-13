package CardGame.Requests;

import static CardGame.ProtocolTypes.BET;

/**
 * This request send the betAmount to the game lobby the user has joined.
 *
 * Created by tom on 12/03/17.
 */
public class RequestBet extends RequestProtocol {
    private String username;
    private int betAmount;

    public RequestBet(int protocolId, int betAmount, String username) {
        super(protocolId, BET);
        this.betAmount = betAmount;
        this.username = username;
    }

    public RequestBet(int betAmount, String userName) {
        super(BET);
        this.betAmount = betAmount;
        this.username = userName;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestBet{" +
                "username='" + username + '\'' +
                ", betAmount=" + betAmount +
                "} " + super.toString();
    }
}
