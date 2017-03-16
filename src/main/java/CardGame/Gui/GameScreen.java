package CardGame.Gui;

import CardGame.ClientModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * gameScreen
 * @author Alex
 *
 */
public class GameScreen extends JPanel {

	private JTextArea textArea;
	private ClientModel clientModel;

	/**
	 * Create the application.
	 */
	public GameScreen(ClientModel clientModel) {
		this.clientModel = clientModel;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//setSize(1024,576);
		//setLayout(null);
		//ScreenFactory.setPanelToFrame();

		/**
		 * Chat message box as a JScroll Pane
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(845, 40, 160, 340);
		add(scrollPane);

		/**
		 * chat label
		 */
		JLabel lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblChat.setBounds(845, 10, 205, 35);
		add(lblChat);

		/**
		 * Send message button
		 */
		JButton btnSendMessage = new JButton();
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendMessage.setBounds(845, 470, 159, 60);
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
		textArea.setBounds(845, 400, 160, 60);
		add(textArea);
		textArea.setColumns(10);

		/**
		 * Game buttons
		 */

		//DoubleDown Button
		JButton btnDoubledown = new JButton();
		btnDoubledown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDoubledown.setBounds(740, 470, 98, 55);
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
		JButton btnStand = new JButton();
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStand.setBounds(640, 470, 98, 55);
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
		JButton btnHit = new JButton();
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHit.setBounds(540, 470, 98, 55);
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
		JButton btnFold = new JButton();
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFold.setBounds(440, 470, 98, 55);
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
		JLabel lblCredits = new JLabel("Credits: \u00A31000");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCredits.setForeground(Color.BLACK);
		lblCredits.setBounds(30, 495, 92, 35);
		add(lblCredits);

		/**
		 * Player credits to bet label
		 */
		JLabel lblSubmitBet = new JLabel("Bet to be placed");
		lblSubmitBet.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSubmitBet.setForeground(Color.BLACK);
		lblSubmitBet.setBounds(30, 455, 92, 35);
		add(lblSubmitBet);

		//Submit Bet
		JButton btnSubmitBet = new JButton();
		btnSubmitBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSubmitBet.setBounds(5, 350, 98, 91);
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
		JButton btnBet1 = new JButton();
		btnBet1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet1.setBounds(105, 370, 81, 81);
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
		JButton btnBet2 = new JButton();
		btnBet2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet2.setBounds(186, 410, 81, 81);
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
		JButton btnBet3 = new JButton();
		btnBet3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet3.setBounds(267, 440, 81, 81);
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
		JButton btnBet4 = new JButton();
		btnBet4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet4.setBounds(353, 450, 81, 81);
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

		JButton btnLeaveGame = new JButton("Leave Game");
		btnLeaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.lobbyScreen);
			}
		});
		btnLeaveGame.setBackground(Color.WHITE);
		btnLeaveGame.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnLeaveGame.setBounds(900, 7, 104, 23);
		add(btnLeaveGame);


		/**
		 * Credits Box
		 */
		JLabel lblCreditsBox = new JLabel();
		lblCreditsBox.setBounds(10, 490, 241, 42);
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
		JLabel lblBetBox = new JLabel();
		lblBetBox.setBounds(10, 450, 144, 45);
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
		JLabel lblBackHud = new JLabel();
		lblBackHud.setBounds(-20, 400, 1034, 204);
		try {
			Image imgHud = ImageIO.read(getClass().getResource("/gameHud/imageHud.png"));
			lblBackHud.setIcon(new ImageIcon(imgHud));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblBackHud);

	}

}
