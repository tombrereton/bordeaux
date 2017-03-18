package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Set;

import static CardGame.ProtocolTypes.PUSH_PLAYER_NAMES;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerNames extends ResponseProtocol {
    private Set<String> playerNames;

    public PushPlayerNames(int protocolID, int success, Set<String> playerNames) {
        super(protocolID, PUSH_PLAYER_NAMES, success);
        this.playerNames = playerNames;
    }

    public Set<String> getPlayerNames() {
        return playerNames;
    }

    @Override
    public String toString() {
        return "PushPlayerNames{" +
                "playerNames=" + playerNames +
                "} " + super.toString();
    }
}
