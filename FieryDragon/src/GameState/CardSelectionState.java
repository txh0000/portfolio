package GameState;

import game.Game;
import gameObject.ChitCard;
import gameObject.player.Bot;
import gameObject.player.PlayerToken;

import javax.swing.*;

/**
 * Used to represent card selection state.
 */
public class CardSelectionState extends GameState{

    /**
     * Set game variable to allow player to select a chit card.
     * @param game the current game instance.
     * @param playerToken the player for this turn.
     */
    @Override
    public void execute(Game game, PlayerToken playerToken) {


        // set current Game state text to "Player {currentPlayer.id}: Select a card"
        if (playerToken.getPlayer().getPlayerType().equals("HUMAN")) {
            game.allowCardToBeSelected = true;
            game.changeStateLabelText(String.format("Player %d: select a card", game.getTurnNumber() + 1));
        }
        else {
            game.changeStateLabelText(String.format("bot %d is selecting a card", game.getTurnNumber() + 1));
            // wait for some time
            Bot botPlayer = (Bot) playerToken.getPlayer();
            Timer stopTimer = new Timer(2000, e -> {
                ChitCard selectedChitCard = botPlayer.selectBotChitCard(game,playerToken);
                if (!selectedChitCard.isFlipped()) {
                    game.selectedCard = selectedChitCard;
                    game.notifyGameStateChange(new DisplayCardState(selectedChitCard));
                }
            });
            stopTimer.setRepeats(false);
            stopTimer.start();




        }
    }
}
