package game.actors.traders;

import game.Status;
import game.TradeableWeapon;
import game.items.weapons.*;

/**
 * Class to represent Finger Reader Enia trader
 *
 * Created by:
 * @author jingweiong
 */
public class FingerReaderEnia extends Trader{

    /**
     * Constructor
     */
    public FingerReaderEnia(){
        super("Finger Reader Enia", 'E');
        this.addCapability(Status.CAN_ACCEPT_REMEMBRANCE);
        this.addWeapon(new TradeableWeapon(new Uchigatana(), 0, 500));
        this.addWeapon(new TradeableWeapon(new Club(), 0, 100));
        this.addWeapon(new TradeableWeapon(new GreatKnife(), 0, 350));
        this.addWeapon(new TradeableWeapon(new Grossmesser(), 0, 100));
        this.addWeapon(new TradeableWeapon(new Scimitar(), 0, 100));
        this.addWeapon(new TradeableWeapon(new AxeOfGodrick(), 0, 100));
        this.addWeapon(new TradeableWeapon(new GraftedDragon(), 0, 200));
    }
}
