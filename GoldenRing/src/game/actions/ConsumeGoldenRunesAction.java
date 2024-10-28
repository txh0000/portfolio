package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.RuneManager;
import game.items.GoldenRunes;

/**
 * Class to represent consuming Golden Runes item action
 *
 * Created by:
 * @author jingweiong
 */
public class ConsumeGoldenRunesAction extends Action {

    /**
     * The golden runes being consumed
     */
    private GoldenRunes goldenRunes;

    /**
     * Constructor
     *
     * @param goldenRunes the golden runes used by the actor
     */
    public ConsumeGoldenRunesAction(GoldenRunes goldenRunes){
        this.goldenRunes = goldenRunes;
    }

    /**
     * Lets the player use the golden runes
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return      the result of the action to be displayed on the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        RuneManager runeManager = RuneManager.getInstance();

        int runeCount = goldenRunes.getRandomRunes();
        runeManager.changeRunes(runeCount);
        actor.removeItemFromInventory(goldenRunes);
        map.locationOf(actor).removeItem(goldenRunes); // if actor straight away uses the golden runes without pickup

        return (actor + " used the Golden Runes and got " + runeCount + " runes.");
    }

    /**
     * Describes the using golden runes action
     *
     * @param actor The actor performing the action.
     * @return      a description used for the menu UI
     */
    @Override
    public String menuDescription(Actor actor) {
        return (actor + " uses the Golden Runes");
    }
}
