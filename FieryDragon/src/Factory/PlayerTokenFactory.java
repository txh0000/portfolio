package Factory;

import animal.Animal;
import gameObject.*;
import gameObject.player.Player;
import gameObject.player.PlayerToken;

public class PlayerTokenFactory extends GameObjectFactory {
    @Override
    public PlayerToken createPlayerToken(Player player) {
        return new PlayerToken(player);
    }
}
