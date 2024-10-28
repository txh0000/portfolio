package game.items.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A staff, represented by "f".
 * Starting weapon of Astrologer player class.
 * Deals 274 damage with 50% accuracy.
 * Created by:
 * @author Jun Lim
 */
public class AstrologersStaff extends WeaponItem {
    /**
     * Constructor
     */
    public AstrologersStaff() {
        super("Astrologer's Staff", 'f', 274, "whacks", 50);
    }
}
