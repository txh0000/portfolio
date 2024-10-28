package game.actors.players;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.weapons.Uchigatana;

/**
 * Class to represent the Samurai player class.
 * Starts with the Uchigatana weapon.
 * Created by:
 * @author Jun Lim
 */
public class Samurai extends Player{
	/**
	 * Constructor.
	 */
	public Samurai() {
		super("Samurai", '@', Samurai.getStartingHp());
		this.addWeaponToInventory(Samurai.getStartingWeapon()); // adding starting weapon
	}

	/**
	 * Creates and returns a new instance of the player class' starting weapon
	 * 
	 * @return a new instance of the starting weapon
	 */
	public static WeaponItem getStartingWeapon() {
		return new Uchigatana();
	}

	/**
	 * Returns the player class' starting maximum hp
	 * 
	 * @return the starting maximum hp
	 */
	public static int getStartingHp() {
		return 455;
	}
}