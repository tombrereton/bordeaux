package CardGame.Responses;

/**
 * This class is a response for Quit game. This tells the
 * client whether the user successfully left the game lobby and unsubscribed
 * from the game pushes, or not.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseQuitGame extends ResponseProtocol {
    public ResponseQuitGame(int protocolId, int type, int requestSuccess) {
        super(protocolId, type, requestSuccess);
    }

    public ResponseQuitGame(int protocolId, int type, int requestSuccess, String errorMsg) {
        super(protocolId, type, requestSuccess, errorMsg);
    }
}
