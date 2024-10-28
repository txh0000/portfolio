package game.actors.players;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.weapons.GreatKnife;

/**
 * Class to represent the Bandit player class.
 * Starts with the Great Knife weapon.
 * Created by:
 * @author Jun Lim
 */
public class Bandit extends Player{
	/**
	 * Constructor.
	 */
	public Bandit() {
		super("Bandit", '@', Bandit.getStartingHp());
		this.addWeaponToInventory(Bandit.getStartingWeapon()); // adding starting weapon
	}

	/**
	 * Creates and returns a new instance of the player class' starting weapon
	 * 
	 * @return a new instance of the starting weapon
	 */
	public static WeaponItem getStartingWeapon() {
		return new GreatKnife();
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