package game.items.remembrances;

import java.util.ArrayList;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Status;
import game.items.weapons.AxeOfGodrick;
import game.items.weapons.GraftedDragon;

/**
 * Class to represent Remembrance of the Grafted item
 *
 * Created by:
 * @author jingweiong
 */
public class RemembranceOfTheGrafted extends Remembrance{
    /**
     * Constructor
     */
    public RemembranceOfTheGrafted(){
        super("Remembrance of the Grafted", 'O', new ArrayList<WeaponItem>() {
            {
                add(new AxeOfGodrick());
                add(new GraftedDragon());
            }
        });
        this.addCapability(Status.REMEMBRANCE);
        
    
    }
}
