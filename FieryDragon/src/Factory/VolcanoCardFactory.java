package Factory;

import animal.Animal;
import gameObject.*;
import gameObject.player.Player;
import gameObject.player.PlayerToken;

public class VolcanoCardFactory extends GameObjectFactory{

    @Override
    public VolcanoCard createVolcanoCard(Animal animal, int id) {
        return new VolcanoCard(animal, id);
    }
}
