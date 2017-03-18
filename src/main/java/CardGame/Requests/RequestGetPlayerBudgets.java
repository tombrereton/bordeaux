package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayerBudgets extends RequestProtocol {
    public RequestGetPlayerBudgets(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_PLAYER_BUDGETS);
    }

    public RequestGetPlayerBudgets() {
        super(ProtocolTypes.PUSH_PLAYER_BUDGETS);
    }
}
