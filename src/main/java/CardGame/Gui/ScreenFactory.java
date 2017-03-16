package CardGame.Gui;

import CardGame.ClientModel;

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
public class ScreenFactory extends JFrame implements Observer, ComponentListener {

//	private ImageIcon icon;
//	private JLabel label;

    /**
     * sets up the screen guis
     * screen ratio 16:9
     */
    public static JPanel masterPane;
    public static JPanel centerPane;
    public static ScreenFactory frame = null;
    public LoginScreen loginScreen;
    public HomeScreen homeScreen;
    public CreateAccountScreen createAccountScreen;
    public SettingsScreen settingsScreen;
    public StatisticsScreen statisticsScreen;
    public LobbyScreen lobbyScreen;
    public GameScreen gameScreen;
    private ClientModel clientModel;


    /**
     * screen size variables
     * smallest screen resolution
     */
    private int screenWidthMin;
    private int screenHeightMin;
    private int screenWidthCurrent;
    private int screenHeightCurrent;
    private int xOrigin;
    private int yOrigin;
    /**
     * Constructor for the frame
     */
    public ScreenFactory(ClientModel clientModel) {
        // add to observer list for notify all
        this.clientModel = clientModel;
        clientModel.addObserver(this);


        // instantiate create account and login screen in constructor
        this.createAccountScreen = new CreateAccountScreen(clientModel, this);
        this.loginScreen = new LoginScreen(clientModel, this);

        screenWidthMin = 1024;
        screenHeightMin = 576;
        screenWidthCurrent = screenWidthMin;
        screenHeightCurrent = screenHeightMin;
        xOrigin = ((screenWidthCurrent -screenWidthMin)/2);
        yOrigin = ((screenHeightCurrent-screenHeightMin)/2);

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
        centerPane.setOpaque(false);
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setLayout(null);
        masterPane = new JPanel(new GridBagLayout());
        masterPane.setBackground(new Color(46, 139, 87));
        masterPane.add(centerPane);
        add(masterPane);
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }



    /**
     * method for changing between panels
     *
     * @param panelAdd Panel to add
     */
    public void setPane(JPanel panelAdd) {
        masterPane.remove(centerPane);
        centerPane = panelAdd;
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setOpaque(false);
        centerPane.setLayout(null);
        masterPane.add(centerPane);
        frame.repaint();
        frame.revalidate();
    }

    /**
     *
     */
    public void factory() {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        screenWidthCurrent = frame.getWidth();
        screenHeightCurrent = frame.getHeight();
        xOrigin = ((screenWidthCurrent - screenWidthMin)/2);
        yOrigin = ((screenHeightCurrent- screenHeightMin)/2);
        centerPane.setPreferredSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setMinimumSize(new Dimension(screenWidthCurrent, screenHeightCurrent));
        centerPane.setOpaque(false);
        centerPane.setLayout(null);
        //TODO: call update bounds method in frame
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

    /**
     * main method with which to run the gui
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientModel clientModel = new ClientModel();
                    frame = new ScreenFactory(clientModel);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        if (observable instanceof ClientModel) {
            ClientModel model = (ClientModel) observable;
            int currentScreen = model.getCurrentScreen();

            switch (currentScreen) {
                case LOGINSCREEN:
                    setPane(this.loginScreen);
                    break;
                case CREATE_ACCOUNTSCREEN:
                    setPane(this.createAccountScreen);
                    break;
                case HOMESCREEN:
                    this.homeScreen = new HomeScreen(model, this);
                    setPane(this.homeScreen);
                    break;
                case LOBBYSCREEN:
                    this.lobbyScreen = new LobbyScreen(model, this);
                    setPane(this.lobbyScreen);
                    break;
                case GAMESCREEN:
                    this.gameScreen = new GameScreen(model, this);
                    setPane(this.gameScreen);
                    break;
                case STATISTICSSCREEN:
                    this.statisticsScreen = new StatisticsScreen(model, this);
                    setPane(this.statisticsScreen);
                    break;
                case SETTINGSSCREEN:
                    this.settingsScreen = new SettingsScreen(model, this);
                    setPane(this.settingsScreen);
                    break;
            }
        }

    }
}
