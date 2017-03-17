package CardGame.Gui;

import CardGame.ClientModel;

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
import java.util.concurrent.CopyOnWriteArrayList;

import static CardGame.Gui.Screens.HOMESCREEN;

/**
 * Lobby Screen
 *
 * @author Alex
 */
public class LobbyScreen extends JPanel implements Observer {
    //    public String[] listOfGames;
    public ArrayList<String> listOfGames;
    public DefaultListModel defaultListModel;
    private String gameName;

    private ClientModel clientModel;
    private ScreenFactory screenFactory;

    private JLabel lblLobby;
    private JButton btnBack;
    private JList list;
    private JButton btnJoinGame;
    private JButton btnCreateGame;

    /**
     * Create the application.
     */
    public LobbyScreen(ClientModel clientModel, ScreenFactory screenFactory) {
        // we become an observer
        this.clientModel = clientModel;
        clientModel.addObserver(this);


        this.screenFactory = screenFactory;
        this.listOfGames = new ArrayList<>();
        lblLobby = new JLabel("Lobby");
        btnBack = new JButton("Back");
        list = new JList();
        btnJoinGame = new JButton("Join game");
        btnCreateGame = new JButton("Create game");
        setBackground(new Color(46, 139, 87));
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        lblLobby.setHorizontalAlignment(SwingConstants.CENTER);
        lblLobby.setFont(new Font("Soho Std", Font.PLAIN, 24));
        lblLobby.setForeground(new Color(255, 255, 255));
        lblLobby.setBounds(screenFactory.getxOrigin() + 391, screenFactory.getyOrigin() + 11, 242, 34);
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
        btnBack.setBounds(screenFactory.getxOrigin() + 40, screenFactory.getyOrigin() + 500, 150, 23);
        add(btnBack);

        /**
         * List of games
         */
        list.setVisibleRowCount(20);
        list.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        list.setFont(new Font("Soho Std", Font.PLAIN, 18));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(defaultListModel = new DefaultListModel() {
            public int getSize() {
                return listOfGames.size();
            }

            public Object getElementAt(int index) {
                return listOfGames.get(index);
            }
        });
        list.setBounds(screenFactory.getxOrigin() + 40, screenFactory.getyOrigin() + 56, 946, 444);

        // add listener to list
        ListSelectionModel listSelectionModel = list.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) listSelectionEvent.getSource();

                // we get the index of the selected text in the list
                int selectionIndex = listSelectionEvent.getFirstIndex();

                // we get the game name with the index
                String gameNameSelected = getListOfGames().get(selectionIndex);

                // we set the game name to the game selected
                setGameName(gameNameSelected);
            }
        });
        add(list);

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
        btnJoinGame.setBounds(screenFactory.getxOrigin() + 836, screenFactory.getyOrigin() + 500, 150, 23);
        add(btnJoinGame);

        /**
         * Create game button
         */
        btnCreateGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // we add the list of games to the list in the GUI
                getClientModel().requestCreateGame();
                getListOfGames().remove(getClientModel().getListOfGames());
                getListOfGames().addAll(getClientModel().getListOfGames());
                defaultListModel.removeAllElements();
                defaultListModel.addElement(getListOfGames());
            }
        });
        btnCreateGame.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnCreateGame.setBackground(Color.WHITE);
        btnCreateGame.setBounds(screenFactory.getxOrigin() + 428, screenFactory.getyOrigin() + 500, 150, 23);
        add(btnCreateGame);


    }

    public void updateBounds() {
        lblLobby.setBounds(screenFactory.getxOrigin() + 391, screenFactory.getyOrigin() + 11, 242, 34);
        btnBack.setBounds(screenFactory.getxOrigin() + 40, screenFactory.getyOrigin() + 500, 150, 23);
        list.setBounds(screenFactory.getxOrigin() + 40, screenFactory.getyOrigin() + 56, 946, 400);
        btnJoinGame.setBounds(screenFactory.getxOrigin() + 836, screenFactory.getyOrigin() + 500, 150, 23);
        btnCreateGame.setBounds(screenFactory.getxOrigin() + 428, screenFactory.getyOrigin() + 500, 150, 23);
    }

    public ArrayList<String> getListOfGames() {
        return listOfGames;
    }

    public ClientModel getClientModel() {
        return clientModel;
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

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ClientModel) {
            ClientModel model = (ClientModel) observable;

            CopyOnWriteArrayList<String> gameNameList = model.getListOfGames();

            getListOfGames().remove(gameNameList);
            getListOfGames().addAll(gameNameList);
            defaultListModel.removeAllElements();
            defaultListModel.addElement(getListOfGames());
        }
    }
}
