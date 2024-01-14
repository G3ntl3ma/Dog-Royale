package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class JoinGameAsObserverDto extends Dto {
    public final int type = TypeMenue.joinGameAsObserver.ordinal() + 100;
    private int gameId;
    private int clientId;

    public JoinGameAsObserverDto(int gameId,int clientId){
        this.gameId = gameId;
        this.clientId = clientId;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
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

}

