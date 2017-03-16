package CardGame.Gui;

import CardGame.ClientModel;
import CardGame.Responses.ResponseProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static CardGame.Gui.Screens.HOMESCREEN;


/**
 * Login Screen
 * @author Alex
 *
 */
public class LoginScreen extends JPanel {

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	static JLabel lblLogin = new JLabel("BlackJack Online");
	static JButton btnCreateAccount = new JButton("Create Account");
	static JButton btnLogin = new JButton("Login");
	static JLabel lblUsername = new JLabel("Username");
	static JLabel lblPassword = new JLabel("Password");
	static JTextField usernameField = new JTextField();
	static JPasswordField passwordField = new JPasswordField();

	private int xOrigin = 0;
	private int yOrigin = 0;

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

		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblUsername.setBounds(ScreenFactory.xOrigin+345, ScreenFactory.yOrigin+226, 127, 31);
		add(lblUsername);

		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblPassword.setBounds(ScreenFactory.xOrigin+345, ScreenFactory.yOrigin+283, 101, 17);
		add(lblPassword);

		usernameField.setBounds(ScreenFactory.xOrigin+526, ScreenFactory.yOrigin+233, 140, 20);
		add(usernameField);

		passwordField.setBounds(ScreenFactory.xOrigin+526, ScreenFactory.yOrigin+283, 140, 20);
		add(passwordField);
		
		
		/**
		 * button events for the login button
		 */
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
		btnLogin.setBounds(ScreenFactory.xOrigin+526, ScreenFactory.yOrigin+332, 140, 30);
		add(btnLogin);
		
		/**
		 * button events for create account button
		 */
		btnCreateAccount.setBackground(new Color(255, 255, 255));
		btnCreateAccount.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.createAccountScreen);
			}
		});
		btnCreateAccount.setBounds(ScreenFactory.xOrigin+432, ScreenFactory.yOrigin+418, 160, 30);
		add(btnCreateAccount);
		
		
		/**
		 * Main Heading
		 */
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Script MT Bold", Font.BOLD, 36));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(ScreenFactory.xOrigin+314, ScreenFactory.yOrigin+115, 396, 57);
		add(lblLogin);
	}

	public static void updateBounds(){
		lblLogin.setBounds(ScreenFactory.xOrigin+314, ScreenFactory.yOrigin+115, 396, 57);
		btnCreateAccount.setBounds(ScreenFactory.xOrigin+432, ScreenFactory.yOrigin+418, 160, 30);
		btnLogin.setBounds(ScreenFactory.xOrigin+526, ScreenFactory.yOrigin+332, 140, 30);
		passwordField.setBounds(ScreenFactory.xOrigin+526, ScreenFactory.yOrigin+283, 140, 20);
		usernameField.setBounds(ScreenFactory.xOrigin+526, ScreenFactory.yOrigin+233, 140, 20);
		lblPassword.setBounds(ScreenFactory.xOrigin+345, ScreenFactory.yOrigin+283, 101, 17);
		lblUsername.setBounds(ScreenFactory.xOrigin+345, ScreenFactory.yOrigin+226, 127, 31);
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

}
