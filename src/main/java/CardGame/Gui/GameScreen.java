package CardGame.Gui;

import CardGame.GameClient;
import CardGame.MessageObject;
import CardGame.Pushes.PushPlayerBudgets;
import CardGame.Responses.ResponseProtocol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * gameScreen
 *
 * @author Alex
 */
public class GameScreen extends JPanel implements Observer {

    private GameClient client;
    private ScreenFactory screenFactory;

	private JTextArea textArea;
	private JList<String> listChat;
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
	private JLabel lblSubmitBetBox;
	private JLabel lblBackHud;
    private JLabel lblSideHud;
    private JLabel lblSideFillHud;

    private JLabel lblDeck;
	private PlayerGui dealerGui;
    private PlayerGui playerGui1;
	private PlayerGui playerGui2;
	private PlayerGui playerGui3;
	private PlayerGui playerGui4;

    //current credits and bets
    private int amountToBet;
    private int credits;

    // chat variables
    public DefaultListModel<String> chatMessageModel;
    public int gameScreenChatOffset;

    /**
     * Create the application.
     */
    public GameScreen(GameClient gameClient, ScreenFactory screenFactory) {
        // we add this to list of observers
        this.client = gameClient;
        gameClient.addObserver(this);

        // chat variables

        this.screenFactory = screenFactory;
        this.amountToBet = amountToBet;
        this.credits = 1000;
        scrollPane = new JScrollPane();
        lblChat = new JLabel("Chat");
        btnSendMessage = new JButton();
        btnLeaveGame = new JButton("Leave");

        btnBet1 = new JButton();
        btnBet2 = new JButton();
        btnBet3 = new JButton();
        btnBet4 = new JButton();
        btnDoubleDown = new JButton();
        btnStand = new JButton();
        btnHit = new JButton();
        btnFold = new JButton();

        lblCredits = new JLabel("Credits: £ " + Integer.toString(credits));
        lblCreditsBox = new JLabel();
        lblSubmitBet = new JLabel(Integer.toString(amountToBet));
        lblSubmitBetBox = new JLabel();
        btnSubmitBet = new JButton();

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

        // chat variables
        this.gameScreenChatOffset = 0;
        this.chatMessageModel = new DefaultListModel<>();
        this.listChat = new JList<>(this.chatMessageModel);

        // create chat window
        this.listChat.setValueIsAdjusting(true);
        scrollPane = new JScrollPane(listChat);

        // set background and initialize
        setBackground(new Color(46, 139, 87));
        initialize();
        updateBounds();

        updateMessageList(gameClient);
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

                // show error in pop up box
                if (textArea.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "You can not send empty messages!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // send message from text area to server
                client.requestSendMessage(textArea.getText());
                textArea.setText("");
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
                client.requestDoubleBet();
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
                client.requestStand();
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
                client.requestHit();
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
                client.requestFold();
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
                ResponseProtocol response = client.requestBet(amountToBet);
                if (response.getRequestSuccess() == 1){
                    PushPlayerBudgets responseBudgets = client.requestGetPlayerBudgets();
                    if(responseBudgets.getRequestSuccess() == 1){
                        lblCredits.setText("Credits: £ " + responseBudgets.getPlayerBudgets().get(client.getLoggedInUser().getUserName()));
                    }
                }
                amountToBet = 0;
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


        /**
         * leave game button
         */
        btnLeaveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResponseProtocol leaveGame = client.requestQuitGame(client.getGameJoined());
                chatMessageModel.clear();
                gameScreenChatOffset = 0;
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
		add(lblDeck);}

		add(dealerGui);
		add(playerGui1);
		add(playerGui2);
		add(playerGui3);
		add(playerGui4);

	}
    public GameClient getClientModel() {
        return client;
    }

    public DefaultListModel<String> getChatMessageModel() {
        return chatMessageModel;
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


    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameClient) {
            GameClient model = (GameClient) observable;
            updateMessageList(model);


        }
    }

    private void updateMessageList(GameClient model) {
        // get clientGameOf
        int clientMsgOffset = model.getMessages().size();

        // add to list
        while (gameScreenChatOffset < clientMsgOffset){
            ArrayList<MessageObject> msg = new ArrayList<>(model.getMessages());
            this.chatMessageModel.addElement(msg.get(gameScreenChatOffset).toString());
            gameScreenChatOffset++;
        }
    }

}
