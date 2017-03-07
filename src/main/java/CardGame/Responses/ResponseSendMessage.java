package CardGame.Responses;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseSendMessage extends ResponseProtocol {
    public ResponseSendMessage(int protocolId, int requestSuccess) {
        super(protocolId, 2, requestSuccess); // type is 2 for sendMessage
    }
}
