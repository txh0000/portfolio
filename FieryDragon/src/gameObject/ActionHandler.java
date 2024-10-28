package gameObject;

import GameState.DisplayCardState;
import game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Custom ActionListener to handle user input for chit card.
 */
public class ActionHandler implements ActionListener {
    private Game game;

    /**
     * Constructor for ActionHandler class.
     * @param game the current game instance.
     */
    public ActionHandler(Game game) {
        this.game = game;
    }

    /**
     * Update the game variables and change to display card state when a chit card has been selected.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof ChitCard) {

            ChitCard c = (ChitCard) source;
            // if its card selection state and card has not been flipped
            if (game.allowCardToBeSelected && !c.isFlipped()) {
                game.allowCardToBeSelected = false;
                game.selectedCard = c;
                game.notifyGameStateChange(new DisplayCardState(c));
            }
        }
    }
}
