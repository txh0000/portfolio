package panel;

import customComponent.NumOfPlayerButton;
import game.GameManager;
import constant.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * Class is used to create configuration screen panel.
 */
public class ConfigurationPanel extends JPanel {

    /**
     * Constructor for ConfigurationPanel class.
     */
    public ConfigurationPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        this.setBackground(Constant.BLUE);

        // add button
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.25;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        NumOfPlayerButton btn1 = new NumOfPlayerButton(1, 250, 800);
        btn1.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.numberOfPlayers = 1;
            g.numberOfBots = 3;
            g.numberOfVolcanoCards = 24;
            g.window.changeToPanel(new GamePanel());

        });
        this.add(btn1, c);

        // add button
        c.gridx = 1;
        NumOfPlayerButton btn2 = new NumOfPlayerButton(2, 250, 800);
        btn2.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.numberOfPlayers = 2;
            g.numberOfBots = 2;
            g.numberOfVolcanoCards = 24;
            g.window.changeToPanel(new GamePanel());

        });
        this.add(btn2, c);

        // add button
        c.gridx = 2;
        NumOfPlayerButton btn3 = new NumOfPlayerButton(3, 250, 800);
        btn3.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.numberOfPlayers = 3;
            g.numberOfBots = 1;
            g.numberOfVolcanoCards = 24;
            g.window.changeToPanel(new GamePanel());

        });
        this.add(btn3, c);

        // add button4
        c.gridx = 3;
        NumOfPlayerButton btn4 = new NumOfPlayerButton(4, 250, 800);
        btn4.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.numberOfPlayers = 4;
            g.numberOfBots = 0;
            g.numberOfVolcanoCards = 24;
            g.window.changeToPanel(new GamePanel());
        });
        this.add(btn4, c);
    }
}
