package CardGame.Requests;

import CardGame.MessageObject;

/**
 * Created by tom on 06/03/17.
 */
public class RequestSendMessage extends AbstractRequestProtocol {
    private MessageObject messageObject;

    public RequestSendMessage(int protocolId, String userName, String message) {
        super(protocolId, 2); // type 2 means send message
        this.messageObject = new MessageObject(userName, message);
    }


    public MessageObject getMessageObject() {
        return messageObject;
    }

    @Override
    public String toString() {
        return "RequestSendMessage{" +
                "messageObject=" + messageObject +
                "} " + super.toString();
    }
}
