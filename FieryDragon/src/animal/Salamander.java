package animal;

import constant.Constant;

/**
 * Salamander class.
 */
public class Salamander extends Creature{

    /**
     * Constructor for Salamander class.
     */
    public Salamander() {
        super(Constant.SALAMANDER_IMAGE_PATH + Constant.PATH_SUFFIX, Constant.SALAMANDER_NAME);
    }
}
