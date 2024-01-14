package Dtos;

import Dtos.CustomClasses.TournamentFinished;
import Dtos.CustomClasses.TournamentProgressing;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ReturnFindTournamentDto extends Dto {
    public final int type = TypeMenue.returnFindTournament.ordinal() + 100;
    private int clientId;
    private ArrayList<TournamentProgressing> tournamentStarting;
    private ArrayList<TournamentProgressing> tournamentInProgress;
    private ArrayList<TournamentFinished> tournamentFinished;

    public ReturnFindTournamentDto(int id, ArrayList<TournamentProgressing> tournamentStarting,
    ArrayList<TournamentProgressing> tournamentInProgress, ArrayList<TournamentFinished> tournamentFinished) {
        this.clientId = id;
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

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public ArrayList<TournamentProgressing> getTournamentStarting() {
        return tournamentStarting;
    }

    public void setTournamentStarting(ArrayList<TournamentProgressing> tournamentStarting) {
        this.tournamentStarting = tournamentStarting;
    }

    public ArrayList<TournamentProgressing> getTournamentInProgress() {
        return tournamentInProgress;
    }

    public void setTournamentInProgress(ArrayList<TournamentProgressing> tournamentInProgress) {
        this.tournamentInProgress = tournamentInProgress;
    }

    public ArrayList<TournamentFinished> getTournamentFinished() {
        return tournamentFinished;
    }

    public void setTournamentFinished(ArrayList<TournamentFinished> tournamentFinished) {
        this.tournamentFinished = tournamentFinished;
    }
}