package CardGame.Requests;

import static CardGame.ProtocolTypes.CREATE_GAME;

/**
 * Created by tom on 11/03/17.
 */
public class RequestCreateGame extends RequestProtocol {
    private String username;

    public RequestCreateGame(String username) {
        super(CREATE_GAME);
        this.username = username;
    }

    public RequestCreateGame(int protocolId, String username) {
        super(protocolId, CREATE_GAME);
        this.username = username;
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
