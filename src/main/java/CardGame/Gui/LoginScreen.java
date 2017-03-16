package CardGame.Gui;

import CardGame.ClientModel;
import CardGame.Responses.ResponseProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.CREATE_ACCOUNTSCREEN;


/**
 * Login Screen
 * @author Alex
 *
 */
public class LoginScreen extends JPanel {

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JLabel lblLogin;
	private JButton btnCreateAccount;
	private JButton btnLogin;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public LoginScreen(ClientModel clientModel, ScreenFactory screenFactory) {
		this.clientModel = clientModel;
		this.screenFactory = screenFactory;
		lblLogin = new JLabel("BlackJack Online");
		btnCreateAccount = new JButton("Create Account");
		btnLogin = new JButton("Login");
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        setBackground(new Color(46, 139, 87));
        initialize();
    }

	/**
	 * Initialize the contents of the panel.
	 */
	private void initialize() {

		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblUsername.setBounds(screenFactory.getxOrigin()+345, screenFactory.getyOrigin()+226, 127, 31);
		add(lblUsername);

		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblPassword.setBounds(screenFactory.getxOrigin()+345, screenFactory.getyOrigin()+283, 101, 17);
		add(lblPassword);

		usernameField.setBounds(screenFactory.getxOrigin()+526, screenFactory.getyOrigin()+233, 140, 20);
		add(usernameField);

		passwordField.setBounds(screenFactory.getxOrigin()+526, screenFactory.getyOrigin()+283, 140, 20);
		add(passwordField);
		
		
		/**
		 * button events for the login button
		 */
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			    // We send a request and get the response
                ResponseProtocol responseProtocol = getClientModel().requestLogin(usernameField.getText(),
                        String.valueOf(passwordField.getPassword()));

                // We display the error if not null
                String errorMsg = responseProtocol.getErrorMsg();
                if (!errorMsg.equals("")){
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }

			}
		});
		btnLogin.setBounds(screenFactory.getxOrigin()+526, screenFactory.getyOrigin()+332, 140, 30);
		add(btnLogin);
		
		/**
		 * button events for create account button
		 */
		btnCreateAccount.setBackground(new Color(255, 255, 255));
		btnCreateAccount.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    getClientModel().setCurrentScreen(CREATE_ACCOUNTSCREEN);
			}
		});
		btnCreateAccount.setBounds(screenFactory.getxOrigin()+432, screenFactory.getyOrigin()+418, 160, 30);
		add(btnCreateAccount);
		
		
		/**
		 * Main Heading
		 */
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Script MT Bold", Font.BOLD, 36));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(screenFactory.getxOrigin()+314, screenFactory.getyOrigin()+115, 396, 57);
		add(lblLogin);
	}

	public void updateBounds(){
		lblLogin.setBounds(screenFactory.getxOrigin()+314, screenFactory.getyOrigin()+115, 396, 57);
		btnCreateAccount.setBounds(screenFactory.getxOrigin()+432, screenFactory.getyOrigin()+418, 160, 30);
		btnLogin.setBounds(screenFactory.getxOrigin()+526, screenFactory.getyOrigin()+332, 140, 30);
		passwordField.setBounds(screenFactory.getxOrigin()+526, screenFactory.getyOrigin()+283, 140, 20);
		usernameField.setBounds(screenFactory.getxOrigin()+526, screenFactory.getyOrigin()+233, 140, 20);
		lblPassword.setBounds(screenFactory.getxOrigin()+345, screenFactory.getyOrigin()+283, 101, 17);
		lblUsername.setBounds(screenFactory.getxOrigin()+345, screenFactory.getyOrigin()+226, 127, 31);
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

}
