package CardGame.Pushes;

import com.google.gson.Gson;

/**
 * Created by tom on 06/03/17.
 */
public class PushProtocol {

    protected int type;
    private String errorMsg;

    public PushProtocol(int type) {
        this.type = type;
    }

    public PushProtocol(int type, String errorMsg){
        this.type = type;
        this.errorMsg = errorMsg;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PushProtocol{" +
                "type=" + type +
                '}';
    }


    /**
     * We use a method to create a PushProtocol from
     * a string input, where the string must be a json string.
     *
     * @param input
     * @return
     */
    public static PushProtocol decodePush (String input){
        Gson gson = new Gson();
        return gson.fromJson(input, PushProtocol.class);
    }

    /**
     * We use a method to create a json string from
     * the PushProtocol object.
     *
     * @param push
     * @return
     */
    public static String encodePush (PushProtocol push){
        Gson gson = new Gson();
        return gson.toJson(push);
    }
}
