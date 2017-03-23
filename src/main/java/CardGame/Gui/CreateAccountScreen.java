package CardGame.Gui;

import CardGame.GameClient;
import CardGame.Responses.ResponseProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.LOGINSCREEN;

/**
 * Class for the create account screen
 * @author Alex
 */

public class CreateAccountScreen extends JPanel {

    //Client and main GUI class
    private GameClient client;
    private BlackjackOnline blackjackOnline;

    //JTextFields
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField emailField;

    //JButtons and JLabels
    private JButton btnSignUp = new JButton("Sign Up");
    private JButton btnBack = new JButton("Back");
    private JLabel lblEmail = new JLabel("email:");
    private JLabel lblPassword = new JLabel("Password:");
    private JLabel lblUsername = new JLabel("Username:");
    private JLabel lblLastName = new JLabel("Last name:");
    private JLabel lblFirstName = new JLabel("First name:");
    private JLabel lblCreateNewAccount = new JLabel("Create new account");

    /**
     * Instantiates the client and GUI, sets the background and initialises the components
     */
    public CreateAccountScreen(GameClient client, BlackjackOnline blackjackOnline) {
        this.client = client;
        this.blackjackOnline = blackjackOnline;
        setBackground(new Color(46, 139, 87));
        initialize();
    }

    /**
     * Initialise the components of the panel for labels, text fields and buttons.
     * Method run as part of the constructor.
     */
    private void initialize() {

        //INITIALISE LABELS
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

        //INITIALISE FIELDS
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

        //INITIALISE BUTTONS

        //BACK BUTTON
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getClientModel().setCurrentScreen(LOGINSCREEN);
            }
        });
        add(btnBack);

        //SIGN UP BUTTON
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
                ResponseProtocol responseRegisterUser = getClientModel().
                        requestRegisterUser(username, password, firstName, lastName);

                if (responseRegisterUser == null){
                    JOptionPane.showMessageDialog(null, "Can't connect to server. Ensure server is up.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // We display the error if not successful
                String errorMsg = responseRegisterUser.getErrorMsg();
                JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnSignUp);
    }

    /**
     * Update Bounds method
     * All component positions except the back button are updated to be positioned relative to the screen center.
     * The back button is fixed to the bottom left side of the panel
     */
	public void updateBounds(){
	    //UPDATE LABELS
        lblEmail.setBounds(blackjackOnline.getxScreenDiff()+391, blackjackOnline.getyScreenDiff()+338, 83, 14);
        lblPassword.setBounds(blackjackOnline.getxScreenDiff()+391, blackjackOnline.getyScreenDiff()+308, 83, 14);
        lblUsername.setBounds(blackjackOnline.getxScreenDiff()+391, blackjackOnline.getyScreenDiff()+278, 83, 14);
        lblLastName.setBounds(blackjackOnline.getxScreenDiff()+391, blackjackOnline.getyScreenDiff()+248, 83, 14);
        lblFirstName.setBounds(blackjackOnline.getxScreenDiff()+374, blackjackOnline.getyScreenDiff()+218, 100, 14);
        lblCreateNewAccount.setBounds(blackjackOnline.getxScreenDiff()+408, blackjackOnline.getyScreenDiff()+121, 207, 23);

        //UPDATE FIELDS
        emailField.setBounds(blackjackOnline.getxScreenDiff()+501, blackjackOnline.getyScreenDiff()+338, 146, 20);
        passwordField.setBounds(blackjackOnline.getxScreenDiff()+501, blackjackOnline.getyScreenDiff()+308, 146, 20);
        usernameField.setBounds(blackjackOnline.getxScreenDiff()+501, blackjackOnline.getyScreenDiff()+278, 146, 20);
        lastnameField.setBounds(blackjackOnline.getxScreenDiff()+501, blackjackOnline.getyScreenDiff()+248, 146, 20);
        firstnameField.setBounds(blackjackOnline.getxScreenDiff()+501, blackjackOnline.getyScreenDiff()+218, 146, 20);

        //UPDATE BUTTONS
		btnSignUp.setBounds(blackjackOnline.getxScreenDiff()+501, blackjackOnline.getyScreenDiff()+392, 146, 23);
		btnBack.setBounds(10, blackjackOnline.getScreenHeightCurrent()-80, 100, 30);
	}

	//GETTERS AND SETTERS
    public GameClient getClientModel() {
        return client;
    }
}
