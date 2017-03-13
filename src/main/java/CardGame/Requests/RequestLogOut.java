package CardGame.Requests;

import static CardGame.ProtocolTypes.LOG_OUT_USER;

/**
 * This is a request to log out the user from the client.
 * <p>
 * Created by tom on 12/03/17.
 */
public class RequestLogOut extends RequestProtocol {
    private String username;

    public RequestLogOut(int protocolId, String username) {
        super(protocolId, LOG_OUT_USER);
        this.username = username;
    }

    public RequestLogOut(String username) {
        super(LOG_OUT_USER);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestLogOut{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
