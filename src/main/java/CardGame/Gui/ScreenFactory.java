package CardGame.Gui;

import CardGame.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static CardGame.Gui.Screens.HOMESCREEN;


/**
 * Class that creates and controls the frame and the panels to be displayed
 *
 * @author Alex
 */
public class ScreenFactory extends JFrame implements ComponentListener {

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
    public SettingsScreen settingsScreen = new SettingsScreen();
    public StatisticsScreen statisticsScreen = new StatisticsScreen();
    public LobbyScreen lobbyScreen = new LobbyScreen();
    public GameScreen gameScreen = new GameScreen();
    private ClientModel clientModel;


    /**
     * screen size variables
     * smallest screen resolution
     */
    public static int scnWMIN = 1024;
    public static int scnHMIN = 576;
    public static int scnW = scnWMIN;
    public static int scnH = scnHMIN;
    public static int xOrigin = ((ScreenFactory.scnW-ScreenFactory.scnWMIN)/2);
    public static int yOrigin = ((ScreenFactory.scnH-ScreenFactory.scnHMIN)/2);

    /**
     * Constructor for the frame
     */
    public ScreenFactory(ClientModel clientModel) {
        this.clientModel = clientModel;

        this.createAccountScreen = new CreateAccountScreen(clientModel);
        this.loginScreen = new LoginScreen(clientModel, this);

        // this screen should be created at run time so it can access the
        // logged in user after the user has logged in
//        this.homeScreen = new homeScreen(clientModel);

        setTitle("CardGame");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, scnW, scnH);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(scnW, scnH));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addComponentListener(this);

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
    public void screenFactory(int type){
        if (type == HOMESCREEN){
            this.homeScreen = new HomeScreen(this.clientModel);
        }
    }


    /**
     * method for changing between panels
     *
     * @param panelAdd Panel to add
     */
    public static void setPane(JPanel panelAdd) {
        masterPane.remove(centerPane);
        centerPane.setPreferredSize(new Dimension(scnW, scnH));
        centerPane.setMinimumSize(new Dimension(scnW, scnH));
        centerPane.setOpaque(false);
        centerPane.setLayout(null);
        masterPane.add(centerPane);
        frame.repaint();
        frame.revalidate();
    }

    /**
     *
     */
    public void factory(){

    }

//    public void frameSize(){
//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Dimension d = tk.getScreenSize();
//        System.out.println("Screen width = " + d.width);
//        System.out.println("Screen height = " + d.height);
//        scnW = d.width;
//        scnH = d.height;
//    }

    @Override
    public void componentResized(ComponentEvent e) {
        scnW = frame.getWidth();
        scnH = frame.getHeight();
        xOrigin = ((scnW-scnWMIN)/2);
        yOrigin = ((scnH-scnHMIN)/2);
        centerPane.setPreferredSize(new Dimension(scnW, scnH));
        centerPane.setMinimumSize(new Dimension(scnW, scnH));
        centerPane.setOpaque(false);
        centerPane.setLayout(null);
        GameScreen.updateBounds();
        LoginScreen.updateBounds();
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
        ClientModel clientModel = new ClientModel();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new ScreenFactory(clientModel);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
