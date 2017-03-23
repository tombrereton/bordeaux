package CardGame.Pushes;

import CardGame.Responses.ResponseProtocol;

import static CardGame.ProtocolTypes.PUSH_ARE_ALL_BETS_PLACED;

/**
 * Created by tom on 11/03/17.
 */
public class PushAreAllBetsPlaced extends ResponseProtocol {
    private boolean allBetsPlaced;

    public PushAreAllBetsPlaced(int protocolID, int success, boolean allBetsPlaced) {
        super(protocolID, PUSH_ARE_ALL_BETS_PLACED, success);
        this.allBetsPlaced = allBetsPlaced;
    }

    public boolean isAllBetsPlaced() {
        return allBetsPlaced;
    }

    @Override
    public String toString() {
        return "PushAreAllPlayersFinished{" +
                "allBetsPlaced=" + allBetsPlaced +
                "} " + super.toString();
    }
}
