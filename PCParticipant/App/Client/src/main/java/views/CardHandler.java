package views;

import Enums.Card;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * made to handle ur handcards
 */
public class CardHandler {

    private PCObserverControllerGameplay pcOCG;

    private HandCard currentCard;
    private HandCard previousCard;
    private TranslateTransition previousTranslate = new TranslateTransition();
    private TranslateTransition currentTranslate = new TranslateTransition();

    private static int selectedVaue;
    private static boolean isStarter;

    /**
     * Constructor for the CardHandler - handles the cards in your hand
     * @param pcOCG the PCObserverControllerGameplay
     */

    public CardHandler(PCObserverControllerGameplay pcOCG) {
        this.pcOCG = pcOCG;
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

        /**
         * Constructor for the HandCard
         * @param card type of card - enum
         * @param numberInHand which place in ur hand is the card in TODO: delete if not needed
         */
        public HandCard(Card card, int numberInHand) {

            this.card = card;
            this.image = new Image( getClass().getResource("/" + card.getCardPath() + ".png" ).toString());
            this.numberInHand = numberInHand;

            this.setImage(image);
            this.fitHeightProperty().set(152);
            this.fitWidthProperty().set(128);
            this.pickOnBoundsProperty().set(true);
            this.preserveRatioProperty().set(true);


            //if Card is clicked
            this.setOnMouseClicked(event -> {
                //Animation for selecting a new different card (and no animation still running)
                if(currentTranslate.getStatus() != Animation.Status.RUNNING && currentCard != this) {

                    Stage stage;
                    Scene scene;
                    FXMLLoader fxmlLoader;
                    Image icon = new Image("icon.png");
                    String css = this.getClass().getResource("style.css").toExternalForm();


                    previousCard = currentCard;
                    previousTranslate.setNode(previousCard);
                    previousTranslate.setByY(42);
                    previousTranslate.play();

                    currentCard = this;
                    currentTranslate.setNode(this);
                    currentTranslate.setByY(-42);
                    currentTranslate.play();

                    switch(this.card)
                    {
                        case startCard1:
                            PieceImages.setSelectEnemyPiece(false);

                            stage = new Stage();
                            fxmlLoader = new FXMLLoader(PCObserverGui.class.getResource("selectValueStartCard1.fxml"));
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            scene.getStylesheets().add(css);
                            stage.setScene(scene);
                            stage.getIcons().add(icon);
                            stage.show();

                            break;
                        case startCard2:
                            PieceImages.setSelectEnemyPiece(false);
                            stage = new Stage();
                            fxmlLoader = new FXMLLoader(PCObserverGui.class.getResource("selectValueStartCard2.fxml"));
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            scene.getStylesheets().add(css);
                            stage.setScene(scene);
                            stage.getIcons().add(icon);
                            stage.show();
                            break;
                        case swapCard:
                            PieceImages.setSelectEnemyPiece(true);
                            selectedVaue = 0;
                            isStarter = false;
                            break;
                        case magnetCard:
                            PieceImages.setSelectEnemyPiece(true);
                            selectedVaue = 0;
                            isStarter = false;
                            break;
                        case plusMinus4:
                            PieceImages.setSelectEnemyPiece(false);
                            stage = new Stage();
                            fxmlLoader = new FXMLLoader(PCObserverGui.class.getResource("selectValuePlusMinus4.fxml"));
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            scene.getStylesheets().add(css);
                            stage.setScene(scene);
                            stage.getIcons().add(icon);
                            stage.show();
                            break;
                        case oneToSeven:
                            PieceImages.setSelectEnemyPiece(false);

                            stage = new Stage();
                            fxmlLoader = new FXMLLoader(PCObserverGui.class.getResource("selectValueOneToSeven.fxml"));
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            scene.getStylesheets().add(css);
                            stage.setScene(scene);
                            stage.getIcons().add(icon);
                            stage.show();
                            break;
                        case copyCard:
                            PieceImages.setSelectEnemyPiece(false);
                            selectedVaue = 0;
                            isStarter = false;
                            break;
                        default:
                            PieceImages.setSelectEnemyPiece(false);
                            PieceImages.selectedEnemyPiece = null;
                            selectedVaue = 0;
                            isStarter = false;
                            break;
                    }
                }
                else if(currentCard == this && (PieceImages.currentPiece != null ||PieceImages.selectEnemyPiece && PieceImages.selectedEnemyPiece != null)) {
                    layCard();
                    PieceImages.setSelectEnemyPiece(false);
                }
                else {
                    //Show a warning to the Player that no Figure has been selected
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Figur auswählen");
                    alert.setHeaderText("Es wurde keine Figur ausgewählt");

                    Image icon = new Image("icon.png");
                    DialogPane dialog = alert.getDialogPane();
                    dialog.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
                    dialog.getStyleClass().add("dialog");
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(icon);

                    alert.show();
                }
            });
            pcOCG.getHandCards().getChildren().add(this);
            //TODO: decide whether we add error testing for illegal moves or not since they are said to punish the player so we do not have to check for them in the client but we could for some
            //TODO: for example can u use a start card on an piece thats already in game and it would be called an illegal move and u get kicked or something or would it just not be possible?
        }



        private void layCard() {
            HandCard cards2 = new HandCard(currentCard.getCard(), 1);
            cards2.setFitWidth(this.getFitWidth());
            cards2.setFitHeight(this.getFitHeight());
            cards2.setOnMouseClicked(event1 -> {
            }); //removed the click-event so it can't be clicked again


            //Animation for removing the card from the hand
            TranslateTransition translate = new TranslateTransition();
            translate.setNode(this);
            translate.setToX((pcOCG.getPaneBoardView().localToScene(0,0).getX() + cards2.localToParent(0,0).getX() + pcOCG.getPaneBoardView().getWidth() / 2 - ((pcOCG.getPaneBoardView().getWidth() / 4))) - this.localToScene(0,0).getX());
            translate.setToY((pcOCG.getPaneBoardView().localToScene(0,0).getY() + cards2.localToParent(0,0).getY() + pcOCG.getPaneBoardView().getHeight() / 2 - (pcOCG.getPaneBoardView().getHeight() / 6)) - this.localToScene(0,0).getY());

            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setNode(this);
            scaleTransition.setToX(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            scaleTransition.setToY(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());

            scaleTransition.setOnFinished(event2 -> {
                //removing the Card from the hand
                pcOCG.getHandCards().getChildren().remove(this);
            });

            //creating new Card that will be layed on the board

            pcOCG.getPaneBoardView().getChildren().add(cards2); //adding the card to the board
            pcOCG.getPaneBoardView().getLocalToSceneTransform();
            cards2.setTranslateX(this.localToScene(0, 0).getX() - pcOCG.getPaneBoardView().localToScene(0, 0).getX());
            cards2.setTranslateY(this.localToScene(0, 0).getY() - pcOCG.getPaneBoardView().localToScene(0, 0).getY());

            System.out.println("blabla" + cards2.localToScene(0,0).getX());

            //Animation for laying the card on the board on the board
            TranslateTransition translateBoard = new TranslateTransition();
            translateBoard.setNode(cards2);
            translateBoard.setToX(pcOCG.getPaneBoardView().getWidth() / 2 - (pcOCG.getPaneBoardView().getWidth() / 4));
            translateBoard.setToY(pcOCG.getPaneBoardView().getHeight() / 2 - (pcOCG.getPaneBoardView().getHeight() / 6));

            ScaleTransition scaleTransitionBoard = new ScaleTransition();
            scaleTransitionBoard.setNode(cards2);
            scaleTransitionBoard.setToX(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());
            scaleTransitionBoard.setToY(pcOCG.getPaneBoardView().getHeight() / 2 / this.getFitHeight());




            //pause Transition to display the card for half a second
            PauseTransition pause = new PauseTransition();
            pause.setDuration(javafx.util.Duration.millis(500));

            pause.setOnFinished(event -> {

                //Animation for laying the card on the discard pile
                TranslateTransition toDiscardPile = new TranslateTransition();
                toDiscardPile.setNode(cards2);
                toDiscardPile.setToX(-pcOCG.getPaneBoardView().localToScene(0,0).getX() + pcOCG.getDiscardPile().localToScene(0,0).getX() - 26);
                toDiscardPile.setToY(-33 - pcOCG.getDiscardPile().getFitHeight());

                ScaleTransition toDiscardPileS = new ScaleTransition();
                toDiscardPileS.setNode(cards2);
                toDiscardPileS.setToX(pcOCG.getDiscardPile().getFitHeight() / cards2.getFitHeight());
                toDiscardPileS.setToY(pcOCG.getDiscardPile().getFitHeight() / cards2.getFitHeight());


                //the cloned Card that is played that will be layed on the discard pile
                HandCard discardCard = new HandCard(currentCard.getCard(), 1);
                discardCard.setFitHeight(cards2.getFitHeight());
                discardCard.setFitWidth(cards2.getFitWidth());
                discardCard.setScaleX(cards2.getScaleY());
                discardCard.setScaleY(cards2.getScaleY());
                discardCard.setTranslateX(cards2.localToScene(0,0).getX() - pcOCG.getDiscardPane().localToScene(0,0).getX()+ (cards2.getFitHeight() * cards2.getScaleY() - cards2.getFitHeight()) / 2 * (64.0/84.0)); //+ 40.7 -0.0618896484375
                discardCard.setTranslateY(cards2.localToScene(0,0).getY() - pcOCG.getDiscardPane().localToScene(0,0).getY() + (cards2.getFitHeight() * cards2.getScaleY() - cards2.getFitHeight()) / 2); //+ 53.5
                pcOCG.getDiscardPane().getChildren().add(discardCard);
                discardCard.setPreserveRatio(true);

                discardCard.setOnMouseClicked(event1 -> {
                }); //removed the click-event, so card can't be clicked again

                TranslateTransition toDiscardPile2 = new TranslateTransition();
                toDiscardPile2.setNode(discardCard);
                toDiscardPile2.setToX(pcOCG.getDiscardPile().getTranslateX() + pcOCG.getDiscardPile().getFitWidth() / 2 + 2);
                toDiscardPile2.setToY(- discardCard.getFitHeight() + 3*pcOCG.getDiscardPile().getFitHeight()/2 - 7);
                System.out.println("discardPile y" + (- discardCard.getFitHeight() + 3*pcOCG.getDiscardPile().getFitHeight()/2 - 7));

               // andAnotherOne.play();
                ScaleTransition toDiscardPileS2 = new ScaleTransition();
                toDiscardPileS2.setNode(discardCard);
                toDiscardPileS2.setToX(84/this.getFitHeight());
                toDiscardPileS2.setToY(84/this.getFitHeight());


                ParallelTransition parallelTransition = new ParallelTransition(
                        toDiscardPile2,
                        toDiscardPileS2,
                        toDiscardPile,
                        toDiscardPileS
                );

                parallelTransition.setOnFinished(event2 -> {
                    pcOCG.getPaneBoardView().getChildren().remove(cards2);
                    //pcOCG.getDiscardPane().getChildren().remove(discardCard); //TODO: as soon as message sending works, remove this directly or 500ms after message send
                    //TODO: send message that card was played with piece Information (might be using Piece.getCurrentPieceIndex() and this.getCard(); or might make static method/variable for that)
                });

                parallelTransition.play();

            });

            //to know when to wait
            translateBoard.setOnFinished(event -> {
                pause.play();
            });

            ParallelTransition parallelTransition = new ParallelTransition(
                    translate,
                    scaleTransition,
                    translateBoard,
                    scaleTransitionBoard
            );


            //Playing the transitions
            parallelTransition.play();
        }
        public Card getCard() { return this.card;}


    }

    public static void setSelectedValue(int value){
        selectedVaue = value;
    }
    public static void setIsStarter(boolean value){
        isStarter = value;
    }
}
