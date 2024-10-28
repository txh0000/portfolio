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
 * Class to represent a single volcano card.
 */
public class VolcanoCard extends JComponent {
    private int cardId;
    private Animal animal;

    /**
     * Constructor for VolcanoCard class.
     * @param animal the animal on the volcano card.
     * @param id the position on the board.
     */
    public VolcanoCard(Animal animal, int id) {
        this.cardId = id;
        this.animal = animal;

        this.setBounds(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
    }

    /**
     * Getter to get animal name.
     * @return String of name of animal on volcano card.
     */
    public String getAnimalName() {return animal.getName();}

    /**
     * draw the volcano card with the animal image in the correct position.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        double angle_per_card = 360.0 / GameManager.getInstance().numberOfVolcanoCards;
        double angleStart = cardId * angle_per_card ;
        double angleEnd = (cardId + 1) * angle_per_card;
        double angleMid = 0.5 * (angleStart + angleEnd);

        int pointX1 = (int) Math.round((double) getWidth()/2 + 250 * Math.cos(angleStart * (Math.PI/180)));
        int pointX2 = (int) Math.round((double) getWidth()/2 + 375 * Math.cos(angleStart * (Math.PI/180)));
        int pointX3 = (int) Math.round((double) getWidth()/2 + 250 * Math.cos(angleEnd * (Math.PI/180)));
        int pointX4 = (int) Math.round((double) getWidth()/2 + 375 * Math.cos(angleEnd * (Math.PI/180)));
        int imageX = (int) Math.round((double) getWidth()/2 + 280 * Math.cos(angleMid * (Math.PI/180)));

        int pointY2 = (int) Math.round((double) getHeight()/2 + 375 * Math.sin(angleStart * (Math.PI/180)));
        int pointY1 = (int) Math.round((double) getHeight()/2 + 250 * Math.sin(angleStart * (Math.PI/180)));
        int pointY3 = (int) Math.round((double) getHeight()/2 + 250 * Math.sin(angleEnd * (Math.PI/180)));
        int pointY4 = (int) Math.round((double) getHeight()/2 + 375 * Math.sin(angleEnd * (Math.PI/180)));
        int imageY = (int) Math.round((double) getHeight()/2 + 280 * Math.sin(angleMid * (Math.PI/180)));

        Graphics2D g2 = (Graphics2D) g;
        g2.drawLine(pointX1, pointY1, pointX2, pointY2);
        g2.drawLine(pointX3, pointY3, pointX4, pointY4);
        g2.drawImage(animal.getImg(), imageX - 20, imageY - 20, 40, 40, null);
    }

    /**
     * Method to save current object state to file.
     * @param fileWriter FileWriter which is used to write to file.
     */
    public void saveToFile(FileWriter fileWriter) {
        try {
            fileWriter.write(Constant.VOLCANO_CARD + ";");
            fileWriter.write(this.cardId + ";");
            fileWriter.write(this.animal.getName() +";");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Create VolcanoCard object based on save file state and add object to game.
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
        VolcanoCard volcanoCard = new VolcanoCard(animal, id);
        game.volcanoCards.add(volcanoCard);
        game.add(volcanoCard, 3);
    }
}
