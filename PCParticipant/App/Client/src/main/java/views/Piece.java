package views;

<<<<<<< HEAD
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Class for the pieces
 * author: mtwardy
 */
public class Piece extends ImageView {      //TODO: make only selectable for the current player / ur own Client ID (PieceHandler has something like int[] whosePiece) - easier to test with server
    int pieceIndex;
    boolean onboard;
    int playerIndex;
    static Pane paneBoard;
    ImageView shine;
    static ImageView redShine;
    static Piece selectedEnemyPiece; //Saves the attacked Piece
    static boolean selectEnemyPiece = false;
    static Piece currentPiece; //Saves the selected Piece

    public double transitionDuration = 1000.0; // in milliseconds
    protected int position = 0;
    protected boolean inHouse = false; // whether "position" means house position
    protected boolean inField = false; // whether "position" means field position
    // if both inHouse and inField are set to false, the piece is not on any board
    public int player = 0; // whose piece this is
    public final ImageView fieldImage = new ImageView();
    public final ImageView houseImage = new ImageView();
    private final Timeline fieldAnimation = new Timeline();
    private final Timeline houseAnimation = new Timeline();
    public boolean goIntoHouse = false; // whether the piece goes into its house as soon as it can

    /**
     * Constructor for Piece
     */
    public Piece() {
    }

    /**
     * Constructor for Piece
     *
     * @param image       Image of the piece
     * @param pieceIndex  Index of the piece
     * @param onboard     Boolean if the piece is on the board
     * @param playerIndex Index of the player
     * @param paneBoard   Pane to draw the piece in
     * @param radiusField Radius of fields
     * @param pos         Position of the piece (x, y)
     */
    public Piece(Image image, int pieceIndex, boolean onboard, int playerIndex, Pane paneBoard, int radiusField, int[] pos) {
        this.setImage(image);
        this.pieceIndex = pieceIndex;
        this.onboard = onboard;
        this.playerIndex = playerIndex;
        this.paneBoard = paneBoard;
        this.setFitWidth(3 * radiusField);
        this.setX(pos[0] - 1.5 * radiusField);
        this.setY(pos[1] - 2 * radiusField);

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
     *
     * @param radiusField is the size of a field
     */
    private void select(int radiusField) {
        if (currentPiece != this && !selectEnemyPiece || currentPiece == null) {
            makeShiny(radiusField);
            currentPiece = this;
        } else if (!selectEnemyPiece) {
            deselect();
        } else if (selectedEnemyPiece != this && currentPiece != this) {

            this.makeShinyRed(radiusField);
            selectedEnemyPiece = this;
        } else if (currentPiece != this) {
            selectedEnemyPiece = null;
            this.removeRedShine();
        } else {
            deselect();
        }
    }

    /**
     * deselects the enemy piece
     */
    public static void deselectEnemyPiece() {
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
        shine.setFitWidth(3 * radiusField);
        shine.setBlendMode(BlendMode.LIGHTEN);
        paneBoard.getChildren().add(shine);
        paneBoard.getChildren().remove(this);
        paneBoard.getChildren().add(this);
        if (currentPiece != null) {
            currentPiece.removeShine();
        }
    }

    private void makeShinyRed(int radiusField) {
        redShine = new ImageView(new Image("/pawnShineRed.png"));
        redShine.setPreserveRatio(true);
        redShine.setX(this.getX() + 1);
        redShine.setY(this.getY());
        redShine.setFitWidth(3 * radiusField);
        redShine.setBlendMode(BlendMode.LIGHTEN);
        paneBoard.getChildren().add(redShine);
        paneBoard.getChildren().remove(this);
        paneBoard.getChildren().add(this);
        if (selectedEnemyPiece != null) {
            selectedEnemyPiece.removeRedShine();
        }
    }


    //removes the shine from the piece (removes the "selected look")
    public void removeShine() {
        paneBoard.getChildren().remove(shine);
    }

    public static void removeRedShine() {
        paneBoard.getChildren().remove(redShine);
    }

    public static void setSelectEnemyPiece(boolean selectEnemyPiece) {

        Piece.selectEnemyPiece = selectEnemyPiece;
        if (!selectEnemyPiece) {
            deselectEnemyPiece();
        }
    }


        /* updates the piece position and sets the piece onto the field*/
        public void moveToField(int fieldPosition){
            position = fieldPosition;
            inHouse = false;
            inField = true;
            fieldImage.setVisible(true); // not technically needed, since the animation contains visibleProperty
        }
        /* updates the piece position and sets the piece into the house*/
        public void moveToHouse ( int housePosition){
            position = housePosition;
            inHouse = true;
            inField = false;
            houseImage.setVisible(true);
        }
        /* updates the piece position (wherever it is: field, house)*/
        public void moveTo ( int pos){
            position = pos;
        }
        public void getKicked () {
            inHouse = false;
            inField = false;
            position = 0;
        }


        public void animateField ( int[][] values){
            animate(fieldAnimation, fieldImage, values);
        }
        public void animateHouse ( int[][] values){
            System.out.println("animatingHouse");
            System.out.println(values.length);
            for (int[] v : values) {
                System.out.print(v[0]);
                System.out.print(", ");
                System.out.println(v[1]);
            }
            animate(houseAnimation, houseImage, values);
        }
        private void animate (Timeline timeline, ImageView image,int[][] values){
            // special case: if a value is {0, 0}, the piece is set to not visible
            // otherwise it will be visible
            double adjustX = -image.getFitWidth() / 2; // so that the image is about centered on the coordinate
            double adjustY = -image.getFitWidth() * 0.75; // height is not perfectly centered because it wouldn't look as good
            if (values.length == 1) {
                image.setX(values[0][0] + adjustX);
                image.setY(values[0][1] + adjustY);
                image.setVisible(true);
                return;
            }
            timeline.getKeyFrames().clear();
            final KeyFrame[] keyFrames = new KeyFrame[values.length];
            for (int i = 0; i < values.length; i++) {
                if (values[i][0] == 0 && values[i][1] == 0) {
                    keyFrames[i] = new KeyFrame(
                            new Duration(i * transitionDuration / values.length),
                            new KeyValue(image.visibleProperty(), false)
                    );
                } else {
                    keyFrames[i] = new KeyFrame(
                            new Duration(i * transitionDuration / values.length),
                            new KeyValue(image.xProperty(), values[i][0] + adjustX),
                            new KeyValue(image.yProperty(), values[i][1] + adjustY),
                            new KeyValue(image.visibleProperty(), true)
                    );
                }
            }
            timeline.getKeyFrames().addAll(keyFrames);
            timeline.play();
        }
    }

