package CardGame.Responses;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseLoginUser extends ResponseProtocol {
    public ResponseLoginUser(int protocolId, int requestSuccess) {
        super(protocolId, 1, requestSuccess); // type is 1 for login
    }
}
