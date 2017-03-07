package CardGame.Requests;
import CardGame.ProtocolTypes;
import CardGame.User;

/**
 * Created by tom on 06/03/17.
 */
public class RequestRegisterUser extends RequestProtocol {
    private User user;


    public RequestRegisterUser(int protocolId, User user) {
        super(protocolId, ProtocolTypes.REGISTER_USER); // type 0 is register
        this.user = user;
    }

    public RequestRegisterUser(User user) {
        super(ProtocolTypes.REGISTER_USER); // type 0 is register
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
