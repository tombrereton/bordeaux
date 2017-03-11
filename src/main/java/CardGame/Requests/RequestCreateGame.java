package CardGame.Requests;

/**
 * Created by tom on 11/03/17.
 */
public class RequestCreateGame extends RequestProtocol {

    public RequestCreateGame(int type) {
        super(type);
    }

    public RequestCreateGame(int protocolId, int type) {
        super(protocolId, type);
    }

}
