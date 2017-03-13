package CardGame.Requests;

import static CardGame.ProtocolTypes.CREATE_GAME;

/**
 * Created by tom on 11/03/17.
 */
public class RequestCreateGame extends RequestProtocol {
    String username;

    public RequestCreateGame() {
        super(CREATE_GAME);
    }

    public RequestCreateGame(int protocolId) {
        super(protocolId, CREATE_GAME);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestCreateGame{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
