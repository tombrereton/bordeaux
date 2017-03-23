package CardGame;

/**
 * This class contains the types for the
 * request and response protocol messages.
 *
 * Created by tom on 07/03/17.
 */
public class ProtocolTypes {
    public static final int REGISTER_USER = 0;
    public static final int LOGIN_USER = 1;
    public static final int SEND_MESSAGE = 2;
    public static final int GET_MESSAGE = 3;
    public static final int CLIENT_ID = 4;
    public static final int LOG_OUT_USER = 5;
    public static final int CREATE_GAME = 20;
    public static final int JOIN_GAME = 21;
    public static final int QUIT_GAME = 22;
    public static final int BET = 23;
    public static final int HIT = 24;
    public static final int DOUBLE = 25;
    public static final int STAND = 26;
    public static final int FOLD = 27;
    public static final int PUSH_GAME_NAMES = 50;
    public static final int PUSH_PLAYER_HANDS = 51;
    public static final int PUSH_PLAYER_NAMES = 52;
    public static final int PUSH_PLAYER_BETS = 53;
    public static final int PUSH_PLAYER_BUDGETS = 54;
    public static final int PUSH_DEALER_HAND = 55;
    public static final int PUSH_PLAYERS_FINISHED = 56;
    public static final int PUSH_PLAYERS_WON = 57;
    public static final int PUSH_PLAYERS_BUST = 58;
    public static final int PUSH_PLAYERS_STAND = 59;
    public static final int PUSH_ARE_PLAYERS_FINISHED = 60;
    public static final int PUSH_ARE_ALL_BETS_PLACED = 61;
    public static final int UNKNOWN_TYPE = 99;
}
