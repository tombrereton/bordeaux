package CardGame.Gui;

import CardGame.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;

import static CardGame.Gui.Screens.*;


/**
 * Class that creates and controls the frame and the panels to be displayed
 *
 * @author Alex
 */
public class BlackjackOnline extends JFrame implements Observer, ComponentListener {

//	private ImageIcon icon;
//	private JLabel label;

    /**
     * sets up the screen guis
     * screen ratio 16:9
     */
    public static JPanel centerPane;
    public static BlackjackOnline frame = null;
    public LoginScreen loginScreen;
    public HomeScreen homeScreen;
    public CreateAccountScreen createAccountScreen;
    public SettingsScreen settingsScreen;
    public StatisticsScreen statisticsScreen;
    public LobbyScreen lobbyScreen;
    public GameScreen gameScreen;
    private GameClient client;


    /**
     * screen size variables
     * smallest screen resolution
     */
    public static final int screenWidthMin = 1024;
    public static final int screenHeightMin = 576;
    private int screenWidthCurrent;
    private int screenHeightCurrent;
    private int xOrigin;
    private int yOrigin;

    /**
     * Constructor for the frame
     */
    public BlackjackOnline(GameClient client) {
        // add to observer list for notify all
        this.client = client;
        client.addObserver(this);

        // instantiate create account and login screen in constructor
        this.createAccountScreen = new CreateAccountScreen(client, this);
        this.loginScreen = new LoginScreen(client, this);

        screenWidthCurrent = screenWidthMin;
        screenHeightCurrent = screenHeightMin;
        xOrigin = ((screenWidthCurrent - screenWidthMin) / 2);
        yOrigin = ((screenHeightCurrent - screenHeightMin) / 2);

        // this screen should be created at run time so it can access the
        // logged in user after the user has logged in
//        this.homeScreen = new homeScreen(clientModel);

        frameSetup();
        initialiseLoginScreen();
    }

    private void frameSetup() {
        setTitle("CardGame");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, screenWidthCurrent, screenHeightCurrent);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addComponentListener(this);

    }


    private void initialiseLoginScreen() {
        centerPane = loginScreen;
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setLayout(null);
        add(centerPane);
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    public int getScreenWidthCurrent() {
        return screenWidthCurrent;
    }

    public int getScreenHeightCurrent() {
        return screenHeightCurrent;
    }

    /**
     * method for changing between panels
     *
     * @param panelAdd Panel to add
     */
    public void setPane(JPanel panelAdd) {
        if (centerPane != panelAdd) {
            frame.remove(centerPane);
            centerPane = panelAdd;
            centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
            centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
            centerPane.setLayout(null);
            updatePanelBounds();
            frame.add(centerPane);
            frame.repaint();
            frame.revalidate();

//            if (panelAdd == this.lobbyScreen) {
//                this.lobbyScreen.getGameNameListModel().clear();
//            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        screenWidthCurrent = frame.getWidth();
        screenHeightCurrent = frame.getHeight();
        xOrigin = ((screenWidthCurrent - screenWidthMin) / 2);
        yOrigin = ((screenHeightCurrent - screenHeightMin) / 2);
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setLayout(null);
        updatePanelBounds();
        frame.repaint();
        frame.revalidate();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    public void updatePanelBounds() {
        int currentScreen = client.getCurrentScreen();
        switch (currentScreen) {
            case 0:
                loginScreen.updateBounds();
                break;
            case 1:
                createAccountScreen.updateBounds();
                break;
            case 2:
                homeScreen.updateBounds();
                break;
            case 3:
                lobbyScreen.updateBounds();
                break;
            case 4:
                gameScreen.updateBounds();
                break;
            case 5:
                statisticsScreen.updateBounds();
                break;
            case 6:
                settingsScreen.updateBounds();
                break;
        }
    }


    /**
     * This update method will change the current jpanel
     * and repaint it as per ClientModel.currentScreen.
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameClient) {
            GameClient model = (GameClient) observable;
            int currentScreen = model.getCurrentScreen();

            switch (currentScreen) {
                case LOGINSCREEN:
                    setPane(this.loginScreen);
                    break;
                case CREATE_ACCOUNTSCREEN:
                    setPane(this.createAccountScreen);
                    break;
                case HOMESCREEN:
                    if (this.homeScreen == null) {
                        this.homeScreen = new HomeScreen(model, this);
                    }
                    setPane(this.homeScreen);
                    break;
                case LOBBYSCREEN:
                    if (this.lobbyScreen == null) {
                        this.lobbyScreen = new LobbyScreen(model, this);
                    }
                    setPane(this.lobbyScreen);
                    break;
                case GAMESCREEN:
                    if (this.gameScreen == null) {
                        this.gameScreen = new GameScreen(model, this);
                    }
                    setPane(this.gameScreen);
                    break;
                case STATISTICSSCREEN:
                    if (this.statisticsScreen == null) {
                        this.statisticsScreen = new StatisticsScreen(model, this);
                    }
                    setPane(this.statisticsScreen);
                    break;
                case SETTINGSSCREEN:
                    if (this.settingsScreen == null) {
                        this.settingsScreen = new SettingsScreen(model, this);
                    }
                    setPane(this.settingsScreen);
                    break;
            }
        }
    }

    /**
     * main method with which to run the gui
     *
     * @param args
     */
    public static void main(String[] args) {

        // parse command line arguments
        int port = 0;
        String host = "";

        if (args.length == 1 && args[0].equals("-h")) {
            System.out.println("Enter: \'[host]\' or \'[host] [post]\' " +
                    "\nOr default is \'[localhost] [7654]\'");
        } else if (args.length == 0) {
            host = "localhost";
            port = 7654;
        } else if (args.length == 1) {
            host = args[0];
            port = 7654;
        } else if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            System.out.println("Enter: \'[port]\' or \'[port] [host]\' " +
                    "\nOr \'[port] [host] [max number of clients]\'" +
                    "\nOr default is \'[7654] [0.0.0.0] [20]\'");
        }

        System.out.println("Host: " + host + ", Port: " + port);

        // set host and port to final
        String finalHost = host;
        int finalPort = port;


        // start gui thread
        EventQueue.invokeLater(() -> {
            GameClient gameClient = null;

            try {
                gameClient = new GameClient(finalHost, finalPort);
                frame = new BlackjackOnline(gameClient);
                frame.setVisible(true);

            } catch (Exception e) {
                System.out.println("problem in main gui thread.");
                e.printStackTrace();
            }
        });
    }
}
