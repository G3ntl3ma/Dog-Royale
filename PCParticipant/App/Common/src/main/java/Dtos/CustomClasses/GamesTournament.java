package Dtos.CustomClasses;

public abstract class GamesTournament {
    // abstarct Class GamesTournament, wich includes attributes and functions, that all inheritors have in common
    protected int gameId;

    public GamesTournament(int gameId) {
        this.gameId = gameId;

    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }


}
