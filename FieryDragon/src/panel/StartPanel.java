package panel;
import game.GameManager;
import constant.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * Class is used to create start screen panel.
 */
public class StartPanel extends JPanel {
    private Image backgroundImage;

    /**
     * Constructor for StartPanel class.
     */
    public StartPanel() {
        Constant.PATH_SUFFIX = "_ryan.png";
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        try {
            backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(Constant.START_SCREEN_BACKGROUND_IMAGE + Constant.PATH_SUFFIX)).getImage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // add title
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.8;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        JLabel title = new JLabel("FIERY DRAGON BOARD GAME");
        title.setFont(Constant.FONT_50);
        this.add(title, c);

        // add button
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.1;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_START;
        JButton playButton = new JButton("PLAY");
        playButton.setFont(Constant.FONT_50);
        playButton.setPreferredSize(new Dimension(400, 150));
        playButton.setFocusPainted(false);

        // add action listener
        playButton.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.window.changeToPanel(new ConfigurationPanel());
        });
        this.add(playButton, c);


        // add theme 1 button
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 0;
        c2.weighty = 0;
        c2.weightx = 0;
        c2.fill = GridBagConstraints.NONE;
        c2.anchor = GridBagConstraints.FIRST_LINE_START;
        JButton theme1Button = new JButton("Theme 1");
        theme1Button.setFont(Constant.FONT_50);
        theme1Button.setPreferredSize(new Dimension(270, 60)); // Adjust the size if needed
        theme1Button.setFocusPainted(false);

        // Add action listener
        theme1Button.addActionListener(e -> {

            Constant.PATH_SUFFIX = "_ryan.png";

            try {
                backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(Constant.START_SCREEN_BACKGROUND_IMAGE + Constant.PATH_SUFFIX)).getImage();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            repaint();


        });
        this.add(theme1Button, c2);


        // Add theme 2 button
        c2.gridx = 0;
        c2.gridy = 0;
        c2.weighty = 0;
        c2.weightx = 0;
        c2.fill = GridBagConstraints.NONE;
        c2.anchor = GridBagConstraints.PAGE_START;
        JButton theme2Button = new JButton("Theme 2");
        theme2Button.setFont(Constant.FONT_50);
        theme2Button.setPreferredSize(new Dimension(270, 60)); // Adjust the size if needed
        theme2Button.setFocusPainted(false);

        // Add action listener
        theme2Button.addActionListener(e -> {

            Constant.PATH_SUFFIX = "_wj.png";

            try {
                backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(Constant.START_SCREEN_BACKGROUND_IMAGE + Constant.PATH_SUFFIX)).getImage();
            }
            catch (Exception e3) {
                e3.printStackTrace();
            }
            repaint();

        });
        this.add(theme2Button, c2);

        // add button
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 0.1;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_START;
        JButton button2 = new JButton("LOAD GAME");
        button2.setFont(Constant.FONT_50);
        button2.setPreferredSize(new Dimension(400, 75));
        button2.setFocusPainted(false);

        // add action listener
        button2.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            // go to file selection window
            g.window.changeToPanel(new GameFileSelectionPanel());
        });
        this.add(button2, c);
    }

    /**
     * Used to draw the background image on the screen.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
