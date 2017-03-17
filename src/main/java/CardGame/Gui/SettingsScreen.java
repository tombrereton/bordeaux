package CardGame.Gui;

import CardGame.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Settings Screen
 * @author Alex
 *
 */
public class SettingsScreen extends JPanel {

	private GameClient client;
	private ScreenFactory screenFactory;

	private JLabel lblTitle;
	private JButton btnBack;

	/**
	 * Create the application.
	 */
	public SettingsScreen(GameClient client, ScreenFactory screenFactory) {
		this.client = client;
		this.screenFactory = screenFactory;
		lblTitle = new JLabel("Settings");
		btnBack = new JButton("Back");
		setBackground(new Color(46, 139, 87));
		initialize();
		updateBounds();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(1024,576);
		setLayout(null);

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
				getClientModel().setCurrentScreen(Screens.HOMESCREEN);
			}
		});
		add(btnBack);
	}

	public void updateBounds(){
		lblTitle.setBounds(screenFactory.getxOrigin()+391, 10, 242, 34);
		btnBack.setBounds(10, screenFactory.getScreenHeightCurrent()-70, 89, 23);
	}

	public GameClient getClientModel() {
		return client;
	}
}
