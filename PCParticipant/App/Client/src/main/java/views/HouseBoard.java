package views;

import java.util.Arrays;
import java.util.Comparator;

public class HouseBoard {
    public final int numPlayers;
    public final int numHouses;
    public int width;
    public int height;
    public int verticalSpacing = 55;
    public int horizontalSpacing = 30;
    public final int[][][] houseCoordinates;// = new int[numPlayers][numHouses];
    public final boolean[] showHouse; // if no piece is there, showHouse has many false
    public int numShownHouses = 20;
    public final boolean[] isEllipsis;
    public HouseBoard(int numPlayers, int numHouses) {
        this.numPlayers = numPlayers;
        this.numHouses = numHouses;
        houseCoordinates = new int[numPlayers][numHouses][2];
        showHouse = new boolean[numHouses];
        Arrays.fill(showHouse, true);
        isEllipsis = new boolean[numHouses];
        calculateHouseCoordinates();
    }
    private void calculateHouseCoordinates() {
        int y = 0;
        for (int i = 0; i < numPlayers; i++) { // iterate player
            int x = 0;
            y += verticalSpacing;
            for (int j = 0; j < numHouses; j++) { // iterate houses
                if (showHouse[j]) {
                    x += horizontalSpacing;
                } else if (showHouse[j-1]) {
                    x += horizontalSpacing;
                    isEllipsis[j] = true;
                }
                houseCoordinates[i][j][0] = x;
                houseCoordinates[i][j][1] = y;
            }
        }
        width = horizontalSpacing * (numHouses + 1);
        height = verticalSpacing * (numPlayers + 1);
    }
    public void calculateHouseCoordinates(PieceHandler pieceHandler) {
        Arrays.fill(showHouse, true);
        Arrays.fill(isEllipsis, false);
        // determine the forlornly empty spaces to cut out
        int[][] slices = new int[numHouses][2];
        int[] index = new int[] {0, 0};
        for (int h = 0; h < numHouses; h++) {
            if (h == 0 || h == numHouses-1) {
                continue;
            }
            boolean hasPiece = pieceHandler.pieceInAnyHouse(h);
            if (index[1] == 0) {
                if (!hasPiece && (h % 5 == 0 || h == 1)) {
                    slices[index[0]][index[1]] = h;
                    index[1] = 1;
                }
            } else {
                if (hasPiece) {
                    slices[index[0]][index[1]] = Math.floorDiv(h+1, 5) * 5-1;
                    index[1] = 0;
                    index[0] += 1;
                }
            }
        }
        if (index[1] == 1) { // close the last slice
            slices[index[0]][index[1]] = numHouses-1;
        }
        // Sort the slices in length
        Arrays.sort(slices, Comparator.comparingInt(s -> (s[1] - s[0])));
        // use the longest until number of houses is acceptable
        int shownHouses = numHouses;
        for (int i = numHouses-1; i >= 0; i--) {
            int[] slice = slices[i];
            if (shownHouses < numShownHouses) {
                break; // it suffices to remove until we reach numShownHouses
            }
            shownHouses++; // for the ellipsis
            for (int j = slice[1]-1; j >= slice[0]; j--) {
                showHouse[j] = false;
                shownHouses--;
                if (shownHouses < numShownHouses) {
                    break; // breaks out of the entire (outer) for loop after the if statement above
                }
            }
        }
        // now calculate
        calculateHouseCoordinates();
    }
}