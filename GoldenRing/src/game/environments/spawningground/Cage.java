package game.environments.spawningground;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.GodrickGuardEnemy.Dog;

/**
 * CLass representing Dog spawning ground
 */
public class Cage extends SpawningGround {

    /**
     * Character used to display the environment
     */
    private static final char displayChar = '<';

    /**
     * Constructor
     */
    public Cage(){
        super(displayChar, 37, 37);
    }

    /**
     * Spawns a Dog, if the Barrack is in the west half of the map
     *
     * @param location the location to spawn the enemy at
     */
    @Override
    public void spawn_east(Location location) {
        Dog dog = new Dog();
        location.addActor(dog);
    }

    /**
     * Spawns a Dog, if the Barrack is in the east half of the map
     *
     * @param location the location to spawn the enemy at
     */
    @Override
    public void spawn_west(Location location) {
        Dog dog = new Dog();
        location.addActor(dog);
    }
}
