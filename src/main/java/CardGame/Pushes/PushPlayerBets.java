package CardGame.Pushes;

import java.util.ArrayList;

/**
 * Created by tom on 11/03/17.
 */
public class PushPlayerBets extends PushProtocol {
    ArrayList<Integer> playerBets;

    public PushPlayerBets(int type, ArrayList<Integer> playerBets) {
        super(type);
        this.playerBets = playerBets;
    }

    public ArrayList<Integer> getPlayerBets() {
        return playerBets;
    }
}
