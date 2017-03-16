package CardGame.Gui;

import CardGame.ClientModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Lobby Screen
 * @author Alex
 *
 */
public class LobbyScreen extends JPanel{
//    public String[] listOfGames;
    public ArrayList<String> listOfGames;
    public DefaultListModel model;

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JLabel lblLobby = new JLabel("Lobby");
	private JButton btnBack = new JButton("Back");
	private JList list = new JList();
	private JButton btnJoinGame = new JButton("Join game");
	private JButton btnCreateGame = new JButton("Create game");

	/**
	 * Create the application.
	 */
	public LobbyScreen() {
		initialize();
		this.listOfGames = new ArrayList<>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		lblLobby.setHorizontalAlignment(SwingConstants.CENTER);
		lblLobby.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblLobby.setForeground(new Color(255, 255, 255));
		lblLobby.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		add(lblLobby);
		
		/**
		 * back button events
		 */
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.homeScreen);
			}
		});
		btnBack.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+515, 150, 23);
		add(btnBack);

        /**
         * List of games
         */
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
		list.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+56, 946, 444);
		add(list);

		/**
		 * Join button
		 */
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.gameScreen);
			}
		});
		btnJoinGame.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnJoinGame.setBackground(Color.WHITE);
		btnJoinGame.setBounds(screenFactory.getxOrigin()+836, screenFactory.getyOrigin()+515, 150, 23);
		add(btnJoinGame);

        /**
         * Create game button
         */
		btnCreateGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: make this show in the list
                listOfGames.add("Test"); //name of game is uername
                model.addElement(listOfGames);
                repaint();
                revalidate();
                //ScreenFactory.setPane(ScreenFactory.frame.lobbyScreen);
            }
        });
		btnCreateGame.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnCreateGame.setBackground(Color.WHITE);
		btnCreateGame.setBounds(screenFactory.getxOrigin()+428, screenFactory.getyOrigin()+515, 150, 23);
        add(btnCreateGame);
	}

	public void updateBounds(){
		lblLobby.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		btnBack.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+515, 150, 23);
		list.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+56, 946, 444);
		btnJoinGame.setBounds(screenFactory.getxOrigin()+836, screenFactory.getyOrigin()+515, 150, 23);
		btnCreateGame.setBounds(screenFactory.getxOrigin()+428, screenFactory.getyOrigin()+515, 150, 23);
	}
}
