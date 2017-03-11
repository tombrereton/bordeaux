package CardGame.Pushes;

/**
 * Created by tom on 11/03/17.
 */
public class PushAreAllPlayersFinished extends PushProtocol {
    private boolean playersFinished;

    public PushAreAllPlayersFinished(int type, boolean playersFinished) {
        super(type);
        this.playersFinished = playersFinished;
    }

    public boolean isPlayersFinished() {
        return playersFinished;
    }
}
