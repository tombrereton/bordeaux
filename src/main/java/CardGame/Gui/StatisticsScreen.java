package CardGame.Gui;

import CardGame.Client;
import CardGame.ClientModel;

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

	private Client client;
	private ScreenFactory screenFactory;

	private JLabel lblWelcome;
	private JButton btnBack;


	/**
	 * Create the application.
	 */
	public StatisticsScreen(Client client, ScreenFactory screenFactory) {
		this.client = client;
		this.screenFactory = screenFactory;
		lblWelcome = new JLabel("Statistics");
		btnBack = new JButton("Back");
        setBackground(new Color(46, 139, 87));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

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
				getClientModel().setCurrentScreen(HOMESCREEN);
			}
		});
		btnBack.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+500, 89, 23);
		add(btnBack);
	}

	public void updateBounds(){
		lblWelcome.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+11, 242, 34);
		btnBack.setBounds(screenFactory.getxOrigin()+40, screenFactory.getyOrigin()+500, 89, 23);
	}

	public Client getClientModel() {
		return client;
	}
}
