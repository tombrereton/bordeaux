package CardGame.Gui;

import CardGame.GameClient;
import CardGame.Responses.ResponseProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static CardGame.Gui.Screens.*;

/**
 * Home Screen
 *
 * @author Alex
 */
public class HomeScreen extends JPanel implements Observer {

    private GameClient client;
    private BlackjackOnline blackjackOnline;

    private JButton btnLogout;
    private String username;
    private JLabel lblWelcome;
    private JButton btnGoToLobby;
    private JButton btnStatistics;
    private JButton btnSettings;
    private JLabel lblCredits;
    private JLabel lblGamesWon;
    private JLabel lblGamesLost;
    private JLabel lblGamesPlayed;

    /**
     * Create the application.
     */
    public HomeScreen(GameClient client, BlackjackOnline blackjackOnline) {
        this.blackjackOnline = blackjackOnline;

        // we add this to list of observers
        this.client = client;
        client.addObserver(this);

        // set welcome message
        username = client.getLoggedInUser().getUserName();
        lblWelcome = new JLabel();
        setWelcomeLabel(username);

        btnLogout = new JButton("Logout");
        btnGoToLobby = new JButton("Go To Lobby");
        btnStatistics = new JButton("Statistics");
        btnSettings = new JButton("Settings");
        lblCredits = new JLabel("Credits:");
        lblGamesWon = new JLabel("Games Won:");
        lblGamesLost = new JLabel("Games Lost:");
        lblGamesPlayed = new JLabel("Games Played:");
        setBackground(new Color(46, 139, 87));
        initialize();
        updateBounds();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        /**
         * button events for the logout button
         */
        btnLogout.setBackground(new Color(255, 255, 255));
        btnLogout.setFont(new Font("Soho Std", Font.PLAIN, 12));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResponseProtocol responseProtocol = getClientModel().requestLogOut();


                if (responseProtocol == null){
                    return;
                }

                // We display the error if not successful
                int success = responseProtocol.getRequestSuccess();
                String errorMsg = responseProtocol.getErrorMsg();
                if (success == 0) {
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(btnLogout);

        /**
         * Title label
         */
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Soho Std", Font.PLAIN, 24));
        lblWelcome.setForeground(new Color(255, 255, 255));
        add(lblWelcome);

        /**
         * Button to go to lobby panel.
         */
        btnGoToLobby.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getClientModel().setCurrentScreen(LOBBYSCREEN);
            }
        });
        btnGoToLobby.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnGoToLobby.setBackground(Color.WHITE);

        add(btnGoToLobby);

        /**
         * Button to go to stats panel.
         */
        btnStatistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getClientModel().setCurrentScreen(STATISTICSSCREEN);
            }
        });
        btnStatistics.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnStatistics.setBackground(Color.WHITE);
        add(btnStatistics);

        /**
         * Button to go to settings panel.
         */
        btnSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getClientModel().setCurrentScreen(SETTINGSSCREEN);
            }
        });
        btnSettings.setFont(new Font("Soho Std", Font.PLAIN, 16));
        btnSettings.setBackground(Color.WHITE);
        add(btnSettings);

        lblCredits.setFont(new Font("Soho Std", Font.PLAIN, 18));
        add(lblCredits);

        lblGamesWon.setFont(new Font("Soho Std", Font.PLAIN, 18));
        add(lblGamesWon);

        lblGamesLost.setFont(new Font("Soho Std", Font.PLAIN, 18));
        add(lblGamesLost);

        lblGamesPlayed.setFont(new Font("Soho Std", Font.PLAIN, 18));
        add(lblGamesPlayed);
    }

    public void updateBounds() {
        btnLogout.setBounds(blackjackOnline.getScreenWidthCurrent() - 130, 10, 100, 30);
        lblWelcome.setBounds(blackjackOnline.getxOrigin() + 391, 10, 242, 34);
        btnGoToLobby.setBounds(blackjackOnline.getxOrigin() + 148, blackjackOnline.getyOrigin() + 174, 165, 34);
        btnStatistics.setBounds(blackjackOnline.getxOrigin() + 148, blackjackOnline.getyOrigin() + 271, 165, 34);
        btnSettings.setBounds(blackjackOnline.getxOrigin() + 148, blackjackOnline.getyOrigin() + 368, 165, 34);
        lblCredits.setBounds(blackjackOnline.getxOrigin() + 560, blackjackOnline.getyOrigin() + 191, 225, 34);
        lblGamesWon.setBounds(blackjackOnline.getxOrigin() + 560, blackjackOnline.getyOrigin() + 300, 225, 34);
        lblGamesLost.setBounds(blackjackOnline.getxOrigin() + 560, blackjackOnline.getyOrigin() + 352, 225, 34);
        lblGamesPlayed.setBounds(blackjackOnline.getxOrigin() + 560, blackjackOnline.getyOrigin() + 243, 225, 34);
    }

    public GameClient getClientModel() {
        return client;
    }

    private void setWelcomeLabel(String username) {
        this.lblWelcome.setText("Welcome " + username);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameClient) {
            GameClient model = (GameClient) observable;

            if (model.getLoggedInUser() != null) {
                setWelcomeLabel(model.getLoggedInUser().getUserName());
            }

        }
    }

}
