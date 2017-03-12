package CardGame.Requests;

/**
 * This class is a request to quit the current game the user is playing.
 * This means the user will leave the game lobby and unsubscribe from the pushes
 * for this game.
 *
 * Created by tom on 12/03/17.
 */
public class RequestQuitGame extends RequestProtocol {

    public RequestQuitGame(int protocolId, int type) {
        super(protocolId, type);
    }

    public RequestQuitGame(int type) {
        super(type);
    }
}
