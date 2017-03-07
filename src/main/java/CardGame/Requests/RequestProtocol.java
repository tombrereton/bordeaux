package CardGame.Requests;

/**
 * Created by tom on 06/03/17.
 */
public class RequestProtocol {
    private int protocolId;
    // TODO: define type with another class which uses final
    protected int type;


    public RequestProtocol(int protocolId, int type) {
        this.protocolId = protocolId;
        this.type = type;
    }

    // TODO: implement cons with rand for protocolID

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
