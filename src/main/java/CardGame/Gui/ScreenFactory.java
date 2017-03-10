package CardGame.Gui;

import javax.swing.*;
import java.awt.*;

public class ScreenFactory extends JFrame {
	
	/**
	 * sets up the screen guis
	 */
	public static ScreenFactory frame = null;
	public LoginScreen LoginScreen = new LoginScreen();
	public HomeScreen HomeScreen = new HomeScreen();
	public CreateAccountScreen CreateAccountScreen = new CreateAccountScreen();
	
	/**
	 * screen size variables
	 */
	private int minWidth = 600;
	private int minHeight = 400;
	
	/**
	 * Constructor for the frame
	 */
	public ScreenFactory(){
		setTitle("CardGame");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,minWidth,minHeight);
		setLocationRelativeTo(null);
		setContentPane(LoginScreen);
	}
	
	/**
	 * method for changing between panels
	 * @param panelRemove Panel to remove
	 * @param panelAdd Panel to add
	 */
	public static void setPane(JPanel panelRemove,JPanel panelAdd){
		frame.remove(panelRemove);
		frame.setContentPane(panelAdd);
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
