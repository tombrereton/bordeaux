package CardGame.Gui;

import CardGame.ClientModel;

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

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JLabel lblWelcome = new JLabel("Settings");
	private JButton btnBack = new JButton("Back");

	/**
	 * Create the application.
	 */
	public SettingsScreen(ClientModel clientModel, ScreenFactory screenFactory) {
		this.clientModel = clientModel;
		this.screenFactory = screenFactory;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(1024,576);
		setLayout(null);

		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		add(lblWelcome);
		
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
		btnBack.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+526, 89, 23);
		add(btnBack);
	}

	public void update(){
		lblWelcome.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		btnBack.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+526, 89, 23);
	}

}
