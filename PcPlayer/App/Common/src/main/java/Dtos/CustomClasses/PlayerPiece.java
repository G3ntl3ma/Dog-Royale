package Dtos.CustomClasses;

public class PlayerPiece {
    // Class for player pieces in BoardStateDto
    private int pieceId;
    private int clientId;
    private int position;
    private boolean isOnBench;
    private int inHousePosition;

    public PlayerPiece(int pieceId, int clientId, int position, boolean isOnBench, int inHousePosition) {
        this.pieceId = pieceId;
        this.clientId = clientId;
        this.position = position;
        this.isOnBench = isOnBench;
        this.inHousePosition = inHousePosition;
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isOnBench() {
        return isOnBench;
    }

    public void setOnBench(boolean onBench) {
        isOnBench = onBench;
    }

    public int getInHousePosition() {
        return inHousePosition;
    }

    public void setInHousePosition(int inHousePosition) {
        this.inHousePosition = inHousePosition;
    }
}
