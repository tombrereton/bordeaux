package CardGame.Gui;

import CardGame.ClientModel;
import CardGame.Responses.ResponseProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.HOMESCREEN;


/**
 * Login Screen
 * @author Alex
 *
 */
public class LoginScreen extends JPanel {

	/**
	 * text field variables
	 */
	private JTextField usernameField;
	private JPasswordField passwordField;
	private ClientModel clientModel;
	private ScreenFactory screenFactory;


	/**
	 * Create the application.
	 */
	public LoginScreen(ClientModel clientModel, ScreenFactory screenFactory) {
		this.clientModel = clientModel;
		this.screenFactory = screenFactory;
		initialize();
	}

	/**
	 * Initialize the contents of the panel.
	 */
	private void initialize() {
		
		setSize(1024,576);
		setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblUsername.setBounds(345, 226, 127, 31);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblPassword.setBounds(345, 283, 101, 17);
		add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setBounds(526, 233, 140, 20);
		add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(526, 283, 140, 20);
		add(passwordField);
		
		
		/**
		 * button events for the login button
		 */
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO change to handle password fields later
			    ResponseProtocol response = getClientModel().login(usernameField.getText(), String.valueOf(passwordField.getPassword()));

			    // If response say success, change screen
			    if (response.getRequestSuccess() == 1){
			    	screenFactory.screenFactory(HOMESCREEN);
					ScreenFactory.setPane(ScreenFactory.frame.homeScreen);
				}


			}
		});
		
		btnLogin.setBounds(526, 332, 140, 30);
		add(btnLogin);
		
		/**
		 * button events for create account button
		 */
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBackground(new Color(255, 255, 255));
		btnCreateAccount.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.createAccountScreen);
			}
		});
		btnCreateAccount.setBounds(432, 418, 160, 30);
		add(btnCreateAccount);
		
		
		/**
		 * Main Heading
		 */
		JLabel lblLogin = new JLabel("BlackJack Online");
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Script MT Bold", Font.BOLD, 36));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(314, 115, 396, 57);
		add(lblLogin);
	}

	public ClientModel getClientModel() {
		return clientModel;
	}
}
