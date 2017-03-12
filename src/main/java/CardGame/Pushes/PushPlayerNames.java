package CardGame.Pushes;

import java.util.ArrayList;

import static CardGame.ProtocolTypes.PUSH_PLAYER_NAMES;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerNames extends PushProtocol {
    private ArrayList<String> playerNames;

    public PushPlayerNames(ArrayList<String> playerNames) {
        super(PUSH_PLAYER_NAMES);
        this.playerNames = playerNames;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    @Override
    public String toString() {
        return "PushPlayerNames{" +
                "playerNames=" + playerNames +
                "} " + super.toString();
    }
}
