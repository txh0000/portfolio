package gameObject.player;

import game.Game;
import gameObject.ChitCard;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player{
    public Bot(int id) {
        super(id, "BOT");
    }

    public ChitCard selectBotChitCard(Game game, PlayerToken playerToken){
        Random random = new Random();
        int percentageCorrectInput = 70;
        ChitCard selectedChitCard;

        // percentage of bot getting the correct chit card/random chit card
        int randomPercentageInput = random.nextInt(101);


        // collect non-flip chit card
        ArrayList<ChitCard> nonFlipChitCards = new ArrayList<>();
        for (int i=0; i < game.chitCards.size(); i++) {
            ChitCard checkChitCard = game.chitCards.get(i);
            if (!checkChitCard.isFlipped()) {
                nonFlipChitCards.add(checkChitCard);
            }
        }

        // correct chit card
        if (randomPercentageInput <= percentageCorrectInput) {
            // collect all the correct chit card
            ArrayList<ChitCard> correctChitCards = new ArrayList<>();
            for (int i=0; i < nonFlipChitCards.size(); i++) {
                ChitCard checkChitCard = nonFlipChitCards.get(i);
                if (playerToken.isInCave() && game.caveCards.get(playerToken.getPlayerId()).getAnimalName().equals(checkChitCard.getAnimal().getName())) {
                    correctChitCards.add(checkChitCard);
                }
                else if (!playerToken.isInCave() && game.volcanoCards.get(playerToken.getCurrentPos()).getAnimalName().equals(checkChitCard.getAnimal().getName())){
                    correctChitCards.add(checkChitCard);
                }
            }
            if (correctChitCards.isEmpty()){
                int selectedRandomChitCard = random.nextInt(nonFlipChitCards.size());
                selectedChitCard = nonFlipChitCards.get(selectedRandomChitCard);
            }
            else {
                int selectedCorrectChitCard = random.nextInt(correctChitCards.size());
                selectedChitCard = correctChitCards.get(selectedCorrectChitCard);
            }

        }
        // Random Chit Card
        else {
            int selectedRandomChitCard = random.nextInt(nonFlipChitCards.size());
            selectedChitCard = nonFlipChitCards.get(selectedRandomChitCard);
        }
        return selectedChitCard;
    }
}
