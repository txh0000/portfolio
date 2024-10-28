package game.environments.spawningground;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.GodrickGuardEnemy.GodrickSoldier;

/**
 * Class representing Godrick Soldier spawning ground
 *
 * Created by:
 * @author jingweiong
 */
public class Barrack extends SpawningGround {

    /**
     * Character used to display the environment
     */
    private static final char displayChar = 'B';

    /**
     * Constructor
     */
    public Barrack(){
        super(displayChar, 45, 45);
    }

    /**
     * Spawns a Godrick Soldier, if the Barrack is in the west half of the map
     *
     * @param location the location to spawn the enemy at
     */
    @Override
    public void spawn_west(Location location) {
        GodrickSoldier godrickSoldier = new GodrickSoldier();
        location.addActor(godrickSoldier);
    }

    /**
     * Spawns a Godrick Soldier, if the Barrack is in the east half of the map
     *
     * @param location the location to spawn the enemy at
     */
    @Override
    public void spawn_east(Location location) {
        GodrickSoldier godrickSoldier = new GodrickSoldier();
        location.addActor(godrickSoldier);
    }
}
