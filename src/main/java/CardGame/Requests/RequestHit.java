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

    public RequestHit(int protocolId) {
        super(protocolId, HIT);
    }

    public RequestHit() {
        super(HIT);
    }
}
