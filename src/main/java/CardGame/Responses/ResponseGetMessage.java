package CardGame.Responses;

import CardGame.MessageObject;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class ResponseGetMessage extends ResponseProtocol {
    private ArrayList<MessageObject> messages;

    public ResponseGetMessage(int protocolId, int type, int requestSuccess, ArrayList<MessageObject> messages) {
        super(protocolId, type, requestSuccess);
        this.messages = messages;
    }

    public ResponseGetMessage(int protocolId, int type, int requestSuccess, String errorMsg, ArrayList<MessageObject> messages) {
        super(protocolId, type, requestSuccess, errorMsg);
        this.messages = messages;
    }

    public ArrayList<MessageObject> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ResponseGetMessage{" +
                "messages=" + messages +
                "} " + super.toString();
    }
}
