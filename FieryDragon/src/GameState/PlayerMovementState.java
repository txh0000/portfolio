package GameState;

import animal.Animal;
import constant.Constant;
import game.Game;
import game.GameManager;
import gameObject.player.PlayerToken;

import javax.swing.*;

/**
 * Used to represent player movement state.
 */
public class PlayerMovementState extends GameState{
    private Animal animalToMatch;
    private int numberOfMoves;

    /**
     * Constructor for PlayerMovementState class.
     * @param animal the animal on the chit card that was flipped.
     * @param numberOfMoves the number of animals on the chit card.
     */
    public PlayerMovementState(Animal animal, int numberOfMoves) {
        this.animalToMatch = animal;
        this.numberOfMoves = numberOfMoves;
    }

    /**
     * Check whether player has a new destination and update accordingly.
     * @param game the current game instance.
     * @param playerToken the player for this turn.
     */
    @Override
    public void execute(Game game, PlayerToken playerToken) {

        if (playerToken.isInCave() && game.caveCards.get(playerToken.getPlayerId()).getAnimalName().equals(animalToMatch.getName())) {
            // if player is still in starting cave, compare with cave animal
            game.changeStateLabelText(String.format("Animals Match!!! Move %d steps", numberOfMoves));
            if(!game.nextPlayerTokenAtPosition(playerToken, playerToken.getCurrentPos(), numberOfMoves)){
                playerToken.travelDistance(this.numberOfMoves);

                // start animation
                playerToken.playAnimation(true);
            }else{
                game.changeStateLabelText("Someone there, can't move");
                game.incrementTurnNumber();
            }

        } else if (!playerToken.isInCave() && game.volcanoCards.get(playerToken.getCurrentPos()).getAnimalName().equals(animalToMatch.getName())) {
            // if animal matches
            game.changeStateLabelText(String.format("Animals Match!!! Move %d steps", numberOfMoves));
            if(!(playerToken.getDistanceTravelled() + numberOfMoves > (game.volcanoCards.size() + 1))) {
                if (!game.nextPlayerTokenAtPosition(playerToken, playerToken.getCurrentPos(), numberOfMoves)) {

                    playerToken.travelDistance(this.numberOfMoves);

                    // start animation
                    playerToken.playAnimation(true);
                    // check if player has won
                    if (playerToken.getDistanceTravelled() == game.volcanoCards.size() + 1) {
                        GameManager.getInstance().winner = playerToken;
                        game.running = false;
                    }
                } else {
                    game.changeStateLabelText("Someone there, can't move");
                    game.incrementTurnNumber();
                }
            }else{

                game.changeStateLabelText("Can't go past the cave! ;-;");
                game.incrementTurnNumber();

            }

        } else if (!playerToken.isInCave() && animalToMatch.getName().equals(Constant.DRAGON_PIRATE_NAME)) {
            // if animal is dragon pirate
            game.changeStateLabelText(String.format("Oh no, Dragon Pirates!!! Move back %d steps", numberOfMoves));
            if(!game.nextPlayerTokenAtPosition(playerToken, playerToken.getCurrentPos(), -numberOfMoves)){


                playerToken.travelDistance(-this.numberOfMoves);

                // start animation
                playerToken.playAnimation(false);

                // increment turn number
                game.incrementTurnNumber();
            }else{
                game.changeStateLabelText("Someone there, can't move");
                game.incrementTurnNumber();
            }

        } else if(!playerToken.isInCave() && animalToMatch.getName().equals(Constant.MAGIC_DRAGON_NAME)){
            game.changeStateLabelText(String.format("Magic Swap!!!!"));
            game.swapWithClosestToken(playerToken, playerToken.getCurrentPos());

            PlayerToken closestToken = game.findClosestToken(playerToken, playerToken.getCurrentPos());
            int closetDistance = game.findClosestDistance(playerToken, playerToken.getCurrentPos());

            if (closestToken != null) {
                if(playerToken.getCurrentPos() > closestToken.getCurrentPos()){
                    playerToken.updatedistanceTravelled(-closetDistance);
                    closestToken.updatedistanceTravelled(closetDistance);
                }else{
                    playerToken.updatedistanceTravelled(closetDistance);
                    closestToken.updatedistanceTravelled(-closetDistance);
                }

                playerToken.playSwapAnimation(true);
                closestToken.playSwapAnimation(true);

                game.incrementTurnNumber();
            }
            else {
                game.incrementTurnNumber();
            }



        }
        else {
            // if no matches
            game.changeStateLabelText("Animals does not match, can't move");
            game.incrementTurnNumber();

        }

        // wait for some time
        Timer stopTimer = new Timer(2000, e -> {
            // change to card selection State
            game.notifyGameStateChange(new CardSelectionState());
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }

    
}
