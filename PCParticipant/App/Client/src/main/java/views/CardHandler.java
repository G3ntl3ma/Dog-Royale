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

            TranslateTransition translate = new TranslateTransition();
            TranslateTransition translateBoard = new TranslateTransition();

            System.out.println(this.localToScene(0, 0));
            translate.setNode(this);
            translate.setByY(-this.localToScene(0, 0).getY());

            ScaleTransition scaleTransition = new ScaleTransition();
            ScaleTransition scaleTransitionBoard = new ScaleTransition();

            scaleTransition.setNode(this);
            scaleTransition.setByY(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            scaleTransition.setByX(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight() * (this.getFitWidth() / this.getFitHeight()));

            scaleTransition.setOnFinished(event2 -> {
                pcOCG.getHandCards().getChildren().remove(this);
            });

            HandCard cards2 = new HandCard(currentCard.getCard(), 1);
            cards2.setOnMouseClicked(event1 -> {
            }); //removing the event so it can't be clicked again
            pcOCG.getPaneBoardView().getChildren().add(cards2); //adding the card to the board, so it can be displayed there
            pcOCG.getPaneBoardView().getLocalToSceneTransform();
            cards2.setTranslateX(this.localToScene(0, 0).getX() - pcOCG.getPaneBoardView().localToScene(0, 0).getX());
            cards2.setTranslateY(this.localToScene(0, 0).getY() - pcOCG.getPaneBoardView().localToScene(0, 0).getY());

            System.out.println("x:" + this.getScaleX() + "y" + this.getScaleY());

            translateBoard.setNode(cards2);
            translateBoard.setToX(pcOCG.getPaneBoardView().getWidth() / 2 - cards2.getFitWidth() * ((pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight()/ 2)) );
            translateBoard.setToY(pcOCG.getPaneBoardView().getHeight() / 2 - cards2.getFitHeight() * (pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight() /4));



            scaleTransitionBoard.setNode(cards2);
            scaleTransitionBoard.setByY(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            scaleTransitionBoard.setByX(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());


            TranslateTransition toDiscardPile = new TranslateTransition();
            toDiscardPile.setNode(cards2);
            toDiscardPile.setToX(pcOCG.getDiscardPile().localToScene(0, 0).getX() - pcOCG.getPaneBoardView().localToScene(0, 0).getX());
            toDiscardPile.setToY(pcOCG.getDiscardPile().localToScene(0,0).getY() - pcOCG.getPaneBoardView().localToScene(0, 0).getY());

            ScaleTransition toDiscardPileS = new ScaleTransition();
            toDiscardPileS.setNode(cards2);
            toDiscardPileS.setByX(-pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            toDiscardPileS.setByY(-pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());

            PauseTransition pause = new PauseTransition();
            pause.setDuration(javafx.util.Duration.millis(500));
            pause.setOnFinished(event -> {
                toDiscardPileS.play();
                toDiscardPile.play();
            });

            scaleTransitionBoard.setOnFinished(event -> { //TODO: DELETE
                pause.play();
            });
            scaleTransition.play();
            translate.play();
            scaleTransitionBoard.play();
            translateBoard.play();
            System.out.println(cards2.getFitHeight() + " " + pcOCG.getPaneBoardView().getHeight());



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
