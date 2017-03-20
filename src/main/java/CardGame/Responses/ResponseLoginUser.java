package CardGame.Responses;

import CardGame.ProtocolTypes;
import CardGame.User;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseLoginUser extends ResponseProtocol {
    private User user;

    public ResponseLoginUser(int protocolId, int requestSuccess, User user) {
        super(protocolId, ProtocolTypes.LOGIN_USER, requestSuccess); // type is 1 for login
        this.user = user;
    }

    public ResponseLoginUser(int protocolId, int requestSuccess, User user, String errorMsg) {
        super(protocolId, ProtocolTypes.LOGIN_USER, requestSuccess, errorMsg); // type 1 for login
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
