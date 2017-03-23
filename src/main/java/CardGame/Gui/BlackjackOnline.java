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
 * @author Alex
 */
public class BlackjackOnline extends JFrame implements Observer, ComponentListener {

    /**
     * Sets up the frame and panel on the GUI
     * Variables for the different GUI screens are created
     * Client variable is created
     */
    public static JPanel centerPane;
    public static BlackjackOnline frame = null;
    public LoginScreen loginScreen;
    public HomeScreen homeScreen;
    public CreateAccountScreen createAccountScreen;
    public LobbyScreen lobbyScreen;
    public GameScreen gameScreen;
    private GameClient client;


    /**
     * Sets the minimum screen resolution allowed for the frame
     * Creates variables for the frame width and height
     * Creates variables for the xScreenDiff and yScreenDiff that are used to
     * offset components for changes in frame size between the minimum frame and new size
     */
    public static final int screenWidthMin = 1024;
    public static final int screenHeightMin = 576;
    private int screenWidthCurrent;
    private int screenHeightCurrent;
    private int xScreenDiff;
    private int yScreenDiff;

    /**
     * Constructor
     * Initialises client, adds the observer pattern
     * Instantiates the login and create account screens
     * Sets the current width and height and sets the x and y screen differences
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
        xScreenDiff = ((screenWidthCurrent - screenWidthMin) / 2);
        yScreenDiff = ((screenHeightCurrent - screenHeightMin) / 2);

        // this screen should be created at run time so it can access the
        // logged in user after the user has logged in
//        this.homeScreen = new homeScreen(clientModel);

        frameSetup();
        initialiseLoginScreen();
    }

    /**
     * Sets the variables for the frame, resizability, minimum size, position,
     * and adds the component listener. This method is run as part of the constructor
     */
    private void frameSetup() {
        setTitle("BlackJack Online");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, screenWidthCurrent, screenHeightCurrent);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addComponentListener(this);
    }

    /**
     * Sets the panel of the frame to login screen
     * run in the constructor to make the login screen appear initially
     */
    private void initialiseLoginScreen() {
        centerPane = loginScreen;
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setLayout(null);
        add(centerPane);
    }

    public int getxScreenDiff() {
        return xScreenDiff;
    }

    public int getyScreenDiff() {
        return yScreenDiff;
    }

    public int getScreenWidthCurrent() {
        return screenWidthCurrent;
    }

    public int getScreenHeightCurrent() {
        return screenHeightCurrent;
    }

    /**
     * Method for changing between panels
     * current panel is removed, new panel is added with updated frame variables
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
        }
    }

    /**
     * Method to listen out for frame resizing. When the frame is resized this method is called.
     * The current width, height x and y screen differences are all updated.
     * The panel is reset to fit the new size of the frame and the positions of the components
     * for that panel are updated using the updatePanelBounds method.
     * @param e The component event
     */
    @Override
    public void componentResized(ComponentEvent e) {
        screenWidthCurrent = frame.getWidth();
        screenHeightCurrent = frame.getHeight();
        xScreenDiff = ((screenWidthCurrent - screenWidthMin) / 2);
        yScreenDiff = ((screenHeightCurrent - screenHeightMin) / 2);
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setLayout(null);
        updatePanelBounds();
        frame.repaint();
        frame.revalidate();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    /**
     * Method which calls the updateBounds method for a specific screen class
     * dependent on which screen is currently being displayed
     */
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
        }
    }


    /**
     * This update method will change the current jpanel
     * and repaint it as per ClientModel.currentScreen
     * @param observable The observable
     * @param o The object to observe
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
                    if (this.gameScreen != null){
                        this.gameScreen.resetHands();
                    }
                    break;
                case GAMESCREEN:
                    if (this.gameScreen == null) {
                        this.gameScreen = new GameScreen(model, this);
                    }
                    setPane(this.gameScreen);
                    break;
            }
        }
    }

    /**
     * main method with which to run the gui
     * @param args
     */
    public static void main(String[] args) {

        // parse command line arguments
        int port = 0;
        String host = "";

        if (args.length == 1 && args[0].equals("-h")) {
            System.out.println("Enter: \'[host]\' or \'[host] [post]\' " +
                    "\nOr default is \'[localhost] [7654]\'");
            return;
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
