package CardGame.Requests;

import com.google.gson.Gson;

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
        this.protocolId = (int) (Math.random() * 99999) + 1;
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

    /**
     * We use a method to create a RequestProtocol from
     * a string input, where the string must be a json string.
     *
     * @param input
     * @return
     */
    public static RequestProtocol decodeRequest(String input){
        Gson gson = new Gson();
        return gson.fromJson(input, RequestProtocol.class);
    }

    /**
     * We use a method to create a json string from
     * the requestProtocol object.
     *
     * @param request
     * @return
     */
    public static String encodeRequest(RequestProtocol request){
        Gson gson = new Gson();
        return gson.toJson(request);
    }
}
