package CardGame.Responses;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseProtocol {

    private int protocolId;
    protected int type;
    protected int requestSuccess; // 0 not success, 1 for success
    protected String errorMsg; // standardised description of error

    public ResponseProtocol(int protocolId, int type, int requestSuccess) {
        this.protocolId = protocolId;
        this.type = type;
        this.requestSuccess = requestSuccess;
        this.errorMsg = "";
    }

    public ResponseProtocol(int protocolId, int type, int requestSuccess, String errorMsg) {
        this.protocolId = protocolId;
        this.type = type;
        this.requestSuccess = requestSuccess;
        this.errorMsg = errorMsg;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return "ResponseProtocol{" +
                "protocolId=" + protocolId +
                ", type=" + type +
                ", requestSuccess=" + requestSuccess +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
