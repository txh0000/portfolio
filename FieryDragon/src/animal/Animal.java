package animal;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract animal class.
 */
public abstract class Animal {
    private Image img;
    private String name;

    /**
     * Constructor for Animal class.
     * @param path path of image.
     * @param name String name of the animal.
     */
    public Animal(String path, String name) {

        try {
            img = new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.name = name;
    }

    /**
     * Getter to get image.
     * @return img.
     */
    public Image getImg() {
        return img;
    }

    /**
     * Getter to get animal name.
     * @return name.
     */
    public String getName() {
        return name;
    }
}
