package game.actors.enemies;

import game.RandomNumberGenerator;
import game.Status;
import game.actors.players.Astrologer;
import game.actors.players.Bandit;
import game.actors.players.Samurai;
import game.actors.players.Wretch;

/** Invader actor class.
 * 
 * Created by:
 * @author Jun Lim
 */
public class Invader extends Enemy {
    /**
     * Constructor.
     */
    public Invader() {
        super("Invader",'ඞ', 0, Status.ENEMY_INVADER, 1358, 5578);
        int randInt = RandomNumberGenerator.getRandomInt(0, 3); // deciding which class to start as, and getting their starting weapon and hp
        // System.out.println(randInt + " random");
        switch(randInt) { 
            case 0: // samurai
                this.addWeaponToInventory(Samurai.getStartingWeapon());
                this.increaseMaxHp(Samurai.getStartingHp());
                break;
            case 1: // bandit
                this.addWeaponToInventory(Bandit.getStartingWeapon());
                this.increaseMaxHp(Bandit.getStartingHp());
                break;
            case 2: // Wretch
                this.addWeaponToInventory(Wretch.getStartingWeapon());
                this.increaseMaxHp(Wretch.getStartingHp());
                break;
            case 3: // Astrologer
                this.addWeaponToInventory(Astrologer.getStartingWeapon());
                this.increaseMaxHp(Astrologer.getStartingHp());
                break;
        }
        // System.out.println(this.getMaxHp());
        // System.out.println(this.getWeaponInventory());

        this.addCapability(Status.REMOVED_ON_PLAYER_DEATH); // allowing invader to be removed when player dies
        this.removeCapability(Status.CAN_DESPAWN); // preventing invader from randomly despawning every turn
    }

    @Override
    public boolean resetOnDeathOnly() {
        return true;
    }
}
