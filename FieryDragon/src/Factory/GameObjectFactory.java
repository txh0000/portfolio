package Factory;


import animal.Animal;
import gameObject.*;
import gameObject.player.Player;
import gameObject.player.PlayerToken;

public abstract class GameObjectFactory {
    public ChitCard createChitCard(int id, Animal animal, int numOfAnimal, ActionHandler actionHandler) {
        return null;
    }

    public CaveCard createCaveCard(int id, Animal animal) {
        return null;
    }

    public PlayerToken createPlayerToken(Player player) {
        return null;
    }

    public VolcanoCard createVolcanoCard(Animal animal, int id) {
        return null;
    }

}
