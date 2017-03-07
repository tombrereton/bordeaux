package CardGame.Responses;

/**
 * Created by tom on 06/03/17.
 */
public abstract class AbstractResponseProtocol {

    private int protocolId;
    protected int type;
    protected int requestSuccess; // 0 not success, 1 for success
//    protected String errorMsg;

    public AbstractResponseProtocol(int protocolId, int type, int requestSuccess) {
        this.protocolId = protocolId;
        this.type = type;
        this.requestSuccess = requestSuccess;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public int getType() {
        return type;
    }

    public int getRequestSuccess() {
        return requestSuccess;
    }

    @Override
    public String toString() {
        return "AbstractResponseProtocol{" +
                "protocolId=" + protocolId +
                ", type=" + type +
                ", requestSuccess=" + requestSuccess +
                '}';
    }
}
