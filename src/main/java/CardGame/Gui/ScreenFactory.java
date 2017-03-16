package CardGame.Gui;

import CardGame.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static CardGame.Gui.Screens.*;


/**
 * Class that creates and controls the frame and the panels to be displayed
 *
 * @author Alex
 */
public class ScreenFactory extends JFrame implements Observer {

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
    public static int scnW = 1024;
    public static int scnH = 576;

    /**
     * Constructor for the frame
     */
    public ScreenFactory(ClientModel clientModel) {
        // add to observer list for notify all
        this.clientModel = clientModel;
        clientModel.addObserver(this);


        // instantiate create account and login screen in constructor
        this.createAccountScreen = new CreateAccountScreen(clientModel);
        this.loginScreen = new LoginScreen(clientModel, this);


        // set up jframe and initialise first screen
        setTitle("CardGame");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, scnW, scnH);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(scnW, scnH));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        centerPane = loginScreen;
        centerPane.setOpaque(false);
        centerPane.setPreferredSize(new Dimension(scnW, scnH));
        centerPane.setMinimumSize(new Dimension(scnW, scnH));
        centerPane.setLayout(null);
        masterPane = new JPanel(new GridBagLayout());
        masterPane.setBackground(new Color(46, 139, 87));

        masterPane.add(centerPane);
        add(masterPane);
    }

    /**
     * This method instantiates a screen at runtime.
     *
     * @param type
     */
    public void screenFactory(int type) {
        if (type == HOMESCREEN) {
            this.homeScreen = new HomeScreen(this.clientModel);
        }
    }


    /**
     * method for changing between panels
     *
     * @param panelAdd Panel to add
     */
    public void setPane(JPanel panelAdd) {
        masterPane.remove(centerPane);
        centerPane = panelAdd;
        centerPane.setPreferredSize(new Dimension(scnW, scnH));
        centerPane.setMinimumSize(new Dimension(scnW, scnH));
        centerPane.setOpaque(false);
        centerPane.setLayout(null);
        masterPane.add(centerPane);
        frame.repaint();
        frame.revalidate();
    }

//    frame.addComponentListener(new FrameListen());
////    addComponentListener(new ComponentListener() {
////        public void componentResized(ComponentEvent e) {
////            // do stuff
////        }
////    });

//    public void setPanelToFrame(){
//        System.out.println(frame.getWidth());
//        //masterPane.remove(centerPane);
//        //centerPane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
//        //centerPane.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
////        centerPane.setOpaque(false);
////        centerPane.setLayout(null);
////        masterPane.add(centerPane);
////        frame.repaint();
////        frame.revalidate();
//    }

    /**
     *
     */
    public void factory() {

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
                    this.homeScreen = new HomeScreen(model);
                    setPane(this.homeScreen);
                    break;
                case LOBBYSCREEN:
                    this.lobbyScreen = new LobbyScreen(model);
                    setPane(this.lobbyScreen);
                    break;
                case GAMESCREEN:
                    this.gameScreen = new GameScreen(model);
                    setPane(this.gameScreen);
                    break;
                case STATISTICSSCREEN:
                    this.statisticsScreen = new StatisticsScreen(model);
                    setPane(this.statisticsScreen);
                    break;
                case SETTINGSSCREEN:
                    this.settingsScreen = new SettingsScreen(model);
                    setPane(this.settingsScreen);
                    break;
            }
        }

    }
}
