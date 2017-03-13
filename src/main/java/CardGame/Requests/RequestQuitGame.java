package CardGame.Requests;

import static CardGame.ProtocolTypes.QUIT_GAME;

/**
 * This class is a request to quit the current game the user is playing.
 * This means the user will leave the game lobby and unsubscribe from the pushes
 * for this game.
 *
 * Created by tom on 12/03/17.
 */
public class RequestQuitGame extends RequestProtocol {
    private String gameToQuit;
    private String username;

    public RequestQuitGame(int protocolId, String gameToQuit, String username) {
        super(protocolId, QUIT_GAME);
        this.gameToQuit = gameToQuit;
        this.username = username;
    }

    public RequestQuitGame(String gameToQuit, String username) {
        super(QUIT_GAME);
        this.gameToQuit = gameToQuit;
        this.username = username;
    }

    public String getGameToQuit() {
        return gameToQuit;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RequestQuitGame{" +
                "gameToQuit='" + gameToQuit + '\'' +
                ", username='" + username + '\'' +
                "} " + super.toString();
    }
}
