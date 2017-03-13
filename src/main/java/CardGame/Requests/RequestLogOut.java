package CardGame.Requests;

import static CardGame.ProtocolTypes.LOG_OUT_USER;

/**
 * This is a request to log out the user from the client.
 *
 * Created by tom on 12/03/17.
 */
public class RequestLogOut extends RequestProtocol {
    public RequestLogOut(int protocolId) {
        super(protocolId, LOG_OUT_USER);
    }

    public RequestLogOut() {
        super(LOG_OUT_USER);
    }
}
