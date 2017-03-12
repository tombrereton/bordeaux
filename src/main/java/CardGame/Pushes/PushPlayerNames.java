package CardGame.Pushes;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerNames extends PushProtocol {
    private ArrayList<String> playerNames;

    public PushPlayerNames(int type, ArrayList<String> playerNames) {
        super(type);
        this.playerNames = playerNames;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
}
