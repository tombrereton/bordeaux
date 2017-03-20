package CardGame.Requests;

import CardGame.ProtocolTypes;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayersStand extends RequestProtocol {
    public RequestGetPlayersStand(int protocolId) {
        super(protocolId, ProtocolTypes.PUSH_PLAYERS_STAND);
    }

    public RequestGetPlayersStand() {
        super(ProtocolTypes.PUSH_PLAYERS_STAND);
    }
}
