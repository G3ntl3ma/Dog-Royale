package views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.ResourceBundle;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/*
Handles drawing all the lil circles to the screen
and choosing the correct color
 */
public class DrawBoard {
    public int radiusField = 11;
    public Color fieldColor = Color.GREY;
    public int borderSize = 2;
    public Color borderColor = Color.BLACK;

    public Color playerColor(int playerIndex) {
        switch (playerIndex) {
            case 0: return Color.RED;
            case 1: return Color.BLUE;
            case 2: return Color.YELLOW;
            case 3: return Color.GREEN;
            case 4: return Color.ORANGE;
            case 5: return Color.PURPLE;
            default: return fieldColor;
        }
    }
    public String playerImagePath(int playerIndex) {
        return "pawn_" + Integer.toString(playerIndex) + ".png";
    }
    private void drawLine(Pane paneBoard, int[] start, int[] end) {
        paneBoard.getChildren().add(new Line(start[0], start[1], end[0], end[1]));
    }

    private void drawCircle(Pane paneBoard, int[] pos, Color color) {
        paneBoard.getChildren().add(new Circle(pos[0],
                pos[1],
                radiusField, borderColor));
        paneBoard.getChildren().add(new Circle(pos[0],
                pos[1],
                radiusField-borderSize, color));
    }
    private void drawEllipsis(Pane paneBoard, int[] pos) {
        int rectPosX = pos[0] - radiusField;
        int rectPosY = (int) (pos[1] - (double) radiusField / 2.0);
        Rectangle r1 = new Rectangle(
            2*radiusField, radiusField, // width, height
            Color.BLACK);
        r1.setX(rectPosX); r1.setY(rectPosY);
        paneBoard.getChildren().add(r1);
        Rectangle r2 = new Rectangle(
                2*radiusField-2*borderSize, radiusField-2*borderSize, // width, height
                Color.WHITE);
        r2.setX(rectPosX + borderSize); r2.setY(rectPosY + borderSize);
        paneBoard.getChildren().add(r2);
        paneBoard.getChildren().add(new Circle(pos[0], pos[1], 1, Color.BLACK));
        paneBoard.getChildren().add(new Circle(pos[0] + (double) radiusField / 2, pos[1], 1, Color.BLACK));
        paneBoard.getChildren().add(new Circle(pos[0] - (double) radiusField / 2, pos[1], 1, Color.BLACK));
    }
    private void drawPiece(Pane paneBoard, int[] pos, int playerIndex, int piece, boolean onBoard) {
        drawCircle(paneBoard, pos, Color.BLACK);
        //this.getClass().getResource("style.css").toExternalForm()
        String path = playerImagePath(playerIndex);
        Piece im = new Piece(new Image(path), piece, onBoard, playerIndex, paneBoard, radiusField, pos);//PCObserverControllerGameplay.class.getResource(path).toString()


        paneBoard.getChildren().add(im);
    }
    private void drawCard(Pane paneBoard, int[] pos) {
        String path = "drawcard_field.png";
        ImageView im = new ImageView(new Image(path));
        im.setPreserveRatio(true);
        im.setFitWidth(1.3*radiusField);
        im.setX(pos[0] - im.getFitWidth()/2);
        im.setY(pos[1] - 0.75*radiusField);
        paneBoard.getChildren().add(im);
    }

    private void drawText(Pane paneBoard, int[] pos, String text) {
        paneBoard.getChildren().add(new Text(pos[0], pos[1], text));
    }

    public void drawBoard(Board board, Pane paneBoard, PieceHandler pieceHandler) {
        paneBoard.getChildren().clear();
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
        // draw pieces
        for (int piece = 0; piece < pieceHandler.piecePositions.length; piece++) {
            int player = pieceHandler.whosePiece[piece];
            int pos = pieceHandler.piecePositions[piece];
            if (pieceHandler.pieceInHouse[piece]) {
                if (pos < board.numHouses) {
                    drawPiece(paneBoard, board.houseCoordinates[player][pos], player, pos, true); /////check if this is house on board or not
                }
            } else if (pieceHandler.pieceInField[piece]) {
                drawPiece(paneBoard, board.fieldCoordinates[pos], player, piece, true);
            }
        }
    }
    public void drawHouses(HouseBoard houseBoard, Pane paneBoard, PieceHandler pieceHandler) {
        for (int i = 0; i < houseBoard.numPlayers; i++) {
            // lines between fields
            int prevIndex = 0;
            for (int j = 0; j < houseBoard.numHouses; j++) {
                drawLine(paneBoard,
                        houseBoard.houseCoordinates[i][prevIndex],
                        houseBoard.houseCoordinates[i][j]);
                prevIndex = j;
            }
            // fields
            for (int j = 0; j < houseBoard.numHouses; j++) {
                int[] pos = houseBoard.houseCoordinates[i][j];
                // determine color of the circle
                if (houseBoard.showHouse[j]) {
                    drawCircle(paneBoard, pos, playerColor(i));
                    if ((j+1) % 5 == 0 || j == 0 || j == houseBoard.numHouses-1) {
                        drawText(paneBoard, new int[] {pos[0]-5, pos[1] - houseBoard.verticalSpacing / 3}, String.valueOf(j+1));
                    }
                } else if (houseBoard.isEllipsis[j]) {
                    drawEllipsis(paneBoard, pos);
                }
            }
            // draw pieces
            for (int piece = 0; piece < pieceHandler.piecePositions.length; piece++) {
                if (pieceHandler.pieceInHouse[piece]) {
                    int player = pieceHandler.whosePiece[piece];
                    int pos = pieceHandler.piecePositions[piece];
                    drawPiece(paneBoard, houseBoard.houseCoordinates[player][pos], player, piece, false);
                }
            }
        }
    }
}
