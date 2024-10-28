package panel;

import constant.Constant;
import game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
/**
 * Class is used to create game file selection screen panel.
 */
public class GameFileSelectionPanel extends JPanel {
    /**
     * Constructor for GameFileSelectionPanel class.
     */
    public GameFileSelectionPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        this.setBackground(Constant.BLUE);

        // add button
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.25;
        c.weighty = 0.7;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(20, 10, 10, 0);
        JButton btn1 = new JButton("SAVE FILE 1");
        btn1.setPreferredSize(new Dimension(300, 600));
        btn1.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.loadGameFile(Constant.SAVE_FILE_1);
        });
        File file = new File(Constant.SAVE_FILE_1);
        if (!file.exists()) {
            btn1.setEnabled(false);
        }
            this.add(btn1, c);

        // add button
        c.gridx = 1;
        JButton btn2 = new JButton("SAVE FILE 2");
        btn2.setPreferredSize(new Dimension(300, 600));
        btn2.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.loadGameFile(Constant.SAVE_FILE_2);
        });
        File file2 = new File(Constant.SAVE_FILE_2);
        if (!file2.exists()) {
            btn2.setEnabled(false);
        }
        this.add(btn2, c);

        // add button
        c.gridx = 2;
        JButton btn3 = new JButton("SAVE FILE 3");
        btn3.setPreferredSize(new Dimension(300, 600));
        btn3.addActionListener(e -> {
            GameManager g = GameManager.getInstance();
            g.loadGameFile(Constant.SAVE_FILE_3);
        });
        File file3 = new File(Constant.SAVE_FILE_3);
        if (!file3.exists()) {
            btn3.setEnabled(false);
        }
        this.add(btn3, c);

        // add back button
        c.gridy = 1;
        c.weighty = 0.3;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(10, 10, 20, 50);
        JButton backBtn = new JButton("GO BACK");
        backBtn.setPreferredSize(new Dimension(100, 50));
        backBtn.addActionListener(e -> GameManager.getInstance().window.changeToPanel(new StartPanel()));
        this.add(backBtn, c);
    }
}
