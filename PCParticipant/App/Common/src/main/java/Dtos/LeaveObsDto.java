package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class LeaveObsDto extends Dto {

    public LeaveObsDto() {
        super(TypeGame.leaveObs.ordinal() + 200);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
