package CardGame.Requests;

import static CardGame.ProtocolTypes.JOIN_GAME;

/**
 * This class sends a request to join a gameLobby. If successful,
 * the user will be added to the game lobby and therefore subscribed to
 * all pushes for that game.
 *
 * Created by tom on 11/03/17.
 */
public class RequestJoinGame extends RequestProtocol {
    private String gameToJoin;
    private String username;

    public RequestJoinGame(int protocolId, String gameToJoin, String username) {
        super(protocolId, JOIN_GAME);
        this.gameToJoin = gameToJoin;
        this.username = username;
    }

    public RequestJoinGame(String gameToJoin, String username) {
        super(JOIN_GAME);
        this.gameToJoin = gameToJoin;
        this.username = username;
    }

    public String getGameToJoin() {
        return gameToJoin;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestJoinGame{" +
                "gameToJoin='" + gameToJoin + '\'' +
                ", username='" + username + '\'' +
                "} " + super.toString();
    }
}
