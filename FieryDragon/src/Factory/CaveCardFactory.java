package Factory;

import animal.Animal;
import gameObject.*;
import gameObject.player.Player;
import gameObject.player.PlayerToken;

public class CaveCardFactory extends GameObjectFactory {

    @Override
    public CaveCard createCaveCard(int id, Animal animal) {
        return new CaveCard(id, animal);
    }

}
