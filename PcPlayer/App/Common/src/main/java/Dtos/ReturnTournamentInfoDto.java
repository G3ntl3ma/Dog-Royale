package Dtos;

import Dtos.CustomClasses.TournamentInfo;
import Enums.TypeMenue;
import com.google.gson.Gson;

public class ReturnTournamentInfoDto extends Dto  {
    public final int type = TypeMenue.returnTournamentInfo.ordinal() + 100;
    private TournamentInfo tournamentInfo;

    public ReturnTournamentInfoDto(TournamentInfo tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public TournamentInfo getTournamentInfo() {
        return tournamentInfo;
    }

    public void setTournamentInfo(TournamentInfo tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
    }
}