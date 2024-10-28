package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.RandomNumberGenerator;
import game.actions.ConsumeGoldenRunesAction;

/**
 * Class to represent Golden Runes item
 *
 * Created by:
 * @author jingweiong
 */
public class GoldenRunes extends Item {

    /**
     * Constructor
     */
    public GoldenRunes(){
        super("Golden Runes", '*', true);
        addAction(new ConsumeGoldenRunesAction(this));
    }

    /**
     * Generate random rune number
     *
     * @return runes count between 200 and 10000
     */
    public int getRandomRunes(){
        return RandomNumberGenerator.getRandomInt(200, 10000);
    }
}
