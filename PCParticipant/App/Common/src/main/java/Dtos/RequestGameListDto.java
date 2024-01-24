package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestGameListDto extends Dto {
    public final int type = TypeMenue.requestGameList.ordinal() + 100;
    private int clientId;
    private int gamesUpcomingCount;
    private int gamesRunningCount;
    private int gamesFinishedCount;

    public RequestGameListDto(int id, int starting, int progress, int finished){
        this.clientId = id;
        this.gamesUpcomingCount = starting;
        this.gamesRunningCount = progress;
        this.gamesFinishedCount = finished;
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

    public int getGamesUpcomingCount() {
        return gamesUpcomingCount;
    }

    public void setGamesUpcomingCount(int gamesUpcomingCount) {
        this.gamesUpcomingCount = gamesUpcomingCount;
    }

    public int getGamesRunningCount() {
        return gamesRunningCount;
    }

    public void setGamesRunningCount(int gamesRunningCount) {
        this.gamesRunningCount = gamesRunningCount;
    }

    public int getGamesFinishedCount() {
        return gamesFinishedCount;
    }

    public void setGamesFinishedCount(int gamesFinishedCount) {
        this.gamesFinishedCount = gamesFinishedCount;
    }
}

