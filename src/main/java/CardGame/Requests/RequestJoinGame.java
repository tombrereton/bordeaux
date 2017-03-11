package CardGame.Requests;

/**
 * Created by tom on 11/03/17.
 */
public class RequestJoinGame extends RequestProtocol {

    public RequestJoinGame(int protocolId, int type) {
        super(protocolId, type);
    }

    public RequestJoinGame(int type) {
        super(type);
    }
}
