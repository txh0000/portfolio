package game.actors.players;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.weapons.Club;

/**
 * Class to represent the Wretch player class.
 * Starts with the Club weapon.
 * Created by:
 * @author Jun Lim
 */
public class Wretch extends Player{
	/**
	 * Constructor.
	 */
	public Wretch() {
		super("Wretch", '@', Wretch.getStartingHp());
		this.addWeaponToInventory(Wretch.getStartingWeapon()); // adding starting weapon
	}

	/**
	 * Creates and returns a new instance of the player class' starting weapon
	 * 
	 * @return a new instance of the starting weapon
	 */
	public static WeaponItem getStartingWeapon() {
		return new Club();
	}

	/**
	 * Returns the player class' starting maximum hp
	 * 
	 * @return the starting maximum hp
	 */
	public static int getStartingHp() {
		return 414;
	}
}