package CardGame.Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Class that creates and controls the frame and the panels to be displayed
 * @author Alex
 *
 */
public class ScreenFactory extends JFrame {
	
//	private ImageIcon icon;
//	private JLabel label;
	
	/**
	 * sets up the screen guis
	 * screen ratio 16:9
	 */
	public static JPanel masterPane;
	public static JPanel centerPane;
	public static JPanel backgroundPane;
	public static ScreenFactory frame = null;
	public LoginScreen LoginScreen = new LoginScreen();
	public HomeScreen HomeScreen = new HomeScreen();
	public CreateAccountScreen CreateAccountScreen = new CreateAccountScreen();
	public SettingsScreen SettingsScreen = new SettingsScreen();
	public StatisticsScreen StatisticsScreen = new StatisticsScreen();
	public LobbyScreen LobbyScreen = new LobbyScreen();
	public GameScreen GameScreen = new GameScreen();

	
	/**
	 * screen size variables
	 * smallest screen resolution
	 */
	public static int scnW = 1024;
	public static int scnH = 576;
	
	/**
	 * Constructor for the frame
	 */
	public ScreenFactory(){
		setTitle("CardGame");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,scnW,scnH);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(scnW, scnH));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		centerPane = LoginScreen;
		centerPane.setOpaque(false);
		centerPane.setPreferredSize(new Dimension(scnW,scnH));
		centerPane.setMinimumSize(new Dimension(scnW,scnH));
		centerPane.setLayout(null);
		masterPane = new JPanel(new GridBagLayout());
		masterPane.setBackground(new Color(46, 139, 87));
		
//		backgroundPane = new JPanel();
//		BufferedImage img = null;
//		try {
//		    img = ImageIO.read(new File("resources/bgimage.jpg"));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//		Image dimg = img.getScaledInstance(1920, 1080,Image.SCALE_SMOOTH);
//		icon = new ImageIcon(dimg);
//	    label = new JLabel(icon);
//	    backgroundPane.add(label);
//	    backgroundPane.setBounds(0, 0, getWidth(), getHeight());
//	    add(backgroundPane);
	    
	    masterPane.add(centerPane);
		add(masterPane);
	}

	
	/**
	 * method for changing between panels
	 * @param panelAdd Panel to add
	 */
	public static void setPane(JPanel panelAdd){
		masterPane.remove(centerPane);
		centerPane = panelAdd;
		centerPane.setPreferredSize(new Dimension(scnW,scnH));
		centerPane.setMinimumSize(new Dimension(scnW,scnH));
		centerPane.setOpaque(false);
		centerPane.setLayout(null);
		masterPane.add(centerPane);
		frame.repaint();
		frame.revalidate();
	}
	

	/**
	 * main method with which to run the gui
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ScreenFactory();
					frame.setVisible(true);
//					frame.client.setHost(args[0]);
//					frame.client.setPort(Integer.parseInt(args[1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
