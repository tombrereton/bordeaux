package CardGame.Gui;

import CardGame.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Home Screen
 * @author Alex
 *
 */
public class HomeScreen extends JPanel {

	private ClientModel clientModel;

	/**
	 * Create the application.
	 */
	public HomeScreen(ClientModel clientModel) {
		this.clientModel = clientModel;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		setSize(1024,576);
		setLayout(null);
		
		/**
		 * button events for the logout button
		 */
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.LoginScreen);
			}
		});
		btnLogout.setBounds(886, 7, 104, 23);
		add(btnLogout);
		
		/**
		 * Title label
		 */
		String username = clientModel.getUser().getUserName();
		JLabel lblWelcome = new JLabel("Welcome " + username); //get the username
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(391, 11, 242, 34);
		add(lblWelcome);
		
		/**
		 * Button to go to lobby panel.
		 */
		JButton btnGoToLobby = new JButton("Go To Lobby");
		btnGoToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.LobbyScreen);
			}
		});
		btnGoToLobby.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnGoToLobby.setBackground(Color.WHITE);
		btnGoToLobby.setBounds(148, 174, 165, 34);
		add(btnGoToLobby);
		
		/**
		 * Button to go to stats panel.
		 */
		JButton btnStatistics = new JButton("Statistics");
		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.StatisticsScreen);
			}
		});
		btnStatistics.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnStatistics.setBackground(Color.WHITE);
		btnStatistics.setBounds(148, 271, 165, 34);
		add(btnStatistics);
		
		/**
		 * Button to go to settings panel.
		 */
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.SettingsScreen);
			}
		});
		btnSettings.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnSettings.setBackground(Color.WHITE);
		btnSettings.setBounds(148, 368, 165, 34);
		add(btnSettings);
		
		JLabel lblCredits = new JLabel("Credits:");
		lblCredits.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblCredits.setBounds(560, 191, 225, 34);
		add(lblCredits);
		
		JLabel lblGamesWon = new JLabel("Games Won:");
		lblGamesWon.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesWon.setBounds(560, 300, 225, 34);
		add(lblGamesWon);
		
		JLabel lblGamesLost = new JLabel("Games Lost:");
		lblGamesLost.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesLost.setBounds(560, 352, 225, 34);
		add(lblGamesLost);
		
		JLabel lblGamesPlayed = new JLabel("Games Played:");
		lblGamesPlayed.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesPlayed.setBounds(560, 243, 225, 34);
		add(lblGamesPlayed);
	}
}
