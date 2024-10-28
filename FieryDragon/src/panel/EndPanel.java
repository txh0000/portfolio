package panel;

import constant.Constant;
import game.GameManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class is used to create end screen panel.
 */
public class EndPanel extends JPanel {

    /**
     * Constructor for EndPanel class.
     */
    public EndPanel() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        this.setBackground(Constant.LIGHT_PINK);

        // add winning text
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.6;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        JLabel winTxt = new JLabel(String.format("WINNER IS PLAYER %d!!!", GameManager.getInstance().winner.getPlayerId() + 1));
        winTxt.setFont(Constant.FONT_50);
        winTxt.setHorizontalAlignment(JLabel.CENTER);
        this.add(winTxt,c);

        // add restart button
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.4;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        JButton restartButton = new JButton("PLAY AGAIN");
        restartButton.setFont(Constant.FONT_20);
        restartButton.setPreferredSize(new Dimension(300, 100));
        restartButton.setHorizontalAlignment(JButton.CENTER);
        restartButton.addActionListener(e -> GameManager.getInstance().restart());

        this.add(restartButton, c);

    }

    /**
     * Used to draw the winning player image on screen.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(GameManager.getInstance().winner.getPlayerToken(), getWidth()/2 - 100, getHeight()/2 - 100, 200, 200, null);
    }
}
