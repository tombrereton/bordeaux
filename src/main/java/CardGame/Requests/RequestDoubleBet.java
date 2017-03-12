package CardGame.Requests;

import static CardGame.ProtocolTypes.DOUBLE;

/**
 * This class is a request to double the bet in the
 * game of blackjack.
 *
 * Created by tom on 12/03/17.
 */
public class RequestDoubleBet extends RequestProtocol {
    String username;

    public RequestDoubleBet(int protocolId, String username) {
        super(protocolId, DOUBLE);
        this.username = username;
    }

    public RequestDoubleBet(String username) {
        super(DOUBLE);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestDoubleBet{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
