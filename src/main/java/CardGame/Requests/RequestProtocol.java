package CardGame.Requests;

/**
 * Created by tom on 06/03/17.
 */
public class RequestProtocol {
    private int protocolId;
    protected int type;

    public RequestProtocol(int protocolId, int type) {
        this.protocolId = protocolId;
        this.type = type;
    }

    public RequestProtocol(int type) {
        this.protocolId = (int) (Math.random() * 1000) + 1;
        this.type = type;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RequestProtocol{" +
                "protocolId=" + protocolId +
                ", type=" + type +
                '}';
    }
}
