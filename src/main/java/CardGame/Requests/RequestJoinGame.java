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

    public RequestJoinGame(int protocolId) {
        super(protocolId, JOIN_GAME);
    }

    public RequestJoinGame() {
        super(JOIN_GAME);
    }
}
