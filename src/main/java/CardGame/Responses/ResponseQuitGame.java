package CardGame.Responses;

import static CardGame.ProtocolTypes.QUIT_GAME;

/**
 * This class is a response for Quit game. This tells the
 * client whether the user successfully left the game lobby and unsubscribed
 * from the game pushes, or not.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseQuitGame extends ResponseProtocol {

    public ResponseQuitGame(int protocolId, int requestSuccess) {
        super(protocolId, QUIT_GAME, requestSuccess);
    }

    public ResponseQuitGame(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, QUIT_GAME, requestSuccess, errorMsg);
    }
}
