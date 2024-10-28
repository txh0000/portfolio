package gameObject.player;

import javax.swing.*;

import constant.Constant;
import game.Game;
import game.GameManager;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to represent Player token.
 */
public class PlayerToken extends JComponent {

    private Player player;
    private int startPos;
    private int currentPos;
    private int distanceTravelled;
    private boolean inCave;
    private int width;
    private int height;
    private Image playerToken;
    private Timer timer;
    private int animationStatePosition;
    private Game game;


    /**
     * Constructor for PlayerToken class.
     * @param player player that will represent this player token.
     */
    public PlayerToken(Player player) {
        this.width = 55;
        this.height = 55;
        this.player = player;
        this.inCave = true;

        // get player image
        try {
            playerToken = new ImageIcon(getClass().getClassLoader().getResource(String.format("%s_%d%s", Constant.PLAYER_TOKEN_PATH, player.getId() + 1, Constant.PATH_SUFFIX))).getImage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.setBounds(0, 0, width, height);
    }

    /**
     * Getter for player.
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter for startPos.
     * @param startPos the id of the volcano card where it will start.
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
        this.animationStatePosition = startPos;
    }

    /**
     * Setter for currentPos.
     * @param newPos the id of the volcano card of the new position.
     */
    public void setCurrentPos(int newPos) {
        this.currentPos = newPos;

    }

    /**
     * Setter for distanceTravelled.
     * @param newPos the id of the volcano card of the new position.
     */
    public void setdistanceTravelled(int newPos) {
        this.distanceTravelled = newPos;
    }

    /**
     * update for distanceTravelled.
     * @param distance the id of the volcano card of the new position.
     */
    public void updatedistanceTravelled(int distance) {
        if(distance > 0){
            this.distanceTravelled += distance;
        }else {
            this.distanceTravelled -= distance;
        }

    }

    /**
     * Getter for currentPos.
     * @return currentPos.
     */
    public int getCurrentPos() {
        return currentPos;
    }

    /**
     * Get the clockwise displacement from startPos.
     * @return distanceTravelled.
     */
    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Getter for whether the player is in the cave.
     * @return boolean value inCave.
     */
    public boolean isInCave() {
        return inCave;
    }

    /**
     * Getter to get player token image.
     * @return image of player token.
     */
    public Image getPlayerToken() {
        return playerToken;
    }

    /**
     * Getter to get playerId.
     * @return playerId.
     */
    public int getPlayerId() {
        return player.getId();
    }

    /**
     * Play the animation of going to new position one step at a time.
     * @param forward whether the player is moving clockwise or anticlockwise.
     */
    public void playAnimation(boolean forward) {
        this.timer = new Timer(500, e -> {
            if (this.animationStatePosition != currentPos) {
                if (forward) {
                    this.animationStatePosition = (this.animationStatePosition + 1) % 24;
                }
                else {
                    int newPos = (this.animationStatePosition - 1) % 24;
                    if (newPos < 0) {
                        newPos += 24;
                    }
                    this.animationStatePosition = newPos;
                }
            }
            else {
                timer.stop();
            }
        });
        timer.start();
    }

    public void playSwapAnimation(boolean forward) {
        new Thread(() -> {
            while (this.animationStatePosition != currentPos) {
                if (forward) {
                    this.animationStatePosition = (this.animationStatePosition + 1) % 24;
                } else {
                    int newPos = (this.animationStatePosition - 1) % 24;
                    if (newPos < 0) {
                        newPos += 24;
                    }
                    this.animationStatePosition = newPos;
                }

                // Redraw the animation frame, assuming there's a method for that
                // repaintAnimation();

                // Sleep for 500 milliseconds
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();
    }

    /**
     * Use to set distanceTravelled and currentPos based on distance to travel.
     * @param distanceTravelled the distance that the player should move.
     */
    public void travelDistance(int distanceTravelled) {
        // if player is still in cave
        if (inCave) {
            this.inCave = false;
            distanceTravelled -= 1;
        }

        // if move backwards
        int newPos = (this.currentPos + distanceTravelled) % 24;
        if (newPos < 0) {
                newPos += 24;
        }
        this.currentPos = newPos;
        this.distanceTravelled += distanceTravelled;
        // if reach back home cave
        if (this.distanceTravelled == 25) {
            inCave = true;
        }
    }

    

    /**
     * Draw the player image based on the current Position.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        double angle_per_card = 360.0 / GameManager.getInstance().numberOfVolcanoCards;
        if (inCave) {
            double angleStart = angle_per_card * startPos;
            double angleEnd = (startPos + 1) * angle_per_card;
            double angleMid = 0.5 * (angleStart + angleEnd);
            int posX = (int) Math.round((double) 600 + 400 * Math.cos(angleMid * (Math.PI/180)));
            int posY = (int) Math.round((double) 450 + 400 * Math.sin(angleMid * (Math.PI/180)));
            this.setLocation(posX - 25, posY - 25);
        }
        else {
            double angleStart = angle_per_card * animationStatePosition;
            double angleEnd = (animationStatePosition + 1) * angle_per_card;
            double angleMid = 0.5 * (angleStart + angleEnd);
            int posX = (int) Math.round((double) 600 + 325 * Math.cos(angleMid * (Math.PI/180)));
            int posY = (int) Math.round((double) 450 + 325 * Math.sin(angleMid * (Math.PI/180)));
            this.setLocation(posX - 25, posY - 25);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(playerToken, 0, 0, width, height, null);
    }

    /**
     * Setter for animationStatePosition.
     * @param animationStatePosition position to set.
     */
    public void setAnimationStatePosition(int animationStatePosition) {
        this.animationStatePosition = animationStatePosition;
    }

    /**
     * Method to save current object state to file.
     * @param fileWriter FileWriter which is used to write to file.
     */
    public void saveToFile(FileWriter fileWriter) {
        try {
            fileWriter.write(Constant.PLAYER_TOKEN + ";");
            fileWriter.write(player.getId() + ";");
            fileWriter.write(player.getPlayerType() + ";");
            fileWriter.write(this.startPos + ";");
            fileWriter.write(this.currentPos + ";");
            fileWriter.write(this.distanceTravelled + ";");
            fileWriter.write(this.inCave + ";");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Create PlayerToken object based on save file state and add object to game.
     * @param values values retrieved from save file.
     * @param game game to add object to.
     */
    public static void loadFromFile(String[] values, Game game) {
        int id = Integer.parseInt(values[1]);
        Player player = values[2].equals("HUMAN") ? new Human(id) : new Bot(id);
        PlayerToken playerToken = new PlayerToken(player);
        playerToken.setStartPos(Integer.parseInt(values[3]));
        playerToken.setCurrentPos(Integer.parseInt(values[4]));
        playerToken.setAnimationStatePosition(Integer.parseInt(values[4]));
        playerToken.setdistanceTravelled(Integer.parseInt(values[5]));
        playerToken.inCave = values[6].equals("true");
        game.playerTokens.add(playerToken);
        game.add(playerToken, 1);
    }
}
