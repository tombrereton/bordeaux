package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.ArrayList;

import static CardGame.ProtocolTypes.PUSH_GAME_NAMES;

/**
 * Created by tom on 11/03/17.
 */
public class PushGameNames extends ResponseProtocol {
    private ArrayList<String> gameNames;

    public PushGameNames(int protocolID, int success, ArrayList<String> gameNames) {
        super(protocolID, PUSH_GAME_NAMES, success);
        this.gameNames = gameNames;
    }

    public ArrayList<String> getGameNames() {
        return gameNames;
    }

    @Override
    public String toString() {
        return "PushGameNames{" +
                "gameNames=" + gameNames +
                "} " + super.toString();
    }
}
