package game.environments;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.DeathAction;

/**
 * A class that represents the Cliff
 * Created by:
 * @author Xi Heng
 * Modified by:
 * @author Jun Lim
 */
public class Cliff extends Ground {
    /**
     * Constructor
     */
    public Cliff(){
        super('+');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
		if (location.containsAnActor() == true) { // if there is an actor at the cliff
			Actor actor = location.getActor();
			if (actor.hasCapability(Status.CAN_FALL_FROM_CLIFFS)) { // if the actor can fall from cliffs
                DeathAction deathAction = new DeathAction(actor); // kill the actor
                deathAction.execute(actor, location.map());
            }
		} 
    }
}
