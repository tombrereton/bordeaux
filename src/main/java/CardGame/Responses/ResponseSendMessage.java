package CardGame.Responses;

import static CardGame.ProtocolTypes.SEND_MESSAGE;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseSendMessage extends ResponseProtocol {

    public ResponseSendMessage(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, SEND_MESSAGE, requestSuccess, errorMsg); // type 2 for sendmessage
    }

    public ResponseSendMessage(int protocolId, int requestSuccess) {
        super(protocolId, SEND_MESSAGE, requestSuccess); // type is 2 for addToMessageQueue
    }
}
