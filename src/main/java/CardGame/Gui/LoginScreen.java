package CardGame.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JPanel {

	/**
	 * text field variables
	 */
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public LoginScreen() {
		setBackground(new Color(46, 139, 87));
		initialize();
	}

	/**
	 * Initialize the contents of the panel.
	 */
	private void initialize() {
		
		setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(106, 89, 73, 14);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(106, 129, 73, 14);
		add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setBounds(200, 86, 154, 20);
		add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(200, 126, 154, 20);
		add(passwordField);
		
		
		/**
		 * button events for the login button
		 */
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.LoginScreen,ScreenFactory.frame.HomeScreen);
			}
		});
		
		btnLogin.setBounds(200, 171, 154, 23);
		add(btnLogin);
		
		/**
		 * button events for create account button
		 */
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.LoginScreen,ScreenFactory.frame.CreateAccountScreen);
			}
		});
		btnCreateAccount.setBounds(197, 214, 157, 23);
		add(btnCreateAccount);
		
		
		/**
		 * formatting
		 */
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(182, 34, 46, 29);
		add(lblLogin);
	}
		
}
