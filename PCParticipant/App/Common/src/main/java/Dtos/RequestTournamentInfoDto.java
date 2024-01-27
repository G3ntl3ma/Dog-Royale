package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestTournamentInfoDto extends Dto {
    private int clientId;
    private int tournamentId;

    public RequestTournamentInfoDto(int clientId,int tournamentId){
        super(TypeMenue.requestTournamentInfo.ordinal() + 100);
        this.clientId = clientId;
        this.tournamentId = tournamentId;
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

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
}

