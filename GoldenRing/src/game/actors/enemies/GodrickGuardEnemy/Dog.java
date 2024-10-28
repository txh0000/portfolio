package game.actors.enemies.GodrickGuardEnemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class for Dog enemy
 *
 * Created by:
 * @author jingweiong
 */
public class Dog extends GodrickGuardEnemy {

    /**
     * Constructor
     */
    public Dog(){
        super("Dog", 'a', 104, 52, 1390);
    }

    /**
     * Creates and returns the Dog's intrinsic weapon
     *
     * @return a new instance of the IntrinsicWeapon
     */
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(101, "bites", 93);
    }
}
