package CardGame.Responses;

import static CardGame.ProtocolTypes.REGISTER_USER;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseRegisterUser extends ResponseProtocol {

    public ResponseRegisterUser(int protocolId, int requestSuccess, String errorMsg) {
        super(protocolId, REGISTER_USER, requestSuccess, errorMsg); // type 0 for register
    }

    public ResponseRegisterUser(int protocolId, int requestSuccess) {
        super(protocolId, REGISTER_USER, requestSuccess); // type 0 for register
    }

}
