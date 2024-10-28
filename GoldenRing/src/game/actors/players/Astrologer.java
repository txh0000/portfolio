package game.actors.players;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.items.weapons.AstrologersStaff;

/**
 * Class to represent the Astrologer player class.
 * Starts with the Astrologer's Staff weapon.
 * Created by:
 * @author Jun Lim
 */
public class Astrologer extends Player{
	/**
	 * Constructor.
	 */
	public Astrologer() {
		super("Astrologer", '@', Astrologer.getStartingHp());
		this.addWeaponToInventory(Astrologer.getStartingWeapon()); // adding starting weapon
	}
	/**
	 * Creates and returns a new instance of the player class' starting weapon
	 * 
	 * @return a new instance of the starting weapon
	 */
	public static WeaponItem getStartingWeapon() {
		return new AstrologersStaff();
	}

	/**
	 * Returns the player class' starting maximum hp
	 * 
	 * @return the starting maximum hp
	 */
	public static int getStartingHp() {
		return 396;
	}
}