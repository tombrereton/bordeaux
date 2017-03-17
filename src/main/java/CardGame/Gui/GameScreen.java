package CardGame.Gui;

import CardGame.ClientModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static CardGame.Gui.Screens.LOBBYSCREEN;

/**
 * gameScreen
 * @author Alex
 *
 */
public class GameScreen extends JPanel {

	private ClientModel clientModel;
	private ScreenFactory screenFactory;

	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JLabel lblChat;
	private JButton btnSendMessage;
	private JButton btnDoubleDown;
	private JButton btnStand;
	private JButton btnHit;
	private JButton btnFold;
	private JLabel lblCredits;
	private JLabel lblSubmitBet;
	private JButton btnSubmitBet;
	private JButton btnBet1;
	private JButton btnBet2;
	private JButton btnBet3;
	private JButton btnBet4;
	private JButton btnLeaveGame;
	private JLabel lblCreditsBox;
	private JLabel lblBetBox;
	private JLabel lblBackHud;
    private JLabel lblSideHud;
    private JLabel lblSideFillHud;

    private int amountToBet;
    private int credits;

	/**
	 * Create the application.
	 */
	public GameScreen(ClientModel clientModel, ScreenFactory screenFactory) {
		this.clientModel = clientModel;
		this.screenFactory = screenFactory;
		this.amountToBet = amountToBet;
		this.credits = 1000;

        scrollPane = new JScrollPane();
        lblChat = new JLabel("Chat");
        btnSendMessage = new JButton();
        btnDoubleDown = new JButton();
        btnStand = new JButton();
        btnHit = new JButton();
        btnFold = new JButton();
        lblCredits = new JLabel("Credits: £ "+ Integer.toString(credits));
        lblSubmitBet = new JLabel(Integer.toString(amountToBet));
        btnSubmitBet = new JButton();
        btnBet1 = new JButton();
        btnBet2 = new JButton();
        btnBet3 = new JButton();
        btnBet4 = new JButton();
        btnLeaveGame = new JButton("Leave Game");
        lblCreditsBox = new JLabel();
        lblBetBox = new JLabel();
        lblBackHud = new JLabel();
        lblSideHud = new JLabel();
        lblSideFillHud = new JLabel();
        setBackground(new Color(46, 139, 87));
        initialize();
		updateBounds();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		/**
		 * Chat message box as a JScroll Pane
		 */
		add(scrollPane);

		/**
		 * chat label
		 */
		lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblChat.setForeground(Color.WHITE);
		add(lblChat);

		/**
		 * Send message button
		 */
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"You can not send empty messages!",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				textArea.setText("");
				clientModel.requestSendMessages(textArea.getText());
				textArea.grabFocus();

			}
		});
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
		add(textArea);
		textArea.setColumns(10);

		/**
		 * Game buttons
		 */

		//DoubleDown Button
		btnDoubleDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDoubleDown.setContentAreaFilled(false);
		btnDoubleDown.setBorderPainted(false);
		try {
			Image imgDoubleDown = ImageIO.read(getClass().getResource("/gameHud/imageBtnDoubleDown.png"));
			btnDoubleDown.setIcon(new ImageIcon(imgDoubleDown));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(btnDoubleDown);

		//Stand Button
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		 * Player credits label
		 */
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCredits.setForeground(Color.BLACK);
		add(lblCredits);

		/**
		 * Player credits to bet label
		 */
		lblSubmitBet.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSubmitBet.setForeground(Color.BLACK);
		add(lblSubmitBet);

		//Submit Bet
		btnSubmitBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    credits = credits - amountToBet;
                amountToBet = 0;
                lblCredits.setText("Credits: £ "+ Integer.toString(credits));
                lblSubmitBet.setText(Integer.toString(amountToBet));
			}
		});
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
                amountToBet += 5;
                lblSubmitBet.setText(Integer.toString(amountToBet));
			}
		});
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
                amountToBet += 10;
                lblSubmitBet.setText(Integer.toString(amountToBet));
			}
		});
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
                amountToBet += 20;
                lblSubmitBet.setText(Integer.toString(amountToBet));
			}
		});
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
                amountToBet += 50;
                lblSubmitBet.setText(Integer.toString(amountToBet));
			}
		});
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


		btnLeaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    // todo make this a request leave game
			    getClientModel().setCurrentScreen(LOBBYSCREEN);
			}
		});
		btnLeaveGame.setBackground(Color.WHITE);
		btnLeaveGame.setFont(new Font("Soho Std", Font.PLAIN, 11));
		add(btnLeaveGame);


		/**
		 * Credits Box
		 */
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
		try {
			Image imgHud = ImageIO.read(getClass().getResource("/gameHud/imageHud.png"));
			lblBackHud.setIcon(new ImageIcon(imgHud));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblBackHud);

        lblSideFillHud.setOpaque(true);
        lblSideFillHud.setBackground(new Color(127, 37, 27));
        add(lblSideFillHud);

        try {
            Image imgSideHud = ImageIO.read(getClass().getResource("/gameHud/imageHudSide.png"));
            lblSideHud.setIcon(new ImageIcon(imgSideHud));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblSideHud);

	}

    public ClientModel getClientModel() {
        return clientModel;
    }


	public void updateBounds(){
		scrollPane.setBounds(screenFactory.getxOrigin()+850, 50,screenFactory.getxOrigin()+150, screenFactory.getyOrigin()+360);
		textArea.setBounds(screenFactory.getxOrigin()+850, screenFactory.getyOrigin()+420, screenFactory.getxOrigin()+150, screenFactory.getyOrigin()+50);
        lblChat.setBounds(screenFactory.getxOrigin()+850, 10, 205, 35);
        lblSideHud.setBounds(screenFactory.getxOrigin()+800, screenFactory.getScreenHeightCurrent()-1390, 66, 1324);
        lblSideFillHud.setBounds(screenFactory.getxOrigin()+850, 0,screenFactory.getScreenWidthCurrent(), screenFactory.getyOrigin()+600);
        btnSendMessage.setBounds(845+(int)(screenFactory.getxOrigin()*1.5), screenFactory.getScreenHeightCurrent()-105, 159, 60);
		btnLeaveGame.setBounds(screenFactory.getScreenWidthCurrent()-120, 12, 100, 23);
		btnDoubleDown.setBounds(740, screenFactory.getScreenHeightCurrent()-100, 98, 55);
		btnStand.setBounds(640, screenFactory.getScreenHeightCurrent()-100, 98, 55);
		btnHit.setBounds(540, screenFactory.getScreenHeightCurrent()-100, 98, 55);
		btnFold.setBounds(440, screenFactory.getScreenHeightCurrent()-100, 98, 55);
		lblCredits.setBounds(30, screenFactory.getScreenHeightCurrent()-81, 200, 35);
		lblSubmitBet.setBounds(30, screenFactory.getScreenHeightCurrent()-121, 92, 35);
        btnSubmitBet.setBounds(5, screenFactory.getScreenHeightCurrent()-226, 98, 91);
		btnBet1.setBounds(105, screenFactory.getScreenHeightCurrent()-206, 81, 81);
		btnBet2.setBounds(186, screenFactory.getScreenHeightCurrent()-166, 81, 81);
		btnBet3.setBounds(267, screenFactory.getScreenHeightCurrent()-136, 81, 81);
		btnBet4.setBounds(353, screenFactory.getScreenHeightCurrent()-126, 81, 81);
		lblCreditsBox.setBounds(10, screenFactory.getScreenHeightCurrent()-86, 241, 42);
		lblBetBox.setBounds(10, screenFactory.getScreenHeightCurrent()-126, 144, 45);
		lblBackHud.setBounds(-20, screenFactory.getScreenHeightCurrent()-176, 2590, 204);
	}

    public int getAmountToBet() {
        return amountToBet;
    }

    public void setAmountToBet(int amountToBet) {
        this.amountToBet = amountToBet;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }


//	public void StartCheckingMessages(){
//
//		Thread thread = new Thread(new Runnable() {

//			@Override
//			public void run() {
//				while (true) {
//					ResponseGetMessages response = clientModel.requestGetMessages();
//					if (response[0].equals("get-message")) {
//						DefaultListModel<String> model = (DefaultListModel<String>) listChat.getModel();
//						for (int i = 1; i < response.length; i = i + 4) {
//							if (ChatClientApp.frame.client.offset < Integer.parseInt(response[i])) {
//								ChatClientApp.frame.client.offset = Integer.parseInt(response[i]);
//								model.addElement(String.format("%s @ (%s): %s", response.getMessages(), response[i + 2], response[i + 3]));
//								try {
//									Thread.sleep(10);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//							}
//						}
//
//					}
//					if (response[0].equals("send-message")) {
//						if (response[1].equals("true")) {
//							System.out.println("Message sent.");
//						} else {
//							JOptionPane.showMessageDialog(ChatClientApp.frame,
//									"Cannot send message!",
//									"Error",
//									JOptionPane.WARNING_MESSAGE);
//						}
//					}
//				}
//			}
//		});
//		thread.start();
//		timer = new java.util.Timer();
//		timer.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				ChatClientApp.frame.client.get_message();
//			}
//		}, 1000, 2000);

//	}
}
