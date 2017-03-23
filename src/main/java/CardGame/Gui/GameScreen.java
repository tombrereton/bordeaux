package CardGame.Gui;

import CardGame.GameClient;
import CardGame.GameEngine.Card;
import CardGame.MessageObject;
import CardGame.Responses.ResponseProtocol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


/**
 * Class for the game screen
 *
 * @author Alex
 */
public class GameScreen extends JPanel implements Observer {

    //Client and main GUI class
    private GameClient client;
    private BlackjackOnline blackjackOnline;

    //Message sidebar components
    private JTextArea textArea;
    private JList<String> listChat;
    private JScrollPane scrollPane;
    private JLabel lblChat;
    private JButton btnSendMessage;
    private JButton btnLeaveGame;
    private JLabel lblBackHud;
    private JLabel lblSideHud;
    private JLabel lblSideFillHud;

    //HUD panel components
    private JButton btnSubmitBet;
    private JButton btnBet1;
    private JButton btnBet2;
    private JButton btnBet3;
    private JButton btnBet4;
    private JButton btnHit;
    private JButton btnStand;
    private JLabel lblBudget;
    private JLabel lblSubmitBet;
    private JLabel lblBudgetBox;
    private JLabel lblSubmitBetBox;

    //Game components
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

    // game variables
    private int dealerHandOffset;
    private int player0HandOffset;
    private int player1HandOffset;
    private int player2HandOffset;
    private int player3HandOffset;
    private boolean gameScreenChatOffsetAllPlayerFinished;

    /**
     * Instantiates the client and GUI, sets the background and initialises the components
     */
    public GameScreen(GameClient gameClient, BlackjackOnline blackjackOnline) {
        // we add this to list of observers
        this.client = gameClient;
        gameClient.addObserver(this);

        //game variables
        this.dealerHandOffset = 0;
        this.player0HandOffset = 0;
        this.player1HandOffset = 0;
        this.player2HandOffset = 0;
        this.player3HandOffset = 0;
        this.gameScreenChatOffsetAllPlayerFinished = true;

        // chat variables
        this.blackjackOnline = blackjackOnline;
        this.amountToBet = amountToBet;
        this.credits = 100;

        //Message sidebar components
        scrollPane = new JScrollPane();
        lblChat = new JLabel("Chat");
        btnSendMessage = new JButton();
        btnLeaveGame = new JButton("Leave");
        textArea = new JTextArea();
        lblBackHud = new JLabel();
        lblSideHud = new JLabel();
        lblSideFillHud = new JLabel();

        //HUD panel components
        btnSubmitBet = new JButton();
        btnBet1 = new JButton();
        btnBet2 = new JButton();
        btnBet3 = new JButton();
        btnBet4 = new JButton();
        btnStand = new JButton();
        btnHit = new JButton();
        lblBudget = new JLabel("Credits: £ " + Integer.toString(credits));
        lblBudgetBox = new JLabel();
        lblSubmitBet = new JLabel(Integer.toString(amountToBet));
        lblSubmitBetBox = new JLabel();

        //Game components
        lblDeck = new JLabel();
        playerGui1 = new PlayerGui();
        playerGui2 = new PlayerGui();
        playerGui3 = new PlayerGui();
        playerGui4 = new PlayerGui();
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

        resetHands();
    }

    /**
     * Initialise the components of the panel for labels, text fields and buttons.
     * Method run as part of the constructor.
     */
    private void initialize() {

        //INITIALISE MESSAGE COMPONENTS
        add(scrollPane);

        //chat label
        lblChat.setFont(new Font("Soho Std", Font.PLAIN, 18));
        lblChat.setForeground(Color.WHITE);
        add(lblChat);

        // SEND BUTTON. Display the error if not successful
        btnSendMessage.addActionListener(e -> {

            // show error in pop up box
            if (textArea.getText().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "You can not send empty messages!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // send message from text area to server
            ResponseProtocol responseProtocol = client.requestSendMessage(textArea.getText());
            textArea.setText("");
            textArea.grabFocus();

            // We display the error if not successful
            int success = responseProtocol.getRequestSuccess();
            String errorMsg = responseProtocol.getErrorMsg();
            if (success == 0) {
                JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btnSendMessage.setContentAreaFilled(false);
        btnSendMessage.setBorderPainted(false);

        // add send button image
        try {
            Image imgSendMessage = ImageIO.read(getClass().getResource("/gameHud/imageBtnMessage.png"));
            btnSendMessage.setIcon(new ImageIcon(imgSendMessage));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(btnSendMessage);
        // END SEND BUTTON

        // CHAT MESSAGE BOX
        textArea.setLineWrap(true);
        add(textArea);
        textArea.setColumns(10);
        // END CHAT MESSAGE BOX

        // LEAVE BUTTON
        btnLeaveGame.addActionListener(e -> {
            ResponseProtocol leaveGame = client.requestQuitGame(client.getGameJoined());

            if (leaveGame.getRequestSuccess() == 1) {
                chatMessageModel.clear();
                gameScreenChatOffset = 0;

                // reset all game data on the client
                getClientModel().resetGameDataWhenQuitting();

                // reset game data on game screen
                resetHands();
            }
        });
        btnLeaveGame.setBackground(Color.WHITE);
        btnLeaveGame.setFont(new Font("Soho Std", Font.PLAIN, 11));
        add(btnLeaveGame);
        // END LEAVE BUTTON


        // STAND BUTTON. Display the error if not successful
        btnStand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // send a stand request to server
                ResponseProtocol responseProtocol = client.requestStand();

                // We display the error if not successful
                int success = responseProtocol.getRequestSuccess();
                String errorMsg = responseProtocol.getErrorMsg();

                if (success == 0) {
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
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
        // END STAND BUTTON

        // HIT BUTTON. Display the error if not successful
        btnHit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // send a hit request to server
                ResponseProtocol responseProtocol = client.requestHit();

                // We display the error if not successful
                int success = responseProtocol.getRequestSuccess();
                String errorMsg = responseProtocol.getErrorMsg();
                if (success == 0) {
                    JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }

                resetHands();
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
        // END HIT BUTTON

        /**
         * Player credits label
         */
        lblBudget.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblBudget.setForeground(Color.BLACK);

        add(lblBudget);

        /**
         * Player credits to bet label
         */
        lblSubmitBet.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblSubmitBet.setForeground(Color.BLACK);
        add(lblSubmitBet);

        // BET BUTTON
        /**
         * Submit Bet Button
         *
         * And display the error if not successful
         */
        btnSubmitBet.addActionListener(e -> {

            // we send the request to bet
            ResponseProtocol response = client.requestBet(amountToBet);

            if (response.getRequestSuccess() == 0) {
                // display error msg if not successful
                String errorMsg = response.getErrorMsg();
                JOptionPane.showMessageDialog(null, errorMsg, "Warning",
                        JOptionPane.WARNING_MESSAGE);

                // set bet amount to 0
                amountToBet = 0;
                lblSubmitBet.setText(Integer.toString(amountToBet));
                return;
            }

            // set bet amount to 0
            amountToBet = 0;
            lblSubmitBet.setText(Integer.toString(amountToBet));

            // reset offsets to 0
            resetHands();

        });

        // set image for bet
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
        btnBet1.addActionListener(e -> {
            amountToBet += 5;
            lblSubmitBet.setText(Integer.toString(amountToBet));
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
        btnBet2.addActionListener(e -> {
            amountToBet += 10;
            lblSubmitBet.setText(Integer.toString(amountToBet));
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
        btnBet3.addActionListener(e -> {
            amountToBet += 20;
            lblSubmitBet.setText(Integer.toString(amountToBet));
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
         * Credits Box
         */

        try {
            Image imgBoxCredits = ImageIO.read(getClass().getResource("/gameHud/imageBoxCredits.png"));
            lblBudgetBox.setIcon(new ImageIcon(imgBoxCredits));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblBudgetBox);

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


        //HUD BACKGROUND
        try {
            Image imgHud = ImageIO.read(getClass().getResource("/gameHud/imageHud.png"));
            lblBackHud.setIcon(new ImageIcon(imgHud));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblBackHud);

        //SIDE FILLED HUD
        lblSideFillHud.setOpaque(true);
        lblSideFillHud.setBackground(new Color(127, 37, 27));
        add(lblSideFillHud);

        //SIDE HUD IMAGE
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

    public GameClient getClientModel() {
        return client;
    }

    public DefaultListModel<String> getChatMessageModel() {
        return chatMessageModel;
    }

    public void updateBounds() {
        scrollPane.setBounds(blackjackOnline.getxScreenDiff() + 850, 50, blackjackOnline.getxScreenDiff() + 150, blackjackOnline.getScreenHeightCurrent() - 230);
        textArea.setBounds(blackjackOnline.getxScreenDiff() + 850, blackjackOnline.getScreenHeightCurrent() - 170, blackjackOnline.getxScreenDiff() + 150, 60);
        lblChat.setBounds(blackjackOnline.getxScreenDiff() + 850, 10, 205, 35);
        lblSideHud.setBounds(blackjackOnline.getxScreenDiff() + 800, blackjackOnline.getScreenHeightCurrent() - 1500, 66, 1434);
        lblSideFillHud.setBounds(blackjackOnline.getxScreenDiff() + 850, 0, blackjackOnline.getScreenWidthCurrent(), blackjackOnline.getScreenHeightCurrent());
        btnSendMessage.setBounds(845 + (int) (blackjackOnline.getxScreenDiff() * 1.5), blackjackOnline.getScreenHeightCurrent() - 105, 159, 60);
        btnLeaveGame.setBounds(blackjackOnline.getScreenWidthCurrent() - 120, 10, 100, 30);
        btnStand.setBounds(540, blackjackOnline.getScreenHeightCurrent() - 100, 98, 55);
        btnHit.setBounds(440, blackjackOnline.getScreenHeightCurrent() - 100, 98, 55);
        lblBudget.setBounds(30, blackjackOnline.getScreenHeightCurrent() - 81, 200, 35);
        lblSubmitBet.setBounds(30, blackjackOnline.getScreenHeightCurrent() - 121, 92, 35);
        btnSubmitBet.setBounds(5, blackjackOnline.getScreenHeightCurrent() - 226, 98, 91);
        btnBet1.setBounds(105, blackjackOnline.getScreenHeightCurrent() - 206, 81, 81);
        btnBet2.setBounds(186, blackjackOnline.getScreenHeightCurrent() - 166, 81, 81);
        btnBet3.setBounds(267, blackjackOnline.getScreenHeightCurrent() - 136, 81, 81);
        btnBet4.setBounds(353, blackjackOnline.getScreenHeightCurrent() - 126, 81, 81);
        lblBudgetBox.setBounds(10, blackjackOnline.getScreenHeightCurrent() - 86, 241, 42);
        lblSubmitBetBox.setBounds(10, blackjackOnline.getScreenHeightCurrent() - 126, 144, 45);
        lblBackHud.setBounds(-20, blackjackOnline.getScreenHeightCurrent() - 176, 2590, 204);
        lblDeck.setBounds(blackjackOnline.getxScreenDiff() + 650, 20, 64, 93);

        dealerGui.setBounds((int) (blackjackOnline.getxScreenDiff() * 0.5) + 320, 20, 200, 200);
        playerGui1.setBounds(20, blackjackOnline.getScreenHeightCurrent() - 425, 200, 200);
        playerGui2.setBounds((int) (blackjackOnline.getxScreenDiff() * 0.3) + 220, blackjackOnline.getScreenHeightCurrent() - 350, 200, 200);
        playerGui3.setBounds((int) (blackjackOnline.getxScreenDiff() * 0.7) + 420, blackjackOnline.getScreenHeightCurrent() - 350, 200, 200);
        playerGui4.setBounds(blackjackOnline.getxScreenDiff() + 620, blackjackOnline.getScreenHeightCurrent() - 425, 200, 200);
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

    /**
     * A helper method to get the correct GUI of each player
     *
     * @param index
     * @return
     */
    public PlayerGui getplayerGui(int index) {
        PlayerGui playerGui = new PlayerGui();
        if (index >= 0 && index < 5) {
            switch (index) {
                case 0:
                    playerGui = playerGui1;
                    break;
                case 1:
                    playerGui = playerGui2;
                    break;
                case 2:
                    playerGui = playerGui3;
                    break;
                case 3:
                    playerGui = playerGui4;
                    break;

                default:
                    playerGui = new PlayerGui();
                    break;

            }
        }
        return playerGui;
    }

    public PlayerGui getDealerGui() {
        return dealerGui;
    }

    /**
     * A helper method to set each player's card
     *
     * @param gui
     * @param index
     * @param valuestr
     */
    public void setLbCards(PlayerGui gui, int index, String valuestr) {
        if (index >= 0 && index < 5) {
            switch (index) {
                case 0:
                    gui.setLblCard1(valuestr);
                    break;
                case 1:
                    gui.setLblCard2(valuestr);
                    break;
                case 2:
                    gui.setLblCard3(valuestr);
                    break;
                case 3:
                    gui.setLblCard4(valuestr);
                    break;
                case 4:
                    gui.setLblCard5(valuestr);
                    break;
                case 5:
                    gui.setLblCard6(valuestr);
                    break;
                case 6:
                    gui.setLblCard7(valuestr);
                    break;
                case 7:
                    gui.setLblCard8(valuestr);
                    break;
                case 8:
                    gui.setLblCard9(valuestr);
                    break;
                case 9:
                    gui.setLblCard10(valuestr);
                    break;
                case 10:
                    gui.setLblCard11(valuestr);
                    break;
                case 11:
                    gui.setLblCard12(valuestr);
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * This method is called when the observable class calls notifyObservers.
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameClient) {
            GameClient model = (GameClient) observable;

            // display popup when server down
            showWarningWhenServerDown(model);

            // update message box with messages
            updateMessageList(model);

            // update your budget
            if (model.getPlayerBudgets() != null && !model.getPlayerBudgets().isEmpty()) {
                int yourBudget = model.getPlayerBudgets().get(model.getLoggedInUser().getUserName());
                this.lblBudget.setText("Budget: £" + yourBudget);
            }

            // we update the dealer hand
            updateDealerHand(model);


            // set players' information and set cards to players
            if (model.getPlayerNames() != null && !model.getPlayerNames().isEmpty()) {

                for (int i = 0; i < model.getPlayerNames().size(); i++) {
                    String playerName = model.getPlayerNames().get(i);
                    String playerBudget = model.getPlayerBudgets().get(playerName) + "";
                    String playerBets = model.getPlayerBets().get(playerName) + "";
                    String playerAvatar = model.getPlayerAvatars().get(i);

                    // set each player's name
                    getplayerGui(i).setLblName(playerName);

                    // set each player's credit
                    getplayerGui(i).setLblBudget(playerBudget);

                    // set each player's bet
                    getplayerGui(i).setLblBetAmount(playerBets);

                    // set each player's card
                    updatePlayerHand(model, playerName, i);

                    // set each player's avatar
                    getplayerGui(i).setLblAvatar(playerAvatar);

                    // check if the player is win or lose
                    checkwin(model);

                }
            }
        }
    }

    private void checkwin(GameClient model) {
        Map<String, Boolean> playerWon = model.getPlayersWon();
        if (model.isAllPlayersFinished()) {
            if (playerWon != null && playerWon.size() > 0 && playerWon.get(model.getLoggedInUser().getUserName())) {
                JOptionPane.showMessageDialog(null, "Congratulations, you win!", "Congratulations",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    private void showWarningWhenServerDown(GameClient model) {
        if (model.isServerDown() && model.getCurrentScreen() == Screens.GAMESCREEN) {
            JOptionPane.showMessageDialog(null, "Server down. Will try to reconnect 3 times." +
                            "\nRestart Blackjack online if failed to reconnect after 15 seconds.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateDealerHand(GameClient model) {
        // set dealer cards
        if (model.getDealerHand() != null) {
            int dealerHandSize = model.getDealerHand().getHand().size();

            if (dealerHandSize >= 2 && model.isAllPlayersFinished()) {
                setLbCards(dealerGui, 0, model.getDealerHand().getCard(0).getImageID());
                repaint();
                revalidate();
            }
            while (dealerHandOffset < dealerHandSize) {
                // only iterate over new cards
                setLbCards(dealerGui, dealerHandOffset, model.getDealerHand().getCard(dealerHandOffset).getImageID());
                getDealerGui().refreshPlayerGui();
                dealerHandOffset++;
                repaint();
                revalidate();
            }
        }
    }

    /**
     * This is a helper function to get the
     * respective player hand offset.
     *
     * @param player
     * @return
     */
    private int getPlayerHandOffset(int player) {
        switch (player) {
            case 0:
                return player0HandOffset;
            case 1:
                return player1HandOffset;
            case 2:
                return player2HandOffset;
            case 3:
                return player3HandOffset;
            default:
                break;
        }
        return 0;
    }

    /**
     * This method sets the relevant player hand offset.
     *
     * @param player
     * @param offset
     */
    private void setPlayerHandOffset(int player, int offset) {
        // Bug fixed: !IMPORTANT please write break after you write the case
        switch (player) {
            case 0:
                this.player0HandOffset = offset;
                break;
            case 1:
                this.player1HandOffset = offset;
                break;
            case 2:
                this.player2HandOffset = offset;
                break;
            case 3:
                this.player3HandOffset = offset;
                break;

        }

    }

    private void updatePlayerHand(GameClient model, String playerName, int i) {


        // set each player's card
        ArrayList<Card> playersCards = model.getPlayerHands().get(playerName).getHand();
        int playerHandSize = playersCards.size();

        //get this player's hand offset
        int playerHandOffset = getPlayerHandOffset(i);

        while (playerHandOffset < playerHandSize) {
            setLbCards(getplayerGui(i), playerHandOffset, playersCards.get(playerHandOffset).getImageID());
            playerHandOffset++;
            getplayerGui(i).refreshPlayerGui();
            repaint();
            revalidate();
        }
        // we set the playerHandOffset after iterating
        setPlayerHandOffset(i, playerHandOffset);
    }

    public void resetHands() {
        // we reset the client player and dealer hands
        getClientModel().resetDealerAndPlayerHands();

        // reset the gamescreen dealer hand offset
        dealerHandOffset = 0;

        // we reset the dealer gui hand to a blank image
        for (int i = 0; i < 12; i++) {
            setLbCards(dealerGui, i, "400");
        }


        // we reset the player cards to a blank image
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                setLbCards(getplayerGui(i), j, "400");
            }
        }

        // we reset all the game screen player hand offsets to 0
        for (int i = 0; i < 4; i++) {
            this.setPlayerHandOffset(i, 0);
        }

        repaint();
        revalidate();
    }

    private void updateMessageList(GameClient model) {
        // get clientGameOf
        int clientMsgOffset = model.getMessages().size();

        // add to list
        while (gameScreenChatOffset < clientMsgOffset) {
            ArrayList<MessageObject> msg = new ArrayList<>(model.getMessages());
            this.chatMessageModel.addElement(msg.get(gameScreenChatOffset).toString());
            gameScreenChatOffset++;
        }
    }


}