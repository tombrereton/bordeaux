package CardGame.Gui;

import CardGame.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.*;

/**
 * Home Screen
 * @author Alex
 *
 */
public class HomeScreen extends JPanel {

	private GameClient client;
	private ScreenFactory screenFactory;

	private JButton btnLogout;
	private String username;
	private JLabel lblWelcome;
	private JButton btnGoToLobby;
	private JButton btnStatistics;
	private JButton btnSettings;
	private JLabel lblCredits;
	private JLabel lblGamesWon;
	private JLabel lblGamesLost;
	private JLabel lblGamesPlayed;

	/**
	 * Create the application.
	 */
	public HomeScreen(GameClient client, ScreenFactory screenFactory) {
		this.screenFactory = screenFactory;
		this.client = client;
		username = client.getLoggedInUser().getUserName();
		lblWelcome = new JLabel("Welcome " + username);
        btnLogout = new JButton("Logout");
        btnGoToLobby = new JButton("Go To Lobby");
        btnStatistics = new JButton("Statistics");
        btnSettings = new JButton("Settings");
        lblCredits = new JLabel("Credits:");
        lblGamesWon = new JLabel("Games Won:");
        lblGamesLost = new JLabel("Games Lost:");
        lblGamesPlayed = new JLabel("Games Played:");
        setBackground(new Color(46, 139, 87));
        initialize();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/**
		 * button events for the logout button
		 */
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().requestLogOut();
			}
		});
		btnLogout.setBounds(screenFactory.getxOrigin()+886, screenFactory.getyOrigin()+7, 104, 23);
		add(btnLogout);
		
		/**
		 * Title label
		 */
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		add(lblWelcome);
		
		/**
		 * Button to go to lobby panel.
		 */
		btnGoToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().setCurrentScreen(LOBBYSCREEN);
			}
		});
		btnGoToLobby.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnGoToLobby.setBackground(Color.WHITE);
		btnGoToLobby.setBounds(screenFactory.getxOrigin()+148, screenFactory.getyOrigin()+174, 165, 34);
		add(btnGoToLobby);
		
		/**
		 * Button to go to stats panel.
		 */
		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().setCurrentScreen(STATISTICSSCREEN);
			}
		});
		btnStatistics.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnStatistics.setBackground(Color.WHITE);
		btnStatistics.setBounds(screenFactory.getxOrigin()+148, screenFactory.getyOrigin()+271, 165, 34);
		add(btnStatistics);
		
		/**
		 * Button to go to settings panel.
		 */
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().setCurrentScreen(SETTINGSSCREEN);
			}
		});
		btnSettings.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnSettings.setBackground(Color.WHITE);
		btnSettings.setBounds(screenFactory.getxOrigin()+148, screenFactory.getyOrigin()+368, 165, 34);
		add(btnSettings);

		lblCredits.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblCredits.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+191, 225, 34);
		add(lblCredits);

		lblGamesWon.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesWon.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+300, 225, 34);
		add(lblGamesWon);

		lblGamesLost.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesLost.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+352, 225, 34);
		add(lblGamesLost);

		lblGamesPlayed.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesPlayed.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+243, 225, 34);
		add(lblGamesPlayed);
	}

	public void updateBounds(){
		btnLogout.setBounds(screenFactory.getxOrigin()+886, screenFactory.getyOrigin()+7, 104, 23);
		lblWelcome.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		btnGoToLobby.setBounds(screenFactory.getxOrigin()+148, screenFactory.getyOrigin()+174, 165, 34);
		btnStatistics.setBounds(screenFactory.getxOrigin()+148, screenFactory.getyOrigin()+271, 165, 34);
		btnSettings.setBounds(screenFactory.getxOrigin()+148, screenFactory.getyOrigin()+368, 165, 34);
		lblCredits.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+191, 225, 34);
		lblGamesWon.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+300, 225, 34);
		lblGamesLost.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+352, 225, 34);
		lblGamesPlayed.setBounds(screenFactory.getxOrigin()+560, screenFactory.getyOrigin()+243, 225, 34);
	}

	public GameClient getClientModel() {
		return client;
	}
}
