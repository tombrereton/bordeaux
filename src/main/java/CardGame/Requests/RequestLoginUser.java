package CardGame.Requests;
import CardGame.User;

/**
 * Created by tom on 06/03/17.
 */
public class RequestLoginUser extends AbstractRequestProtocol {
    private User user;

    public RequestLoginUser(int protocolId, User user) {
        super(protocolId, 1); // type 1 is for login
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
