package game.environments;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.GoldenFogDoorTravelAction;

/**
 * Golden Fog Door environment class.
 * Created by:
 * @author Toh Xi Heng
 * Modified by:
 * @author Jun Lim
 */
public class GoldenFogDoor extends Ground {
    /**
     * Location the Golden Fog Door leads to
     */
    private Location destination;
    
    public GoldenFogDoor(Location destination){
        super('D');
        this.destination = destination;
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (actor.hasCapability(Status.CAN_USE_GOLDEN_FOG_DOORS)) { // if the actor can use golden fog doors
            actions.add(new GoldenFogDoorTravelAction(destination)); // create and add the action to use it
        }
        return actions;
    }
}
