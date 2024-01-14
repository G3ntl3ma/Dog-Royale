package views;

import Enums.Card;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Path;

public class Cards extends ImageView {


    private Card card;

    private Image image;
    private int numberInHand;


    public Cards(Card card, int numberInHand) {
        this.card = card;
        this.image = new Image( getClass().getResource("/" + card.getCardPath() + ".png" ).toString());
        this.numberInHand = numberInHand;


        this.setImage(image);
        this.fitHeightProperty().set(150);
        this.fitWidthProperty().set(200);
        this.pickOnBoundsProperty().set(true);
        this.preserveRatioProperty().set(true);

        this.setOnMouseClicked(event -> {
            TranslateTransition translate = new TranslateTransition();
            translate.setNode(this);
            translate.setByY(-50);
            translate.play();

        });
    }



    public Card getCard() { return this.card;}

    public int getNumberInHand() {
        return this.numberInHand;
    }

    public void setNumberInHand(int numberInHand) {
        this.numberInHand = numberInHand;
    }

}
