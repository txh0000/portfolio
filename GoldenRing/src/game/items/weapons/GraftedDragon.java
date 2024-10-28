package game.items.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A weapon represented by "N", carried by Godrick the Grafted
 * It deals 89 damage with 90% accuracy
 * Created by:
 * @author jingweiong
 */
public class GraftedDragon extends WeaponItem {

    /**
     * Constructor
     */
    public GraftedDragon(){
        super("Grafted Dragon", 'N', 89, "slashes", 90);
    }
}
