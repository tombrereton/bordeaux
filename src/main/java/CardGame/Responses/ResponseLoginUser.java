package CardGame.Responses;

import CardGame.ProtocolTypes;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseLoginUser extends ResponseProtocol {

    public ResponseLoginUser(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, ProtocolTypes.LOGIN_USER, requestSuccess, errorMsg); // type 1 for login
    }

    public ResponseLoginUser(int protocolId, int requestSuccess) {
        super(protocolId, ProtocolTypes.LOGIN_USER, requestSuccess); // type is 1 for login
    }
}
