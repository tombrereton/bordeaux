package CardGame.Gui;

import CardGame.ClientModel;

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

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JButton btnLogout = new JButton("Logout");
	private String username;
	private JLabel lblWelcome; //get the username
	private JButton btnGoToLobby = new JButton("Go To Lobby");
	private JButton btnStatistics = new JButton("Statistics");
	private JButton btnSettings = new JButton("Settings");
	private JLabel lblCredits = new JLabel("Credits:");
	private JLabel lblGamesWon = new JLabel("Games Won:");
	private JLabel lblGamesLost = new JLabel("Games Lost:");
	private JLabel lblGamesPlayed = new JLabel("Games Played:");

	/**
	 * Create the application.
	 */
	public HomeScreen(ClientModel clientModel,ScreenFactory screenFactory) {
		this.screenFactory = screenFactory;
		this.clientModel = clientModel;
		username = clientModel.getUser().getUserName();
		lblWelcome = new JLabel("Welcome " + username);

		// last thing
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

	public ClientModel getClientModel() {
		return clientModel;
	}
}
