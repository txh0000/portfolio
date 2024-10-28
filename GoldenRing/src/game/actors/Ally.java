package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.RandomNumberGenerator;
import game.Status;
import game.actions.AttackAction;
import game.actors.players.Astrologer;
import game.actors.players.Bandit;
import game.actors.players.Player;
import game.actors.players.Samurai;
import game.actors.players.Wretch;
import game.behaviours.WanderBehaviour;

/** Ally actor class.
 * 
 * Created by:
 * @author Jun Lim
 */
public class Ally extends Player {
    /**
     * Constructor.
     */
    public Ally() {
        super("Ally", 'A',0);
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

        this.removeCapability(Status.CAN_TRADE); // removing ability to perform player-only actions
        this.removeCapability(Status.CAN_DROP_RUNES);
        this.removeCapability(Status.CAN_GAIN_RUNES);
        this.addCapability(Status.REMOVED_ON_PLAYER_DEATH);
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Action action : actions) { // for each action
            if (action.getClass() == AttackAction.class || action.getClass().getSuperclass() == AttackAction.class) { // if the action is an attackaction or extends it
                // System.out.println("attack action found!!");
                return action;
            }
        }
        Action wanderAction = new WanderBehaviour().getAction(this, map); // wander around randomly otherwise
        return wanderAction;
    }

    @Override
    public void reset(GameMap map) {
        map.removeActor(this);
    }

    @Override
    public boolean resetOnDeathOnly() {
        return true;
    }
    
}
