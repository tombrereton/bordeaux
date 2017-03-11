package CardGame.Responses;

/**
 * Created by tom on 11/03/17.
 */
public class ResponseCreateGame extends ResponseProtocol {
    String gameName;

    public ResponseCreateGame(int protocolId, int type, int requestSuccess, String gameName) {
        super(protocolId, type, requestSuccess);
        this.gameName = gameName;
    }

    public ResponseCreateGame(int protocolId, int type, int requestSuccess,
                              String gameName, String errorMsg) {
        super(protocolId, type, requestSuccess, errorMsg);
        this.gameName = gameName;
    }
}
