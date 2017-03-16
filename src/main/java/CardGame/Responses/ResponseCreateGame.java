package CardGame.Responses;

import static CardGame.ProtocolTypes.CREATE_GAME;

/**
 * Created by tom on 11/03/17.
 */
public class ResponseCreateGame extends ResponseProtocol {
    private String gameName;

    public ResponseCreateGame(int protocolId, int requestSuccess, String gameName) {
        super(protocolId, CREATE_GAME, requestSuccess);
        this.gameName = gameName;
    }

    public ResponseCreateGame(int protocolId, int requestSuccess,
                              String gameName, String errorMsg) {
        super(protocolId, CREATE_GAME, requestSuccess, errorMsg);
        this.gameName = gameName;
    }
    
    public String getGameName(){
    	return gameName;
    }
    
}
