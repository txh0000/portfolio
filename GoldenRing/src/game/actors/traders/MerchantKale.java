package game.actors.traders;

import game.TradeableWeapon;
import game.items.weapons.*;

/**
 * Class to represent Merchant Kale trader.
 * 
 * Created by:
 * @author Jun Lim
 */
public class MerchantKale extends Trader{
    /**
     * Constructor.
     */
    public MerchantKale(){
        super("Merchant Kale", 'K');
        this.addWeapon(new TradeableWeapon(new Uchigatana(), 5000, 500));
        this.addWeapon(new TradeableWeapon(new Club(), 600, 100));
		this.addWeapon(new TradeableWeapon(new GreatKnife(), 3500, 350));
        this.addWeapon(new TradeableWeapon(new Grossmesser(), 0, 100));
        this.addWeapon(new TradeableWeapon(new Scimitar(), 600, 100));
        this.addWeapon(new TradeableWeapon(new AxeOfGodrick(), 0, 100));
        this.addWeapon(new TradeableWeapon(new GraftedDragon(), 0, 200));
    }
}
