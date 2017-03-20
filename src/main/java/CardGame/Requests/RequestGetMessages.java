package CardGame.Requests;

import static CardGame.ProtocolTypes.GET_MESSAGE;

/**
 * A request for messages, where the offset specifies how many
 * messages it requires.
 *
 * For example, offset = -1 is all messages (first time poll).
 * offset = 5, would get all the messages with index greater than 5 would be sent.
 * Created by tom on 11/03/17.
 */
public class RequestGetMessages extends RequestProtocol {
    private int offset;


    public RequestGetMessages(int offset) {
        super(GET_MESSAGE);
        this.offset = offset;
    }

    public RequestGetMessages(int protocolId, int offset) {
        super(protocolId, GET_MESSAGE);
        this.offset = offset;
    }


    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "RequestGetMessages{" +
                "offset=" + offset +
                "} " + super.toString();
    }
}
