package CardGame.Pushes;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class PushGameNames extends PushProtocol {
    private ArrayList<String> gameNames;

    public PushGameNames(int type, ArrayList<String> gameNames) {
        super(type);
        this.gameNames = gameNames;
    }
}
