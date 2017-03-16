package CardGame.Gui;

import CardGame.ClientModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.LOBBYSCREEN;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * gameScreen
 * @author Alex
 *
 */
public class GameScreen extends JPanel {

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JTextArea textArea;
	private ClientModel clientModel;
	private JScrollPane scrollPane = new JScrollPane();
	private JLabel lblChat = new JLabel("Chat");
	private JButton btnSendMessage = new JButton();
	private JButton btnDoubledown = new JButton();
	private JButton btnStand = new JButton();
	private JButton btnHit = new JButton();
	private JButton btnFold = new JButton();
	private JLabel lblCredits = new JLabel("Credits: \u00A31000");
	private JLabel lblSubmitBet = new JLabel("Bet to be placed");
	private JButton btnSubmitBet = new JButton();
	private JButton btnBet1 = new JButton();
	private JButton btnBet2 = new JButton();
	private JButton btnBet3 = new JButton();
	private JButton btnBet4 = new JButton();
	private JButton btnLeaveGame = new JButton("Leave Game");
	private JLabel lblCreditsBox = new JLabel();
	private JLabel lblBetBox = new JLabel();
	private JLabel lblBackHud = new JLabel();

	/**
	 * Create the application.
	 */
	public GameScreen(ClientModel clientModel, ScreenFactory screenFactory) {
		this.clientModel = clientModel;
		this.screenFactory = screenFactory;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		/**
		 * Chat message box as a JScroll Pane
		 */
		scrollPane.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+40, 160, 340);
		add(scrollPane);

		/**
		 * chat label
		 */
		lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblChat.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+10, 205, 35);
		add(lblChat);

		/**
		 * Send message button
		 */
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendMessage.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+470, 159, 60);
		btnSendMessage.setContentAreaFilled(false);
		btnSendMessage.setBorderPainted(false);
		try {
			Image imgSendMessage = ImageIO.read(getClass().getResource("/gameHud/imageBtnMessage.png"));
			btnSendMessage.setIcon(new ImageIcon(imgSendMessage));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnSendMessage);

		/**
		 * Editable text area for sending messages
		 */
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+400, 160, 60);
		add(textArea);
		textArea.setColumns(10);

		/**
		 * Game buttons
		 */

		//DoubleDown Button
		btnDoubledown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDoubledown.setBounds(screenFactory.getxOrigin()+740, screenFactory.getyOrigin()+470, 98, 55);
		btnDoubledown.setContentAreaFilled(false);
		btnDoubledown.setBorderPainted(false);
		try {
			Image imgDoubleDown = ImageIO.read(getClass().getResource("/gameHud/imageBtnDoubleDown.png"));
			btnDoubledown.setIcon(new ImageIcon(imgDoubleDown));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnDoubledown);

		//Stand Button
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStand.setBounds(screenFactory.getxOrigin()+640, screenFactory.getyOrigin()+470, 98, 55);
		btnStand.setContentAreaFilled(false);
		btnStand.setBorderPainted(false);
		try {
			Image imgStand = ImageIO.read(getClass().getResource("/gameHud/imageBtnStand.png"));
			btnStand.setIcon(new ImageIcon(imgStand));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnStand);

		//Hit Button
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHit.setBounds(screenFactory.getxOrigin()+540, screenFactory.getyOrigin()+470, 98, 55);
		btnHit.setContentAreaFilled(false);
		btnHit.setBorderPainted(false);
		try {
			Image imgHit = ImageIO.read(getClass().getResource("/gameHud/imageBtnHit.png"));
			btnHit.setIcon(new ImageIcon(imgHit));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnHit);

		//Hold Button
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFold.setBounds(screenFactory.getxOrigin()+440, screenFactory.getyOrigin()+470, 98, 55);
		btnFold.setContentAreaFilled(false);
		btnFold.setBorderPainted(false);
		try {
			Image imgFold = ImageIO.read(getClass().getResource("/gameHud/imageBtnFold.png"));
			btnFold.setIcon(new ImageIcon(imgFold));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnFold);

		/**
		 * Pot label
		 */
//		JLabel lblNewLabel = new JLabel("Pot:");
//		lblNewLabel.setFont(new Font("Soho Std", Font.PLAIN, 18));
//		lblNewLabel.setBounds(383, 149, 46, 29);
//		add(lblNewLabel);

		/**
		 * Player credits label
		 */
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCredits.setForeground(Color.BLACK);
		lblCredits.setBounds(screenFactory.getxOrigin()+30, screenFactory.getyOrigin()+495, 92, 35);
		add(lblCredits);

		/**
		 * Player credits to bet label
		 */
		lblSubmitBet.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSubmitBet.setForeground(Color.BLACK);
		lblSubmitBet.setBounds(screenFactory.getxOrigin()+30, screenFactory.getyOrigin()+455, 92, 35);
		add(lblSubmitBet);

		//Submit Bet
		btnSubmitBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSubmitBet.setBounds(screenFactory.getxOrigin()+5, screenFactory.getyOrigin()+350, 98, 91);
		btnSubmitBet.setContentAreaFilled(false);
		btnSubmitBet.setBorderPainted(false);
		try {
			Image imgSubmitBet = ImageIO.read(getClass().getResource("/gameHud/imageBtnBet.png"));
			btnSubmitBet.setIcon(new ImageIcon(imgSubmitBet));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnSubmitBet);

		/**
		 * Bet Buttons
		 */
		//Bet 1: small
		btnBet1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet1.setBounds(screenFactory.getxOrigin()+105, screenFactory.getyOrigin()+370, 81, 81);
		btnBet1.setContentAreaFilled(false);
		btnBet1.setBorderPainted(false);
		try {
			Image imgBet1 = ImageIO.read(getClass().getResource("/gameHud/imageBtnBet1.png"));
			btnBet1.setIcon(new ImageIcon(imgBet1));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnBet1);


		//Bet 2: medium
		btnBet2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet2.setBounds(screenFactory.getxOrigin()+186, screenFactory.getyOrigin()+410, 81, 81);
		btnBet2.setContentAreaFilled(false);
		btnBet2.setBorderPainted(false);
		try {
			Image imgBet2 = ImageIO.read(getClass().getResource("/gameHud/imageBtnBet2.png"));
			btnBet2.setIcon(new ImageIcon(imgBet2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		add(btnBet2);

		//Bet 3: high
		btnBet3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet3.setBounds(screenFactory.getxOrigin()+267, screenFactory.getyOrigin()+440, 81, 81);
		btnBet3.setContentAreaFilled(false);
		btnBet3.setBorderPainted(false);
		try {
			Image imgBet3 = ImageIO.read(getClass().getResource("/gameHud/imageBtnBet3.png"));
			btnBet3.setIcon(new ImageIcon(imgBet3));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		add(btnBet3);

		//Bet 4: very high
		btnBet4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet4.setBounds(screenFactory.getxOrigin()+353, screenFactory.getyOrigin()+450, 81, 81);
		btnBet4.setContentAreaFilled(false);
		btnBet4.setBorderPainted(false);
		try {
			Image imgBet4 = ImageIO.read(getClass().getResource("/gameHud/imageBtnBet4.png"));
			btnBet4.setIcon(new ImageIcon(imgBet4));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		add(btnBet4);

		/**
		 * Player positions and cards
		 */
//		JLabel lblDeck = new JLabel();
//		lblDeck.setBounds(700, 100, 241, 42);
//		try {
//			Image imgDeck = ImageIO.read(getClass().getResource("/cards/000.png"));
//			lblDeck.setIcon(new ImageIcon(imgDeck));
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
//		add(lblDeck);

//		JTextArea txtPlayer1 = new JTextArea();
//		txtPlayer1.setLineWrap(true);
//		txtPlayer1.setText("Player1\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
//		txtPlayer1.setEditable(false);
//		txtPlayer1.setBounds(96, 305, 92, 109);
//		add(txtPlayer1);
//
//		JTextArea txtPlayer2 = new JTextArea();
//		txtPlayer2.setText("Player2\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
//		txtPlayer2.setLineWrap(true);
//		txtPlayer2.setEditable(false);
//		txtPlayer2.setBounds(233, 327, 92, 114);
//		add(txtPlayer2);
//
//		JTextArea txtPlayer3 = new JTextArea();
//		txtPlayer3.setText("Player3\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
//		txtPlayer3.setLineWrap(true);
//		txtPlayer3.setEditable(false);
//		txtPlayer3.setBounds(366, 344, 92, 114);
//		add(txtPlayer3);
//
//		JTextArea txtPlayer4 = new JTextArea();
//		txtPlayer4.setText("Player4\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
//		txtPlayer4.setLineWrap(true);
//		txtPlayer4.setEditable(false);
//		txtPlayer4.setBounds(505, 327, 92, 114);
//		add(txtPlayer4);
//
//		JTextArea txtPlayer5 = new JTextArea();
//		txtPlayer5.setText("Player5\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
//		txtPlayer5.setLineWrap(true);
//		txtPlayer5.setEditable(false);
//		txtPlayer5.setBounds(646, 305, 92, 109);
//		add(txtPlayer5);
//
//		JTextArea txtDealer = new JTextArea();
//		txtDealer.setText("Dealer\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
//		txtDealer.setLineWrap(true);
//		txtDealer.setEditable(false);
//		txtDealer.setBounds(163, 41, 92, 97);
//		add(txtDealer);

		btnLeaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    // todo make this a request leave game
			    getClientModel().setCurrentScreen(LOBBYSCREEN);
			}
		});
		btnLeaveGame.setBackground(Color.WHITE);
		btnLeaveGame.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnLeaveGame.setBounds(screenFactory.getxOrigin()+900, screenFactory.getyOrigin()+7, 104, 23);
		add(btnLeaveGame);


		/**
		 * Credits Box
		 */
		lblCreditsBox.setBounds(screenFactory.getxOrigin()+10, screenFactory.getyOrigin()+490, 241, 42);
		try {
			Image imgBoxCredits = ImageIO.read(getClass().getResource("/gameHud/imageBoxCredits.png"));
			lblCreditsBox.setIcon(new ImageIcon(imgBoxCredits));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblCreditsBox);

		/**
		 * Bet Box
		 */
		lblBetBox.setBounds(screenFactory.getxOrigin()+10, screenFactory.getyOrigin()+450, 144, 45);
		try {
			Image imgSubmitBox = ImageIO.read(getClass().getResource("/gameHud/imageBoxBet.png"));
			lblBetBox.setIcon(new ImageIcon(imgSubmitBox));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblBetBox);

		/**
		 * Back hud
		 */
		lblBackHud.setBounds(screenFactory.getxOrigin()-20, screenFactory.getyOrigin()+400, 1034, 204);
		try {
			Image imgHud = ImageIO.read(getClass().getResource("/gameHud/imageHud.png"));
			lblBackHud.setIcon(new ImageIcon(imgHud));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblBackHud);

	}

    public ClientModel getClientModel() {
        return clientModel;
    }
	public void updateBounds(){
		scrollPane.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+40, 160, 340);
		lblChat.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+10, 205, 35);
		btnSendMessage.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+470, 159, 60);
		textArea.setBounds(screenFactory.getxOrigin()+845, screenFactory.getyOrigin()+400, 160, 60);
		btnDoubledown.setBounds(screenFactory.getxOrigin()+740, screenFactory.getyOrigin()+470, 98, 55);
		btnStand.setBounds(screenFactory.getxOrigin()+640, screenFactory.getyOrigin()+470, 98, 55);
		btnHit.setBounds(screenFactory.getxOrigin()+540, screenFactory.getyOrigin()+470, 98, 55);
		btnFold.setBounds(screenFactory.getxOrigin()+440, screenFactory.getyOrigin()+470, 98, 55);
		lblCredits.setBounds(screenFactory.getxOrigin()+30, screenFactory.getyOrigin()+495, 92, 35);
		lblSubmitBet.setBounds(screenFactory.getxOrigin()+30, screenFactory.getyOrigin()+455, 92, 35);
		btnBet1.setBounds(screenFactory.getxOrigin()+105, screenFactory.getyOrigin()+370, 81, 81);
		btnBet2.setBounds(screenFactory.getxOrigin()+186, screenFactory.getyOrigin()+410, 81, 81);
		btnBet3.setBounds(screenFactory.getxOrigin()+267, screenFactory.getyOrigin()+440, 81, 81);
		btnBet4.setBounds(screenFactory.getxOrigin()+353, screenFactory.getyOrigin()+450, 81, 81);
		btnLeaveGame.setBounds(screenFactory.getxOrigin()+900, screenFactory.getyOrigin()+7, 104, 23);
		lblCreditsBox.setBounds(screenFactory.getxOrigin()+10, screenFactory.getyOrigin()+490, 241, 42);
		lblBetBox.setBounds(screenFactory.getxOrigin()+10, screenFactory.getyOrigin()+450, 144, 45);
		lblBackHud.setBounds(screenFactory.getxOrigin()-20, screenFactory.getyOrigin()+400, 1034, 204);
	}

}
