package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.actors.Ally;
import game.actors.enemies.Invader;

/**
 * Summon Sign summon action class.
 * Created by:
 * @author Jun Lim
 */
public class SummonSignAction extends Action{
    private Location signLocation; // Location to summon from

    /**
     * Constructor.
     */
    public SummonSignAction(Location signLocation) 
    {
        this.signLocation = signLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Location spawnLocation = null;

        for(Exit exit : signLocation.getExits()) { // checking if there is an empty space arount the sign's location to summon
            Location exitDestination = exit.getDestination();
            if (exitDestination.containsAnActor() == false) {
                spawnLocation = exitDestination;
            }
        }

        if (spawnLocation == null) { // if there are no empty exits
            return("there was no space to summon.");
        }

        boolean bool = RandomNumberGenerator.percentChance(50); // deciding whether to spawn an ally or an invader
        // System.out.println(bool + " summon");
        if (bool == true) { // summon an ally
            map.addActor(new Ally(), spawnLocation);
            return(actor + " summons an ally.");
        } else { // summon an invader
            map.addActor(new Invader(), spawnLocation);
            return(actor + " summons an invader.");
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return(actor + " summons an ally or an invader");
    }
    
}
