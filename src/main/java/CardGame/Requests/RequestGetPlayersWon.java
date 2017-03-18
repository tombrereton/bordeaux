package CardGame.Requests;

import CardGame.ProtocolTypes;

import static CardGame.ProtocolTypes.PUSH_PLAYERS_WON;

/**
 * Created by ivan on 2017/3/17.
 */
public class RequestGetPlayersWon extends RequestProtocol {
    public RequestGetPlayersWon(int protocolId, int type) {
        super(protocolId, PUSH_PLAYERS_WON);
    }

    public RequestGetPlayersWon() {
        super(PUSH_PLAYERS_WON);
    }
}
