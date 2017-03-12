package CardGame.Requests;
import CardGame.User;

import static CardGame.ProtocolTypes.REGISTER_USER;

/**
 * Created by tom on 06/03/17.
 */
public class RequestRegisterUser extends RequestProtocol {
    private User user;


    public RequestRegisterUser(int protocolId, User user) {
        super(protocolId, REGISTER_USER); // type 0 is register
        this.user = user;
    }

    public RequestRegisterUser(User user) {
        super(REGISTER_USER); // type 0 is register
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "RequestRegisterUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
