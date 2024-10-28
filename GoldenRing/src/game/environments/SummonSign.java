package game.environments;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SummonSignAction;

/**
 * Summon Sign ground class.
 * Created by:
 * @author Jun Lim
 */
public class SummonSign extends Ground {
	/**
	 * Constructor.
	 */
	public SummonSign() {
		super('=');
	}

	@Override
	public ActionList allowableActions(Actor actor, Location location, String direction) {
		ActionList actions = new ActionList();
		actions.add(new SummonSignAction(location)); // passing location of sign to summon from
		return actions;
	}
}
