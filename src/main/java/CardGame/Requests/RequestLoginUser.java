package CardGame.Requests;
import CardGame.ProtocolTypes;
import CardGame.User;

/**
 * Created by tom on 06/03/17.
 */
public class RequestLoginUser extends RequestProtocol {
    private User user;

    public RequestLoginUser(int protocolId, User user) {
        super(protocolId, ProtocolTypes.LOGIN_USER); // type 1 is for login
        this.user = user;
    }

    public RequestLoginUser(User user) {
        super(ProtocolTypes.LOGIN_USER); // type 1 is for login
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "RequestLoginUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
