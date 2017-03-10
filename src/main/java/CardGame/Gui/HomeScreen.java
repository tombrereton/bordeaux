package CardGame.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JPanel {

	/**
	 * Create the application.
	 */
	public HomeScreen() {
		setBackground(new Color(46, 139, 87));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		setLayout(null);
		
		/**
		 * button events for the logout button
		 */
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.HomeScreen,ScreenFactory.frame.LoginScreen);
			}
		});
		btnLogout.setBounds(335, 227, 89, 23);
		add(btnLogout);
		
		/**
		 * formatting
		 */
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(10, 11, 130, 34);
		add(lblWelcome);
	}




}
