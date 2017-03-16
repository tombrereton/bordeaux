package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import static CardGame.ProtocolMessages.SUCCESS;

/**
 * Created by tom on 06/03/17.
 */
public class PushProtocol extends ResponseProtocol{


    public PushProtocol(int type) {
        super(0, type, SUCCESS);
    }

    public PushProtocol(int type, String errorMsg) {
        super(0, type, SUCCESS, errorMsg);
    }
}
