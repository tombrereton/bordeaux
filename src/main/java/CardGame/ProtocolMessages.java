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
    public static final String PASSWORD_MISMATCH = "username or password does not match.";
    public static final String EMPTY_INSERT = "cannot insert empty user into database.";
    public static final String REGISTERED = "successfully registered user";
    public static final String NO_CLIENTS = "no clients connected.";
    public static final String NOT_LOGGED_IN = "user not logged in.";
    public static final String ALREADY_LOGGED_IN = "user already logged in.";
    public static final String GAME_ALREADY_EXISTS = "game already exists.";
    public static final String USERNAME_MISMATCH = "username does not match logged in user.";
    public static final String NO_GAMES = "no games exist.";
    public static final String NO_GAME = "game requested does not exist.";
    public static final String NO_GAME_JOINED = "user is not in a game.";
    public static final String GAME_FULL = "the game is full.";
    public static final String EMPTY_MSG = "message sent is empty or null.";
    public static final String EMPTY = "protocol sent contains empty or null elements.";
    public static final String BET_NOT_IN_BUDGET = "bet not within player budget.";
    public static final String BET_TOO_SMALL = "bet must be greater than or equal to 5.";
    public static final String WRONG_OFFSET = "incorrect offset for messages.";
    public static final String NOT_FINISHED_ROUND = "player has not finished their round.";
    public static final String FINISHED_ROUND = "player has already finished their round.";
    public static final String NO_BET = "player has not placed a bet.";
    public static final String ALREADY_STANDING = "player already standing.";
    public static final String PLAYER_BET_PLACED = "you have already bet.";
    public static final String PLAYER_BUST = "you are bust. Please wait for other players to finish or place a bet.";
    public static final String PLAYER_STAND_ALL = "all players are standing."; // need to be fixed
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

}
