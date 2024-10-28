package Factory;

import animal.Animal;
import gameObject.*;
import gameObject.player.Player;
import gameObject.player.PlayerToken;

public class ChitCardFactory extends GameObjectFactory{
    @Override
    public ChitCard createChitCard(int id, Animal animal, int numOfAnimal, ActionHandler actionHandler) {
        return new ChitCard(id, animal, numOfAnimal, actionHandler);
    }

}
