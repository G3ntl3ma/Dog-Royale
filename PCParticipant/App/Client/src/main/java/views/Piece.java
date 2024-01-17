package views;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Class for the pieces
 * author: mtwardy
 */
public class Piece extends ImageView {      //TODO: make only selectable for the current player / ur own Client ID
    int pieceIndex;
    boolean onboard;
    int playerIndex;
    Pane paneBoard;
    ImageView shine;
    static Piece currentPiece; //Saves the selected Piece hier

    /**
     * Constructor for Piece
     */
    public Piece() {
    }
    /**
     * Constructor for Piece
     * @param image Image of the piece
     * @param pieceIndex Index of the piece
     * @param onboard Boolean if the piece is on the board
     * @param playerIndex Index of the player
     * @param paneBoard Pane to draw the piece in
     * @param radiusField Radius of fields
     * @param pos Position of the piece (x, y)
     */
    public Piece(Image image, int pieceIndex, boolean onboard, int playerIndex, Pane paneBoard, int radiusField, int[] pos) {
        this.setImage(image);
        this.pieceIndex = pieceIndex;
        this.onboard = onboard;
        this.playerIndex = playerIndex;
        this.paneBoard = paneBoard;
        this.setFitWidth(3*radiusField);
        this.setX(pos[0] - 1.5*radiusField);
        this.setY(pos[1] - 2*radiusField);

        this.setOnMouseClicked(e -> {       //changing the clicked event
            select(radiusField);
        });
    }


    public int getPieceIndex() {
        return pieceIndex;
    }

    public int getCurrentPieceIndex() {
        return currentPiece.getPieceIndex();
    }
    public Piece getCurrentPiece() {
        return currentPiece;
    }
    public ImageView getShine() {
        return shine;
    }


    /**
     * selects the piece
     * @param radiusField is the size of a field
     */
    private void select(int radiusField)
    {
        if (currentPiece != this) {
            makeShiny(radiusField);
            currentPiece = this;
        }
        else {
            currentPiece = null;
            paneBoard.getChildren().remove(shine);
        }
    }

    /**
     * gives the piece the "selected look"
     * @param radiusField is the sice of a field
     */
    private void makeShiny(int radiusField) {
        shine = new ImageView(new Image("/pawnShine.png"));
        shine.setPreserveRatio(true);
        shine.setX(this.getX() + 1);
        shine.setY(this.getY());
        shine.setFitWidth(3*radiusField);
        shine.setBlendMode(BlendMode.LIGHTEN);
        paneBoard.getChildren().add(shine);
        paneBoard.getChildren().remove(this);
        paneBoard.getChildren().add(this);
        if (currentPiece != null)
        {
            currentPiece.removeShine();
        }
    }

    //removes the shine from the piece (removes the "selected look")
    public void removeShine()
    {
        paneBoard.getChildren().remove(shine);
    }
}
