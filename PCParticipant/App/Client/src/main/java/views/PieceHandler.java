package views;

import java.util.Arrays;
import java.util.Collections;

public class PieceHandler {
    public final Piece[] pieces;
    private final Board board;
    private final HouseBoard houseBoard;
    public final int numTotalPieces;
    public PieceHandler(Board board, HouseBoard houseBoard) {
        this.board = board;
        this.houseBoard = houseBoard;
        numTotalPieces = board.numPieces * board.numPlayers;
        // declare and fill pieces
        pieces = new Piece[numTotalPieces];
        for (int i = 0; i < numTotalPieces; i++) { //creates new Pieces here.
            pieces[i] = new Piece();
            pieces[i].houseImage.isOnHouse = true;
        }
    }

    /*
    For this piece, if anything is not right, it gets updated accordingly.
    For positionInHouse, read the documented conversation of two developers/magicians which
    also encaptures our feelings about the subject:
     - legends say the positionInHouse reads 0 if the piece is not in the house,
       and if it is >0, it represents the index (starting at 1) from the last house place...
       I'm not gonna question it, only implement it
     - yooooooo, wtf?!
     */
    public void assertPieceState(int pieceIndex, boolean onBench, int positionInField, int positionInHouse) {
        Piece piece = pieces[pieceIndex];
        if (onBench) {
            if (piece.inField) {
                piece.inField = false;
                piece.fieldImage.setVisible(false);
                throw new AssertionError("piece "+pieceIndex+" belongs on bench, was however in field");
            }
            if (piece.inHouse) {
                piece.inHouse = false;
                piece.houseImage.setVisible(false);
                throw new AssertionError("piece "+pieceIndex+" belongs on bench, was however in house");
            }
            return;
        }
        if (positionInHouse <= 0) { // piece in field
            if (!piece.inField) {
                piece.inField = true;
                piece.fieldImage.setVisible(true);
                piece.inHouse = false;
                piece.houseImage.setVisible(false);
                piece.position = positionInField;
                piece.animateField(new int[][]{board.fieldCoordinates[piece.position]});
                throw new AssertionError("piece " + pieceIndex + " belongs in field, but wasn't");
            }
            if (piece.position != positionInField) {
                piece.position = positionInField;
                piece.animateField(new int[][]{board.fieldCoordinates[piece.position]});
                throw new AssertionError("piece " + pieceIndex + " had a wrong position in field");
            }
        } else { // piece in house
            if (!piece.inHouse) {
                piece.inField = false;
                piece.fieldImage.setVisible(false);
                piece.inHouse = true;
                piece.houseImage.setVisible(true);
                piece.position = houseBoard.numHouses - positionInHouse;
                if (piece.position < board.numHouses) {
                    piece.animateField(new int[][]{board.houseCoordinates[piece.player][piece.position]});
                }
                piece.animateHouse(new int[][]{houseBoard.houseCoordinates[piece.player][piece.position]});
                throw new AssertionError("piece " + pieceIndex + " belongs in house, but wasn't");
            }
            if (piece.position != houseBoard.numHouses - positionInHouse) {
                piece.position = houseBoard.numHouses - positionInHouse;
                if (piece.position < board.numHouses) {
                    piece.animateField(new int[][]{board.houseCoordinates[piece.player][piece.position]});
                }
                piece.animateHouse(new int[][]{houseBoard.houseCoordinates[piece.player][piece.position]});
                throw new AssertionError("piece "+pieceIndex+" had a wrong position in house");
            }
        }
    }

    /*
    Handles moving the piece, updating its attribute piece.position.
    Also sets off the animations fot the movement.
    Does all regular movements which excludes: swapping, magnet, startPiece
    This method assumes that the piece cannot do an extra round. It always takes the route into its house.
    If it halts on another piece, the other piece gets kicked
     */
    public void movePiece(int numFields, int pieceIndex) {
        if (numFields == 0) {
            throw new AssertionError("Call to PieceHandler.movePiece with numFields==0, which should not be possible. Check the server logic or the controller which passes it onward");
        }
        Piece piece = pieces[pieceIndex];
        int oldPos = piece.position;
        int newPos = piece.position + numFields;
        int startingPos = board.startingPosIndices[piece.player];
        if (piece.inField) {
            // test, if the piece comes around its own house, in which case it always goes in
            boolean goIntoHouse = (
                    (newPos > oldPos ? oldPos : oldPos-board.fieldSize) <= startingPos
                 && (newPos > oldPos ? newPos : newPos+ board.fieldSize) > startingPos);
            if (!piece.goIntoHouse || numFields <= 0) {
                goIntoHouse = false;
            }
            // now handle movement
            if (goIntoHouse) {
                // piece walks into its house
                newPos = Math.floorMod(newPos, board.fieldSize);
                newPos -= (startingPos + 1);
                animateMovementIntoHouse(pieceIndex, newPos);
                piece.moveToHouse(newPos);
            } else {
                // piece simply walks in the fields
                piece.moveTo(newPos);
                piece.animateField(fieldCoordsFromTo(oldPos, newPos));
                piece.goIntoHouse = true;
                // kick pieces here
                for (Piece other : pieces) {
                    if (other == piece) {continue;}
                    if (other.inField && other.position == newPos) {
                        other.animateField(new int[][] {
                                board.fieldCoordinates[other.position],
                                board.fieldCoordinates[other.position],
                                board.fieldCoordinates[other.position],
                                getBenchCoordinate(other.player),
                                new int[]{0,0}});
                        other.getKicked();
                    }
                }
            }
        } else if (piece.inHouse) {
            if (newPos < 0 || newPos >= houseBoard.numHouses) {
                throw new AssertionError("Should be a problem of server logic (without wanting to accuse others :-)). The piece shouldn't be able to leave the confines of its house in any direction (i.e. not step out again backwards, and not surpass the number of house fields)");
            }
            piece.moveTo(newPos);
            if (newPos > oldPos && oldPos < board.numHouses) {
                int[][] values = Arrays.copyOfRange(board.houseCoordinates[piece.player], oldPos, Math.min(board.numHouses+1, newPos+1));
                values[values.length-1] = new int[]{0,0};
                piece.animateField(values);
            } else if (newPos < oldPos) {
                int[][] values = Arrays.copyOfRange(board.houseCoordinates[piece.player], newPos, Math.min(board.numHouses, oldPos+1));
                Collections.reverse(Arrays.asList(values));
                piece.animateField(values);
            }
            piece.animateHouse(Arrays.copyOfRange(houseBoard.houseCoordinates[piece.player], oldPos, newPos+1));
        } else {
            throw new AssertionError("Should be a problem of server logic (without wanting to accuse others :-)). The piece is neither inField nor inHouse, yet needs to do a non-starter move");
        }
    }

    /*
    Sets this piece in front of his house
     */
    public void startPiece(int pieceIndex) {
        Piece piece = pieces[pieceIndex];
        int newPos = board.startingPosIndices[piece.player];
        piece.moveToField(newPos);
        pieces[pieceIndex].animateField(new int[][] {getBenchCoordinate(piece.player), board.fieldCoordinates[newPos]});
    }
    public void switchPieces(int pieceOne, int pieceTwo){
        int posOne = pieces[pieceOne].position;
        int posTwo = pieces[pieceTwo].position;
        pieces[pieceOne].moveTo(posTwo);
        pieces[pieceTwo].moveTo(posOne);
        pieces[pieceOne].animateField(new int[][]{board.fieldCoordinates[posOne], board.fieldCoordinates[posTwo]});
        pieces[pieceTwo].animateField(new int[][]{board.fieldCoordinates[posTwo], board.fieldCoordinates[posOne]});
    }

    /* piece walks forward until reaching otherPiece, halting directly behind it */
    public void magnet(int piece, int otherPiece) {
        pieces[piece].position = Math.floorMod((pieces[otherPiece].position - 1), board.fieldSize);
    }

    /*
    Returns whether there is a piece in any house at the given index.
    Useful for drawing the house popup that doesn't show spots without pieces in any house.
    */
    public boolean pieceInAnyHouse(int index) {
        for (Piece piece : pieces) {
            if (piece.position == index && piece.inHouse) {
                return true;
            }
        }
        return false;
    }

    /*
    for a given player (index), returns the number of pieces that are on the bench
     */
    public int numPiecesOnBench(int player) {
        int counter = board.numPieces;
        for (Piece piece : pieces) {
            if (piece.player == player && (piece.inHouse || piece.inField)) {
                counter--;
            }
        }
        return counter;
    }
    /*
    returns an integer array with all the coordinates of fields between indices from and to (including both end points)
    e.g. fieldCoordsFromTo(3, 5)  -> {coordinate of field 3, coordinate of field 4, coordinate of field 5}
    */
    private int[][] fieldCoordsFromTo(int from, int to) {
        if (from <= to) {
            return Arrays.copyOfRange(board.fieldCoordinates, from, to+1);
        } else {
            int[][] coordinates = new int[to+1-from+board.fieldSize][2];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = board.fieldCoordinates[Math.floorMod((from+i), board.fieldSize)];
            }
            return coordinates;
        }
    }
    /*
    If the piece gets kicked or starts, it flies over the screen.
    For this, an approximate bench coordinate is needed
     */
    private int[] getBenchCoordinate(int player) {
        int y = (int) (board.height * (player+0.5) / board.numPlayers);
        return new int[] {0, y};
    }
    /* only for applying the animation to the piece walking around the corner */
    private void animateMovementIntoHouse(int pieceIndex, int positionInHouse) {
        Piece piece = pieces[pieceIndex];
        int oldPos = piece.position;
        int startingPos = board.startingPosIndices[piece.player];
        int[][] values = new int[Math.floorMod((startingPos - oldPos),  board.fieldSize) + positionInHouse + 2][3];
        boolean fieldWalking = true;
        int pos = oldPos;
        for (int i = 0; i < values.length; i++) {
            if (fieldWalking) {
                values[i] = board.fieldCoordinates[pos];
                if (pos == startingPos) {
                    fieldWalking = false; // transition into house
                    pos = -1;
                }
            } else {
                if (pos >= board.numHouses) {
                    values[i] = new int[]{0,0}; // invisible
                } else {
                    values[i] = board.houseCoordinates[piece.player][pos];
                }
            }
            pos++;
        }
        piece.animateField(values);
        piece.animateHouse(Arrays.copyOfRange(houseBoard.houseCoordinates[piece.player], 0, positionInHouse + 1));
    }
}
