package Dtos.CustomClasses;

public abstract class Games {
    // abstarct Class Games, wich includes attributes and functions, that all inheritors have in common
    protected int gameId;
    protected String gameName = "Default GameName";
    public Games(int gameId, String gameName) {
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }
}
