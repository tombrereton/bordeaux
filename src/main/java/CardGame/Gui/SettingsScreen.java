package CardGame.Gui;

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

	/**
	 * Create the application.
	 */
	public SettingsScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(1024,576);
		setLayout(null);
		
		JLabel lblWelcome = new JLabel("Settings");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(391, 11, 242, 34);
		add(lblWelcome);
		
		/**
		 * back button events
		 */
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.HomeScreen);
			}
		});
		btnBack.setBounds(40, 526, 89, 23);
		add(btnBack);
	}

}