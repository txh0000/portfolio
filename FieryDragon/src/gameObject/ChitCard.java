package gameObject;

import animal.*;
import constant.Constant;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to represent a single chit card.
 */
public class ChitCard extends JButton {
    private int cardId;
    private Animal animal;
    private int numOfAnimal;
    private Image defaultImg;
    private int width;
    private int height;
    private boolean flipped;

    /**
     * Constructor for ChitCard class.
     * @param id the position on the board.
     * @param animal the animal on the chit card.
     * @param numOfAnimal the number of animal on the chit card.
     * @param actionHandler the ActionListener used the handle user input.
     */
    public ChitCard(int id, Animal animal, int numOfAnimal, ActionHandler actionHandler) {
        this.cardId = id;
        this.animal = animal;
        this.numOfAnimal = numOfAnimal;
        this.defaultImg = new ImageIcon(getClass().getClassLoader().getResource(Constant.DEFAULT_CHIT_CARD_IMAGE_PATH + Constant.PATH_SUFFIX)).getImage();
        this.width = 80;
        this.height = 80;
        this.flipped = false;

        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.addActionListener(actionHandler);
        this.setBounds(Constant.CHIT_CARD_X_COORDINATES_LIST[cardId], Constant.CHIT_CARD_Y_COORDINATES_LIST[cardId], width, height);

    }

    /**
     * Getter to get animal.
     * @return animal.
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * Getter to get number of animal.
     * @return numOfAnimal.
     */
    public int getNumOfAnimal() {
        return numOfAnimal;
    }

    /**
     * Getter to know whether card is flipped.
     * @return boolean value flipped.
     */
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * Used to reset chit card.
     */
    public void reset() {
        this.flipped = false;
    }

    /**
     * Used to flip the card.
     */
    public void flip() {
        this.flipped = true;
    }

    /**
     * Draw the chit card based on whether it is flipped.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3.0f));
        g2.setColor(Constant.BEIGE);
        g2.fillOval(0, 0, width, height);
        g2.setColor(Color.BLACK);
        g2.drawOval(0, 0, width, height);

        if (flipped) {

            switch (numOfAnimal) {
                case 1:
                    // draw 1 animal
                    g2.drawImage(animal.getImg(), 15, 15, width - 30, height - 30, null);
                    break;
                case 2:
                    // draw 2 animal
                    g2.drawImage(animal.getImg(), 5, 30, width - 50, height - 50, null);
                    g2.drawImage(animal.getImg(), 45, 30, width - 50, height - 50, null);

                    break;
                case 3:
                    // draw 3 animal
                    g2.drawImage(animal.getImg(), 27, 10, width - 55, height - 55, null);
                    g2.drawImage(animal.getImg(), 10, 40, width - 55, height - 55, null);
                    g2.drawImage(animal.getImg(), 45, 40, width - 55, height - 55, null);
                    break;
            }
        } else {
            g2.drawImage(defaultImg, 15, 15, width - 30, height - 30, null);
        }
    }

    /**
     * Method to save current object state to file.
     * @param fileWriter FileWriter which is used to write to file.
     */
    public void saveToFile(FileWriter fileWriter) {
        try {
            fileWriter.write(Constant.CHIT_CARD + ";");
            fileWriter.write(this.cardId + ";");
            fileWriter.write(this.animal.getName() + ";");
            fileWriter.write(this.numOfAnimal + ";");
            fileWriter.write(this.flipped + ";");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Create ChitCard object based on save file state and add object to game.
     * @param values values retrieved from save file.
     * @param game game to add object to.
     */
    public static void loadFromFile(String[] values, Game game) {
        int id = Integer.parseInt(values[1]);
        Animal animal;
        if (values[2].equals(Constant.BAT_NAME)) animal = new Bat();
        else if (values[2].equals(Constant.BABY_DRAGON_NAME)) animal = new BabyDragon();
        else if (values[2].equals(Constant.SALAMANDER_NAME)) animal = new Salamander();
        else if (values[2].equals(Constant.SPIDER_NAME)) animal = new Spider();
        else if (values[2].equals(Constant.MAGIC_DRAGON_NAME)) animal = new DragonMagic();
        else animal = new DragonPirate();
        ChitCard chitCard = new ChitCard(id, animal, Integer.parseInt(values[3]), game.getActionHandler());
        if (values[4].equals("true")) {
            chitCard.flip();
        }
        game.chitCards.add(chitCard);
        game.add(chitCard, 0);
    }
}
