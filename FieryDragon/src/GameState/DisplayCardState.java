package GameState;

import game.Game;
import gameObject.ChitCard;
import gameObject.player.PlayerToken;

import javax.swing.*;

/**
 * Used to represent display card state.
 */
public class DisplayCardState extends GameState {
    private ChitCard chitCard;

    /**
     * Constructor for DisplayCardState class.
     * @param chitCard that was selected by the player for this turn.
     */
    public DisplayCardState(ChitCard chitCard) {
        this.chitCard = chitCard;
    }

    /**
     * Display the animal(s) of the flipped card for a certain period of time.
     * @param game the current game instance.
     * @param playerToken the player for this turn.
     */
    @Override
    public void execute(Game game, PlayerToken playerToken) {
        // set current Game state text
        game.changeStateLabelText("Calculating moves...");

        // flip card
        chitCard.flip();

        // show animation (none for now)

        // wait for some time
        Timer stopTimer = new Timer(1000, e -> {
            // change state to playerMovementState when completed
            game.notifyGameStateChange(new PlayerMovementState(chitCard.getAnimal(), chitCard.getNumOfAnimal()));
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }
}
