package CardGame.Pushes;

import CardGame.ClientThread;
import CardGame.GameEngine.GameLobby;
import CardGame.GameEngine.Hand;
import CardGame.GameEngine.Player;
import CardGame.User;

import java.util.HashMap;
import java.util.Map;

import static CardGame.ProtocolMessages.UNKNOWN_ERROR;
import static CardGame.ProtocolTypes.*;

/**
 * This class creates the appropriate push protocol given the
 * type and clienThread.
 *
 * Created by tom on 12/03/17.
 */
public class PushFactory {

    public PushProtocol createPush(int type, ClientThread clientThread) {
        PushProtocol push = null;

        GameLobby game = getGame(clientThread);
        Map<String, Hand> playerHands = new HashMap<>();

        switch (type) {
            case PUSH_GAME_NAMES:
                return new PushGameNames(clientThread.getGameNames());
            case PUSH_PLAYER_HANDS:

                for(Player player: game.getPlayers()){
                    playerHands.put(player.getUsername(), player.getPlayerHand());
                }

                return new PushPlayerHands(playerHands);
            case PUSH_PLAYER_BUDGETS:

                Map<String, Integer> playerBudgets = new HashMap<>();


                return new PushPlayerBudgets(playerBudgets);
            default:
                return new PushProtocol(type, UNKNOWN_ERROR);
        }
    }

    private GameLobby getGame(ClientThread clientThread) {
        User user = clientThread.getLoggedInUser();
        return clientThread.getGame(user);
    }
}