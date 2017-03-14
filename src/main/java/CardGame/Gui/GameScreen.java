package CardGame.Gui;

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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(809, 41, 181, 373);
		add(scrollPane);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblChat.setBounds(809, 11, 205, 35);
		add(lblChat);
		
		JButton btnSendMessage = new JButton("Send");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendMessage.setBackground(Color.WHITE);
		btnSendMessage.setFont(new Font("Soho Std", Font.PLAIN, 18));
		btnSendMessage.setBounds(809, 498, 181, 29);
		add(btnSendMessage);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(809, 425, 181, 62);
		add(textArea);
		textArea.setColumns(10);
		
		JButton btnDoubledown = new JButton("Double Down");
		btnDoubledown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDoubledown.setBackground(Color.WHITE);
		btnDoubledown.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnDoubledown.setBounds(671, 492, 109, 35);
		add(btnDoubledown);
		
		JButton btnStand = new JButton("Stand");
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStand.setBackground(Color.WHITE);
		btnStand.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnStand.setBounds(539, 493, 109, 35);
		add(btnStand);
		
		JButton btnHit = new JButton("Hit");
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHit.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnHit.setBackground(Color.WHITE);
		btnHit.setBounds(405, 492, 109, 35);
		add(btnHit);
		
		JLabel lblNewLabel = new JLabel("Pot:");
		lblNewLabel.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblNewLabel.setBounds(383, 149, 46, 29);
		add(lblNewLabel);
		
		JLabel lblCredits = new JLabel("Credits: \u00A31000");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCredits.setBounds(54, 452, 92, 35);
		add(lblCredits);
		
		JButton btnBet1 = new JButton("10");
		btnBet1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet1.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnBet1.setBackground(Color.WHITE);
		btnBet1.setBounds(54, 492, 60, 35);
		add(btnBet1);
		
		JButton btnBet2 = new JButton("50");
		btnBet2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet2.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnBet2.setBackground(Color.WHITE);
		btnBet2.setBounds(123, 492, 60, 35);
		add(btnBet2);
		
		JButton btnBet3 = new JButton("100");
		btnBet3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet3.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnBet3.setBackground(Color.WHITE);
		btnBet3.setBounds(195, 492, 60, 35);
		add(btnBet3);
		
		JButton btnBet4 = new JButton("500");
		btnBet4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBet4.setFont(new Font("Soho Std", Font.PLAIN, 12));
		btnBet4.setBackground(Color.WHITE);
		btnBet4.setBounds(265, 492, 60, 35);
		add(btnBet4);
		
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
}
