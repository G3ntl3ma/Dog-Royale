package Dtos.CustomClasses;

public abstract class Games {
    // abstarct Class Games, wich includes attributes and functions, that all inheritors have in common
    protected int gameId;

    public Games(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
