package game;

import constant.Constant;

import javax.swing.*;

/**
 * Main window, inherits JFrame, implemented as a singleton.
 */
public class MainFrame extends JFrame {

    private static MainFrame mainFrame;
    private JPanel currP;

    /**
     * Constructor for MainFrame class.
     */
    private MainFrame() {
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("FIERY DRAGON BOARD GAME");
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
    }

    /**
     * Used to get the single MainFrame instance.
     * @return mainFrame.
     */
    public static MainFrame getInstance() {
        if (mainFrame == null) mainFrame = new MainFrame();
        return mainFrame;
    }

    /**
     * Change the panel.
     * @param newP the new panel to change to.
     */
    public void changeToPanel(JPanel newP) {
        if (currP != null) {
            this.remove(currP);
        }
        this.currP = newP;
        this.add(newP);

        this.revalidate();
        this.repaint();
    }
}
