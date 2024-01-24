package views;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Class for the pieces
 * author: mtwardy
 */
public class Piece extends ImageView {      //TODO: make only selectable for the current player / ur own Client ID (PieceHandler has something like int[] whosePiece) - easier to test with server
    int pieceIndex;
    boolean onboard;
    int playerIndex;
    Pane paneBoard;
    ImageView shine;
    ImageView redShine;
    static Piece selectedEnemyPiece; //Saves the attacked Piece
    static boolean selectEnemyPiece = false;
    static Piece currentPiece; //Saves the selected Piece

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

    public static int getCurrentPieceIndex() {
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
        if (currentPiece != this && !selectEnemyPiece || currentPiece == null) {
            makeShiny(radiusField);
            currentPiece = this;
        }
        else if (!selectEnemyPiece) {
            deselect();
        }
        else if (selectedEnemyPiece != this && currentPiece != this){

            this.makeShinyRed(radiusField);
            selectedEnemyPiece = this;
        }
        else if (currentPiece != this){
            selectedEnemyPiece = null;
            this.removeRedShine();
        }
        else
        {
            deselect();
        }
    }

    /**
     * deselects the piece
     */
    public void deselect() {
        currentPiece = null;
        this.removeShine();

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

    private void makeShinyRed(int radiusField) {
        redShine = new ImageView(new Image("/pawnShineRed.png"));
        redShine.setPreserveRatio(true);
        redShine.setX(this.getX() + 1);
        redShine.setY(this.getY());
        redShine.setFitWidth(3*radiusField);
        redShine.setBlendMode(BlendMode.LIGHTEN);
        paneBoard.getChildren().add(redShine);
        paneBoard.getChildren().remove(this);
        paneBoard.getChildren().add(this);
        if (selectedEnemyPiece != null)
        {
            selectedEnemyPiece.removeRedShine();
        }
    }



    //removes the shine from the piece (removes the "selected look")
    public void removeShine()
    {
        paneBoard.getChildren().remove(shine);
    }
    public void removeRedShine()
    {
        paneBoard.getChildren().remove(redShine);
    }

    public static void setSelectEnemyPiece(boolean selectEnemyPiece) {
        Piece.selectEnemyPiece = selectEnemyPiece;
    }
}
