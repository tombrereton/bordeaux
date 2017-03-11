package CardGame.Responses;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class ResponseCreateGame extends ResponseProtocol {
    ArrayList<String> gameNames;

    public ResponseCreateGame(int protocolId, int type, int requestSuccess, ArrayList<String> gameNames) {
        super(protocolId, type, requestSuccess);
        this.gameNames = gameNames;
    }

    public ResponseCreateGame(int protocolId, int type, int requestSuccess,
                              ArrayList<String> gameNames, String errorMsg) {
        super(protocolId, type, requestSuccess, errorMsg);
        this.gameNames = gameNames;
    }
}
