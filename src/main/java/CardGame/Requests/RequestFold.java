package CardGame.Requests;

import static CardGame.ProtocolTypes.FOLD;

/**
 * This is a request to Fold in the black jack game,
 * i.e. stop dealing cards to this dealer and give the bet
 * to the dealer.
 *
 * Created by tom on 12/03/17.
 */
public class RequestFold extends RequestProtocol {
    String username;

    public RequestFold(int protocolId, String username) {
        super(protocolId, FOLD);
        this.username = username;
    }

    public RequestFold(String username) {
        super(FOLD);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestHit{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
