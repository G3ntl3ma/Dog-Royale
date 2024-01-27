package views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.*;

/**
 *Handles drawing all the lil circles to the screen
 *and choosing the correct color
 *
 * @author Gruppe 8
 */
public class DrawBoard {
    public int radiusField = 11;
    public Color fieldColor = Color.GREY;
    public int borderSize = 2;
    public Color borderColor = Color.BLACK;
    private Set<Shape> houseObjects;

    /**
     * returns the color of the player
     * @param playerIndex index of the player
     * @return color of the player
     */
    public Color playerColor(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.PURPLE;
            default:
                return fieldColor;
        }
    }

    /**
     * returns the file path of an image associated with a player
     * @param playerIndex index of the player
     * @return A string containing the file path
     */
    public String playerImagePath(int playerIndex) {
        return "pawn_" + playerIndex + ".png";
    }

    /**
     *  fills paneBoard with the playing field
     *
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param board Represents an instance of a Board class
     * @param pieceHandler Represents an instance of a PieceHandler class
     */
    public DrawBoard(Pane paneBoard, Board board, PieceHandler pieceHandler) {
        drawBoard(paneBoard, board);
        for (Piece piece : pieceHandler.pieces) {
            //drawCircle(paneBoard, pos, Color.BLACK);
            piece.fieldImage.setImage(new Image(playerImagePath(piece.player)));
            piece.fieldImage.setPreserveRatio(true);
            piece.fieldImage.setFitWidth(3 * radiusField);
            piece.fieldImage.setVisible(false);
            paneBoard.getChildren().add(piece.fieldImage);
        }
    }
    /**
     *  fills paneBoard with the house fields
     *  and the pieces in the house
     *  @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     *  @param houseBoard Represents an instance of a HouseBoard class
     *  @param pieceHandler Represents an instance of a PieceHandler class
     */
    public DrawBoard(Pane paneBoard, HouseBoard houseBoard, PieceHandler pieceHandler) {
        drawHouses(paneBoard, houseBoard);
        for (Piece piece : pieceHandler.pieces) {
            //drawCircle(paneBoard, pos, Color.BLACK);
            piece.houseImage.setImage(new Image(playerImagePath(piece.player)));
            piece.houseImage.setPreserveRatio(true);
            piece.houseImage.setFitWidth(3 * radiusField);
            piece.houseImage.setVisible(piece.inHouse);
            if (piece.inHouse) {
                piece.animateHouse(new int[][]{houseBoard.houseCoordinates[piece.player][piece.position]});
            }
            paneBoard.getChildren().add(piece.houseImage);
        }
    }

    /**
     *  fills paneBoard with the playing field
     *
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param board Represents an instance of a Board class
     */
    private void drawBoard(Pane paneBoard, Board board) {
        // lines between house fields
        for (int i = 0; i < board.numPlayers; i++) {
            int[] prevPos = board.fieldCoordinates[board.startingPosIndices[i]];
            for (int j = 0; j < board.houseCoordinates[i].length; j++) {
                int[] pos = board.houseCoordinates[i][j];
                drawLine(paneBoard, prevPos, board.houseCoordinates[i][j]);
                prevPos = pos;
            }
        }
        // house fields
        for (int i = 0; i < board.numPlayers; i++) {
            for (int j = 0; j < board.houseCoordinates[i].length; j++) {
                if (j < board.numHouses) {
                    drawCircle(paneBoard, board.houseCoordinates[i][j], playerColor(i));
                } else {
                    drawEllipsis(paneBoard, board.houseCoordinates[i][j]);
                }
            }
        }
        // lines between regular fields
        int prevIndex = board.fieldSize-1;
        for (int i = 0; i < board.fieldSize; i++) {
            drawLine(paneBoard,
                    board.fieldCoordinates[prevIndex],
                    board.fieldCoordinates[i]);
            prevIndex = i;
        }
        // regular fields
        for (int i = 0; i < board.fieldSize; i++) {
            int[] pos = board.fieldCoordinates[i];
            // determine color of the circle
            drawCircle(paneBoard, pos, playerColor(board.whoseStartingPosIndex(i)));
        }
        // drawCard fields
        for (int card = 0; card < board.drawCardFields.length; card++) {
            drawCard(paneBoard, board.fieldCoordinates[board.drawCardFields[card]]);
        }
    }

    /**
     * fills paneBoard with the house fields
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param houseBoard Represents an instance of a HouseBoard class
     */
    private void drawHouses(Pane paneBoard, HouseBoard houseBoard) {
        houseObjects = new HashSet<>();
        for (int i = 0; i < houseBoard.numPlayers; i++) {
            // lines between fields
            int prevIndex = 0;
            for (int j = 0; j < houseBoard.numHouses; j++) {
                houseObjects.addAll(
                        drawLine(paneBoard,
                                houseBoard.houseCoordinates[i][prevIndex],
                                houseBoard.houseCoordinates[i][j])
                );
                prevIndex = j;
            }
            // fields
            for (int j = 0; j < houseBoard.numHouses; j++) {
                int[] pos = houseBoard.houseCoordinates[i][j];
                // determine color of the circle
                if (houseBoard.showHouse[j]) {
                    houseObjects.addAll(drawCircle(paneBoard, pos, playerColor(i)));
                    if ((j + 1) % 5 == 0 || j == 0 || j == houseBoard.numHouses - 1) {
                        houseObjects.addAll(
                                drawText(paneBoard,
                                        new int[]{pos[0] - 5, pos[1] - houseBoard.verticalSpacing / 3},
                                        String.valueOf(j + 1))
                        );
                    }
                } else if (houseBoard.isEllipsis[j]) {
                    houseObjects.addAll(drawEllipsis(paneBoard, pos));
                }
            }
        }
    }

    /**
     * updates the pieces on the playing field
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param houseBoard Represents an instance of a HouseBoard class
     */
    public void updateHouses(Pane paneBoard, HouseBoard houseBoard) {
        assert !houseObjects.isEmpty(); // DrawBoard has not been initialized
        paneBoard.getChildren().removeAll(houseObjects);
        drawHouses(paneBoard, houseBoard);
    }


    //  ---- drawing utility ----

    /**
     * draws a line between two points
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param start starting point of the line
     * @param end end point of the line
     * @return A set of shapes that represent the line
     */
    private Set<Shape> drawLine(Pane paneBoard, int[] start, int[] end) {
        Set<Shape> shapes = new HashSet<>();
        shapes.add(new Line(start[0], start[1], end[0], end[1]));
        paneBoard.getChildren().addAll(shapes);
        return shapes;
    }

    /**
     * draws a circle at a given position
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param pos position of the circle
     * @param color color of the circle
     * @return A set of shapes that represent the circle
     */
    private Set<Shape> drawCircle(Pane paneBoard, int[] pos, Color color) {
        Circle c1 = new Circle(pos[0],
                pos[1],
                radiusField, borderColor);
        Circle c2 = new Circle(pos[0],
                pos[1],
                radiusField - borderSize, color);
        Set<Shape> shapes = new HashSet<>();
        shapes.add(c1); shapes.add(c2);
        paneBoard.getChildren().add(c1); paneBoard.getChildren().add(c2);
        return shapes;
    }

    /**
     * draws an ellipsis at a given position
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param pos position of the ellipsis
     * @return A set of shapes that represent the ellipsis
     */
    private Set<Shape> drawEllipsis(Pane paneBoard, int[] pos) {
        Set<Shape> shapes = new HashSet<>();
        int rectPosX = pos[0] - radiusField;
        int rectPosY = (int) (pos[1] - (double) radiusField / 2.0);
        Rectangle r1 = new Rectangle(
                2 * radiusField, radiusField, // width, height
                Color.BLACK);
        r1.setX(rectPosX);
        r1.setY(rectPosY);
        shapes.add(r1);
        Rectangle r2 = new Rectangle(
                2 * radiusField - 2 * borderSize, radiusField - 2 * borderSize, // width, height
                Color.WHITE);
        r2.setX(rectPosX + borderSize);
        r2.setY(rectPosY + borderSize);
        shapes.add(r2);
        Circle c1 = new Circle(pos[0], pos[1], 1, Color.BLACK);
        shapes.add(c1);
        Circle c2 = new Circle(pos[0] + (double) radiusField / 2, pos[1], 1, Color.BLACK);
        shapes.add(c2);
        Circle c3 = new Circle(pos[0] - (double) radiusField / 2, pos[1], 1, Color.BLACK);
        shapes.add(c3);
        paneBoard.getChildren().add(r2);
        paneBoard.getChildren().add(c1);
        paneBoard.getChildren().add(c2);
        paneBoard.getChildren().add(c3);
        return shapes;
    }

    /**
     * draws a card at a given position
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param pos position of the card
     */
    private void drawCard(Pane paneBoard, int[] pos) {
        String path = "drawcard_field.png";
        ImageView im = new ImageView(new Image(path));
        im.setPreserveRatio(true);
        im.setFitWidth(1.3 * radiusField);
        im.setX(pos[0] - im.getFitWidth() / 2);
        im.setY(pos[1] - 0.75 * radiusField);
        paneBoard.getChildren().add(im);
    }

    /**
     * draws text at a given position
     * @param paneBoard Represents the JavaFX Pane where the game board is to be drawn.
     * @param pos position of the text
     * @param text text to be drawn
     * @return A set of shapes that represent the text
     */
    private Set<Shape> drawText(Pane paneBoard, int[] pos, String text) {
        Set<Shape> shapes = new HashSet<>();
        shapes.add(new Text(pos[0], pos[1], text));
        paneBoard.getChildren().addAll(shapes);
        return shapes;
    }
}