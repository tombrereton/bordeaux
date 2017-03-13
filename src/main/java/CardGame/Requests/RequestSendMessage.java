package CardGame.Requests;

import CardGame.MessageObject;

import static CardGame.ProtocolTypes.SEND_MESSAGE;

/**
 * Created by tom on 06/03/17.
 */
public class RequestSendMessage extends RequestProtocol {
    private MessageObject messageObject;

    public RequestSendMessage(int protocolId, String userName, String message) {
        super(protocolId, SEND_MESSAGE); // type 2 means send message
        this.messageObject = new MessageObject(userName, message);
    }

    public RequestSendMessage(String userName, String message) {
        super(SEND_MESSAGE); // type 2 means send message
        this.messageObject = new MessageObject(userName, message);
    }

    public RequestSendMessage(MessageObject messageObject){
        super(SEND_MESSAGE);
        this.messageObject = messageObject;
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
