package game;

import constant.Constant;
import gameObject.CaveCard;
import gameObject.ChitCard;
import gameObject.VolcanoCard;
import gameObject.player.PlayerToken;
import panel.GamePanel;
import panel.StartPanel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * GameManager class used to share data between panel, implemented as singleton.
 */
public class GameManager {
    private static GameManager gameManager;
    public MainFrame window = MainFrame.getInstance();
    public int numberOfPlayers;
    public PlayerToken winner;
    public int numberOfBots;
    public int numberOfVolcanoCards;

    /**
     * Constructor for GameManager class.
     */
    private GameManager() {}

    /**
     * Used to get the single GameManager instance.
     * @return gameManager.
     */
    public static GameManager getInstance() {
        if (gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

    /**
     * Display the main window.
     */
    public void startWindow() {
        this.window.changeToPanel(new StartPanel());
        this.window.setVisible(true);
    }

    /**
     * Reset GameManager instance by creating a new instance.
     */
    public void restart() {
        gameManager = new GameManager();
        window.changeToPanel(new StartPanel());
    }

    /**
     * Method to load game save file.
     * @param file name of file to load.
     */
    public void loadGameFile(String file) {
        System.out.println(file);
        // create new instance of game
        Game game = new Game(false);
        try {

            // get the values for previous game state from text file
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (String line: lines) {
                String[] values;
                values = line.split(";");

                if (values[0].equals(Constant.GAME_MANAGER)) {
                    // set the state
                    numberOfPlayers = Integer.parseInt(values[1]);
                    numberOfBots = Integer.parseInt(values[2]);
                    numberOfVolcanoCards = Integer.parseInt(values[3]);
                }
                else if (values[0].equals(Constant.PLAYER_TOKEN)) {
                    // create and set the state of player tokens
                    PlayerToken.loadFromFile(values, game);
                }
                else if (values[0].equals(Constant.CHIT_CARD)) {
                    // create and set the state of chit cards
                    ChitCard.loadFromFile(values, game);
                }
                else if (values[0].equals(Constant.CAVE_CARD)) {
                    // create and set the state of cave cards
                    CaveCard.loadFromFile(values, game);
                }
                else if (values[0].equals(Constant.VOLCANO_CARD)) {
                    // create and set the state of volcano cards
                    VolcanoCard.loadFromFile(values, game);
                }
                else if (values[0].equals(Constant.GAME)) {
                    // create and set the state of the game
                    game.loadFromFile(values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create new game panel
        window.changeToPanel(new GamePanel(game));
    }


}
