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

	static JButton btnLogout = new JButton("Logout");
	String username = clientModel.getUser().getUserName();
	static JLabel lblWelcome = new JLabel("Welcome "); //get the username
	static JButton btnGoToLobby = new JButton("Go To Lobby");
	static JButton btnStatistics = new JButton("Statistics");
	static JButton btnSettings = new JButton("Settings");
	static JLabel lblCredits = new JLabel("Credits:");
	static JLabel lblGamesWon = new JLabel("Games Won:");
	static JLabel lblGamesLost = new JLabel("Games Lost:");
	static JLabel lblGamesPlayed = new JLabel("Games Played:");

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
		
		/**
		 * button events for the logout button
		 */
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.loginScreen);
			}
		});
		btnLogout.setBounds(ScreenFactory.xOrigin+886, ScreenFactory.yOrigin+7, 104, 23);
		add(btnLogout);
		
		/**
		 * Title label
		 */
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+11, 242, 34);
		add(lblWelcome);
		
		/**
		 * Button to go to lobby panel.
		 */
		btnGoToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.lobbyScreen);
			}
		});
		btnGoToLobby.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnGoToLobby.setBackground(Color.WHITE);
		btnGoToLobby.setBounds(ScreenFactory.xOrigin+148, ScreenFactory.yOrigin+174, 165, 34);
		add(btnGoToLobby);
		
		/**
		 * Button to go to stats panel.
		 */
		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.statisticsScreen);
			}
		});
		btnStatistics.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnStatistics.setBackground(Color.WHITE);
		btnStatistics.setBounds(ScreenFactory.xOrigin+148, ScreenFactory.yOrigin+271, 165, 34);
		add(btnStatistics);
		
		/**
		 * Button to go to settings panel.
		 */
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.settingsScreen);
			}
		});
		btnSettings.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnSettings.setBackground(Color.WHITE);
		btnSettings.setBounds(ScreenFactory.xOrigin+148, ScreenFactory.yOrigin+368, 165, 34);
		add(btnSettings);

		lblCredits.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblCredits.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+191, 225, 34);
		add(lblCredits);

		lblGamesWon.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesWon.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+300, 225, 34);
		add(lblGamesWon);

		lblGamesLost.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesLost.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+352, 225, 34);
		add(lblGamesLost);

		lblGamesPlayed.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblGamesPlayed.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+243, 225, 34);
		add(lblGamesPlayed);
	}

	public static void updateBounds(){
		btnLogout.setBounds(ScreenFactory.xOrigin+886, ScreenFactory.yOrigin+7, 104, 23);
		lblWelcome.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+11, 242, 34);
		btnGoToLobby.setBounds(ScreenFactory.xOrigin+148, ScreenFactory.yOrigin+174, 165, 34);
		btnStatistics.setBounds(ScreenFactory.xOrigin+148, ScreenFactory.yOrigin+271, 165, 34);
		btnSettings.setBounds(ScreenFactory.xOrigin+148, ScreenFactory.yOrigin+368, 165, 34);
		lblCredits.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+191, 225, 34);
		lblGamesWon.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+300, 225, 34);
		lblGamesLost.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+352, 225, 34);
		lblGamesPlayed.setBounds(ScreenFactory.xOrigin+560, ScreenFactory.yOrigin+243, 225, 34);
	}
}
