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
	static JTextField firstnameField;
	static JTextField lastnameField;
	static JTextField usernameField;
	static JTextField passwordField;
	static JTextField emailField;
	static ClientModel clientModel;

	static JButton btnSignUp = new JButton("Sign Up");
	static JButton btnBack = new JButton("Back");
	static JLabel lblEmail = new JLabel("email:");
	static JLabel lblPassword = new JLabel("Password:");
	static JLabel lblUsername = new JLabel("Username:");
	static JLabel lblLastName = new JLabel("Last name:");
	static JLabel lblFirstName = new JLabel("First name:");
	static JLabel lblCreateNewAccount = new JLabel("Create new account");

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

		lblCreateNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateNewAccount.setFont(new Font("Soho Std", Font.PLAIN, 20));
		lblCreateNewAccount.setForeground(new Color(255, 255, 255));
		lblCreateNewAccount.setBounds(ScreenFactory.xOrigin+408, ScreenFactory.yOrigin+121, 207, 23);
		add(lblCreateNewAccount);

		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblFirstName.setForeground(new Color(255, 255, 255));
		lblFirstName.setBounds(ScreenFactory.xOrigin+374, ScreenFactory.yOrigin+218, 100, 14);
		add(lblFirstName);

		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblLastName.setForeground(new Color(255, 255, 255));
		lblLastName.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+248, 83, 14);
		add(lblLastName);

		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+278, 83, 14);
		add(lblUsername);

		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+308, 83, 14);
		add(lblPassword);

		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Soho Std", Font.PLAIN, 16));
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+338, 83, 14);
		add(lblEmail);
		
		firstnameField = new JTextField();
		firstnameField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+218, 146, 20);
		add(firstnameField);
		firstnameField.setColumns(10);
		
		lastnameField = new JTextField();
		lastnameField.setColumns(10);
		lastnameField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+248, 146, 20);
		add(lastnameField);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+278, 146, 20);
		add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+308, 146, 20);
		add(passwordField);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+338, 146, 20);
		add(emailField);
		
		/**
		 * back button events
		 */
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.loginScreen);
			}
		});
		btnBack.setBounds(ScreenFactory.xOrigin+40, ScreenFactory.yOrigin+500, 89, 23);
		add(btnBack);
		
		/**
		 * sign up button events
		 */
		btnSignUp.setBackground(new Color(255, 255, 255));
		btnSignUp.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If usernameField.getText() is unique create account and return to login screen


                getClientModel().registerUser(usernameField.getText(), passwordField.getText(),
                        firstnameField.getText(), lastnameField.getText());

                // if successfully registered - change page:
				ScreenFactory.setPane(ScreenFactory.frame.loginScreen);
				//else display message that username is not unique
			}
		});
		btnSignUp.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+392, 146, 23);
		add(btnSignUp);
	}

	public static void updateBounds(){
		btnSignUp.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+392, 146, 23);
		btnBack.setBounds(ScreenFactory.xOrigin+40, ScreenFactory.yOrigin+500, 89, 23);
		emailField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+338, 146, 20);
		passwordField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+308, 146, 20);
		usernameField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+278, 146, 20);
		lastnameField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+248, 146, 20);
		firstnameField.setBounds(ScreenFactory.xOrigin+501, ScreenFactory.yOrigin+218, 146, 20);
		lblEmail.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+338, 83, 14);
		lblPassword.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+308, 83, 14);
		lblUsername.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+278, 83, 14);
		lblLastName.setBounds(ScreenFactory.xOrigin+391, ScreenFactory.yOrigin+248, 83, 14);
		lblFirstName.setBounds(ScreenFactory.xOrigin+374, ScreenFactory.yOrigin+218, 100, 14);
		lblCreateNewAccount.setBounds(ScreenFactory.xOrigin+408, ScreenFactory.yOrigin+121, 207, 23);
	}

    public ClientModel getClientModel() {
        return clientModel;
    }
}
