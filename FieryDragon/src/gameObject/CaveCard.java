package gameObject;

import animal.*;
import constant.Constant;
import game.Game;
import game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to represent a single cave card.
 */
public class CaveCard extends JComponent {
    private int cardId;
    private Animal animal;
    private int width;
    private int height;
    private Image backgroundImg;

    /**
     * Constructor for CaveCard class.
     * @param id the position on the board.
     * @param animal the animal on the cave card.
     */
    public CaveCard(int id, Animal animal) {
        this.cardId = id;
        this.animal = animal;
        this.width = 100;
        this.height = 100;
        this.backgroundImg = new ImageIcon(getClass().getClassLoader().getResource(Constant.CAVE_BACKGROUND_IMAGE_PATH)).getImage();
        this.setBounds(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
//        this.setBounds(Constant.CAVE_CARD_X_COORDINATES_LIST[cardId], Constant.CAVE_CARD_Y_COORDINATES_LIST[cardId], width, height);
    }

    /**
     * Getter to get animal name.
     * @return String of name of animal on volcano card.
     */
    public String getAnimalName() {
        return animal.getName();
    }

    /**
     * draw the cave card with the animal image in the correct position.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        double angle_per_card = 360.0 / GameManager.getInstance().numberOfVolcanoCards;
        double angleStart = cardId * angle_per_card ;
        double angleEnd = (cardId + 1) * angle_per_card;
        double angleMid = 0.5 * (angleStart + angleEnd);

        int imageX = (int) Math.round((double) getWidth()/2 + 400 * Math.cos(angleMid * (Math.PI/180)));
        int imageY = (int) Math.round((double) getHeight()/2 + 400 * Math.sin(angleMid * (Math.PI/180)));

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImg, imageX - 45, imageY - 45, 90, 90, null);
        g2.drawImage(animal.getImg(), imageX - 25, imageY - 25, 50, 50, null);

    }

    /**
     * Method to save current object state to file.
     * @param fileWriter FileWriter which is used to write to file.
     */
    public void saveToFile(FileWriter fileWriter) {
        try {
            fileWriter.write(Constant.CAVE_CARD + ";");
            fileWriter.write(this.cardId + ";");
            fileWriter.write(this.animal.getName() + ";");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Create CaveCard object based on save file state and add object to game.
     * @param values values retrieved from save file.
     * @param game game to add object to.
     */
    public static void loadFromFile(String[] values, Game game) {
        int id = Integer.parseInt(values[1]);
        Animal animal;
        if (values[2].equals(Constant.BAT_NAME)) animal = new Bat();
        else if (values[2].equals(Constant.BABY_DRAGON_NAME)) animal = new BabyDragon();
        else if (values[2].equals(Constant.SALAMANDER_NAME)) animal = new Salamander();
        else animal = new Spider();
        CaveCard caveCard = new CaveCard(id, animal);
        game.caveCards.add(caveCard);
        game.add(caveCard);
    }
}
