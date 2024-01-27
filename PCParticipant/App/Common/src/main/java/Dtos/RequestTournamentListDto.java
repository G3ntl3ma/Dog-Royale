package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestTournamentListDto extends Dto {
    private int clientId;
    private int tournamentsUpcomingCount;
    private int tournamentsRunningCount;
    private int tournamentsFinishedCount;

    public RequestTournamentListDto(int clientId, int tournamentsUpcomingCount, int tournamentsRunningCount, int tournamentsFinishedCount){
        super(TypeMenue.requestTournamentList.ordinal() + 100);
        this.clientId = clientId;
        this.tournamentsUpcomingCount = tournamentsUpcomingCount;
        this.tournamentsRunningCount = tournamentsRunningCount;
        this.tournamentsFinishedCount = tournamentsFinishedCount;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public int getTournamentsUpcomingCount() {
        return tournamentsUpcomingCount;
    }

    public void setTournamentsUpcomingCount(int tournamentsUpcomingCount) {
        this.tournamentsUpcomingCount = tournamentsUpcomingCount;
    }

    public int getTournamentsRunningCount() {
        return tournamentsRunningCount;
    }

    public void setTournamentsRunningCount(int tournamentsRunningCount) {
        this.tournamentsRunningCount = tournamentsRunningCount;
    }

    public int getTournamentsFinishedCount() {
        return tournamentsFinishedCount;
    }

    public void setTournamentsFinishedCount(int tournamentsFinishedCount) {
        this.tournamentsFinishedCount = tournamentsFinishedCount;
    }
}

