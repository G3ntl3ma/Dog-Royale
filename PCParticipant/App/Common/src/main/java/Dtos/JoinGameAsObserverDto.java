package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class JoinGameAsObserverDto extends Dto {
    private int gameId;
    private int clientId;
    private String playerName;

    public JoinGameAsObserverDto(int gameId,int clientId){
        super(TypeMenue.joinGameAsObserver.ordinal() + 100);
        this.gameId = gameId;
        this.clientId = clientId;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

}

