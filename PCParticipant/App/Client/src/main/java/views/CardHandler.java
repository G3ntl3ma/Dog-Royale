package views;

import Enums.Card;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;

public class CardHandler {

    private PCObserverControllerGameplay pcOCG;

    private HandCard currentCard;
    private HandCard previousCard;
    private TranslateTransition previousTranslate = new TranslateTransition();
    private TranslateTransition currentTranslate = new TranslateTransition();



    //Pane paneBoardView;
    //HBox handCards;

    public CardHandler(PCObserverControllerGameplay pcOCG) {
        this.pcOCG = pcOCG;
        //this.paneBoardView = pcOCG.getPaneBoardView(); //dont ask me why but it doesnt work with this - I always have to get the paneBoardView from the PCObserverControllerGameplay - same with handCards
        //this.handCards = pcOCG.getHandCards(); //
    }


    /**
     * Adds a Card to the Hand
     */
    public void addCard(Card card)
    {
        HandCard cardcard = new HandCard(card, 1);
    }

    /**
     * Image Views for the hand cards
     */
    public class HandCard extends ImageView {

        private Card card;

        private Image image;
        private int numberInHand;

        public HandCard(Card card, int numberInHand) {
            this.card = card;
            this.image = new Image( getClass().getResource("/" + card.getCardPath() + ".png" ).toString());
            this.numberInHand = numberInHand;

            System.out.println(this.getNumberInHand());
            this.setImage(image);
            this.fitHeightProperty().set(152);
            this.fitWidthProperty().set(128);
            this.pickOnBoundsProperty().set(true);
            this.preserveRatioProperty().set(true);

            //testing //TODO: remove
            try {
                System.out.println(pcOCG.currentPiece.getCurrentPieceIndex());
            }
            catch (Exception e){
                System.out.println("No current piece");
            }
            //

            //if Card is clicked
            this.setOnMouseClicked(event -> {
                //Animation for selecting a new different card (and no animation still running)
                if(currentTranslate.getStatus() != Animation.Status.RUNNING && currentCard != this) {
                    previousCard = currentCard;
                    previousTranslate.setNode(previousCard);
                    previousTranslate.setByY(42);
                    previousTranslate.play();

                    currentCard = this;
                    currentTranslate.setNode(this);
                    currentTranslate.setByY(-42);
                    currentTranslate.play();
                }
                else if(currentCard == this){
                    layCard();
                }
            });
            pcOCG.getHandCards().getChildren().add(this);
        }



        private void layCard() {

            //Animation for removing the card from the hand
            TranslateTransition translate = new TranslateTransition();
            translate.setNode(this);
            translate.setByY(-this.localToScene(0, 0).getY());

            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setNode(this);
            scaleTransition.setByY(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            scaleTransition.setByX(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight() * (this.getFitWidth() / this.getFitHeight()));

            scaleTransition.setOnFinished(event2 -> {
                //removing the Card from the hand
                pcOCG.getHandCards().getChildren().remove(this);
            });

            //creating new Card that will be layed on the board
            HandCard cards2 = new HandCard(currentCard.getCard(), 1);
            cards2.setFitWidth(64);
            cards2.setFitHeight(84);
            cards2.setOnMouseClicked(event1 -> {
            }); //removed the click-event so it can't be clicked again
            pcOCG.getPaneBoardView().getChildren().add(cards2); //adding the card to the board
            pcOCG.getPaneBoardView().getLocalToSceneTransform();
            cards2.setTranslateX(this.localToScene(0, 0).getX() - pcOCG.getPaneBoardView().localToScene(0, 0).getX());
            cards2.setTranslateY(this.localToScene(0, 0).getY() - pcOCG.getPaneBoardView().localToScene(0, 0).getY());

            //Animation for laying the card on the board on the board
            TranslateTransition translateBoard = new TranslateTransition();
            translateBoard.setNode(cards2);
            translateBoard.setToX(pcOCG.getPaneBoardView().getWidth() / 2 - cards2.getFitWidth() * ((pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight() / 2)));
            translateBoard.setToY(pcOCG.getPaneBoardView().getHeight() / 2 - cards2.getFitHeight() * (pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight() / 4));

            ScaleTransition scaleTransitionBoard = new ScaleTransition();
            scaleTransitionBoard.setNode(cards2);
            scaleTransitionBoard.setByY(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            scaleTransitionBoard.setByX(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());

            //Animation for laying the card on the discard pile
            TranslateTransition toDiscardPile = new TranslateTransition();
            toDiscardPile.setNode(cards2);
            toDiscardPile.setToX(pcOCG.getDiscardPile().localToScene(0,0).getX() - pcOCG.getPaneBoardView().localToScene(0, 0).getX()- (cards2.getFitWidth() * cards2.getScaleX() * (1/cards2.getScaleX()) - pcOCG.getDiscardPile().getFitWidth())/2);
            toDiscardPile.setToY(pcOCG.getDiscardPile().localToScene(0,0).getY() - pcOCG.getPaneBoardView().localToScene(0, 0).getY());

            ScaleTransition toDiscardPileS = new ScaleTransition();
            toDiscardPileS.setNode(cards2);
            toDiscardPileS.setByX(-pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            toDiscardPileS.setByY(-pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());

            toDiscardPileS.setOnFinished(event -> {
                HandCard discardCard = new HandCard(currentCard.getCard(), 1);
                pcOCG.getDiscardPane().getChildren().add(discardCard);
                discardCard.setFitHeight(cards2.getFitHeight());
                discardCard.setFitWidth(cards2.getFitWidth());
                discardCard.setScaleX(toDiscardPileS.getByX() * (1-scaleTransitionBoard.getByX()));
                discardCard.setScaleY(toDiscardPileS.getByY() * (1-scaleTransitionBoard.getByY()));
                discardCard.setPreserveRatio(true);
                discardCard.setTranslateX(cards2.localToScene(0,0).getX() - pcOCG.getDiscardPane().localToScene(0, 0).getX());
                discardCard.setTranslateY(cards2.localToScene(0,0).getY() - pcOCG.getDiscardPile().localToScene(0, 0).getY());

                System.out.println("balbla" + cards2.getScaleY());


                ScaleTransition andAnotherOne = new ScaleTransition();
                andAnotherOne.setNode(cards2);
                andAnotherOne.setByX(-1 + pcOCG.getDiscardPile().getFitHeight() / cards2.getFitHeight());
                andAnotherOne.setByY(-1 + pcOCG.getDiscardPile().getFitHeight() / cards2.getFitHeight());
                System.out.println(pcOCG.getDiscardPile().getFitWidth() / cards2.getFitWidth());

                //TranslateTransition andAnotherTranslate = new TranslateTransition();
                //andAnotherTranslate.setNode(cards2);
                //andAnotherTranslate.setByX(andAnotherOne.getByX() * cards2.getFitWidth() / 2);
                //andAnotherTranslate.setByY(andAnotherOne.getByY() * cards2.getFitHeight() / 2);
                //andAnotherTranslate.play();

                //the cloned Card that is played that will be layed on the discard pile

                discardCard.setPreserveRatio(true);
                discardCard.setTranslateX(cards2.localToScene(0,0).getX() - pcOCG.getDiscardPane().localToScene(0, 0).getX());
                discardCard.setTranslateY(cards2.localToScene(0,0).getY() - pcOCG.getDiscardPile().localToScene(0, 0).getY());
                System.out.println(cards2.localToScene(0,0).getY() - pcOCG.getDiscardPile().localToScene(0,0).getY());
                TranslateTransition toDiscardPile2 = new TranslateTransition();
                toDiscardPile2.setNode(discardCard);
                double newY = cards2.localToScene(0,0).getY() - discardCard.getY()  ;
                //toDiscardPile2.setToX(-discardCard.getFitWidth() * andAnotherOne.getByX()/2);
                toDiscardPile2.setToY(discardCard.getFitHeight() * andAnotherOne.getByY()/2);
                System.out.println("moving by" + (pcOCG.getDiscardPile().localToParent(0,0).getY() - discardCard.localToParent(0,0).getY()));
                System.out.println(pcOCG.getDiscardPile().localToScene(0, 0).getY());
                System.out.println(newY);


                //andAnotherOne.play();
                ScaleTransition toDiscardPileS2 = new ScaleTransition();
                toDiscardPileS2.setNode(discardCard);
                toDiscardPileS2.setByX(andAnotherOne.getByX());
                toDiscardPileS2.setByY(andAnotherOne.getByY());


                //toDiscardPile2.play();
                //toDiscardPileS2.play();


            });




            //pause Transition to display the card for half a second
            PauseTransition pause = new PauseTransition();
            pause.setDuration(javafx.util.Duration.millis(500));
            pause.setOnFinished(event -> {
                toDiscardPileS.play();
                toDiscardPile.play();

            });
            //to know when to wait
            scaleTransitionBoard.setOnFinished(event -> { //TODO: DELETE
                pause.play();
            });

            //Playing the transitions
            scaleTransition.play();
            translate.play();
            scaleTransitionBoard.play();
            translateBoard.play();
        }

        public Card getCard() { return this.card;}

        public int getNumberInHand() {
            return this.numberInHand;
        }

        public void setNumberInHand(int numberInHand) {
            this.numberInHand = numberInHand;
        }
    }
}
