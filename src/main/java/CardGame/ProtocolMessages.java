package CardGame;

/**
 * This class contains error messages for the responses
 * to the requests. The responses can only contain one
 * of these error messages or none.
 *
 * Each error message shall be lower case and terminated
 * with a period.
 *
 * Created by tom on 07/03/17.
 */
public class ProtocolMessages {
    public static final String UNKNOWN_ERROR = "failed to recognise protocol; unknown handle error.";
    public static final String DUPE_USERNAME = "duplicate username in database.";
    public static final String NON_EXIST = "does not exist.";
    public static final String EMPTY_INSERT = "cannot insert empty object into database.";
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

}
