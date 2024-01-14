package views;

import java.util.Arrays;

public class PieceHandler {
    public final int[] piecePositions;
    public final boolean[] pieceInHouse;
    public final boolean[] pieceInField;
    public int[] whosePiece;
    private final Board board;
    private final HouseBoard houseBoard;
    public final int numTotalPieces;
    public final int[] numPiecesInField;
    public PieceHandler(Board board, HouseBoard houseBoard) {
        this.board = board;
        this.houseBoard = houseBoard;
        numTotalPieces = board.numPieces * board.numPlayers;
        piecePositions = new int[numTotalPieces];
        pieceInHouse = new boolean[numTotalPieces];
        pieceInField = new boolean[numTotalPieces];
        whosePiece = new int[numTotalPieces];
        numPiecesInField = new int[board.numPlayers];
        for(int i=0; i<board.numPlayers;i++){
            numPiecesInField[i] = board.numPieces;
        }
    }

    public void movePieceIntoHouse(int pieceIndex, int positionInHouse) {
        // legends say the positionInHouse reads 0 if the piece is not in the house,
        // and if it is >0, it represents the index (starting at 1) from the last house place...
        // I'm not gonna question it, only implement it

        // yooooooo, wtf?!

        if (positionInHouse <= 0) {
            return;
        }
        piecePositions[pieceIndex] = houseBoard.numHouses - positionInHouse;
        pieceInHouse[pieceIndex] = true;
        pieceInField[pieceIndex] = false;
        assertWellDefinedPosition();
    }

    public void movePiece(int numFields, int pieceIndex, boolean isStarter) {
        if(!isStarter){
            int length = (pieceInField[pieceIndex]) ? board.fieldSize : houseBoard.numHouses;
            int pos = piecePositions[pieceIndex];
            piecePositions[pieceIndex] = (pos + numFields) % length;
        }
        else {
            // piece is set to starting position
            int player = whosePiece[pieceIndex];
            piecePositions[pieceIndex] = board.startingPosIndices[player];
            pieceInField[pieceIndex] = true;
            numPiecesInField[player] = numPiecesInField[player] - 1;
            System.out.println(Arrays.toString(numPiecesInField));
        }
        assertWellDefinedPosition();
    }

    public void switchPieces(int pieceOne, int pieceTwo){
        int posOne = piecePositions[pieceOne];
        int posTwo = piecePositions[pieceTwo];
        piecePositions[pieceOne] = posTwo;
        piecePositions[pieceTwo] = posOne;
        assertWellDefinedPosition(); // just in case. Better early than never
    }

    /*
    public void magnet(int piece, int opponentPiece) {
        piecePositions[piece] = (piecePositions[opponentPiece] + 1) % board.fieldSize;
        assertWellDefinedPosition();
    }*/

    /*
    Returns whether there is a piece in any house at the given index.
    Useful for drawing the house popup that doesn't show spots without pieces in any house.
     */
    public boolean pieceInAnyHouse(int index) {
        boolean answer = false;
        for (int p = 0; p < board.numPlayers; p++) {
            for (int h = 0; h < board.numPieces; h++) {
                if (piecePositions[h] == index && pieceInHouse[h]) {
                    answer = true;
                }
            }
        }
        return answer;
    }

    private void assertWellDefinedPosition() throws AssertionError {
        // errors should never occur. However, that is not a reason not to do assertions
        // well-spoken words

        // no piece can be in house and in field at the same time
        for (int piece = 0; piece < numTotalPieces; piece++) {
            if (pieceInField[piece] && pieceInHouse[piece]) {
                throw new AssertionError("piece " + Integer.toString(piece) + " is in field and in house at the same time");
            }
        }
        // makes sure that no piece is in multiple positions
        boolean[] fieldsHavingPiece = new boolean[board.fieldSize];
        boolean[] housesHavingPiece = new boolean[houseBoard.numHouses];
        for (int piece = 0; piece < piecePositions.length; piece++) {
            if (pieceInField[piece]) {
                if (fieldsHavingPiece[piecePositions[piece]]) {
                    throw new AssertionError("piece " + Integer.toString(piece) + " shares its position in the field with another piece");
                }
                fieldsHavingPiece[piecePositions[piece]] = true;
            } else if (pieceInHouse[piece]) {
                if (housesHavingPiece[piecePositions[piece]]) {
                    throw new AssertionError("piece " + Integer.toString(piece) + " shares its position in the house with another piece");
                }
                housesHavingPiece[piecePositions[piece]] = true;
            }

        }
    }

}
