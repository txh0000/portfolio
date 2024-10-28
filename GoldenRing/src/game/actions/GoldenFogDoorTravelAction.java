package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
/**
 * An action executed by an actor to travel between maps using a Golden Fog Door.
 * 
 * Created by:
 * @author Jun Lim
 */
public class GoldenFogDoorTravelAction extends Action{
    /**
     * Location the actor is travelling to
     */
    private Location destination;
    /**
     * Constructor.
     * @param destination the location the actor is travelling to
     */
    public GoldenFogDoorTravelAction(Location destination) {
        this.destination = destination;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        map.moveActor(actor, destination); // move the actor to the new location
        String result = menuDescription(actor);
        return (result);
    }

    @Override
    public String menuDescription(Actor actor) {
        return (actor + " travels through the Golden Fog");
    }
}
