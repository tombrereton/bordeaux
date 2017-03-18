package CardGame.Gui;

import CardGame.GameClient;
import CardGame.Responses.ResponseProtocol;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static CardGame.Gui.Screens.HOMESCREEN;

/**
 * Lobby Screen
 *
 * @author Alex
 */
public class LobbyScreen extends JPanel implements Observer {
    private String gameName;

    private GameClient client;
    private ScreenFactory screenFactory;

    private JLabel lblLobby;
    private JButton btnBack;
    private JButton btnJoinGame;
    private JButton btnCreateGame;

    // gameJList variables
    private DefaultListModel gameNameListModel;
    private JList gameJList;
    private int lobbyGamesOffset;

    /**
     * Create the application.
     */
    public LobbyScreen(GameClient client, ScreenFactory screenFactory) {
        // we become an observer
        this.client = client;
        this.screenFactory = screenFactory;
        client.addObserver(this);


        // gameJList variables
        this.gameNameListModel = new DefaultListModel();
        this.gameJList = new JList(this.gameNameListModel);
        this.lobbyGamesOffset = 0;

        lblLobby = new JLabel("Lobby");
        btnBack = new JButton("Back");
        btnJoinGame = new JButton("Join game");
        btnCreateGame = new JButton("Create game");
        setBackground(new Color(46, 139, 87));
        initialize();
        updateBounds();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        lblLobby.setHorizontalAlignment(SwingConstants.CENTER);
        lblLobby.setFont(new Font("Soho Std", Font.PLAIN, 24));
        lblLobby.setForeground(new Color(255, 255, 255));
        add(lblLobby);

        /**
         * back button events
         */
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getClientModel().setCurrentScreen(HOMESCREEN);
            }
        });

        add(btnBack);

        /**
         * List of games
         */
        gameJList.setVisibleRowCount(20);
        gameJList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        gameJList.setFont(new Font("Soho Std", Font.PLAIN, 18));
        gameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // add listener to gameJList
        ListSelectionModel listSelectionModel = gameJList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) listSelectionEvent.getSource();

                // we get the index of the selected text in the gameJList
                int selectionIndex = listSelectionEvent.getFirstIndex();

                // we get the game name with the index
                ArrayList<String> games = new ArrayList<>(getClientModel().getListOfGames());
                if (games.size() != 0 && games.size() > selectionIndex) {
                    String gameNameSelected = games.get(selectionIndex);

                    // we set the game name to the game selected
                    setGameName(gameNameSelected);
                }

            }
        });
        add(gameJList);

        /**
         * Join button
         */
        btnJoinGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ResponseProtocol responseProtocol = getClientModel().requestJoinGame(getGameName());

                // We display the error if not successful
                int success = responseProtocol.getRequestSuccess();
                String errorMsg = responseProtocol.getErrorMsg();
                if (success == 0) {
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnJoinGame.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnJoinGame.setBackground(Color.WHITE);
        add(btnJoinGame);

        /**
         * Create game button
         */
        btnCreateGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // send request to create a game
                ResponseProtocol responseProtocol = getClientModel().requestCreateGame();
                ResponseProtocol responseProtocol1 = getClientModel().requestJoinGame(getClientModel().getLoggedInUser().getUserName());

                // We display the error if not successful
                int success = responseProtocol.getRequestSuccess();
                int success2 = responseProtocol1.getRequestSuccess();
                String errorMsg = responseProtocol.getErrorMsg();
                String errorMsg1 = responseProtocol1.getErrorMsg();

                if (success == 0) {
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else if (success2 == 0) {
                    JOptionPane.showMessageDialog(null, errorMsg1, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }


            }
        });
        btnCreateGame.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnCreateGame.setBackground(Color.WHITE);
        add(btnCreateGame);
    }

    public void updateBounds() {
        lblLobby.setBounds(screenFactory.getxOrigin() + 391, 10, 242, 34);
        btnBack.setBounds(50, screenFactory.getScreenHeightCurrent() - 100, 300, 50);
        gameJList.setBounds(50, 50, screenFactory.getScreenWidthCurrent() - 120, screenFactory.getScreenHeightCurrent() - 160);
        btnJoinGame.setBounds(screenFactory.getScreenWidthCurrent() - 370, screenFactory.getScreenHeightCurrent() - 100, 300, 50);
        btnCreateGame.setBounds(screenFactory.getxOrigin() + 352, screenFactory.getScreenHeightCurrent() - 100, 300, 50);
    }

    public GameClient getClientModel() {
        return client;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public ScreenFactory getScreenFactory() {
        return screenFactory;
    }

    public DefaultListModel getGameNameListModel() {
        return gameNameListModel;
    }

    public void resetGameList() {
        this.lobbyGamesOffset = 0;
        this.getGameNameListModel().clear();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameClient) {
            GameClient model = (GameClient) observable;

            // get clientGameOf
            int clientGameOffset = model.getListOfGames().size();

            ArrayList<String> gameNames = new ArrayList<>(model.getListOfGames());
            // add to list
            while (lobbyGamesOffset < clientGameOffset) {
                this.gameNameListModel.addElement(gameNames.get(lobbyGamesOffset));
                lobbyGamesOffset++;
            }

            // remove game from game list if not in client list of games
            for (int i = 0; i < gameNameListModel.size(); i++) {
                if (!gameNames.contains(gameNameListModel.get(i))) {
                    gameNameListModel.remove(i);
                    lobbyGamesOffset = 0;
                }
            }

        }
    }
}
