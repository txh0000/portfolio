package game;

import Factory.*;
import GameState.*;

import GameState.GameState;
import animal.*;
import constant.Constant;
import gameObject.*;
import gameObject.player.Bot;
import gameObject.player.Human;
import gameObject.player.PlayerToken;
import panel.EndPanel;
import panel.StartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * Class to run game loop and contain all game components.
 */
public class Game extends JLayeredPane implements Runnable{
    public ArrayList<PlayerToken> playerTokens;
    public ArrayList<ChitCard> chitCards;
    public ArrayList<VolcanoCard> volcanoCards;
    public ArrayList<CaveCard> caveCards;
    private Thread gameThread;
    private ActionHandler actionHandler;
    private int turnNumber;
    public boolean running;
    public GameState gameState;
    public boolean allowCardToBeSelected;
    public ChitCard selectedCard;
    private JLabel currentStateLabel;
    private JButton saveButton1;
    private JButton saveButton2;
    private JButton saveButton3;
    private JButton quitButton;

    /**
     * Constructor for Game class.
     */
    public Game(boolean newGame) {
        this.actionHandler = new ActionHandler(this);

        this.setLayout(null);
        this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        this.volcanoCards = new ArrayList<>();
        this.caveCards = new ArrayList<>();
        this.chitCards = new ArrayList<>();
        this.playerTokens = new ArrayList<>();

        this.createSaveButton();
        this.running = true;
        this.allowCardToBeSelected = false;
        this.createLabels(GameManager.getInstance().numberOfPlayers, GameManager.getInstance().numberOfBots);

        if (newGame) {
            // create objects
            this.createVolcanoCards();
            this.createCaveCards();
            this.createChitCards(actionHandler);
            this.createPlayers(GameManager.getInstance().numberOfPlayers, GameManager.getInstance().numberOfBots);
            this.turnNumber = 0;
            this.gameState = new CardSelectionState();
        }
    }

    /**
     * Getter for actionHandler.
     * @return actionHandler.
     */
    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    /**
     * Start game loop.
     */
    public void runGame() {
        // start thread
        gameThread = new Thread(this);
        running = true;
        gameThread.start();

        // set starting state
        gameState.execute(this, playerTokens.get(turnNumber));
    }

    /**
     * run game loop.
     */
    @Override
    public void run() {
        double interval = (double) 1000000000 / Constant.FPS;
        double nextInterval = System.nanoTime() + interval;

        // game loop to repaint objects at fix rate
        while (this.isVisible()) {

            // redraw
            repaint();

            // make sure game runs at 60 fps
            double remainingTime = nextInterval - System.nanoTime();
            if (remainingTime > 0) {
                try {
                    Thread.sleep((long) remainingTime / 1000000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Getter to get turnNumber.
     * @return turnNumber.
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Change currentStateLabel's text.
     * @param text String to change to.
     */
    public void changeStateLabelText(String text) {
        this.currentStateLabel.setText(text);
    }

    /**
     * Increase to the next turn number.
     */
    public void incrementTurnNumber() {
        // reset board
        for (ChitCard card: chitCards) {
            card.reset();
        }
        turnNumber = (turnNumber + 1) % (GameManager.getInstance().numberOfPlayers + GameManager.getInstance().numberOfBots);
    }

    /**
     * Change state and execute new state.
     * @param newGameState state to change to.
     */
    public void notifyGameStateChange(GameState newGameState) {
        this.gameState = newGameState;

        // if game is still running
        if (running) {
             gameState.execute(this, playerTokens.get(turnNumber));

             // enable save button if card selection state
            if (gameState.getClass().toString().equals(CardSelectionState.class.toString())) {
                saveButton1.setEnabled(true);
                saveButton2.setEnabled(true);
                saveButton3.setEnabled(true);
            }
            else {
                saveButton1.setEnabled(false);
                saveButton2.setEnabled(false);
                saveButton3.setEnabled(false);
            }
        }
        else {
            // go to end screen
            GameManager.getInstance().window.changeToPanel(new EndPanel());
        }
    }

    /**
     * Draw the volcano cards circle.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw volcano cards circle
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3.0f));

        // outer circle
        g2.setColor(Constant.BEIGE);
        g2.fillOval(getWidth()/2 - 375, getHeight()/2 - 375, 750, 750);

        // inner circle
        g2.setColor(Color.darkGray);
        g2.fillOval(getWidth()/2 - 250, getHeight()/2 - 250, 500, 500);

        // outline
        g2.setColor(Color.BLACK);
        g2.drawOval(getWidth()/2 - 375, getHeight()/2 - 375, 750, 750);
        g2.drawOval(getWidth()/2 - 250, getHeight()/2 - 250, 500, 500);
    }

    /**
     * Create players.
     * @param numberOfPlayers number of players to create.
     */
    public void createPlayers(int numberOfPlayers, int numberOfBots) {
        PlayerTokenFactory gameObjectFactoryPlayer = new PlayerTokenFactory();
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerToken playerToken = gameObjectFactoryPlayer.createPlayerToken(new Human(i));

            switch (i) {
                case 0:
                    playerToken.setStartPos(14);
                    playerToken.setCurrentPos(14);
                    break;
                case 1:
                    playerToken.setStartPos(20);
                    playerToken.setCurrentPos(20);
                    break;
                case 2:
                    playerToken.setStartPos(2);
                    playerToken.setCurrentPos(2);
                    break;
                case 3:
                    playerToken.setStartPos(8);
                    playerToken.setCurrentPos(8);

            }
            // add to player list
            playerTokens.add(playerToken);

            // add to game board
            this.add(playerToken,1);
        }

        for (int i = numberOfPlayers; i < (numberOfBots + numberOfPlayers); i++) {
            PlayerToken playerToken = gameObjectFactoryPlayer.createPlayerToken(new Bot(i));

            switch (i) {
                case 0:
                    playerToken.setStartPos(14);
                    playerToken.setCurrentPos(14);
                    break;
                case 1:
                    playerToken.setStartPos(20);
                    playerToken.setCurrentPos(20);
                    break;
                case 2:
                    playerToken.setStartPos(2);
                    playerToken.setCurrentPos(2);
                    break;
                case 3:
                    playerToken.setStartPos(8);
                    playerToken.setCurrentPos(8);

            }
            // add to player list
            playerTokens.add(playerToken);

            // add to game board
            this.add(playerToken,1);
        }
    }

    /**
     * Check is the volcano card has a player token
     * @param distanceTravel distance to check
     */
    public boolean nextPlayerTokenAtPosition(PlayerToken playerToken, int currentposition, int distanceTravel) {
        // Iterate through all player tokens
        int newPos = (currentposition + distanceTravel) % 24;
        if (newPos < 0) {
            newPos += 24;
        }
        for (PlayerToken token : playerTokens) {
            if(!playerToken.isInCave()){
                // Check if the player token is at the target position
                if (token.getCurrentPos() == newPos && !token.isInCave()) {

                    return true; // Player token found at the target position
                }
            }else{
                if (token.getCurrentPos() == newPos - 1 && !token.isInCave()) {

                    return true; // Player token found at the target position
                }
            }
        }
        return false; // No player token found at the target position
    }

    public PlayerToken findClosestToken(PlayerToken playerToken, int currentPosition) {
        PlayerToken closestToken = null;
        int minDistance = Integer.MAX_VALUE;

        for (PlayerToken token : playerTokens) {
            // Skip the same token or tokens in a cave
            if (token.equals(playerToken) || token.isInCave()) {
                continue;
            }

            int tokenPosition = token.getCurrentPos();
            int distance = calculateCircularDistance(currentPosition, tokenPosition);

            if (distance < minDistance) {
                minDistance = distance;
                closestToken = token;
            }
        }

        return closestToken;
    }

    public int findClosestDistance(PlayerToken playerToken, int currentPosition) {
        PlayerToken closestToken = null;
        int minDistance = Integer.MAX_VALUE;

        for (PlayerToken token : playerTokens) {
            // Skip the same token or tokens in a cave
            if (token.equals(playerToken) || token.isInCave()) {
                continue;
            }

            int tokenPosition = token.getCurrentPos();
            int distance = calculateCircularDistance(currentPosition, tokenPosition);

            if (distance < minDistance) {
                minDistance = distance;
                closestToken = token;
            }
        }

        return minDistance;
    }

    private int calculateCircularDistance(int pos1, int pos2) {
        int directDistance = Math.abs(pos1 - pos2);
        int circularDistance = 24 - directDistance;
        return Math.min(directDistance, circularDistance);
    }

    public void swapWithClosestToken(PlayerToken playerToken, int currentPosition) {
        PlayerToken closestToken = findClosestToken(playerToken, currentPosition);
        if (closestToken != null) {
            // Swap positions
            int closestTokenPosition = closestToken.getCurrentPos();
            closestToken.setCurrentPos(currentPosition);
            playerToken.setCurrentPos(closestTokenPosition);
        }
    }

    /**
     * Create chit cards in random position.
     * @param actionHandler actionListener to add to all the chit cards.
     */
    public void  createChitCards(ActionHandler actionHandler) {

        ChitCardFactory gameObjectFactoryChitCard = new ChitCardFactory();
        // Create an array to hold numbers from 0 to 15
        int[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

//         use Fisher Yates algorithm to shuffle
        Random random = new Random();
        for (int i = num.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = num[i];
            num[i] = num[j];
            num[j] = temp;
        }


        // create chit cards
        ChitCard cc0 = gameObjectFactoryChitCard.createChitCard(num[0], new Spider(), 1, actionHandler);
        ChitCard cc1 = gameObjectFactoryChitCard.createChitCard(num[1], new Spider(), 2, actionHandler);
        ChitCard cc2 = gameObjectFactoryChitCard.createChitCard(num[2], new Spider(), 3, actionHandler);
        ChitCard cc3 = gameObjectFactoryChitCard.createChitCard(num[3], new BabyDragon(), 1, actionHandler);
        ChitCard cc4 = gameObjectFactoryChitCard.createChitCard(num[4], new BabyDragon(), 2, actionHandler);
        ChitCard cc5 = gameObjectFactoryChitCard.createChitCard(num[5], new BabyDragon(), 3, actionHandler);
        ChitCard cc6 = gameObjectFactoryChitCard.createChitCard(num[6], new Bat(), 1, actionHandler);
        ChitCard cc7 = gameObjectFactoryChitCard.createChitCard(num[7], new Bat(), 2, actionHandler);
        ChitCard cc8 = gameObjectFactoryChitCard.createChitCard(num[8], new Bat(), 3, actionHandler);
        ChitCard cc9 = gameObjectFactoryChitCard.createChitCard(num[9], new Salamander(), 1, actionHandler);
        ChitCard cc10 = gameObjectFactoryChitCard.createChitCard(num[10], new Salamander(), 2, actionHandler);
        ChitCard cc11 = gameObjectFactoryChitCard.createChitCard(num[11], new Salamander(), 3, actionHandler);
        ChitCard cc12 = gameObjectFactoryChitCard.createChitCard(num[12], new DragonPirate(), 1, actionHandler);
        ChitCard cc13 = gameObjectFactoryChitCard.createChitCard(num[13], new DragonPirate(), 1, actionHandler);
        ChitCard cc14 = gameObjectFactoryChitCard.createChitCard(num[14], new DragonPirate(), 2, actionHandler);
        ChitCard cc15 = gameObjectFactoryChitCard.createChitCard(num[15], new DragonPirate(), 2, actionHandler);
        ChitCard cc16 = gameObjectFactoryChitCard.createChitCard(num[16], new DragonMagic(), 1, actionHandler);

        // add to list
        chitCards.add(cc0);
        chitCards.add(cc1);
        chitCards.add(cc2);
        chitCards.add(cc3);
        chitCards.add(cc4);
        chitCards.add(cc5);
        chitCards.add(cc6);
        chitCards.add(cc7);
        chitCards.add(cc8);
        chitCards.add(cc9);
        chitCards.add(cc10);
        chitCards.add(cc11);
        chitCards.add(cc12);
        chitCards.add(cc13);
        chitCards.add(cc14);
        chitCards.add(cc15);
        chitCards.add(cc16);

        // at to game board pane
        this.add(cc0, 0);
        this.add(cc1, 0);
        this.add(cc2, 0);
        this.add(cc3, 0);
        this.add(cc4, 0);
        this.add(cc5, 0);
        this.add(cc6, 0);
        this.add(cc7, 0);
        this.add(cc8, 0);
        this.add(cc9, 0);
        this.add(cc10, 0);
        this.add(cc11, 0);
        this.add(cc12, 0);
        this.add(cc13, 0);
        this.add(cc14, 0);
        this.add(cc15, 0);
        this.add(cc16, 0);
    }

    /**
     * Create cave cards.
     */
    public void createCaveCards() {
        CaveCardFactory gameObjectFactoryCaveCard = new CaveCardFactory();

        // create caves
        CaveCard c0 = gameObjectFactoryCaveCard.createCaveCard(Constant.DEFAULT_CAVE_CARD_POSITION[0], new BabyDragon());
        CaveCard c1 = gameObjectFactoryCaveCard.createCaveCard(Constant.DEFAULT_CAVE_CARD_POSITION[1], new Bat());
        CaveCard c2 = gameObjectFactoryCaveCard.createCaveCard(Constant.DEFAULT_CAVE_CARD_POSITION[2], new Spider());
        CaveCard c3 = gameObjectFactoryCaveCard.createCaveCard(Constant.DEFAULT_CAVE_CARD_POSITION[3], new Salamander());

        // add to cave card list
        caveCards.add(c0);
        caveCards.add(c1);
        caveCards.add(c2);
        caveCards.add(c3);

        // add to game board pane
        this.add(c0, 2);
        this.add(c1, 2);
        this.add(c2, 2);
        this.add(c3, 2);
    }

    /**
     * Create volcano cards.
     */
    public void createVolcanoCards() {
        VolcanoCardFactory gameObjectFactoryVolcanoCard = new VolcanoCardFactory();

        // create volcano cards
        VolcanoCard vc0 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Spider(), 0);
        VolcanoCard vc1 = gameObjectFactoryVolcanoCard.createVolcanoCard(new BabyDragon(), 1);
        VolcanoCard vc2 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Bat(), 2);
        VolcanoCard vc3 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Spider(), 3);
        VolcanoCard vc4 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Spider(), 4);
        VolcanoCard vc5 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Bat(), 5);
        VolcanoCard vc6 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Salamander(), 6);
        VolcanoCard vc7 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Salamander(), 7);
        VolcanoCard vc8 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Spider(), 8);
        VolcanoCard vc9 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Bat(), 9);
        VolcanoCard vc10 = gameObjectFactoryVolcanoCard.createVolcanoCard(new BabyDragon(), 10);
        VolcanoCard vc11 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Salamander(), 11);
        VolcanoCard vc12 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Bat(), 12);
        VolcanoCard vc13 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Spider(), 13);
        VolcanoCard vc14 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Salamander(), 14);
        VolcanoCard vc15 = gameObjectFactoryVolcanoCard.createVolcanoCard(new BabyDragon(), 15);
        VolcanoCard vc16 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Bat(), 16);
        VolcanoCard vc17 = gameObjectFactoryVolcanoCard.createVolcanoCard(new BabyDragon(), 17);
        VolcanoCard vc18 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Salamander(), 18);
        VolcanoCard vc19 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Bat(), 19);
        VolcanoCard vc20 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Spider(), 20);
        VolcanoCard vc21 = gameObjectFactoryVolcanoCard.createVolcanoCard(new BabyDragon(), 21);
        VolcanoCard vc22 = gameObjectFactoryVolcanoCard.createVolcanoCard(new Salamander(), 22);
        VolcanoCard vc23 = gameObjectFactoryVolcanoCard.createVolcanoCard(new BabyDragon(), 23);

        // add to volcano card list
        volcanoCards.add(vc0);
        volcanoCards.add(vc1);
        volcanoCards.add(vc2);
        volcanoCards.add(vc3);
        volcanoCards.add(vc4);
        volcanoCards.add(vc5);
        volcanoCards.add(vc6);
        volcanoCards.add(vc7);
        volcanoCards.add(vc8);
        volcanoCards.add(vc9);
        volcanoCards.add(vc10);
        volcanoCards.add(vc11);
        volcanoCards.add(vc12);
        volcanoCards.add(vc13);
        volcanoCards.add(vc14);
        volcanoCards.add(vc15);
        volcanoCards.add(vc16);
        volcanoCards.add(vc17);
        volcanoCards.add(vc18);
        volcanoCards.add(vc19);
        volcanoCards.add(vc20);
        volcanoCards.add(vc21);
        volcanoCards.add(vc22);
        volcanoCards.add(vc23);

        // add to game board pane
        this.add(vc0, 3);
        this.add(vc1, 3);
        this.add(vc2, 3);
        this.add(vc3, 3);
        this.add(vc4, 3);
        this.add(vc5, 3);
        this.add(vc6, 3);
        this.add(vc7, 3);
        this.add(vc8, 3);
        this.add(vc9, 3);
        this.add(vc10, 3);
        this.add(vc11, 3);
        this.add(vc12, 3);
        this.add(vc13, 3);
        this.add(vc14, 3);
        this.add(vc15, 3);
        this.add(vc16, 3);
        this.add(vc17, 3);
        this.add(vc18, 3);
        this.add(vc19, 3);
        this.add(vc20, 3);
        this.add(vc21, 3);
        this.add(vc22, 3);
        this.add(vc23, 3);
    }

    /**
     * Create all the labels.
     */
    public void createLabels(int numberOfPlayers, int numberOfBots) {
        // current state label
        currentStateLabel = new JLabel();
        currentStateLabel.setBounds(350, 0, 500, 50);
        currentStateLabel.setBackground(Color.BLACK);
        currentStateLabel.setHorizontalAlignment(JLabel.CENTER);
        currentStateLabel.setOpaque(true);
        currentStateLabel.setForeground(Color.WHITE);
        currentStateLabel.setFont(Constant.FONT_20);
        this.add(currentStateLabel);

        String Player1NameTex = "Player 1: Green";
        String Player2NameTex = "Player 2: Blue";
        String Player3NameTex = "Player 3: White";
        String Player4NameTex = "Player 4: Orange";

        // player text
        for (int i = numberOfPlayers; i < (numberOfBots + numberOfPlayers); i++) {
            switch (i) {
                case 1:
                    Player2NameTex = "Bot 2: Blue";
                    break;
                case 2:
                    Player3NameTex = "Bot 3: White";
                    break;
                case 3:
                    Player4NameTex = "Bot 4: Orange";
            }
        }

        // add players text
        JLabel playerName1 = new JLabel(Player1NameTex);
        playerName1.setFont(Constant.FONT_20);
        playerName1.setBounds(0, 100, 200, 50);
        playerName1.setBackground(Color.BLACK);
        playerName1.setHorizontalAlignment(JLabel.CENTER);
        playerName1.setOpaque(true);
        playerName1.setForeground(Color.WHITE);
        playerName1.setFont(Constant.FONT_20);
        this.add(playerName1);

        JLabel playerName2 = new JLabel(Player2NameTex);
        playerName2.setFont(Constant.FONT_20);
        playerName2.setBounds(1000, 100, 200, 50);
        playerName2.setBackground(Color.BLACK);
        playerName2.setHorizontalAlignment(JLabel.CENTER);
        playerName2.setOpaque(true);
        playerName2.setForeground(Color.WHITE);
        playerName2.setFont(Constant.FONT_20);
        this.add(playerName2);

        JLabel playerName3 = new JLabel(Player3NameTex);
        playerName3.setFont(Constant.FONT_20);
        playerName3.setBounds(1000, 750, 200, 50);
        playerName3.setBackground(Color.BLACK);
        playerName3.setHorizontalAlignment(JLabel.CENTER);
        playerName3.setOpaque(true);
        playerName3.setForeground(Color.WHITE);
        playerName3.setFont(Constant.FONT_20);
        this.add(playerName3);

        JLabel playerName4 = new JLabel(Player4NameTex);
        playerName4.setFont(Constant.FONT_20);
        playerName4.setBounds(0, 750, 200, 50);
        playerName4.setBackground(Color.BLACK);
        playerName4.setHorizontalAlignment(JLabel.CENTER);
        playerName4.setOpaque(true);
        playerName4.setForeground(Color.WHITE);
        playerName4.setFont(Constant.FONT_20);
        this.add(playerName4);
    }

    /**
     * Create the buttons for saving to save files and quitting.
     */
    public void createSaveButton() {
        saveButton1 = new JButton("SAVE TO FILE 1");
        saveButton1.setBounds(0, 365, 150, 40);
        saveButton1.addActionListener(e -> {
            // save current game state to text file
            try {
                FileWriter fileWriter = new FileWriter(Constant.SAVE_FILE_1);
                this.saveToFile(fileWriter);
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        this.add(saveButton1);

        saveButton2 = new JButton("SAVE TO FILE 2");
        saveButton2.setBounds(0, 425, 150, 40);
        saveButton2.addActionListener(e -> {
            // save current game state to text file
            try {
                FileWriter fileWriter = new FileWriter(Constant.SAVE_FILE_2);
                this.saveToFile(fileWriter);
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        this.add(saveButton2);

        saveButton3 = new JButton("SAVE TO FILE 3");
        saveButton3.setBounds(0, 485, 150, 40);
        saveButton3.addActionListener(e -> {
            // save current game state to text file
            try {
                FileWriter fileWriter = new FileWriter(Constant.SAVE_FILE_3);
                this.saveToFile(fileWriter);
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        this.add(saveButton3);

        quitButton = new JButton("QUIT");
        quitButton.setBounds(0, 545, 150, 40);
        quitButton.addActionListener(e -> {

            // go back to starting screen
            GameManager.getInstance().window.changeToPanel(new StartPanel());
        });
        this.add(quitButton);



    }

    /**
     * Method to save current game state to file.
     * @param fileWriter FileWriter which is used to write to file.
     */
    public void saveToFile(FileWriter fileWriter) {
        try {
            fileWriter.write(Constant.GAME_MANAGER + ";");
            fileWriter.write(GameManager.getInstance().numberOfPlayers + ";");
            fileWriter.write(GameManager.getInstance().numberOfBots + ";");
            fileWriter.write(this.volcanoCards.size() + ";");
            fileWriter.write("\n");

            // save current volcano cards state
            for (VolcanoCard v: volcanoCards) {
                v.saveToFile(fileWriter);
                fileWriter.write("\n");
            }
            // save current cave cards state
            for (CaveCard c : caveCards) {
                c.saveToFile(fileWriter);
                fileWriter.write("\n");
            }
            // save current player tokens state
            for (PlayerToken p : playerTokens) {
                p.saveToFile(fileWriter);
                fileWriter.write("\n");
            }
            // save current chit cards state
            for (ChitCard c : chitCards) {
                c.saveToFile(fileWriter);
                fileWriter.write("\n");
            }

            // save current game variables
            fileWriter.write(Constant.GAME + ";");
            fileWriter.write(this.turnNumber + ";");
            fileWriter.write(this.gameState.getClass() + ";");
            fileWriter.write(this.allowCardToBeSelected + ";");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Set the state of the game based on the values from save file.
     * @param values values from the save file.
     */
    public void loadFromFile(String[] values) {
        this.turnNumber = Integer.parseInt(values[1]); // set turn number
        this.allowCardToBeSelected = values[3].equals("true"); // set condition
        if (values[2].equals(CardSelectionState.class.toString())) this.gameState = new CardSelectionState();
        else if (values[2].equals(DisplayCardState.class.toString())) this.gameState = new DisplayCardState(this.selectedCard);
        else if (values[2].equals(PlayerMovementState.class.toString())) this.gameState = new PlayerMovementState(this.selectedCard.getAnimal(), this.selectedCard.getNumOfAnimal());
    }
}
