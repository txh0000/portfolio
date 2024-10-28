package game.items.remembrances;


import java.util.ArrayList;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.ExchangeRemembranceAction;

/**
 * Interface for Remembrance items.
 * Created by:
 * @author Jun Lim
 */
public abstract class Remembrance extends Item{
    /**
     * List of weapons the remembrance can be exchanged for.
     */
    protected ArrayList<WeaponItem> exchangeWeapons;

    public Remembrance(String name, char displayChar, ArrayList<WeaponItem> exchangeWeapons) {
        super(name, displayChar, true);
        this.exchangeWeapons = exchangeWeapons;
    }

    public ActionList getExchangeActions() {
        ActionList actions = new ActionList();
        for (WeaponItem exchangeWeapon : this.exchangeWeapons) { // for each weapon the remembrance can be exchanged for
            actions.add(new ExchangeRemembranceAction(this, exchangeWeapon)); // create and add an action to exchange for it
        }
        return actions;
    }
}
