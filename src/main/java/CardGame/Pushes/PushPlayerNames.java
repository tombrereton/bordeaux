package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.ArrayList;

import static CardGame.ProtocolTypes.PUSH_PLAYER_NAMES;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerNames extends ResponseProtocol {
    private ArrayList<String> playerNames;

    public PushPlayerNames(int protocolID, int success, ArrayList<String> playerNames) {
        super(protocolID, PUSH_PLAYER_NAMES, success);
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
