package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestTournamentInfoDto extends Dto {
    public final int type = TypeMenue.requestTournamentInfo.ordinal() + 100;
    private int clientId;
    private int tournamentId;

    public RequestTournamentInfoDto(int clientId,int tournamentId){
        this.clientId = clientId;
        this.tournamentId = tournamentId;
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

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
}

