package CardGame.Requests;

import static CardGame.ProtocolTypes.STAND;

/**
 * This class is a request to stand in the game of blackjack.
 * i.e. deal no cards to the user who sent this request.
 *
 * Created by tom on 12/03/17.
 */
public class RequestStand extends RequestProtocol {
    private String username;

    public RequestStand(int protocolId, String username) {
        super(protocolId, STAND);
        this.username = username;
    }

    public RequestStand(String username) {
        super(STAND);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestStand{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
