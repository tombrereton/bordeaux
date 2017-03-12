package CardGame.Responses;

/**
 * This class is a response to the Join Game Request.
 *
 * If requestSuccess is 1, then the user has successfully joined the
 * game and therefore has been added to the gameLobby. This means
 * the user is now subscribed to all pushes for that gamelobby.
 *
 * Created by tom on 12/03/17.
 */
public class ResponseJoinGame extends ResponseProtocol {

    public ResponseJoinGame(int protocolId, int type, int requestSuccess) {
        super(protocolId, type, requestSuccess);
    }

    public ResponseJoinGame(int protocolId, int type, int requestSuccess, String errorMsg) {
        super(protocolId, type, requestSuccess, errorMsg);
    }
}
