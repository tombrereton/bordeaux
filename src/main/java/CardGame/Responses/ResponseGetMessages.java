package CardGame.Responses;

import CardGame.MessageObject;

import java.util.ArrayList;

import static CardGame.ProtocolTypes.GET_MESSAGE;

/**
 * Created by tom on 11/03/17.
 */
public class ResponseGetMessages extends ResponseProtocol {
    private ArrayList<MessageObject> messages;
    private int offset;

    public ResponseGetMessages(int protocolId, int requestSuccess, ArrayList<MessageObject> messages, int offset) {
        super(protocolId, GET_MESSAGE, requestSuccess);
        this.messages = messages;
        this.offset = offset;
    }

    public ResponseGetMessages(int protocolId, int requestSuccess, ArrayList<MessageObject> messages, String errorMsg) {
        super(protocolId, GET_MESSAGE, requestSuccess, errorMsg);
        this.messages = messages;
    }

    public ArrayList<MessageObject> getMessages() {
        return messages;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "ResponseGetMessages{" +
                "messages=" + messages +
                "} " + super.toString();
    }
}
