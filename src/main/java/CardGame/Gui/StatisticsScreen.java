package CardGame.Gui;

import CardGame.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.HOMESCREEN;

/**
 * Statistics Screen
 * @author Alex
 *
 */
public class StatisticsScreen extends JPanel {

	private GameClient client;
	private BlackjackOnline blackjackOnline;

	private JLabel lblTitle;
	private JButton btnBack;


	/**
	 * Create the application.
	 */
	public StatisticsScreen(GameClient client, BlackjackOnline blackjackOnline) {
		this.client = client;
		this.blackjackOnline = blackjackOnline;
		lblTitle = new JLabel("Statistics");
		btnBack = new JButton("Back");
        setBackground(new Color(46, 139, 87));
		initialize();
		updateBounds();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblTitle.setForeground(new Color(255, 255, 255));
		add(lblTitle);
		
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
	}

	public void updateBounds(){
		lblTitle.setBounds(blackjackOnline.getxOrigin()+391, 10, 242, 34);
		btnBack.setBounds(10, blackjackOnline.getScreenHeightCurrent()-80, 100, 30);
	}

	public GameClient getClientModel() {
		return client;
	}
}
