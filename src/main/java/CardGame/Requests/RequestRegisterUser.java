package CardGame.Requests;
import CardGame.User;

/**
 * Created by tom on 06/03/17.
 */
public class RequestRegisterUser extends RequestProtocol {
    private User user;


    public RequestRegisterUser(int protocolId, User user) {
        super(protocolId, 0); // type 0 is register
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
