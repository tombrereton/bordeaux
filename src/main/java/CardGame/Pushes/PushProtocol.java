package CardGame.Pushes;

/**
 * Created by tom on 06/03/17.
 */
public class PushProtocol {

    protected int type;

    public PushProtocol(int type) {
        this.type = type;
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
}
