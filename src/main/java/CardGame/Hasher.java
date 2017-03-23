package CardGame;

/**
 * Created by tom on 22/03/17.
 */
public class Hasher {

    /**
     * This method hashes the password.
     *
     * @param password
     * @return
     */
    public static String hashPassword(String password) {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return sha256hex;
    }
}
