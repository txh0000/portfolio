package game.actors.enemies.GodrickGuardEnemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.items.weapons.Scimitar;

/**
 * Class for Godrick Soldier enemy
 *
 * Created by:
 * @author jingweiong
 */
public class GodrickSoldier extends GodrickGuardEnemy {

    /**
     * Constructor
     */
    public GodrickSoldier(){
        super("Godrick Soldier", 'p', 198, 38, 70);
        this.addWeaponToInventory(new Scimitar()); // not creating Heavy crossbow
    }

    /**
     * Creates and returns the Godrick Soldier's intrinsic weapon
     * (intrinsic weapon is not mentioned in spec, creating own)
     *
     * @return a new instance of the IntrinsicWeapon
     */
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(15, "shambles at", 100);
    }


}
