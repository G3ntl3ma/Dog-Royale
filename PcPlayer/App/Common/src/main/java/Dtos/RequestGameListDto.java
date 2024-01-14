package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestGameListDto extends Dto {
    public final int type = TypeMenue.requestGameList.ordinal() + 100;
    private int clientId;
    private int gameCountStarting;
    private int gameCountInProgress;
    private int gameCountFinished;

    public RequestGameListDto(int id, int starting, int progress, int finished){
        this.clientId = id;
        this.gameCountStarting = starting;
        this.gameCountInProgress = progress;
        this.gameCountFinished = finished;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getGameCountStarting() {
        return gameCountStarting;
    }

    public void setGameCountStarting(int gameCountStarting) {
        this.gameCountStarting = gameCountStarting;
    }

    public int getGameCountInProgress() {
        return gameCountInProgress;
    }

    public void setGameCountInProgress(int gameCountInProgress) {
        this.gameCountInProgress = gameCountInProgress;
    }

    public int getGameCountFinished() {
        return gameCountFinished;
    }

    public void setGameCountFinished(int gameCountFinished) {
        this.gameCountFinished = gameCountFinished;
    }
}

