package CardGame.Pushes;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBudgets extends PushProtocol {
    ArrayList<Integer> playerBudgets;

    public PushPlayerBudgets(int type, ArrayList<Integer> playerBudgets) {
        super(type);
        this.playerBudgets = playerBudgets;
    }

    public ArrayList<Integer> getPlayerBudgets() {
        return playerBudgets;
    }
}
