package CardGame.Pushes;

import static CardGame.ProtocolTypes.PUSH_PLAYER_BUDGETS;

import java.util.Map;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBudgets extends PushProtocol {
    private Map<String, Integer> playerBudgets;

    public PushPlayerBudgets(Map<String, Integer> playerBudgets) {
        super(PUSH_PLAYER_BUDGETS);
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
