package Dtos;

import Dtos.CustomClasses.TournamentsFinished;
import Dtos.CustomClasses.TournamentsUpcoming;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ReturnTournamentListDto extends Dto {
    private int clientId;
    private ArrayList<TournamentsUpcoming> tournamentsUpcoming;
    private ArrayList<TournamentsUpcoming> tournamentsRunning;
    private ArrayList<TournamentsFinished> tournamentsFinished;

    public ReturnTournamentListDto(int id, ArrayList<TournamentsUpcoming> tournamentsUpcoming,
                                   ArrayList<TournamentsUpcoming> tournamentsRunning, ArrayList<TournamentsFinished> tournamentsFinished) {
        super(TypeMenue.returnTournamentList.ordinal() + 100);
        this.clientId = id;
        this.tournamentsUpcoming = tournamentsUpcoming;
        this.tournamentsRunning = tournamentsRunning;
        this.tournamentsFinished = tournamentsFinished;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public ArrayList<TournamentsUpcoming> getTournamentsUpcoming() {
        return tournamentsUpcoming;
    }

    public void setTournamentsUpcoming(ArrayList<TournamentsUpcoming> tournamentsUpcoming) {
        this.tournamentsUpcoming = tournamentsUpcoming;
    }

    public ArrayList<TournamentsUpcoming> getTournamentsRunning() {
        return tournamentsRunning;
    }

    public void setTournamentsRunning(ArrayList<TournamentsUpcoming> tournamentsRunning) {
        this.tournamentsRunning = tournamentsRunning;
    }

    public ArrayList<TournamentsFinished> getTournamentsFinished() {
        return tournamentsFinished;
    }

    public void setTournamentsFinished(ArrayList<TournamentsFinished> tournamentsFinished) {
        this.tournamentsFinished = tournamentsFinished;
    }
}