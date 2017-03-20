package CardGame.Gui;

import CardGame.GameClient;
import CardGame.Responses.ResponseRegisterUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.LOGINSCREEN;

public class CreateAccountScreen extends JPanel {

    private GameClient client;
    private BlackjackOnline blackjackOnline;

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
    public CreateAccountScreen(GameClient client, BlackjackOnline blackjackOnline) {
        this.client = client;
        this.blackjackOnline = blackjackOnline;
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

                // get user details
                String username = usernameField.getText();
                String password = passwordField.getText();
                String firstName = firstnameField.getText();
                String lastName = lastnameField.getText();

                // sent register request to server
                ResponseRegisterUser responseRegisterUser = getClientModel().
                        requestRegisterUser(username, password, firstName, lastName);

                // We display the error if not successful
                String errorMsg = responseRegisterUser.getErrorMsg();
                JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                        JOptionPane.WARNING_MESSAGE);


            }
        });
        add(btnSignUp);
    }

	public void updateBounds(){
		btnSignUp.setBounds(blackjackOnline.getxOrigin()+501, blackjackOnline.getyOrigin()+392, 146, 23);
		btnBack.setBounds(10, blackjackOnline.getScreenHeightCurrent()-80, 100, 30);
		emailField.setBounds(blackjackOnline.getxOrigin()+501, blackjackOnline.getyOrigin()+338, 146, 20);
		passwordField.setBounds(blackjackOnline.getxOrigin()+501, blackjackOnline.getyOrigin()+308, 146, 20);
		usernameField.setBounds(blackjackOnline.getxOrigin()+501, blackjackOnline.getyOrigin()+278, 146, 20);
		lastnameField.setBounds(blackjackOnline.getxOrigin()+501, blackjackOnline.getyOrigin()+248, 146, 20);
		firstnameField.setBounds(blackjackOnline.getxOrigin()+501, blackjackOnline.getyOrigin()+218, 146, 20);
		lblEmail.setBounds(blackjackOnline.getxOrigin()+391, blackjackOnline.getyOrigin()+338, 83, 14);
		lblPassword.setBounds(blackjackOnline.getxOrigin()+391, blackjackOnline.getyOrigin()+308, 83, 14);
		lblUsername.setBounds(blackjackOnline.getxOrigin()+391, blackjackOnline.getyOrigin()+278, 83, 14);
		lblLastName.setBounds(blackjackOnline.getxOrigin()+391, blackjackOnline.getyOrigin()+248, 83, 14);
		lblFirstName.setBounds(blackjackOnline.getxOrigin()+374, blackjackOnline.getyOrigin()+218, 100, 14);
		lblCreateNewAccount.setBounds(blackjackOnline.getxOrigin()+408, blackjackOnline.getyOrigin()+121, 207, 23);
	}

    public GameClient getClientModel() {
        return client;
    }
}
