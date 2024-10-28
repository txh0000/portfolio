package customComponent;

import constant.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button for configuration panel.
 */
public class NumOfPlayerButton extends JButton {

    private Image playerIcon;
    private int numOfPlayers;

    /**
     * Constructor for NumOfPlayerButton class.
     * @param numOfPlayers number of players on the button.
     * @param width width of the button.
     * @param height height of the button.
     */
    public NumOfPlayerButton(int numOfPlayers, int width, int height) {
        this.numOfPlayers = numOfPlayers;

        try {
            playerIcon = new ImageIcon(getClass().getClassLoader().getResource(Constant.PLAYER_ICON_PATH)).getImage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(width, height));
    };

    /**
     * Draw the player icon image on the button based on the number of players.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        switch (numOfPlayers) {
            case 1:
                g2.drawImage(playerIcon,getWidth()/2 - 100, getHeight()/2 - 100, 200, 200, null);
                break;
            case 2:
                g2.drawImage(playerIcon, 25, getHeight()/2 - 50,100, 100, null);
                g2.drawImage(playerIcon, 125, getHeight()/2 - 50,100, 100, null);
                break;
            case 3:
                g2.drawImage(playerIcon, getWidth()/2 - 50, getHeight()/2 - 100,100, 100, null);
                g2.drawImage(playerIcon, 25, getHeight()/2,100, 100, null);
                g2.drawImage(playerIcon, 125, getHeight()/2,100, 100, null);
                break;

            case 4:
                g2.drawImage(playerIcon, 25, getHeight()/2 - 115,100, 100, null);
                g2.drawImage(playerIcon, 25, getHeight()/2,100, 100, null);
                g2.drawImage(playerIcon, 125, getHeight()/2 - 115,100, 100, null);
                g2.drawImage(playerIcon, 125, getHeight()/2,100, 100, null);
                break;
        }
    }
}
