package CardGame.Gui;

import CardGame.GameClient;
import CardGame.Responses.ResponseProtocol;

import javax.imageio.ImageIO;
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

	private GameClient client;
	private BlackjackOnline blackjackOnline;

	private JButton btnCreateAccount;
	private JButton btnLogin;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel lblLogo;

	/**
	 * Create the application.
	 */
	public LoginScreen(GameClient client, BlackjackOnline blackjackOnline) {
		this.client = client;
		this.blackjackOnline = blackjackOnline;
		btnCreateAccount = new JButton("Create Account");
		btnLogin = new JButton("Login");
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        lblLogo = new JLabel();
        setBackground(new Color(46, 139, 87));
        initialize();
		updateBounds();
    }

	/**
	 * Initialize the contents of the panel.
	 */
	private void initialize() {

		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Soho Std", Font.PLAIN, 18));
		add(lblUsername);

		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Soho Std", Font.PLAIN, 18));
		add(lblPassword);

		add(usernameField);

		add(passwordField);

		try {
			Image imgHud = ImageIO.read(getClass().getResource("/gameHud/imageLogo.png"));
			lblLogo.setIcon(new ImageIcon(imgHud));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblLogo);
		
		
		/**
		 * button events for the login button
		 */
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setFont(new Font("Soho Std", Font.PLAIN, 16));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			    // We send a request and get the response
				String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                ResponseProtocol responseProtocol = getClientModel().requestLogin(username, String.valueOf(password));

                usernameField.setText("");
                passwordField.setText("");


                if (responseProtocol == null){
                    return;
                }

                // We display the error if not successful
                int success = responseProtocol.getRequestSuccess();
                String errorMsg = responseProtocol.getErrorMsg();
                if (success == 0){
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }

			}
		});
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
		add(btnCreateAccount);

	}

	public void updateBounds(){
		lblLogo.setBounds(blackjackOnline.getxOrigin()+312, blackjackOnline.getyOrigin()+40, 400, 221);
		btnCreateAccount.setBounds(blackjackOnline.getxOrigin()+432, blackjackOnline.getyOrigin()+480, 160, 30);
		btnLogin.setBounds(blackjackOnline.getxOrigin()+526, blackjackOnline.getyOrigin()+400, 140, 30);
		passwordField.setBounds(blackjackOnline.getxOrigin()+526, blackjackOnline.getyOrigin()+350, 140, 20);
		usernameField.setBounds(blackjackOnline.getxOrigin()+526, blackjackOnline.getyOrigin()+300, 140, 20);
		lblPassword.setBounds(blackjackOnline.getxOrigin()+345, blackjackOnline.getyOrigin()+350, 101, 17);
		lblUsername.setBounds(blackjackOnline.getxOrigin()+345, blackjackOnline.getyOrigin()+300, 127, 31);
	}

	public GameClient getClientModel() {
		return client;
	}

}
