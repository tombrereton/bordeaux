package CardGame.Gui;

import CardGame.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.LOGINSCREEN;

public class CreateAccountScreen extends JPanel {

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JTextField firstnameField;
	private JTextField lastnameField;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField emailField;


	private JButton btnSignUp = new JButton("Sign Up");
	private JButton btnBack = new JButton("Back");
	private JLabel lblEmail = new JLabel("email:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblUsername = new JLabel("Username:");
	private JLabel lblLastName = new JLabel("Last name:");
	private JLabel lblFirstName = new JLabel("First name:");
	private JLabel lblCreateNewAccount = new JLabel("Create new account");

    /**
	 * Create the application.
	 */
	public CreateAccountScreen(ClientModel clientModel, ScreenFactory screenFactory) {
        this.clientModel = clientModel;
		this.screenFactory = screenFactory;
        setBackground(new Color(46, 139, 87));
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		lblCreateNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateNewAccount.setFont(new Font("Soho Std", Font.PLAIN, 20));
		lblCreateNewAccount.setForeground(new Color(255, 255, 255));
		add(lblCreateNewAccount);

		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblFirstName.setForeground(new Color(255, 255, 255));
		add(lblFirstName);

		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblLastName.setForeground(new Color(255, 255, 255));
		add(lblLastName);

		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblUsername.setForeground(new Color(255, 255, 255));
		add(lblUsername);

		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblPassword.setForeground(new Color(255, 255, 255));
		add(lblPassword);

		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblEmail.setForeground(new Color(255, 255, 255));
		add(lblEmail);
		
		firstnameField = new JTextField();
		add(firstnameField);
		firstnameField.setColumns(10);
		
		lastnameField = new JTextField();
		lastnameField.setColumns(10);
		add(lastnameField);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		add(passwordField);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		add(emailField);
		
		/**
		 * back button events
		 */
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().setCurrentScreen(LOGINSCREEN);
			}
		});
		add(btnBack);
		
		/**
		 * sign up button events
		 */
		btnSignUp.setBackground(new Color(255, 255, 255));
		btnSignUp.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If usernameField.getText() is unique create account and return to login screen


                getClientModel().requestRegisterUser(usernameField.getText(), passwordField.getText(),
                        firstnameField.getText(), lastnameField.getText());

                // if successfully registered - change page:
//				ScreenFactory.setPane(ScreenFactory.frame.loginScreen);
				//else display message that username is not unique
			}
		});
		add(btnSignUp);
	}

	public void updateBounds(){
		btnSignUp.setBounds(screenFactory.getxOrigin()+501, screenFactory.getyOrigin()+392, 146, 23);
		btnBack.setBounds(10, screenFactory.getScreenHeightCurrent()-70, 89, 23);
		emailField.setBounds(screenFactory.getxOrigin()+501, screenFactory.getyOrigin()+338, 146, 20);
		passwordField.setBounds(screenFactory.getxOrigin()+501, screenFactory.getyOrigin()+308, 146, 20);
		usernameField.setBounds(screenFactory.getxOrigin()+501, screenFactory.getyOrigin()+278, 146, 20);
		lastnameField.setBounds(screenFactory.getxOrigin()+501, screenFactory.getyOrigin()+248, 146, 20);
		firstnameField.setBounds(screenFactory.getxOrigin()+501, screenFactory.getyOrigin()+218, 146, 20);
		lblEmail.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+338, 83, 14);
		lblPassword.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+308, 83, 14);
		lblUsername.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+278, 83, 14);
		lblLastName.setBounds(screenFactory.getxOrigin()+391, screenFactory.getyOrigin()+248, 83, 14);
		lblFirstName.setBounds(screenFactory.getxOrigin()+374, screenFactory.getyOrigin()+218, 100, 14);
		lblCreateNewAccount.setBounds(screenFactory.getxOrigin()+408, screenFactory.getyOrigin()+121, 207, 23);
	}

    public ClientModel getClientModel() {
        return clientModel;
    }
}
