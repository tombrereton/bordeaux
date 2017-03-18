package CardGame.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 18/03/2017.
 */
public class PlayerGui extends JPanel {

    private JLabel lblAvatar;
    private JLabel lblCard1;
    private JLabel lblCard2;
    private JLabel lblCard3;
    private JLabel lblCard4;
    private JLabel lblCard5;
    private JLabel lblCard6;
    private JLabel lblCard7;
    private JLabel lblCard8;
    private JLabel lblCard9;
    private JLabel lblCard10;
    private JLabel lblCard11;
    private JLabel lblCard12;

    public PlayerGui(){
        lblAvatar = new JLabel();
        lblCard1 = new JLabel();
        lblCard2 = new JLabel();
        lblCard3 = new JLabel();
        lblCard4 = new JLabel();
        lblCard5 = new JLabel();
        lblCard6 = new JLabel();
        lblCard7 = new JLabel();
        lblCard8 = new JLabel();
        lblCard9 = new JLabel();
        lblCard10 = new JLabel();
        lblCard11 = new JLabel();
        lblCard12 = new JLabel();
        initialize();
    }

    public void initialize() {

        setSize(new Dimension(200,150));

        //Set the initial avatar to an empty chair
        try {
            Image imgAvatar = ImageIO.read(getClass().getResource("/avatar/0.png"));
            lblAvatar.setIcon(new ImageIcon(imgAvatar));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblAvatar);

        //set the bounds of the avatar and card images
        lblAvatar.setBounds(0, 70, 64, 64);
        lblCard1.setBounds(0, 0, 64, 93);
        lblCard2.setBounds(10, 10, 64, 93);
        lblCard2.setBounds(20, 20, 64, 93);
        lblCard2.setBounds(30, 30, 64, 93);
        lblCard2.setBounds(40, 40, 64, 93);
        lblCard2.setBounds(50, 40, 64, 93);
        lblCard2.setBounds(60, 40, 64, 93);
        lblCard2.setBounds(70, 40, 64, 93);
        lblCard2.setBounds(80, 40, 64, 93);
        lblCard2.setBounds(90, 40, 64, 93);
        lblCard2.setBounds(100, 40, 64, 93);
        lblCard2.setBounds(110, 40, 64, 93);

    }

    public void setLblAvatar(String avatarID) {
        remove(lblAvatar);
        try {
            Image imgAvatar = ImageIO.read(getClass().getResource("/avatar/"+avatarID+".png"));
            lblAvatar.setIcon(new ImageIcon(imgAvatar));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblAvatar);
    }

    public void setLblCard1(String cardID) {
        remove(lblCard1);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard1.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard1);
    }

    public void setLblCard2(String cardID) {
        remove(lblCard2);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard2.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard2);
    }
    public void setLblCard3(String cardID) {
        remove(lblCard3);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard3.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard3);
    }

    public void setLblCard4(String cardID) {
        remove(lblCard4);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard4.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard4);
    }

    public void setLblCard5(String cardID) {
        remove(lblCard5);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard5.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard5);
    }

    public void setLblCard6(String cardID) {
        remove(lblCard6);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard6.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard6);
    }

    public void setLblCard7(String cardID) {
        remove(lblCard7);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard7.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard7);
    }

    public void setLblCard8(String cardID) {
        remove(lblCard8);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard8.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard8);
    }

    public void setLblCard9(String cardID) {
        remove(lblCard9);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard9.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard9);
    }

    public void setLblCard10(String cardID) {
        remove(lblCard10);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard10.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard10);
    }

    public void setLblCard11(String cardID) {
        remove(lblCard11);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard11.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard11);
    }

    public void setLblCard12(String cardID) {
        remove(lblCard12);
        try {
            Image imgCard = ImageIO.read(getClass().getResource("/cards/"+cardID+".png"));
            lblCard12.setIcon(new ImageIcon(imgCard));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        add(lblCard12);
    }

}
