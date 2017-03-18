package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayerBets extends RequestProtocol {
    public RequestGetPlayerBets(int protocolId)
    {
        super(protocolId, ProtocolTypes.PUSH_PLAYER_BETS);
    }
    public RequestGetPlayerBets()
    {
        super(ProtocolTypes.PUSH_PLAYER_BETS);
    }
}
