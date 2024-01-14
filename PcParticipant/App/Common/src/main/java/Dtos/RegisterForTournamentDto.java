package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RegisterForTournamentDto extends Dto {
    public final int type = TypeMenue.registerForTournament.ordinal() + 100;
    private int tournamentID;
    private int clientId;

    public RegisterForTournamentDto(int tournamentID, int clientId){
        this.tournamentID = tournamentID;
        this.clientId = clientId;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

}

