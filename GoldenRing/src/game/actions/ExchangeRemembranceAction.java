package game.actions;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.remembrances.Remembrance;

/**
 * Class to represent exchanging remembrances with a Trader action.
 * Created by:
 * @author Jun Lim
 */
public class ExchangeRemembranceAction extends Action {
    /**
     * The remembrance being exchanged.
     */
    private Remembrance remembrance;

    private WeaponItem weaponItem;
    
    /**
     * Constructor.
     * 
     * @param remembrance the remembrance being exchanged
     */
    public ExchangeRemembranceAction(Remembrance remembrance, WeaponItem weaponItem){
        this.remembrance = remembrance;
        this.weaponItem = weaponItem;
    }

    /**
     * Lets the player exchange the remembrance.
     * 
     * @param actor the player exchanging the remembrance
     * @param map   the map the actor is in
     */
    @Override
    public String execute(Actor actor, GameMap map) {      
        Item playerRemembrance = actor.getItemInventory().get(actor.getItemInventory().indexOf(this.remembrance)); // getting the player weapon
        actor.removeItemFromInventory(playerRemembrance);       // removing the weapon from the player's inventory
        actor.addWeaponToInventory(weaponItem);
        System.out.println("adding to inventory " + weaponItem);

        return menuDescription(actor);
    }

    /**
	 * Describes the successful exchange action.
	 *
	 * @param actor the actor exchanging
	 * @return      a description used for the menu UI
	 */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " exchanges the " + remembrance + " for " + weaponItem;
    }
}
