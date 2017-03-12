package CardGame.Pushes;

import CardGame.ProtocolTypes;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class PushGameNames extends PushProtocol {
    private ArrayList<String> gameNames;

    public PushGameNames(ArrayList<String> gameNames) {
        super(ProtocolTypes.PUSH_GAME_NAMES);
        this.gameNames = gameNames;
    }

    @Override
    public String toString() {
        return "PushGameNames{" +
                "gameNames=" + gameNames +
                "} " + super.toString();
    }
}
