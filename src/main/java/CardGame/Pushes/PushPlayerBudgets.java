package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import java.util.Map;

import static CardGame.ProtocolTypes.PUSH_PLAYER_BUDGETS;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBudgets extends ResponseProtocol {
    private Map<String, Integer> playerBudgets;

    public PushPlayerBudgets(int protocolID, int success, Map<String, Integer> playerBudgets) {
        super(protocolID, PUSH_PLAYER_BUDGETS, success);
        this.playerBudgets = playerBudgets;
    }

    public Map<String, Integer> getPlayerBudgets() {
        return playerBudgets;
    }

    @Override
    public String toString() {
        return "PushPlayerBudgets{" +
                "playerBudgets=" + playerBudgets +
                "} " + super.toString();
    }
}
