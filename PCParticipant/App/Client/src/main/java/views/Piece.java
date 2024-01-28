package views;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This class is responsible for the pieces on the board.
 * It contains the images of the pieces and the methods to move them.
 *
 * @author mtwardy
 */
public class Piece {
    public double transitionDuration = 1000.0; // in milliseconds
    protected int position = 0;
    protected boolean inHouse = false; // whether "position" means house position
    protected boolean inField = false; // whether "position" means field position
    // if both inHouse and inField are set to false, the piece is not on any board
    public boolean isOnBench = true; // whether the piece is on the bench

    public int player = 0; // whose piece this is
    public final PieceImages fieldImage = new PieceImages();
    public final PieceImages houseImage = new PieceImages();


    private final Timeline fieldAnimation = new Timeline();
    private final Timeline houseAnimation = new Timeline();
    public boolean goIntoHouse = false; // whether the piece goes into its house as soon as it can


    /**
     * Constructor for Piece
     * @param i is the index of the piece
     */
    public Piece(int i)
    {
        this.fieldImage.pieceIndex = i;
        this.houseImage.pieceIndex = i;
    }

    /**
     * responsible for setting the player index for various components related to the game board
     *
     * @param playerindex Represents the index of the player.
     */
    public void setPlayer(int playerindex)
    {
        this.player = playerindex;
        this.houseImage.playerIndex = playerindex;
        this.fieldImage.playerIndex = playerindex;
    }

    /**
     *  updates the piece position and sets the piece onto the field
     *
     * @param fieldPosition Represents the position of the field
     */
    public void moveToField(int fieldPosition) {
        position = fieldPosition;
        inHouse = false;
        inField = true;
        fieldImage.setVisible(true); // not technically needed, since the animation contains visibleProperty
    }
    /**
     *  updates the piece position and sets the piece into the house
     *
     * @param housePosition Represents the position of the house
     */
    public void moveToHouse(int housePosition) {
        position = housePosition;
        inHouse = true;
        inField = false;
        houseImage.setVisible(true);
    }
    /**
     * updates the piece position (wherever it is: field, house)
     *
     * @param pos Represents the position of the piece
     */
    public void moveTo(int pos) {
        position = pos;
    }

    /**
     * resets the state of an object
     */
    public void getKicked() {
        inHouse = false;
        inField = false;
        position = 0;
    }

    /**
     * serves as a wrapper for a more generalized animate method, where it specifically applies the animation to a field
     *
     * @param values represents a 2D array of integers, likely containing data associated with the animation of a field
     */
    public void animateField(int[][] values) {
        animate(fieldAnimation, fieldImage, values);
    }

    /**
     * initiate the house animation based on the given coordinates
     *
     * @param values a 2D array of integers representing coordinates
     */
    public void animateHouse(int[][] values) {
        System.out.println("animatingHouse");
        System.out.println(values.length);
        for (int[] v : values) {
            System.out.print(v[0]);
            System.out.print(", ");
            System.out.println(v[1]);
        }
        animate(houseAnimation, houseImage, values);
    }

    /**
     * responsible for creating and executing a timeline-based animation for an ImageView
     *
     * @param timeline Represents a JavaFX Timeline, a class used for defining animations
     * @param image Represents an ImageView, which is an image container in JavaFX
     * @param values Represents a 2D array of integer values defining the coordinates for animation
     */
    private void animate(Timeline timeline, ImageView image, int[][] values) {
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
