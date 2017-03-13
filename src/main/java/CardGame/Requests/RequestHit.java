package CardGame.Requests;

import static CardGame.ProtocolTypes.HIT;

/**
 * This is a request to Hit in the black jack game,
 * i.e. deal another card to the user who sent this
 * request.
 *
 * Created by tom on 12/03/17.
 */
public class RequestHit extends RequestProtocol {
    String username;

    public RequestHit(int protocolId, String username) {
        super(protocolId, HIT);
        this.username = username;
    }

    public RequestHit(String username) {
        super(HIT);
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
