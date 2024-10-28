package game.items.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * An axe of Godrick represented by "T", carried by Godrick the Grafted
 * It deals 142 damage with 84% accuracy
 * Created by:
 * @author jingweiong
 */
public class AxeOfGodrick extends WeaponItem {

    /**
     * Constructor
     */
    public AxeOfGodrick(){
        super("Axe of Godrick", 'T', 142, "slashes", 84);
    }
}
