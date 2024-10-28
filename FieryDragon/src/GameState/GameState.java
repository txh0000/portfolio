package GameState;

import game.Game;
import gameObject.player.PlayerToken;

/**
 * Abstract class GameState.
 */
public abstract class GameState {

    /**
     * Carry out the necessary action for that state.
     * @param game the current game instance.
     * @param playerToken the player for this turn.
     */
    public abstract void execute(Game game, PlayerToken playerToken);
}
