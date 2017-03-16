package CardGame.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * gameScreen
 * @author Alex
 *
 */
public class GameScreen extends JPanel {

	private JTextArea textArea;
	private static Graphics g;

	/**
	 * Create the application.
	 */
	public GameScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setSize(1024,576);
		setLayout(null);

		/**
		 * Chat message box as a JScroll Pane
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(809, 41, 181, 373);
		add(scrollPane);

		/**
		 * chat label
		 */
		JLabel lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblChat.setBounds(809, 11, 205, 35);
		add(lblChat);

		/**
		 * Send message button
		 */
		JButton btnSendMessage = new JButton("Send");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendMessage.setBackground(Color.WHITE);
		btnSendMessage.setFont(new Font("Soho Std", Font.PLAIN, 18));
		btnSendMessage.setBounds(809, 498, 181, 29);
		add(btnSendMessage);

		/**
		 * Editable text area for sending messages
		 */
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(809, 425, 181, 62);
		add(textArea);
		textArea.setColumns(10);

		/**
		 * Game buttons
		 */

		//DoubleDown Button
		JButton btnDoubledown = new JButton("Double Down");
		btnDoubledown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDoubledown.setBackground(Color.WHITE);
		btnDoubledown.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnDoubledown.setBounds(675, 492, 80, 35);
		add(btnDoubledown);

		//Stand Button
		JButton btnStand = new JButton("Stand");
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStand.setBackground(Color.WHITE);
		btnStand.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnStand.setBounds(585, 493, 80, 35);
		add(btnStand);

		//Hit Button
		JButton btnHit = new JButton("Hit");
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHit.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnHit.setBackground(Color.WHITE);
		btnHit.setBounds(495, 492, 80, 35);
		add(btnHit);

		//Hold Button
		JButton btnFold = new JButton("Fold");
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFold.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnFold.setBackground(Color.WHITE);
		btnFold.setBounds(405, 492, 80, 35);
		add(btnFold);

		/**
		 * Pot label
		 */
		JLabel lblNewLabel = new JLabel("Pot:");
		lblNewLabel.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblNewLabel.setBounds(383, 149, 46, 29);
		add(lblNewLabel);

		/**
		 * Player credits label
		 */
		JLabel lblCredits = new JLabel("Credits: \u00A31000");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCredits.setForeground(Color.WHITE);
		lblCredits.setBounds(30, 500, 92, 35);
		add(lblCredits);

		/**
		 * Bet Buttons
		 */

		//Bet 1: small
		JButton btnBet1 = new JButton();
		btnBet1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet1.setBounds(10, 380, 96, 96);
		btnBet1.setContentAreaFilled(false);
		btnBet1.setBorderPainted(false);
		try {
			Image imgBet1 = ImageIO.read(getClass().getResource("/chips/chipBlack.png"));
			btnBet1.setIcon(new ImageIcon(imgBet1));
		} catch (Exception ex) {
			System.out.println(ex);
		}
//		btnBet1.setFont(new Font("Soho Std", Font.PLAIN, 12));
//		btnBet1.setForeground(Color.WHITE);
		add(btnBet1);


		//Bet 2: medium
		JButton btnBet2 = new JButton();
		btnBet2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet2.setBounds(106, 405, 96, 96);
		btnBet2.setContentAreaFilled(false);
		btnBet2.setBorderPainted(false);
		try {
			Image imgBet2 = ImageIO.read(getClass().getResource("/chips/chipBlue.png"));
			btnBet2.setIcon(new ImageIcon(imgBet2));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnBet2);

		//Bet 3: high
		JButton btnBet3 = new JButton();
		btnBet3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet3.setBounds(202, 420, 96, 96);
		btnBet3.setContentAreaFilled(false);
		btnBet3.setBorderPainted(false);
		try {
			Image imgBet3 = ImageIO.read(getClass().getResource("/chips/chipPurple.png"));
			btnBet3.setIcon(new ImageIcon(imgBet3));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnBet3);

		//Bet 4: very high
		JButton btnBet4 = new JButton();
		btnBet4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet4.setBounds(298, 430, 96, 96);
		btnBet4.setContentAreaFilled(false);
		btnBet4.setBorderPainted(false);
		try {
			Image imgBet4 = ImageIO.read(getClass().getResource("/chips/chipRed.png"));
			btnBet4.setIcon(new ImageIcon(imgBet4));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnBet4);

		/**
		 * Player positions
		 */
		JTextArea txtPlayer1 = new JTextArea();
		txtPlayer1.setLineWrap(true);
		txtPlayer1.setText("Player1\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
		txtPlayer1.setEditable(false);
		txtPlayer1.setBounds(96, 305, 92, 109);
		add(txtPlayer1);

		JTextArea txtPlayer2 = new JTextArea();
		txtPlayer2.setText("Player2\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
		txtPlayer2.setLineWrap(true);
		txtPlayer2.setEditable(false);
		txtPlayer2.setBounds(233, 327, 92, 114);
		add(txtPlayer2);

		JTextArea txtPlayer3 = new JTextArea();
		txtPlayer3.setText("Player3\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
		txtPlayer3.setLineWrap(true);
		txtPlayer3.setEditable(false);
		txtPlayer3.setBounds(366, 344, 92, 114);
		add(txtPlayer3);

		JTextArea txtPlayer4 = new JTextArea();
		txtPlayer4.setText("Player4\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
		txtPlayer4.setLineWrap(true);
		txtPlayer4.setEditable(false);
		txtPlayer4.setBounds(505, 327, 92, 114);
		add(txtPlayer4);

		JTextArea txtPlayer5 = new JTextArea();
		txtPlayer5.setText("Player5\nUsername\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
		txtPlayer5.setLineWrap(true);
		txtPlayer5.setEditable(false);
		txtPlayer5.setBounds(646, 305, 92, 109);
		add(txtPlayer5);

		JTextArea txtDealer = new JTextArea();
		txtDealer.setText("Dealer\nCard#1:\r\nCard#2:\nCard#3:\nCard#4:");
		txtDealer.setLineWrap(true);
		txtDealer.setEditable(false);
		txtDealer.setBounds(163, 41, 92, 97);
		add(txtDealer);

		JButton btnLeaveGame = new JButton("Leave Game");
		btnLeaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenFactory.setPane(ScreenFactory.frame.lobbyScreen);
			}
		});
		btnLeaveGame.setBackground(Color.WHITE);
		btnLeaveGame.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnLeaveGame.setBounds(886, 7, 104, 23);
		add(btnLeaveGame);


	}

	/**
	 * Paint Graphics
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color hudColour = new Color(120, 44, 44);
		g.setColor(hudColour);
		g.fillRect(0, 480, 1024, 80);
	}

}
