package panel;

import constant.Constant;
import game.Game;

import javax.swing.*;

/**
 * Class is used to create game screen panel.
 */
public class GamePanel extends JPanel{

    /**
     * Constructor for GamePanel class.
     */
    public GamePanel() {
        this.setLayout(null);
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        this.setBackground(Constant.BLUE);

        // add game board to the screen and start game
        Game game = new Game(true);
        this.add(game);
        game.runGame();
    }
    /**
     * Another Constructor for ConfigurationPanel class to use existing game.
     */
    public GamePanel(Game game) {
        this.setLayout(null);
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        this.setBackground(Constant.BLUE);

        // add game board to the screen and start game
        this.add(game);
        game.runGame();
    }

}
