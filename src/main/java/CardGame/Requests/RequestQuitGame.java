package CardGame.Requests;

import static CardGame.ProtocolTypes.QUIT_GAME;

/**
 * This class is a request to quit the current game the user is playing.
 * This means the user will leave the game lobby and unsubscribe from the pushes
 * for this game.
 *
 * Created by tom on 12/03/17.
 */
public class RequestQuitGame extends RequestProtocol {

    public RequestQuitGame(int protocolId) {
        super(protocolId, QUIT_GAME);
    }

    public RequestQuitGame() {
        super(QUIT_GAME);
    }
}
