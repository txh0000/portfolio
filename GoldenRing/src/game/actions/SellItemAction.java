package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.TradeableItem;
import game.RuneManager;

/**
 * Class to represent selling items to a Trader action.
 * Created by:
 * @author Jun Lim
 */
public class SellItemAction extends Action {
    /**
     * The tradeable item being sold.
     */
    private TradeableItem item;
    
    /**
     * Constructor.
     * 
     * @param item the tradeable item being sold
     */
    public SellItemAction(TradeableItem item){
        this.item = item;
    }

    /**
     * Lets the player sell the item.
     * 
     * @param actor the player selling the item
     * @param map   the map the actor is in
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        RuneManager runeManager = RuneManager.getInstance(); // getting the RuneManager instance       
        Item itemItem = actor.getItemInventory().get(actor.getItemInventory().indexOf(item.getItem())); // getting the player item
        actor.removeItemFromInventory(itemItem);       // removing the item from the player's inventory
        runeManager.changeRunes(item.getSellingPrice()); // updating the player's rune count
        return menuDescription(actor);
    }

    /**
	 * Describes the successful sell action.
	 *
	 * @param actor the actor selling
	 * @return      a description used for the menu UI
	 */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " sells the " + item.getItem().toString() + " for " + item.getSellingPrice() + " runes";
    }
}
