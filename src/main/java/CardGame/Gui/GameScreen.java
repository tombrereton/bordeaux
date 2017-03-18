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

	//message panel
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JLabel lblChat;
	private JButton btnSendMessage;
	private JButton btnLeaveGame;

	//Hud buttons
	private JButton btnSubmitBet;
	private JButton btnBet1;
	private JButton btnBet2;
	private JButton btnBet3;
	private JButton btnBet4;
	private JButton btnDoubleDown;
	private JButton btnStand;
	private JButton btnHit;
	private JButton btnFold;

	//Credits boxes
	private JLabel lblCredits;
	private JLabel lblSubmitBet;
	private JLabel lblCreditsBox;
	private JLabel lblSubmitBetBox;

	//Hud background
	private JLabel lblBackHud;
    private JLabel lblSideHud;
    private JLabel lblSideFillHud;

    //board images and players
    private JLabel lblDeck;
	private PlayerGui dealerGui;
    private PlayerGui playerGui1;
	private PlayerGui playerGui2;
	private PlayerGui playerGui3;
	private PlayerGui playerGui4;

    //current credits and bets
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

		//message panel
		textArea = new JTextArea();
        scrollPane = new JScrollPane();
        lblChat = new JLabel("Chat");
        btnSendMessage = new JButton();
        btnLeaveGame = new JButton("Leave");

        //Hud buttons
		btnSubmitBet = new JButton();
        btnBet1 = new JButton();
        btnBet2 = new JButton();
        btnBet3 = new JButton();
        btnBet4 = new JButton();
        btnDoubleDown = new JButton();
        btnStand = new JButton();
        btnHit = new JButton();
        btnFold = new JButton();

        //Credits boxes
        lblCredits = new JLabel("Credits: £ "+ Integer.toString(credits));
        lblCreditsBox = new JLabel();
        lblSubmitBet = new JLabel(Integer.toString(amountToBet));
        lblSubmitBetBox = new JLabel();

		//Hud background
        lblBackHud = new JLabel();
        lblSideHud = new JLabel();
        lblSideFillHud = new JLabel();

		//board images and players
		lblDeck = new JLabel();
		playerGui1 = new PlayerGui("14");
		playerGui2 = new PlayerGui("31");
		playerGui3 = new PlayerGui("5");
		playerGui4 = new PlayerGui("3");
		dealerGui = new PlayerGui("1");

        setBackground(new Color(46, 139, 87));
        initialize();
		updateBounds();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//Message panel: textArea, scrollPane, lblChat, btnSendMessage, btnLeaveGame
		textArea.setLineWrap(true);
		add(textArea);
		textArea.setColumns(10);

		add(scrollPane);

		lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
		lblChat.setForeground(Color.WHITE);
		add(lblChat);

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

		btnLeaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// todo make this a request leave game
				getClientModel().setCurrentScreen(LOBBYSCREEN);
			}
		});
		btnLeaveGame.setBackground(Color.WHITE);
		btnLeaveGame.setFont(new Font("Soho Std", Font.PLAIN, 13));
		add(btnLeaveGame);


		//Hud Buttons: btnSubmitBet, btnBet1, btnBet2, btnBet3, btnBet4, btnDoubleDown, btnStand, btnHit, btnFold
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

		//Credits boxes: lblCredits, lblSubmitBet, lblCreditsBox,lblSubmitBetBox
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCredits.setForeground(Color.BLACK);
		add(lblCredits);

		/**
		 * Player credits to bet label
		 */
		lblSubmitBet.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSubmitBet.setForeground(Color.BLACK);
		add(lblSubmitBet);


		try {
			Image imgBoxCredits = ImageIO.read(getClass().getResource("/gameHud/imageBoxCredits.png"));
			lblCreditsBox.setIcon(new ImageIcon(imgBoxCredits));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblCreditsBox);

		try {
			Image imgSubmitBox = ImageIO.read(getClass().getResource("/gameHud/imageBoxBet.png"));
			lblSubmitBetBox.setIcon(new ImageIcon(imgSubmitBox));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblSubmitBetBox);


		//Hud background, lblBackHud, lblSideHud, lblSideFillHud
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


        //Player positions and board images: lblDeck, playerGui1, playerGui2, playerGui3, playerGui4
		try {
			Image imgDeck = ImageIO.read(getClass().getResource("/cards/000.png"));
			lblDeck.setIcon(new ImageIcon(imgDeck));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add(lblDeck);

		add(dealerGui);
		add(playerGui1);
		add(playerGui2);
		add(playerGui3);
		add(playerGui4);

	}

    public ClientModel getClientModel() {
        return clientModel;
    }


	public void updateBounds(){
		scrollPane.setBounds(screenFactory.getxOrigin()+850, 50,screenFactory.getxOrigin()+150, screenFactory.getScreenHeightCurrent()-230);
		textArea.setBounds(screenFactory.getxOrigin()+850, screenFactory.getScreenHeightCurrent()-170, screenFactory.getxOrigin()+150, 60);
        lblChat.setBounds(screenFactory.getxOrigin()+850, 10, 205, 35);
        lblSideHud.setBounds(screenFactory.getxOrigin()+800, screenFactory.getScreenHeightCurrent()-1500, 66, 1434);
        lblSideFillHud.setBounds(screenFactory.getxOrigin()+850, 0,screenFactory.getScreenWidthCurrent(), screenFactory.getyOrigin()+800);
        btnSendMessage.setBounds(845+(int)(screenFactory.getxOrigin()*1.5), screenFactory.getScreenHeightCurrent()-105, 159, 60);
		btnLeaveGame.setBounds(screenFactory.getScreenWidthCurrent()-120, 10, 100, 30);
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
		lblSubmitBetBox.setBounds(10, screenFactory.getScreenHeightCurrent()-126, 144, 45);
		lblBackHud.setBounds(-20, screenFactory.getScreenHeightCurrent()-176, 2590, 204);
        lblDeck.setBounds(screenFactory.getxOrigin()+650, 20, 64, 93);

		dealerGui.setBounds((int)(screenFactory.getxOrigin()*0.5)+320, 20, 200, 200);
		playerGui1.setBounds(20, screenFactory.getScreenHeightCurrent()-425, 200, 200);
		playerGui2.setBounds((int)(screenFactory.getxOrigin()*0.3)+220, screenFactory.getScreenHeightCurrent()-350, 200, 200);
		playerGui3.setBounds((int)(screenFactory.getxOrigin()*0.7)+420, screenFactory.getScreenHeightCurrent()-350, 200, 200);
		playerGui4.setBounds(screenFactory.getxOrigin()+620, screenFactory.getScreenHeightCurrent()-425, 200, 200);
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
