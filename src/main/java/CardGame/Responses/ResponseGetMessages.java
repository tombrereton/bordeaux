package CardGame.Responses;

import CardGame.MessageObject;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class ResponseGetMessages extends ResponseProtocol {
    private ArrayList<MessageObject> messages;

    public ResponseGetMessages(int protocolId, int type, int requestSuccess, ArrayList<MessageObject> messages) {
        super(protocolId, type, requestSuccess);
        this.messages = messages;
    }

    public ResponseGetMessages(int protocolId, int type, int requestSuccess, String errorMsg, ArrayList<MessageObject> messages) {
        super(protocolId, type, requestSuccess, errorMsg);
        this.messages = messages;
    }

    public ArrayList<MessageObject> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ResponseGetMessages{" +
                "messages=" + messages +
                "} " + super.toString();
    }
}
