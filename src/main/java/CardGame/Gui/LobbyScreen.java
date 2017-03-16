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

import static CardGame.Gui.Screens.HOMESCREEN;

/**
 * Lobby Screen
 * @author Alex
 *
 */
public class LobbyScreen extends JPanel implements ListSelectionListener {
//    public String[] listOfGames;
    public ArrayList<String> listOfGames;
    public DefaultListModel model;
    private ClientModel clientModel;
    private String gameName;

	/**
	 * Create the application.
	 */
	public LobbyScreen(ClientModel clientModel) {
		this.clientModel = clientModel;
		this.listOfGames = new ArrayList<>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(1024,576);
		setLayout(null);
		
		JLabel lblWelcome = new JLabel("Lobby");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(391, 11, 242, 34);
		add(lblWelcome);
		
		/**
		 * back button events
		 */
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().setCurrentScreen(HOMESCREEN);
			}
		});
		btnBack.setBounds(40, 515, 150, 23);
		add(btnBack);

        /**
         * List of games
         */
		JList list = new JList();
		list.setVisibleRowCount(20);
		list.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		list.setFont(new Font("Soho Std", Font.PLAIN, 18));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(model = new DefaultListModel() {
            public int getSize() {
				return listOfGames.size();
			}
			public Object getElementAt(int index) {
				return listOfGames.get(index);
			}
		});
		list.setBounds(40, 56, 946, 444);
		add(list);

		/**
		 * Join button
		 */
		JButton button = new JButton("Join game");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    // todo: create this method and somehow get gameName from list
			    getClientModel().requestJoinGame(getGameName());
			}
		});
		button.setFont(new Font("Soho Std", Font.PLAIN, 16));
		button.setBackground(Color.WHITE);
		button.setBounds(836, 515, 150, 23);
		add(button);

        /**
         * Create game button
         */
        JButton createGameButton = new JButton("Create game");
        createGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: make this show in the list
                // todo: double check this
                listOfGames.add("Test"); //name of game is uername
                model.addElement(listOfGames);
                getClientModel().requestCreateGame();
                //ScreenFactory.setPane(ScreenFactory.frame.lobbyScreen);
            }
        });
        createGameButton.setFont(new Font("Soho Std", Font.PLAIN, 16));
        createGameButton.setBackground(Color.WHITE);
        createGameButton.setBounds(428, 515, 150, 23);
        add(createGameButton);
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

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        ListSelectionModel lsm = (ListSelectionModel) listSelectionEvent.getSource();

        String gameName = listSelectionEvent.toString();
        setGameName(gameName);
    }
}
