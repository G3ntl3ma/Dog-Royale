package Dtos;

import Dtos.CustomClasses.TournamentInfo;
import Enums.TypeMenue;
import com.google.gson.Gson;

public class ReturnTournamentInfoDto extends Dto  {
    private TournamentInfo tournamentInfo;

    public ReturnTournamentInfoDto(TournamentInfo tournamentInfo) {
        super(TypeMenue.returnTournamentInfo.ordinal() + 100);
        this.tournamentInfo = tournamentInfo;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public TournamentInfo getTournamentInfo() {
        return tournamentInfo;
    }

    public void setTournamentInfo(TournamentInfo tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
    }
}