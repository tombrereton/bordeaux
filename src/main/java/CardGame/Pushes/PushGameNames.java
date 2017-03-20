package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Set;

import static CardGame.ProtocolTypes.PUSH_GAME_NAMES;

/**
 * Created by tom on 11/03/17.
 */
public class PushGameNames extends ResponseProtocol {
    private Set<String> gameNames;

    public PushGameNames(int protocolID, int success, Set<String> gameNames) {
        super(protocolID, PUSH_GAME_NAMES, success);
        this.gameNames = gameNames;
    }

    public Set<String> getGameNames() {
        return gameNames;
    }

    @Override
    public String toString() {
        return "PushGameNames{" +
                "gameNames=" + gameNames +
                "} " + super.toString();
    }
}
