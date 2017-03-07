package CardGame.Responses;

/**
 * Created by tom on 06/03/17.
 */
public class ResponseRegisterUser extends AbstractResponseProtocol {
    public ResponseRegisterUser(int protocolId, int requestSuccess) {
        super(protocolId, 0, requestSuccess); // type 0 for register
    }

}
