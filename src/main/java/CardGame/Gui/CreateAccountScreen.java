package CardGame.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountScreen extends JPanel {

	/**
	 * textfields
	 */
	private JTextField firstnameField;
	private JTextField lastnameField;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField emailField;

	/**
	 * Create the application.
	 */
	public CreateAccountScreen() {
		setBackground(new Color(46, 139, 87));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setLayout(null);
		
		JLabel lblCreateNewAccount = new JLabel("Create new account");
		lblCreateNewAccount.setForeground(new Color(255, 255, 255));
		lblCreateNewAccount.setBounds(157, 11, 120, 14);
		add(lblCreateNewAccount);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setForeground(new Color(255, 255, 255));
		lblFirstName.setBounds(123, 58, 83, 14);
		add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setForeground(new Color(255, 255, 255));
		lblLastName.setBounds(123, 84, 83, 14);
		add(lblLastName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(123, 109, 83, 14);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(123, 134, 83, 14);
		add(lblPassword);
		
		JLabel lblEmail = new JLabel("email:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(123, 159, 46, 14);
		add(lblEmail);
		
		firstnameField = new JTextField();
		firstnameField.setBounds(196, 55, 146, 20);
		add(firstnameField);
		firstnameField.setColumns(10);
		
		lastnameField = new JTextField();
		lastnameField.setColumns(10);
		lastnameField.setBounds(196, 83, 146, 20);
		add(lastnameField);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(196, 109, 146, 20);
		add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(196, 134, 146, 20);
		add(passwordField);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(196, 159, 146, 20);
		add(emailField);
		
		/**
		 * back button events
		 */
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.CreateAccountScreen,ScreenFactory.frame.LoginScreen);
			}
		});
		btnBack.setBounds(10, 213, 89, 23);
		add(btnBack);
		
		/**
		 * sign up button events
		 */
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If usernameField.getText() is unique create account and return to login screen
				ScreenFactory.setPane(ScreenFactory.frame.CreateAccountScreen,ScreenFactory.frame.LoginScreen);
				//else display message that username is not unique
			}
		});
		btnSignUp.setBounds(196, 213, 89, 23);
		add(btnSignUp);
	}

}
