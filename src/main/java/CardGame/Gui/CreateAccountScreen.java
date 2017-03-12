package CardGame.Gui;

import CardGame.ClientModel;

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
    private ClientModel clientModel;

    /**
	 * Create the application.
	 */
	public CreateAccountScreen(ClientModel clientModel) {
        this.clientModel = clientModel;
        setBackground(new Color(46, 139, 87));
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setSize(1024,576);
		setLayout(null);
		
		JLabel lblCreateNewAccount = new JLabel("Create new account");
		lblCreateNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateNewAccount.setFont(new Font("Soho Std", Font.PLAIN, 20));
		lblCreateNewAccount.setForeground(new Color(255, 255, 255));
		lblCreateNewAccount.setBounds(408, 121, 207, 23);
		add(lblCreateNewAccount);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblFirstName.setForeground(new Color(255, 255, 255));
		lblFirstName.setBounds(374, 218, 100, 14);
		add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblLastName.setForeground(new Color(255, 255, 255));
		lblLastName.setBounds(391, 248, 83, 14);
		add(lblLastName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(391, 278, 83, 14);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(391, 308, 83, 14);
		add(lblPassword);
		
		JLabel lblEmail = new JLabel("email:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(391, 338, 83, 14);
		add(lblEmail);
		
		firstnameField = new JTextField();
		firstnameField.setBounds(501, 218, 146, 20);
		add(firstnameField);
		firstnameField.setColumns(10);
		
		lastnameField = new JTextField();
		lastnameField.setColumns(10);
		lastnameField.setBounds(501, 248, 146, 20);
		add(lastnameField);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(501, 278, 146, 20);
		add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(501, 308, 146, 20);
		add(passwordField);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(501, 338, 146, 20);
		add(emailField);
		
		/**
		 * back button events
		 */
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.LoginScreen);
			}
		});
		btnBack.setBounds(40, 526, 89, 23);
		add(btnBack);
		
		/**
		 * sign up button events
		 */
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBackground(new Color(255, 255, 255));
		btnSignUp.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If usernameField.getText() is unique create account and return to login screen


                getClientModel().registerUser(usernameField.getText(), passwordField.getText(),
                        firstnameField.getText(), lastnameField.getText());

                // if successfully registered - change page:
				ScreenFactory.setPane(ScreenFactory.frame.LoginScreen);
				//else display message that username is not unique
			}
		});
		btnSignUp.setBounds(501, 392, 146, 23);
		add(btnSignUp);
	}

    public ClientModel getClientModel() {
        return clientModel;
    }
}
