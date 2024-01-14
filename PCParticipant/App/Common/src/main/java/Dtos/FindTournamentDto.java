package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class FindTournamentDto extends Dto {
    public final int type = TypeMenue.findTournament.ordinal() + 100;
    private int clientId;
    private int tournamentStarting;
    private int tournamentInProgress;
    private int tournamentFinished;

    public FindTournamentDto(int clientId,int tournamentStarting,int tournamentInProgress, int tournamentFinished){
        this.clientId = clientId;
        this.tournamentStarting = tournamentStarting;
        this.tournamentInProgress = tournamentInProgress;
        this.tournamentFinished = tournamentFinished;
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

    public void setClientId(int id) {
        this.clientId = id;
    }

    public int getTournamentStarting() {
        return tournamentStarting;
    }

    public void setTournamentStarting(int tournamentStarting) {
        this.tournamentStarting = tournamentStarting;
    }

    public int getTournamentInProgress() {
        return tournamentInProgress;
    }

    public void setTournamentInProgress(int tournamentInProgress) {
        this.tournamentInProgress = tournamentInProgress;
    }

    public int getTournamentFinished() {
        return tournamentFinished;
    }

    public void setTournamentFinished(int tournamentFinished) {
        this.tournamentFinished = tournamentFinished;
    }
}

