package CardGame.Gui;

import CardGame.GameClient;

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
    public ArrayList<String> listOfGames;
    private String gameName;

    private GameClient client;
    private ScreenFactory screenFactory;

    private JLabel lblLobby;
    private JButton btnBack;
    private JButton btnJoinGame;
    private JButton btnCreateGame;

    // gameList variables
    private DefaultListModel gameNameListModel;
    private JList gameList;
    private int lobbyGamesOffset;

    /**
     * Create the application.
     */
    public LobbyScreen(GameClient client, ScreenFactory screenFactory) {
        // we become an observer
        this.client = client;
        this.screenFactory = screenFactory;
        client.addObserver(this);


        // gameList variables
        this.gameNameListModel = new DefaultListModel();
        this.gameList = new JList(this.gameNameListModel);
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
        gameList.setVisibleRowCount(20);
        gameList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        gameList.setFont(new Font("Soho Std", Font.PLAIN, 18));
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // add listener to gameList
        ListSelectionModel listSelectionModel = gameList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) listSelectionEvent.getSource();

                // we get the index of the selected text in the gameList
                int selectionIndex = listSelectionEvent.getFirstIndex();

                // we get the game name with the index
                ArrayList<String> games = new ArrayList<>(getClientModel().getListOfGames());
                String gameNameSelected = games.get(selectionIndex);

                // we set the game name to the game selected
                setGameName(gameNameSelected);
            }
        });
        add(gameList);

        /**
         * Join button
         */
        btnJoinGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                getClientModel().requestJoinGame(getGameName());
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
                getClientModel().requestCreateGame();
                getClientModel().requestJoinGame(getClientModel().getLoggedInUser().getUserName());
            }
        });
        btnCreateGame.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnCreateGame.setBackground(Color.WHITE);
        add(btnCreateGame);
    }

	public void updateBounds(){
		lblLobby.setBounds(screenFactory.getxOrigin()+391, 10, 242, 34);
		btnBack.setBounds(50, screenFactory.getScreenHeightCurrent()-100, 300, 50);
		gameList.setBounds(50, 50, screenFactory.getScreenWidthCurrent()-120, screenFactory.getScreenHeightCurrent()-160);
		btnJoinGame.setBounds(screenFactory.getScreenWidthCurrent()-370, screenFactory.getScreenHeightCurrent()-100, 300, 50);
		btnCreateGame.setBounds(screenFactory.getxOrigin()+352, screenFactory.getScreenHeightCurrent()-100, 300, 50);
	}

    public ArrayList<String> getListOfGames() {
        return listOfGames;
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

    public void setListOfGames(ArrayList<String> listOfGames) {
        this.listOfGames = listOfGames;
    }

    public DefaultListModel getGameNameListModel() {
        return gameNameListModel;
    }

    private void addToList(ArrayList<String> gamesNames) {

        int clientGameOffset = getClientModel().getListOfGames().size();

        while (lobbyGamesOffset < clientGameOffset){
            ArrayList<String> gameNames = new ArrayList<>(getClientModel().getListOfGames());
            this.gameNameListModel.addElement(gameNames.get(lobbyGamesOffset));

        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameClient) {
            GameClient model = (GameClient) observable;

            // get clientGameOf
            int clientGameOffset = model.getListOfGames().size();

            // add to list
            while (lobbyGamesOffset < clientGameOffset){
                ArrayList<String> gameNames = new ArrayList<>(model.getListOfGames());
                this.gameNameListModel.addElement(gameNames.get(lobbyGamesOffset));
                lobbyGamesOffset++;
            }

        }
    }
}
