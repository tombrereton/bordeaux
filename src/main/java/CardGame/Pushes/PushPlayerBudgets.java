package CardGame.Pushes;

import java.util.Map;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBudgets extends PushProtocol {
    private Map<String, Integer> playerBudgets;

    public PushPlayerBudgets(int type, Map<String, Integer> playerBudgets) {
        super(type);
        this.playerBudgets = playerBudgets;
    }

    public Map<String, Integer> getPlayerBudgets() {
        return playerBudgets;
    }
}
