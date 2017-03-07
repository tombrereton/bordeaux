package CardGame.Responses;

import CardGame.ProtocolTypes;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseSendMessage extends ResponseProtocol {

    public ResponseSendMessage(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, ProtocolTypes.SEND_MESSAGE, requestSuccess, errorMsg); // type 2 for sendmessage
    }

    public ResponseSendMessage(int protocolId, int requestSuccess) {
        super(protocolId,ProtocolTypes.SEND_MESSAGE, requestSuccess); // type is 2 for sendMessage
    }
}
