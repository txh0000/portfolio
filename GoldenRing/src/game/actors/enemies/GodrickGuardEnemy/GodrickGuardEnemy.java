package game.actors.enemies.GodrickGuardEnemy;

import game.Status;
import game.actors.enemies.Enemy;

/**
 * Abstract class which all Godrick's Guard enemy types extend.
 *
 * Created by:
 * @author jingweiong
 */
public abstract class GodrickGuardEnemy extends Enemy {

    /**
     * Constructor
     * @param name          name of the enemy
     * @param displayChar   character used to display enemy in the terminal
     * @param hitPoints     starting hit points of the enemy
     * @param runes_min     the minimum number of runes the enemy drops when killed by a player
     * @param runes_max     the maximum number of runes the enemy drops when killed by a player
     */
    public GodrickGuardEnemy(String name, char displayChar, int hitPoints, int runes_min, int runes_max){
        super(name, displayChar, hitPoints, Status.ENEMY_GRAFTED_GUARD, runes_min, runes_max);
    }

}
