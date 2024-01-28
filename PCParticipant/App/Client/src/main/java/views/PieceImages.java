package views;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class is responsible for the pieces on the board.
 * It contains the images of the pieces and the methods to move them.
 *
 * @author mtwardy
 */
public class PieceImages extends ImageView {
    int pieceIndex;
    boolean onboard;
    public int playerIndex;
    static Pane paneBoard;
    ImageView shine;
    static ImageView redShine;
    static PieceImages selectedEnemyPiece; //Saves the attacked Piece
    static boolean selectEnemyPiece = false;
    static PieceImages currentPiece; //Saves the selected Piece

    public boolean isOnHouse = false;


    static Pane houseBoard;

    static int clientPlayerIndex;



    /**
     * Constructor for Piece
     *
     * @param paneBoardd is the Pane the pieces are drawn on
     */
    public PieceImages(Pane paneBoardd) {
        paneBoard = paneBoardd;
        this.setOnMouseClicked(e -> {       //changing the clicked event
            select((int) this.getFitWidth()/3);
        });
    }
    /**
     * Constructor for Piece
     */
    public PieceImages() {
        this.setOnMouseClicked(e -> {       //changing the clicked event
            select((int) this.getFitWidth()/3);
        });
    }

    /**
     * getter method for the index of the piece
     *
     * @return the index of the piece
     */
    public int getPieceIndex() {
        return pieceIndex;
    }

    /**
     * setter method for the index of the piece
     *
     * @param playerIndex An Integer representing the index of the player
     */
    public void setPlayerIndex(int playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    /**
     * getter method for the current piece index
     *
     * @return the current piece index
     */
    public static int getCurrentPieceIndex() {
        return currentPiece.getPieceIndex();
    }

    /**
     * getter method for the current piece
     *
     * @return the current piece
     */
    public PieceImages getCurrentPiece() {
        return currentPiece;
    }

    /**
     * getter methd for shine
     *
     * @return the shine
     */
    public ImageView getShine() {
        return shine;
    }


    /**
     * selects the piece
     *
     * @param radiusField is the size of a field
     */
    private void select(int radiusField)
    {
        if (playerIndex == clientPlayerIndex) {
            if (currentPiece != this) {
                makeShiny(radiusField);
                currentPiece = this;
            } else  {
                deselect();
            }
        }
        else if (selectedEnemyPiece != this && selectEnemyPiece) {
            this.makeShinyRed(radiusField);
            selectedEnemyPiece = this;
        }
        else if (selectEnemyPiece) {
            selectedEnemyPiece = null;
            removeRedShine();
        }
    }

    /**
     * deselects the enemy piece
     */
    public static void deselectEnemyPiece()
    {
        selectedEnemyPiece = null;
        removeRedShine();
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
     *
     * @param radiusField is the sice of a field
     */
    private void makeShiny(int radiusField) {
        shine = new ImageView(new Image("/pawnShine.png"));
        shine.setPreserveRatio(true);
        shine.setX(this.getX() + 1);
        shine.setY(this.getY());
        shine.setFitWidth(3*radiusField);
        shine.setBlendMode(BlendMode.LIGHTEN);
        if (isOnHouse)
        {
            houseBoard.getChildren().add(shine);
            houseBoard.getChildren().remove(this);
            houseBoard.getChildren().add(this);
        }
        else{
            paneBoard.getChildren().add(shine);
            paneBoard.getChildren().remove(this);
            paneBoard.getChildren().add(this);
        }
        if (currentPiece != null)
        {
            currentPiece.removeShine();
        }
    }

    /**
     * responsible for giving the enemy piece the "selected look"
     *
     * @param radiusField is the radius of a field
     */
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

    /**
     * removes the shine from the piece (removes the "selected look")
     */
    public void removeShine()
    {
        paneBoard.getChildren().remove(shine);
    }

    /**
     * removes the red shine from the piece (removes the "selected look")
     */
    public static void removeRedShine()
    {
        paneBoard.getChildren().remove(redShine);
    }

    /**
     * setter method for SelectEnemyPiece
     *
     * @param selectEnemyPiece is a boolean representing whether the enemy piece is selected
     */
    public static void setSelectEnemyPiece(boolean selectEnemyPiece) {

        PieceImages.selectEnemyPiece = selectEnemyPiece;
        if (!selectEnemyPiece)
        {
            deselectEnemyPiece();
        }
    }

    /**
     * getter method for the selected enemy piece
     *
     * @return the selected enemy piece
     */
    public static int getSelectedEnemyPieceId()
    {
        if (selectedEnemyPiece == null)
        {
            return -1;
        }
        return selectedEnemyPiece.getPieceIndex();
    }
}
