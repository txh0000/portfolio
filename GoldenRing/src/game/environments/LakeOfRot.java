package game.environments;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.DeathAction;

/**
 * Lake of Rot environment class.
 * Created by:
 * @author Jun Lim
 */
public class LakeOfRot extends Ground {
	/**
	 * Damage dealt to actors who are on the Lake of Rot every turn
	 */
	private int damage = 10;
	/**
	 * Constructor.
	 */
	public LakeOfRot() {
		super('W');
	}

	@Override
	public void tick(Location location) {
		super.tick(location);
		if (location.containsAnActor() == true) { // if there is an actor at the lake of rot
			Actor actor = location.getActor();
			if (actor.hasCapability(Status.SCARLET_ROT_IMMUNE) == false) { // if the actor is not immune, deal damage to them
				actor.hurt(this.damage);
				if (actor.isConscious() == false) {
					DeathAction deathAction = new DeathAction(actor);
					deathAction.execute(actor, location.map());
				}
			}
		}
		List<Item> items = location.getItems();
		// System.out.println(items);
		ArrayList<Item> itemsToRemove = new ArrayList<Item>();
		for (Item item : items) { // for each item at the location
			if (item.hasCapability(Status.SCARLET_ROT_IMMUNE) == false) { // if it is not immune, add it to the list of items to be removed
				itemsToRemove.add(item);
			}
		}
		for (Item itemToRemove : itemsToRemove) { // remove the items to be removed
			location.removeItem(itemToRemove);
		}
	}
}
